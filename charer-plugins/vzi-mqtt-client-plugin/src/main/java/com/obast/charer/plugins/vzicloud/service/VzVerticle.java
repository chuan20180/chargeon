package com.obast.charer.plugins.vzicloud.service;


import com.obast.charer.common.plugin.core.thing.dcam.IDcamService;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.plugins.vzicloud.http.HttpClient;

import com.obast.charer.plugins.vzicloud.http.HttpMethod;
import com.obast.charer.plugins.vzicloud.http.HttpRequest;
import com.obast.charer.plugins.vzicloud.http.HttpResponse;
import com.obast.charer.plugins.vzicloud.config.MqttConfig;
import com.obast.charer.plugins.vzicloud.config.VziConfig;
import com.obast.charer.plugins.vzicloud.handler.MessageHandler;
import com.gitee.starblues.bootstrap.annotation.AutowiredType;
import com.gitee.starblues.core.PluginInfo;
import io.vertx.core.AbstractVerticle;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author huangwenl
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Service
@Component
@Data
public class VzVerticle extends AbstractVerticle {

    String vzStpUrl = "http://open.vzicloud.com/openapi/v1/stp/clients/route";

    @Autowired
    @AutowiredType(AutowiredType.Type.MAIN_PLUGIN)
    private IDcamService dcamService;

    @Autowired
    private PluginInfo pluginInfo;

    @Autowired
    private VziConfig config;

    @Autowired
    private MqttConfig mqttConfig;


    private MqttClient client;

    private boolean isConnecting;

    private boolean registered;


    /**
     * 创建配置文件
     */
    @PostConstruct
    public void initConfig()  {
        Executors.newSingleThreadScheduledExecutor().schedule(this::initServer, 3, TimeUnit.SECONDS);
    }

    /**
     * 初始TCP服务
     */
    private void initServer() {
        //log.info("vzicloud 配置参数: {}", config);
        HttpClient client = new HttpClient(config.getBaseUrl(), config.getAccesskeyId(), config.getAccesskeySecret());
        String path = "/openapi/v1/stp/clients/route";
        HttpRequest request = new HttpRequest(HttpMethod.GET, path);
        request.addParam("sid", "123456");
        HttpResponse response = client.doRequest(request);
        log.info("初始化 vzi 请求结果 {}", response.getBodyStr());
        int status = response.getStatus();
        if(status != 200) {
            log.error("[插件调试]初始化vzi参数失败, 返回状态异常 {} {}", response.getStatus(), response.getBody());
            return;
        }
         mqttConfig = JsonUtils.parseObject(response.getBodyStr(), MqttConfig.class);


        try {
            isConnecting = true;
            MqttClientOptions options = new MqttClientOptions();
            options.setUsername(mqttConfig.getUsername());
            options.setPassword(mqttConfig.getPassword());
            options.setCleanSession(true);
            options.setKeepAliveInterval(30);
            options.setClientId(mqttConfig.getClientid());
            options.setReconnectInterval(3000);
            options.setReconnectAttempts(100);

            MqttClient mqttClient = MqttClient.create(Vertxs.getVertx(), options);

            CountDownLatch countDownLatch = new CountDownLatch(1);
            mqttClient.connect(mqttConfig.getPort(), mqttConfig.getHost(), s -> {
                if (s.succeeded()) {
                    //log.info("[插件调试]Mqtt服务器连接成功 clientId={}", mqttClient.clientId());
                    countDownLatch.countDown();
                    registered = true;
                } else {
                    log.info("[插件调试]Mqtt服务器连接失败 clientId={} , msg={}", mqttClient.clientId(), s.cause().getLocalizedMessage());
                    countDownLatch.countDown();
                }
            }).pingResponseHandler(s -> {
                log.info("[插件调试]收到Mqtt服务器ping包 clientId={}", mqttClient.clientId());
            });
            countDownLatch.await();
            if(!registered){
                return;
            }

            // 订阅
            List<String> topics = Arrays.asList(
                    "device/+/message/up/ivs_result",
                    "device/+/push/state"
            );


            for(String topic: topics) {
                mqttClient.subscribe(topic, 1, r -> {
                    log.info("[插件调试]订阅成功 topic={}, result={}", topic, r);
                });
            }

            mqttClient.publishHandler(new MessageHandler(dcamService, pluginInfo, config, mqttClient));
            mqttClient.closeHandler((v) -> {
                log.info("[插件调试]Mqtt服务器连接关闭 result={}", v);
                mqttClient.disconnect();
            });


        } catch (Throwable e) {
            log.error("[插件调试]Mqtt服务器处理异常 result={}", e.getLocalizedMessage());
        } finally {
            isConnecting = false;
        }
    }
}
