package com.obast.charer.push.wechat.vo;

import lombok.Data;

import java.util.Map;

/**
 * @ Author：chuan
 * @ Date：2025-02-18-12:59
 * @ Version：1.0
 * @ Description：WxMssVo
 */
@Data
public class WxMssVo {
    private String touser;//用户openid

    private String template_id;//模版id

    /**
     * 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转
     */
    private String page;

    /**
     * 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
     */
    private String miniprogram_state = "formal";

    /**
     * 进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN
     */
    private String lang = "zh_CN";

    /**
     * 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }的object
     */
    private Map<String, TemplateDataVo> data;
}
