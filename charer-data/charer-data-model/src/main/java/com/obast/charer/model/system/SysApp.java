package com.obast.charer.model.system;

import com.obast.charer.enums.AppTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysApp extends BaseModel implements Id<String>, Serializable{

    private String id;

    private String appName;

    private String appId;

    private String appSecret;

    private String config;

    private AppTypeEnum appType;

    private String note;

    private EnableStatusEnum status;

    @Data
    static
    public class WechatConfig {

        private String appId;

        private String appSecret;
    }
}
