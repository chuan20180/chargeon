package com.obast.charer.plugins.vzicloud.handler;


import com.obast.charer.common.model.ActionResult;
import com.obast.charer.common.model.DeviceImage;
import com.obast.charer.common.plugin.core.thing.DeviceState;
import com.obast.charer.common.plugin.core.thing.DeviceStatus;
import com.obast.charer.common.plugin.core.thing.ThingDevice;
import com.obast.charer.common.plugin.core.thing.dcam.IDcamService;
import com.obast.charer.common.plugin.core.thing.dcam.actions.DcamCarInEventAction;
import com.obast.charer.common.plugin.core.thing.dcam.actions.DcamCarOutEventAction;
import com.obast.charer.common.plugin.core.thing.dcam.actions.DcamStateEventAction;
import com.obast.charer.common.plugin.core.thing.dcam.message.DcamCarInEventMessage;
import com.obast.charer.common.plugin.core.thing.dcam.message.DcamCarOutEventMessage;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.plugins.vzicloud.handler.message.*;
import com.obast.charer.plugins.vzicloud.service.Downloader;
import com.obast.charer.plugins.vzicloud.config.VziConfig;

import cn.hutool.core.util.IdUtil;
import com.gitee.starblues.core.PluginInfo;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.messages.MqttPublishMessage;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
public class MessageHandler implements Handler<MqttPublishMessage> {

    private MqttClient client;

    private IDcamService dcamService;

    private PluginInfo pluginInfo;

    private VziConfig vziConfig;

    private final Map<String, MqttClient> endpointMap = new HashMap<>();
    private static final Map<String, Boolean> MQTT_CONNECT_POOL = new ConcurrentHashMap<>();


    public MessageHandler(IDcamService dcamService, PluginInfo pluginInfo, VziConfig vziConfig, MqttClient client) {
        this.dcamService = dcamService;
        this.pluginInfo = pluginInfo;
        this.vziConfig = vziConfig;
        this.client = client;
    }

    @SneakyThrows
    @Override
    public void handle(MqttPublishMessage msg) {
        String payload = msg.payload().toString();

        try {
            log.info("[插件调试]收到mqtt消息: client={}, topic={}, payload={}", client.clientId(), msg.topicName(), payload);
            EventMessage eventMessage = JsonUtils.parseObject(payload, EventMessage.class);
            assert eventMessage != null;
            String deviceDn = eventMessage.getSn();

            ThingDevice device = dcamService.getDevice(deviceDn);
            if(device == null) {
                log.error("[插件调试]设备不存在: dn={}, msgType={}", deviceDn, eventMessage.getName());
                return;
            }

            if(!device.getStatus().equals(DeviceStatus.Enabled)) {
                log.error("[插件调试]设备未启用: dn={}, msgType={}", deviceDn, eventMessage.getName());
                return;
            }

            //注册成功
            //保存设备与连接关系
            endpointMap.put(deviceDn, client);
            MQTT_CONNECT_POOL.put(client.clientId(), true);

            String messagePayload = JsonUtils.toJsonString(eventMessage.getPayload());
            switch (eventMessage.getName()) {
                case "online":
                    processOnline(eventMessage.getSn());
                    break;

                case "offline_record":
                    processOfflineRecord(deviceDn, messagePayload);
                    break;

                case "offline":
                    processOffline(deviceDn, messagePayload);
                    break;

                case "serial_data":
                    processSerialData(messagePayload);
                    break;

                case "ivs_result":
                    processIvsResult(deviceDn, messagePayload);
                    break;

                case "gpio_in":
                    processGpioIn(messagePayload);
                    break;

                case "snapshot":
                    processSnapShot(messagePayload);
                    break;

                default:
                    log.info("[插件调试]收到未知mqtt消息: {}", eventMessage.getName());
                    break;
            }
        } catch (Throwable e) {
            log.info("receive msg error", e);
        }
    }

    private void processOnline(String deviceDn) {
        log.info("收到Online消息: deviceDn={}", deviceDn);
        dcamService.post(pluginInfo.getPluginId(), DcamStateEventAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .state(DeviceState.ONLINE)
                .time(System.currentTimeMillis())
                .build());


    }

    private void processOfflineRecord(String deviceDn, String payload) {
        log.info("收到Offline Record消息: deviceDn={}, payload={}", deviceDn, payload);
        dcamService.post(pluginInfo.getPluginId(), DcamStateEventAction.builder()
                .id(IdUtil.simpleUUID())
                .dn(deviceDn)
                .state(DeviceState.ONLINE)
                .time(System.currentTimeMillis())
                .build());
    }

    private void processOffline(String deviceDn, String payload) {
        log.info("收到Offline消息: deviceDn={}, payload={}", deviceDn, payload);

    }


    private void processSerialData(String payload) {
        SerialDataMessage serialDataMessagePayload = JsonUtils.parseObject(payload, SerialDataMessage.class);
        log.info("收到 Serial Data消息: {}", serialDataMessagePayload);
    }

