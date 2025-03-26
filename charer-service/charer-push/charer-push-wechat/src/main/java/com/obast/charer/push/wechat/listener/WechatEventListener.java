package com.obast.charer.push.wechat.listener;

import com.alibaba.fastjson.JSONObject;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.data.business.IPushConfigData;
import com.obast.charer.enums.AppOsEnum;
import com.obast.charer.model.push.PushConfig;
import com.obast.charer.push.wechat.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class WechatEventListener {

    @Autowired
    IPushConfigData pushConfigData;

    @Autowired
    WechatPushService wechatPushService;

    @EventListener
    public void doEvent(WechatPushEvent event) {

        WechatPushConfig config = event.getConfig();

        WechatPushMessage message = event.getMessage();
        log.info("[通知调试]收到Wechat Push 事件, msg={}", message);

        PushConfig pushConfig = pushConfigData.findByIdentifier(AppOsEnum.Wechat.name());
        if(pushConfig == null) {
            log.error("[通知调试]发送Wechat Push 失败, msg={}", ErrCode.PUSH_NOT_FOUND_AVAILABLE_PROVIDER.getValue());
            throw new BizException(ErrCode.PUSH_NOT_FOUND_AVAILABLE_PROVIDER);
        }

        WechatPushProperties properties = null;
        try {
            properties = JSONObject.parseObject(pushConfig.getProperties(), WechatPushProperties.class);
        } catch (Exception e) {
            log.error("[通知调试]发送Wechat Push 失败, msg={}", ErrCode.PARSE_JSON_EXCEPTION.getValue());
            throw new BizException(ErrCode.PARSE_JSON_EXCEPTION);
        }

        if(properties == null) {
            log.error("[通知调试]发送Wechat Push 失败, msg={}", ErrCode.PARSE_JSON_EXCEPTION.getValue());
            throw new BizException(ErrCode.PARSE_JSON_EXCEPTION);
        }

        wechatPushService.setProperties(properties);

        Map<String, Object> param = message.getParam();
        if(!param.containsKey("dn")) {
            log.error("[通知调试]Wechat Push推送失败: {}", "dn 未指定");
            return;
        }

        String dn = (String) param.get("dn");

        wechatPushService.send(config.getTemplateId(), dn, message);

        log.info("[通知调试]结束Wechat Push 事件, msg={}", message);
    }
}
