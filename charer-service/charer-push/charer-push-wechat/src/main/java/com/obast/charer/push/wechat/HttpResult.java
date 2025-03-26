package com.obast.charer.push.wechat;


import com.alibaba.fastjson.JSONObject;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.enums.PushIdentifierEnum;
import com.obast.charer.push.core.IPushService;
import com.obast.charer.push.core.PushResult;
import com.obast.charer.push.wechat.vo.TemplateDataVo;
import com.obast.charer.push.wechat.vo.WxMssVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class HttpResult {

   private Integer errcode;
   private String errmsg;

}