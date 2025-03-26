package com.obast.charer.plugins.ykc.tcp.server;

import com.obast.charer.common.api.IMessage;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.common.plugin.core.thing.DeviceState;
import com.obast.charer.common.plugin.core.thing.charger.ChargerDirectiveEnum;
import com.obast.charer.common.plugin.core.thing.charger.IChargerService;
import com.obast.charer.common.plugin.core.thing.charger.actions.*;
import com.obast.charer.common.utils.HexUtil;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.plugins.ykc.tcp.cilent.YkcTcpClient;
import com.obast.charer.plugins.ykc.tcp.conf.YkcTcpConfig;
import com.obast.charer.plugins.ykc.tcp.parser.DataDecoder;
import com.obast.charer.plugins.ykc.tcp.parser.DataEncoder;
import com.obast.charer.plugins.ykc.tcp.parser.DataPackage;
import com.obast.charer.plugins.ykc.tcp.parser.DataReader;
import cn.hutool.core.util.IdUtil;
import com.gitee.starblues.bootstrap.annotation.AutowiredType;
import com.gitee.starblues.core.PluginInfo;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Service
@Component
@Data
public class YkcTcpVerticle extends AbstractVerticle {

    @Autowired
    private YkcTcpConfig config;

    private final Map<String, YkcTcpClient> clientMap = new ConcurrentHashMap<>();

    private final Map<String, String> dnToPk = new HashMap<>();

    private final Map<String, Long> heartbeatDevice = new HashMap<>();

    @Setter
    private long keepAliveTimeout = Duration.ofSeconds(150).toMillis();

    private NetServer netServer;

    @Autowired
    private PluginInfo pluginInfo;

    @Autowired
    @AutowiredType(AutowiredType.Type.MAIN_PLUGIN)
    private IChargerService chargerService;

    @Override
    public void stop() {
        if (netServer != null) {
            netServer.close(rst -> {
                log.info("tcp server close:{}", rst.succeeded());
            });
        }
        log.info("tcp server stopped");
    }

    /**
     * 创建配置文件
     */
    @PostConstruct
    public void initConfig() {

        Executors.newSingleThreadScheduledExecutor().schedule(this::initTcpServer, 3, TimeUnit.SECONDS);
    }

    /**
     * 初始TCP服务
     */
    private void initTcpServer() {
        netServer = vertx.createNetServer(
                new NetServerOptions().setHost(config.getHost())
                        .setPort(config.getPort())
                        .setSendBufferSize(8192)
                        .setReceiveBufferSize(8192)
        );
        netServer.connectHandler(this::acceptTcpConnection);
        netServer.listen(config.createSocketAddress(), result -> {
            if (result.succeeded()) {
                log.info("tcp server startup on {}: {}", config.getHost(),result.result().actualPort());
            } else {
                log.error(result.cause().getLocalizedMessage());
            }
        });
    }

