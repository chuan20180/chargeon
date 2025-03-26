package com.obast.charer.push.getui;

import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.enums.AppOsEnum;
import com.obast.charer.enums.PushIdentifierEnum;
import com.obast.charer.push.core.IPushService;
import com.obast.charer.push.core.PushPayload;
import com.obast.charer.push.core.PushResult;
import com.getui.push.v2.sdk.ApiHelper;
import com.getui.push.v2.sdk.GtApiConfiguration;
import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.CommonEnum;
import com.getui.push.v2.sdk.dto.req.Audience;
import com.getui.push.v2.sdk.dto.req.Settings;
import com.getui.push.v2.sdk.dto.req.Strategy;
import com.getui.push.v2.sdk.dto.req.message.PushChannel;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.getui.push.v2.sdk.dto.req.message.PushMessage;
import com.getui.push.v2.sdk.dto.req.message.android.AndroidDTO;
import com.getui.push.v2.sdk.dto.req.message.android.GTNotification;
import com.getui.push.v2.sdk.dto.req.message.android.ThirdNotification;
import com.getui.push.v2.sdk.dto.req.message.android.Ups;
import com.getui.push.v2.sdk.dto.req.message.ios.Alert;
import com.getui.push.v2.sdk.dto.req.message.ios.Aps;
import com.getui.push.v2.sdk.dto.req.message.ios.IosDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
@Component
public class GetuiPushService implements IPushService {

    PushIdentifierEnum identifier = PushIdentifierEnum.Getui;

    GetuiPushProperties properties;


    private PushApi getPushApi() {
        GtApiConfiguration apiConfiguration = new GtApiConfiguration();
        apiConfiguration.setAppId(properties.getAppId());
        apiConfiguration.setAppKey(properties.getAppKey());
        apiConfiguration.setMasterSecret(properties.getMasterSecret());
        // 接口调用前缀，请查看文档: 接口调用规范 -> 接口前缀, 可不填写appId
        apiConfiguration.setDomain("https://restapi.getui.com/v2/");
        apiConfiguration.setAnalyseStableDomainInterval(500);
//        apiContext.configuration.setOpenCheckHealthDataSwitch(true);
        apiConfiguration.setCheckHealthInterval(500);
        apiConfiguration.setOpenAnalyseStableDomainSwitch(false);  //关闭
        ApiHelper apiHelper = ApiHelper.build(apiConfiguration);

        return apiHelper.creatApi(PushApi.class);
    }


    public PushResult send(com.obast.charer.push.core.PushMessage message, AppOsEnum platform, String cid) {
        log.debug("个推推送发送 {} {},  {}", message, platform, cid);


        String title = message.getTitle();

        if(title.length() > 255) {
            title = StringUtils.substring(title, 255);
        }

        String content = message.getContent();
        if(content.length() > 255) {
            content = StringUtils.substring(content,255);
        }

        String pushPayload = JsonUtils.toJsonString(new PushPayload(message.getType(), message.getData()));

        log.debug("payload {}", pushPayload);

        PushApi pushApi = getPushApi();

        PushDTO<Audience> pushDTO = new PushDTO<>();
        pushDTO.setRequestId(System.currentTimeMillis() + "");
        pushDTO.setGroupName("g-name1");

        Settings settings = new Settings();
        settings.setTtl(3600000);
        Strategy strategy = new Strategy();
        strategy.setSt(1);
        settings.setStrategy(strategy);

        pushDTO.setSettings(settings);

        PushMessage pushMessage = new PushMessage();
        GTNotification notification = new GTNotification();
        notification.setTitle(title);
        notification.setBody(content);
        notification.setClickType(CommonEnum.ClickTypeEnum.TYPE_PAYLOAD.type);
        notification.setPayload(pushPayload);

        pushMessage.setNotification(notification);
        pushDTO.setPushMessage(pushMessage);

        PushChannel pushChannel = new PushChannel();
        AndroidDTO androidDTO = new AndroidDTO();
        Ups ups = new Ups();
        ThirdNotification thirdNotification = new ThirdNotification();
        thirdNotification.setClickType(CommonEnum.ClickTypeEnum.TYPE_PAYLOAD.type);
        thirdNotification.setPayload(pushPayload);
        thirdNotification.setTitle(title);
        thirdNotification.setBody(content);
        ups.setNotification(thirdNotification);

        //设置options 方式一
        ups.addOption("HW","badgeAddNum",3);
        ups.addOption("HW","badgeClass","com.getui.demo.GetuiSdkDemoActivity");
        ups.addOption("OP","app_message_id",11);
        ups.addOption("VV","message_sort",1);
        ups.addOptionAll("channel","default");

        //设置options 方式二
        Map<String, Map<String,Object>> options = new HashMap<String, Map<String, Object>>();
        Map<String,Object> all = new HashMap<String, Object>();
        all.put("channel","default");
        options.put("ALL",all);
        Map<String,Object> hw = new HashMap<String, Object>();
        all.put("badgeAddNum",3);
        all.put("badgeClass","com.getui.demo.GetuiSdkDemoActivity");
        options.put("HW",hw);
        ups.setOptions(options);

        androidDTO.setUps(ups);
        pushChannel.setAndroid(androidDTO);

        IosDTO iosDTO = new IosDTO();
        Aps aps = new Aps();

        Alert alert = new Alert();
        alert.setTitle(title);
        alert.setBody(content);

        aps.setAlert(alert);

        iosDTO.setAps(aps);
        iosDTO.setPayload(pushPayload);

        pushChannel.setIos(iosDTO);

        pushDTO.setPushChannel(pushChannel);

        /*设置接收人信息*/
        Audience audience = new Audience();
        audience.addCid(cid);

        pushDTO.setAudience(audience);


        ApiResult<?> result = pushApi.pushToSingleByCid(pushDTO);

        log.debug("推送参数: {}", pushDTO);

        log.debug("推送结果: {}", result);

       return new PushResult(0, null, null);
    }

}