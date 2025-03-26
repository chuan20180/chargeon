package com.obast.charer.sms.core.push;

import com.obast.charer.data.business.IPushConfigData;
import com.obast.charer.sms.core.SmsFactory;
import com.obast.charer.sms.core.SmsResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SmsPushEventListener {

    @Autowired
    IPushConfigData pushConfigData;

    @Autowired
    SmsFactory smsFactory;
    @EventListener(classes = SmsPushEvent.class)
    public void doEvent(SmsPushEvent event) {

        SmsPushConfig config = event.getConfig();

        SmsPushMessage message = event.getMessage();
        log.error("[通知调试]收到 Sms Push 事件, msg={}", message);

        Map<String, Object> param = message.getParam();
        if(!param.containsKey("mobile")) {
            log.error("[通知调试] Sms Push推送失败: {}", "mobile 未指定");
            return;
        }

        String mobile = (String) param.get("mobile");
        SmsResult result = smsFactory.sendNotify(config.getContent(), mobile);
    }
}
