package com.obast.charer.model.notify;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.NotifyIdentifierEnum;
import com.obast.charer.enums.YesNoEnum;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import javax.persistence.Convert;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyConfig extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String name;

    public NotifyIdentifierEnum identifier;

    private NotifyConfig.Properties properties;

    private EnableStatusEnum status;

    private String note;

    @Data
    public static class Properties {
        /**
         * 是否发送通知短信
         */
        @Convert(converter = YesNoEnum.Converter.class)
        private YesNoEnum isSendSms;

        /**
         * 短信配置id
         */
        private String smsConfigId;

        /**
         * 短信服务商提供的模板id
         */
        private String smsTemplateId;

        /**
         * 发送短信内容(如果短信服务商允许自定义短信)
         */
        private String smsContent;

        /**
         * 是否发送APP推送(包含微信小程序推送)
         */
        @Convert(converter = YesNoEnum.Converter.class)
        private YesNoEnum isSendPush;

        /**
         * 微信小程序通知模板id
         */
        private String pushWechatTemplateId;

        /**
         * APP推送消息标题
         */
        private String pushAppTitle;

        /**
         * APP推送消息内容
         */
        private String pushAppContent;

    }
}
