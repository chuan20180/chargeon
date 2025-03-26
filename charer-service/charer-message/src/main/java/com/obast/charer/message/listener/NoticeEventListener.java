package com.obast.charer.message.listener;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.business.IChannelConfigData;
import com.obast.charer.data.business.ICustomerLoginData;
import com.obast.charer.message.event.MessageEvent;
import com.obast.charer.message.event.NoticeEvent;
import com.obast.charer.enums.AppOsEnum;
import com.obast.charer.enums.ChannelIdentifierEnum;
import com.obast.charer.message.model.Message;
import com.obast.charer.message.model.MessageChannel;
import com.obast.charer.model.customer.CustomerLogin;
import com.obast.charer.model.notify.ChannelConfig;
import com.obast.charer.model.system.Notice;
import com.obast.charer.enums.PageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class NoticeEventListener {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private IChannelConfigData channelConfigData;

    @Autowired
    ICustomerLoginData customerLoginData;

    @EventListener(classes = NoticeEvent.class)
    public void doEvent(NoticeEvent event) {
        log.debug("开始发公告: {}", event);

        Notice notice = event.getNotice();

        ChannelConfig channelConfig = channelConfigData.findByIdentifier(ChannelIdentifierEnum.App);
        if(channelConfig == null) {
            throw new BizException(ErrCode.NOTIFY_CHANNEL_SERVICE_NOT_FOUND);
        }

        List<CustomerLogin> customerLogins = customerLoginData.findListByPlatforms(List.of(AppOsEnum.Ios, AppOsEnum.Android));
        if(!customerLogins.isEmpty()) {
            for(CustomerLogin customerLogin: customerLogins) {
                Map<String, Object> params = new HashMap<>();
                params.put("dn", customerLogin.getDn());
                params.put("platform", customerLogin.getPlatform());
                params.put("type", PageTypeEnum.Notice.name());
                params.put("data", notice.getId());

                String title = notice.getTitle().get(customerLogin.getLanguage());
                String content = notice.getContent().get(customerLogin.getLanguage());
                if(StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
                    log.error("公告发布失败，内容为空");
                    return;
                }

                MessageChannel channel = new MessageChannel(channelConfig.getIdentifier().name(), channelConfig.getProperties());
                Message message = new Message(channel, title,  content, params);
                log.debug("开始发送消息 {}", message);
                applicationEventPublisher.publishEvent(new MessageEvent(message));
            }
        }
    }
}
