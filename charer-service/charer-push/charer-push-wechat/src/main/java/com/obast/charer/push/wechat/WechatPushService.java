package com.obast.charer.push.wechat;


import com.alibaba.fastjson.JSONObject;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.enums.PushIdentifierEnum;
import com.obast.charer.push.core.IPushService;
import com.obast.charer.push.wechat.vo.TemplateDataVo;
import com.obast.charer.push.wechat.vo.WxMssVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Component
public class WechatPushService implements IPushService {

    PushIdentifierEnum identifier = PushIdentifierEnum.Webchat;

    WechatPushProperties properties;

    private RestTemplate restTemplate = new RestTemplate();

    public String getAccessToken() {
        WechatPushProperties properties = getProperties();
        String interfaceUrl = "https://api.weixin.qq.com/cgi-bin/token";
        String url = interfaceUrl + "?grant_type=client_credential" + "&appid=" + properties.getAppId() + "&secret=" + properties.getAppSecret();
        String result = restTemplate.getForObject(url, String.class);
        JSONObject entries = JSONObject.parseObject(result);
        return entries.getString("access_token");
    }

    public ActionResult<?> send(String templateId, String cid, WechatPushMessage message) {
        log.debug("[通知调试]正式开始发送Wechat Push, templateId={}, cid={}", templateId, cid);



        Map<String, Object> params = message.getParam();

        Map<String, TemplateDataVo> data = new HashMap<>();

        data.put("character_string5", new TemplateDataVo(params.get("orderTranId")));
        data.put("amount6", new TemplateDataVo(params.get("settledAmount")));
        data.put("character_string8", new TemplateDataVo(params.get("deviceDn")));
        data.put("thing7", new TemplateDataVo(params.get("stopReason")));
        data.put("number13", new TemplateDataVo(params.get("chargerQty")));

        String access_token = getAccessToken();
        String url = String.format("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=%s", access_token);
        log.debug("[通知调试]小程序推送数据 url={}", url);

        String page = String.format("/module/mine/order/detail?id=%s", params.get("orderId"));

        //拼接推送的模版
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser(cid);
        wxMssVo.setPage(page);
        wxMssVo.setTemplate_id(templateId);
        wxMssVo.setData(data);
        HttpResult result = null;
        try {
            List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
            httpMessageConverters.forEach(httpMessageConverter -> {
                if(httpMessageConverter instanceof StringHttpMessageConverter){
                    StringHttpMessageConverter messageConverter = (StringHttpMessageConverter) httpMessageConverter;
                    messageConverter.setDefaultCharset(StandardCharsets.UTF_8);
                }
            });
            ResponseEntity<HttpResult> responseEntity = restTemplate.postForEntity(url, JsonUtils.toJsonString(wxMssVo), HttpResult.class);
            log.debug("[通知调试]小程序推送数据={}", JsonUtils.toJsonString(wxMssVo));
            log.debug("[通知调试]小程序推送结果={}", responseEntity.getBody());
            result =  responseEntity.getBody();
        } catch (Exception e) {
            log.error("[通知调试]正式开始发送Wechat Push异常, msg={}", e.getLocalizedMessage());
        }

        if(result == null) {
            log.error("[通知调试]正式开始发送Wechat Push异常, msg={}", "返回为空");
            return new ActionResult<>(200000, "返回为空");
        }

        return new ActionResult<>(result.getErrcode(), result.getErrmsg());
    }
}