    public void sendMsg(String addr, Buffer msg) {
        YkcTcpClient tcpClient = clientMap.get(addr);
        if (tcpClient != null) {
            tcpClient.sendMessage(msg);
        }
    }

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.SECONDS)
    private void offlineCheckTask() {

        Set<String> clients = new HashSet<>(clientMap.keySet());
        for (String key : clients) {
            YkcTcpClient client = clientMap.get(key);
            if (!client.isOnline()) {
                client.shutdown();
            }
        }
        heartbeatDevice.keySet().iterator().forEachRemaining(addr -> {
            Long time = heartbeatDevice.get(addr);
            //心跳超时，判定为离线
            if (System.currentTimeMillis() - time > keepAliveTimeout * 2) {
                heartbeatDevice.remove(addr);

                log.info("[设备调试]客户端长时间未收到心跳，断开连接 [{}] ", addr);

                //离线上报
                chargerService.post(pluginInfo.getPluginId(), StateChangeEventAction.builder()
                        .id(IdUtil.simpleUUID())
                        .dn(addr)
                        .state(DeviceState.OFFLINE)
                        .time(System.currentTimeMillis())
                        .build());
            }
        });
    }

    /**
     * TCP连接处理逻辑
     *
     * @param socket socket
     */
    protected void acceptTcpConnection(NetSocket socket) {
        // 客户端连接处理
        String clientId = IdUtil.simpleUUID() + "_" + socket.remoteAddress();
        YkcTcpClient client = new YkcTcpClient(clientId);
        client.setKeepAliveTimeoutMs(keepAliveTimeout);

        try {
            // TCP异常和关闭处理
            socket.exceptionHandler(Throwable::printStackTrace).closeHandler(nil -> {
                log.warn("[设备调试]客户端异常关闭 [{}]", socket.remoteAddress());
                client.shutdown();
            });

            log.info("[设备调试]客户端连接成功 {} [{}] ", clientId, socket.remoteAddress());
            // 这个地方是在TCP服务初始化的时候设置的 parserSupplier
            client.setKeepAliveTimeoutMs(keepAliveTimeout);
            client.setSocket(socket);

            Handler<Buffer> handler = new Handler<>() {
                @Override
                public void handle(Buffer buffer) {
                    try {
                        DataPackage data = DataDecoder.unpack(buffer);
                        short code = data.getCode();

                        log.debug("[设备调试]解包后的数据[{}] ", JsonUtils.toJsonString(data));

                        if(code == DataPackage.LOGIN_EVENT) {
                            login(client, clientId, socket, data );
                            return;
                        }

//                    //未注册断开连接
//                    if (!clientMap.containsKey(data.getDeviceDn())) {
//                        log.info("[设备调试]收到未注册设备消息，断开连接 {} [{}] ", clientId, socket.remoteAddress());
//                        socket.close();
//                    }

                        switch (code) {
                            //心跳
                            case DataPackage.PING_EVENT:
                                ping(client, socket, data );
                                break;

                            //计价模型验证
                            case DataPackage.PRICE_VERIFY_EVENT:
                                priceVerify(client, socket, data );
                                break;

                            //计价模型请求
                            case DataPackage.PRICE_REQUEST_EVENT:
                                priceRequest(client, socket, data );
                                break;

                            //上传实时监测数据
                            case DataPackage.DATA_REPORT_EVENT:
                                dataReport(client, socket, data );
                                break;

                            //交易记录上报
                            case DataPackage.ORDER_REPORT_EVENT:
                                orderReport(data );
                                break;

                            //远程启动充电命令回复
                            case DataPackage.START_CHARGE_REPLY:
                                startChargeReply(client, socket, data );
                                break;

                            //远程停机命令回复
                            case DataPackage.STOP_CHARGE_REPLY:
                                stopChargeReply(client, socket, data );
                                break;

                            //余额更新应答
                            case DataPackage.BALANCE_UPDATE_REPLY:
                                balanceUpdateReply(client, socket, data );
                                break;

                            //充电桩工作参数设置应答
                            case DataPackage.PARAM_CONFIG_REPLY:
                                paramConfigReply(client, socket, data );
                                break;

                            //对时设置应答
                            case DataPackage.TIMEING_CONFIG_REPLY:
                                timeConfigReply(client, socket, data );
                                break;

                            //计费模型设置应答
                            case DataPackage.PRICE_CONFIG_REPLY:
                                priceConfigReply(client, socket, data );
                                break;

                            //远程重起应答
                            case DataPackage.REBOOT_REPLY:
                                rebootReply(client, socket, data );
                                break;

                            //下发二维码应答
                            case DataPackage.QRCODE_REPLY:
                                qrcodeReply(client, socket, data );
                                break;

                            default:
                                log.error("[设备调试]收到设备未知消息 {} [{}] ", code, Hex.encodeHexString(data.getPack(), false));
                                break;
                        }

                    } catch (Exception e) {
                        log.error("[设备调试]解析数据包异常, pack={}, msg={}", Hex.encodeHexString(buffer.getBytes()), e.getLocalizedMessage());
                    }

                }
            };

            client.setHandler(handler);

            //client.setParser(parser);
        } catch (Exception e) {
            log.error("[设备调试]发生异常，服务器断开连接 {} [{}] ", clientId, socket.remoteAddress());
            client.shutdown();
        }
    }

    //设备登陆
    private void login(YkcTcpClient client, String clientId, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]登陆请求 [{}] ", Hex.encodeHexString(data.getPack(),false));
        String deviceDn = data.getDeviceDn();
        String pk = Arrays.toString(data.getPayload());
        String pack = Hex.encodeHexString(data.getPack());
        short code = data.getCode();
        short serial = data.getSerial();
        IMessage message = DataDecoder.decode(code, data.getPayload());

        clientMap.put(deviceDn, client);
        heartbeatDevice.remove(deviceDn);

        ActionResult<?> rst = chargerService.post(pluginInfo.getPluginId(), LoginEventAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(Hex.encodeHexString(data.getPack()))
                .data(message)
                .time(System.currentTimeMillis())
                .build());

        //登陆成功
        if(rst.getCode() == 0) {
            log.info("[设备调试]登陆成功 {} [{}] ", clientId, socket.remoteAddress());
            //设备上线
            online(deviceDn, pack);

            dnToPk.put(deviceDn, pk);

            DataPackage dataPackage = DataEncoder.builder(deviceDn, serial, ChargerDirectiveEnum.LOGIN_ACK, rst.getData() );
            Buffer buffer = DataEncoder.encode(dataPackage);
            //log.info("[设备调试]登陆回复 {} ", Hex.encodeHexString(buffer.getBytes(), false));
            sendMsg(deviceDn, buffer);

            //登陆成功后通知
            chargerService.notify(pluginInfo.getPluginId(), LoginAckAction.builder()
                    .id(IdUtil.simpleUUID())
                    .dn(deviceDn)
                    .serial(serial)
                    .pack(Hex.encodeHexString(buffer.getBytes()))
                    .data(rst.getData())
                    .time(System.currentTimeMillis())
                    .build());

            return;
        }

        log.info("[设备调试]登陆失败 {} [{}] ", clientId, socket.remoteAddress());
    }

    //心跳
    private void ping(YkcTcpClient client, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]心跳请求 [{}] ", Hex.encodeHexString(data.getPack(), false));
        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        IMessage message = DataDecoder.decode(code, data.getPayload());

        online(deviceDn, Hex.encodeHexString(data.getPack()));

        ActionResult<?> rst = chargerService.post(pluginInfo.getPluginId(), PingEventAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(Hex.encodeHexString(data.getPack()))
                .data(message)
                .time(System.currentTimeMillis())
                .build());

        if(rst.getCode() == 0) {
            DataPackage dataPackage = DataEncoder.builder(deviceDn, serial, ChargerDirectiveEnum.PING_ACK, rst.getData() );
            Buffer buffer = DataEncoder.encode(dataPackage);
            //log.info("[设备调试]心跳回复 [{}] ", Hex.encodeHexString(buffer.getBytes(), false));
            sendMsg(deviceDn, buffer);
        }
    }

    //计费模型验证
    private void priceVerify(YkcTcpClient client, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]计费模型验证请求 [{}] ", Hex.encodeHexString(data.getPack(),false));
        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        String pack = Hex.encodeHexString(data.getPack());
        IMessage message = DataDecoder.decode(code, data.getPayload());

        ActionResult<?> rst = chargerService.post(pluginInfo.getPluginId(), PriceVerifyEventAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(pack)
                .data(message)
                .time(System.currentTimeMillis())
                .build());

        if(rst.getCode() == 0) {
            DataPackage dataPackage = DataEncoder.builder(deviceDn, serial, ChargerDirectiveEnum.PRICE_VERIFY_ACK, rst.getData() );
            Buffer buffer = DataEncoder.encode(dataPackage);
            //log.info("[设备调试]计费模型验证回复 {} ", Hex.encodeHexString(buffer.getBytes(), false));
            sendMsg(deviceDn, buffer);
        }
    }

    //计费模型请求
    private void priceRequest(YkcTcpClient client, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]计费模型请求 [{}] ", Hex.encodeHexString(data.getPack(), false));
        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        String pack = Hex.encodeHexString(data.getPack());
        IMessage message = DataDecoder.decode(code, data.getPayload());

        ActionResult<?> rst = chargerService.post(pluginInfo.getPluginId(), PriceRequestEventAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(pack)
                .data(message)
                .time(System.currentTimeMillis())
                .build());


        if(rst.getCode() == 0) {
            DataPackage dataPackage = DataEncoder.builder(deviceDn, serial, ChargerDirectiveEnum.PRICE_REQUEST_ACK, rst.getData() );
            Buffer buffer = DataEncoder.encode(dataPackage);
            //log.info("[设备调试]计费模型回复 {} ", Hex.encodeHexString(buffer.getBytes(), false));
            sendMsg(deviceDn, buffer);
        }
    }

    //上传实时监测数据
    private void dataReport(YkcTcpClient client, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]上传实时监测数据[{}] ", Hex.encodeHexString(data.getPack(), false));
        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        String pack = Hex.encodeHexString(data.getPack());

        IMessage message = DataDecoder.decode(code, data.getPayload());
        //log.info("[设备调试]上传实时监测数据[{}] ", message);
        chargerService.post(pluginInfo.getPluginId(), DataReportEventAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(pack)
                .data(message)
                .time(System.currentTimeMillis())
                .build());
    }

    //交易记录上报
    private void orderReport(DataPackage data) {
        //log.info("[设备调试]交易记录上报请求 [{}] ", Hex.encodeHexString(data.getPack(),false));

        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        String pack = Hex.encodeHexString(data.getPack());

        IMessage message = DataDecoder.decode(code, data.getPayload());
        //log.info("[设备调试]交易记录上报请求 [{}] ", message);
        ActionResult<?> rst = chargerService.post(pluginInfo.getPluginId(), OrderReportEventAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .data(message)
                .pack(pack)
                .time(System.currentTimeMillis())
                .build());

        if(rst.getCode() == 0) {
            DataPackage dataPackage = DataEncoder.builder(deviceDn, serial, ChargerDirectiveEnum.ORDER_REPORT_ACK, rst.getData() );
            Buffer buffer = DataEncoder.encode(dataPackage);
            sendMsg(deviceDn, buffer);
            log.info("[设备调试]交易记录上报回复 [{}]", Hex.encodeHexString(buffer.getBytes(), false));
        }
    }

    //远程启动充电命令回复
    private void startChargeReply(YkcTcpClient client, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]启动充电指令应答 [{}] ", Hex.encodeHexString(data.getPack(),false));
        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        String pack = Hex.encodeHexString(data.getPack());
        IMessage message = DataDecoder.decode(code, data.getPayload());

        ActionResult<?> rst = chargerService.post(pluginInfo.getPluginId(), StartChargeReplyAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(pack)
                .data(message)
                .time(System.currentTimeMillis())
                .build());

       log.info("[设备调试]远程启动充电命令应答结果: {} ", rst);

    }

    //远程停机命令应答
    private void stopChargeReply(YkcTcpClient client, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]远程停机指令应答 [{}] ", Hex.encodeHexString(data.getPack(),false));

        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        String pack = Hex.encodeHexString(data.getPack());

        IMessage message = DataDecoder.decode(code, data.getPayload());

        ActionResult<?> rst = chargerService.post(pluginInfo.getPluginId(), StopChargeReplyAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(pack)
                .data(message)
                .time(System.currentTimeMillis())
                .build());

        log.info("[设备调试]远程停机指令应答结果: {} ", rst);
    }

    //余额更新应答
    private void balanceUpdateReply(YkcTcpClient client, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]余额更新应答 [{}] ", Hex.encodeHexString(data.getPack(),false));

        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        String pack = Hex.encodeHexString(data.getPack());

        IMessage message = DataDecoder.decode(code, data.getPayload());

        chargerService.post(pluginInfo.getPluginId(), BalanceUpdateReplyAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(pack)
                .data(message)
                .time(System.currentTimeMillis())
                .build());
    }

    //充电桩工作参数设置应答
    private void paramConfigReply(YkcTcpClient client, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]充电桩工作参数设置应答 [{}] ", Hex.encodeHexString(data.getPack(),false));
        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        String pack = Hex.encodeHexString(data.getPack());

        IMessage message = DataDecoder.decode(code, data.getPayload());

        chargerService.post(pluginInfo.getPluginId(), ParamConfigReplyAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(pack)
                .data(message)
                .time(System.currentTimeMillis())
                .build());

    }

    //对时设置应答
    private void timeConfigReply(YkcTcpClient client, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]校时指令应答 [{}] ", Hex.encodeHexString(data.getPack(), false));
        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        String pack = Hex.encodeHexString(data.getPack());

        IMessage message = DataDecoder.decode(code, data.getPayload());

        chargerService.post(pluginInfo.getPluginId(), TimingConfigReplyAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(pack)
                .data(message)
                .time(System.currentTimeMillis())
                .build());

    }

    //计费模型设置应答
    private void priceConfigReply(YkcTcpClient client, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]计费模型设置应答 [{}] ", Hex.encodeHexString(data.getPack(), false));
        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        String pack = Hex.encodeHexString(data.getPack());

        IMessage message = DataDecoder.decode(code, data.getPayload());

        chargerService.post(pluginInfo.getPluginId(), PriceConfigReplyAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(pack)
                .data(message)
                .time(System.currentTimeMillis())
                .build());

    }

    //远程重起应答
    private void rebootReply(YkcTcpClient client, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]远程重起应答 [{}] ", Hex.encodeHexString(data.getPack(), false));
        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        String pack = Hex.encodeHexString(data.getPack());

        IMessage message = DataDecoder.decode(code, data.getPayload());

        chargerService.post(pluginInfo.getPluginId(), RebootReplyAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(pack)
                .data(message)
                .time(System.currentTimeMillis())
                .build());

    }

    //下发二维码应答
    private void qrcodeReply(YkcTcpClient client, NetSocket socket, DataPackage data) {
        //log.info("[设备调试]下发二维码应答 [{}] ", Hex.encodeHexString(data.getPack(), false));
        String deviceDn = data.getDeviceDn();
        short code = data.getCode();
        short serial = data.getSerial();
        String pack = Hex.encodeHexString(data.getPack());

        IMessage message = DataDecoder.decode(code, data.getPayload());

        chargerService.post(pluginInfo.getPluginId(), QrcodeReplyAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .serial(serial)
                .pack(pack)
                .data(message)
                .time(System.currentTimeMillis())
                .build());

    }

    private void online(String deviceDn, String pack) {
        //第一次心跳，上线
        if(!heartbeatDevice.containsKey(deviceDn)) {
            chargerService.post(pluginInfo.getPluginId(), StateChangeEventAction.builder()
                    .id(IdUtil.simpleUUID())
                    .dn(deviceDn)
                    .state(DeviceState.ONLINE)
                    .pack(pack)
                    .time(System.currentTimeMillis())
                    .build());
        }
        heartbeatDevice.put(deviceDn, System.currentTimeMillis());
    }
}