    private void processIvsResult(String deviceDn, String payload) {
        log.info("收到 Ivs result: deviceDn={}, payload={}", deviceDn, payload);
        IvsResultMessage message = JsonUtils.parseObject(payload, IvsResultMessage.class);
        if(message == null) {
            log.error("[插件调试]客户端 ivs 失败 ,decode 消息失败, message为空, message={}", message);
            return;
        }

        IvsResultMessage.ProductH productH = message.getProductH();
        if(productH == null) {
            log.error("[插件调试]客户端 ivs 失败 ,decode 消息失败, productH 为空, message={}", message);
            return;
        }

        IvsResultMessage.Parking parking = productH.getParking();
        if(parking == null) {
            log.error("[插件调试]客户端 ivs 失败 ,decode 消息失败, parking 为空, message={}", message);
            return;
        }

        IvsResultMessage.Plate plate = productH.getPlate();
        if(plate == null) {
            log.error("[插件调试]客户端 ivs 失败 ,decode 消息失败, plate 为空, message={}", message);
            return;
        }

        IvsResultMessage.Reco reco = productH.getReco();
        if(reco == null) {
            log.error("[插件调试]客户端 ivs 失败 ,decode 消息失败, reco 为空, message={}", message);
            return;
        }

        int parkingState = parking.getParkingState();

        if(parkingState != 1 && parkingState != 4) {
            log.error("[插件调试]客户端 ivs parkingState 忽略 , parkingState={}", parkingState);
            return;
        }

        log.info("[插件调试]客户端 ivs 准备下载bgImg图片 , {}", message.getBgImg());
        List<DeviceImage> bgImages = new ArrayList<>();
        if(message.getBgImg() != null && !message.getBgImg().isEmpty()) {
            for(IvsResultMessage.Img image : message.getBgImg()) {
                String base64ImageString = Downloader.downloadImage(vziConfig,  "/openapi/v1/", image.getPath());
                if(StringUtils.isBlank(base64ImageString)) {
                    log.error("[插件调试]客户端 ivs 下载图片失败");
                    continue;
                }

                String fileName = Downloader.getFileName(image.getPath());
                String suffix = Downloader.getFileSuffix(fileName);
                int length = image.getImageLength();
                int width = 0;
                int height = 0;

                if(image.getResolution() != null) {
                    width = image.getResolution().getWidth();
                    height = image.getResolution().getHeight();
                }

                bgImages.add(new DeviceImage(fileName, suffix, base64ImageString, width, height, length));
            }
        }

        log.info("[插件调试]客户端 ivs 准备下载fetureImg图片 , {}", message.getFetureImg());
        List<DeviceImage> fetureImages = new ArrayList<>();
        if(message.getFetureImg() != null && !message.getFetureImg().isEmpty()) {
            for(IvsResultMessage.Img image : message.getFetureImg()) {
                String base64ImageString = Downloader.downloadImage(vziConfig, "/openapi/v1/" , image.getPath());
                if(StringUtils.isBlank(base64ImageString)) {
                    log.error("[插件调试]客户端 ivs 下载图片失败");
                    continue;
                }

                String fileName = Downloader.getFileName(image.getPath());
                String suffix = Downloader.getFileSuffix(fileName);
                int length = image.getImageLength();
                int width = 0;
                int height = 0;

                if(image.getResolution() != null) {
                    width = image.getResolution().getWidth();
                    height = image.getResolution().getHeight();
                }

                fetureImages.add(new DeviceImage(fileName, suffix, base64ImageString, width, height, length));

            }
        }

        if(parkingState == 1) {
            DcamCarInEventMessage inMessage = new DcamCarInEventMessage(plate.getColor(), plate.getPlateType(), plate.getPlate(), parking.getZoneId(), parking.getZoneName(), reco.getRecoTime());
            dcamService.post(pluginInfo.getPluginId(), DcamCarInEventAction.builder()
                    .id(IdUtil.simpleUUID())
                    .dn(deviceDn)
                    .data(inMessage)
                    .bgImages(bgImages)
                    .fetureImages(fetureImages)
                    .time(System.currentTimeMillis())
                    .build());
        } else {
            DcamCarOutEventMessage outMessage = new DcamCarOutEventMessage(plate.getColor(), plate.getPlateType(), plate.getPlate(), parking.getZoneId(), parking.getZoneName(), reco.getRecoTime());
            dcamService.post(pluginInfo.getPluginId(), DcamCarOutEventAction.builder()
                    .id(IdUtil.simpleUUID())
                    .dn(deviceDn)
                    .data(outMessage)
                    .bgImages(bgImages)
                    .fetureImages(fetureImages)
                    .time(System.currentTimeMillis())
                    .build());
        }
    }

    private void processGpioIn(String payload) {
        //IvsResultPayload ivsResultPayload = JsonUtils.parseObject(payload, IvsResultPayload.class);
        //log.info("收到 Gpio In 消息: {}", ivsResultPayload);
    }

    private void processSnapShot(String payload) {
        //IvsResultPayload ivsResultPayload = JsonUtils.parseObject(payload, IvsResultPayload.class);
        //log.info("收到 Snap Shot 消息: {}", ivsResultPayload);
    }

    private void publishOfflineCheckMessage(String deviceDn) {
        String topic = "device/{sn}/message/down/check_offline_record";
        String newTopic = topic.replace("{sn}", deviceDn);
        CheckOfflineInvokeMessage checkOfflineInvokeMessage = new CheckOfflineInvokeMessage("check_offline_record", new CheckOfflineInvokeMessage.Body("push", 1, 0));
        checkOfflineInvokeMessage.setType("check_offline_record");

        InvokeMessage invokeMessage = new InvokeMessage(UUID.randomUUID().toString(), deviceDn, "check_offline_record", "1.0", new Date().getTime(), checkOfflineInvokeMessage);

        String jsonString = JsonUtils.toJsonString(invokeMessage);

        log.info("[插件调试]发布离线检测消息, topic={}, message={}", newTopic, jsonString);

        client.publish(newTopic, Buffer.buffer(jsonString), MqttQoS.AT_LEAST_ONCE, false, false);
    }
}
