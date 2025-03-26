package com.obast.charer.system.operate.impl;

import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.common.utils.SpringUtils;
import com.obast.charer.data.business.INotifyConfigData;
import com.obast.charer.enums.*;

import com.obast.charer.message.service.MessageService;
import com.obast.charer.model.notify.NotifyConfig;
import com.obast.charer.push.wechat.WechatPushConfig;
import com.obast.charer.push.wechat.WechatPushEvent;
import com.obast.charer.push.wechat.WechatPushMessage;
import com.obast.charer.sms.core.push.SmsPushConfig;
import com.obast.charer.sms.core.push.SmsPushEvent;
import com.obast.charer.sms.core.push.SmsPushMessage;
import com.obast.charer.system.operate.INotifyOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class NotifyOperateServiceImpl implements INotifyOperateService {

    @Autowired
    private INotifyConfigData notifyConfigData;

    @Autowired
    MessageService messageService;

    public void sendNotify(NotifyIdentifierEnum identifier, PlatformTypeEnum platform, Map<String, Object> params) {
        log.debug("[通知调试]通知开始发送 {} {}", identifier, params);
        NotifyConfig notifyConfig = notifyConfigData.findByIdentifier(identifier);
        if(notifyConfig == null) {
            log.error("[通知调试]通知发送失败, identifier={}, msg={}", identifier.name(), "通知配置不存在");
            return;
        }

        if(notifyConfig.getStatus().equals(EnableStatusEnum.Disabled)) {
            log.error("[通知调试]通知发送失败, identifier={}, msg={}", identifier.name(), "通道未启用");
            return;
        }

        NotifyConfig.Properties properties = notifyConfig.getProperties();
        if(properties == null) {
            log.error("[通知调试]通知发送失败, identifier={}, msg={}", identifier.name(), "通道配置文件为空");
            return;
        }

        //短信发送开始
        if(properties.getIsSendSms().equals(YesNoEnum.Y)) {
            log.debug("[通知调试]开始发送通知短信, identifier={}, msg={}", identifier.name(), "通道配置文件为空");
            SmsPushMessage message = new SmsPushMessage(params);
            SmsPushConfig config = new SmsPushConfig(properties.getSmsContent());

            SpringUtils.context().publishEvent(new SmsPushEvent(config, message));
        }

        if(platform == null) {
            log.error("[通知调试]通知发送失败, msg={}", "获取平台类型异常");
            return;
        }

        //推送发送开始
        if(properties.getIsSendPush().equals(YesNoEnum.Y)) {
            switch (platform) {
                case Weixin:
                    WechatPushConfig config = new WechatPushConfig(properties.getPushWechatTemplateId());
                    WechatPushMessage message = new WechatPushMessage(params);
                    SpringUtils.context().publishEvent(new WechatPushEvent(config, message));
                    break;

                default:
                    break;

            }
        }
    }
}
