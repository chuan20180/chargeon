package com.obast.charer.openapi.util;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.data.system.ISysAppData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysApp;
import com.obast.charer.openapi.config.request.RequestLocale;
import com.obast.charer.openapi.config.request.RequestLocaleHolder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;

/**
 * @ Author：chuan
 * @ Date：2024-11-13-12:20
 * @ Version：1.0
 * @ Description：WechatUtil
 */

@Component
@Slf4j
public class WechatUtil {


    @Autowired
    private ISysAppData sysAppData;

    public WxAuthorizeResult authorize(String loginCode) {

        RequestLocale requestLocale = RequestLocaleHolder.getLocale();

        String appId = requestLocale.getApiId();
        if(StringUtils.isBlank(appId)) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        SysApp sysApp = sysAppData.findByAppId(requestLocale.getApiId());
        if(sysApp == null) {
            throw new BizException(ErrCode.SYS_APP_NOT_EXSIT);
        }

        if(sysApp.getStatus().equals(EnableStatusEnum.Disabled)) {
            throw new BizException(ErrCode.SYS_APP_DISABLED);
        }

        SysApp.WechatConfig wechatConfig;
        try {
            wechatConfig = JsonUtils.parse(sysApp.getConfig(), SysApp.WechatConfig.class);
        } catch (Exception e) {
            throw new BizException(ErrCode.SYS_APP_CONFIG_PARSE_EXCEPTION);
        }

        if(wechatConfig == null) {
            throw new BizException(ErrCode.SYS_APP_CONFIG_NULL);
        }

        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String requestUrl = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("appid", wechatConfig.getAppId())
                .queryParam("secret", wechatConfig.getAppSecret())
                .queryParam("js_code", loginCode)
                .queryParam("grant_type", "authorization_code")
                .toUriString();
        HttpResponse response = HttpUtil.createGet(requestUrl).execute();
        log.debug("返回数据: {}", response.body());
        return JsonUtils.parse(response.body(), WxAuthorizeResult.class);
    }

    @Slf4j
    @Data
    public static class WxAuthorizeResult {
        private String openid;
        private String session_key;
    }

    @Slf4j
    @Data
    public static class WxLoginResult {
        String phoneNumber;
        String purePhoneNumber;
        String countryCode;
    }

    /**
     * 检验数据的真实性，并且获取解密后的明文.
     */
    public WxLoginResult decryptData(String sessionKey, String encryptedData, String iv) {
        if (StringUtils.length(sessionKey) != 24) {
            throw new BizException(ErrCode.WEIXIN_DATA_PARSE_EXCEPTION);
        }
        // 对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
        byte[] aesKey = Base64.decodeBase64(sessionKey);

        if (StringUtils.length(iv) != 24) {
            throw new BizException(ErrCode.WEIXIN_DATA_PARSE_EXCEPTION);
        }
        // 对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
        byte[] aesIV = Base64.decodeBase64(iv);

        // 对称解密的目标密文为 Base64_Decode(encryptedData)
        byte[] aesCipher = Base64.decodeBase64(encryptedData);
        try {
            byte[] resultByte = AESUtil.decrypt(aesCipher, aesKey, aesIV);
            if (null != resultByte && resultByte.length > 0) {
                String responseString = new String(resultByte, StandardCharsets.UTF_8);
                return JsonUtils.parse(responseString, WxLoginResult.class);
            } else {
                throw new BizException(ErrCode.WEIXIN_DATA_PARSE_EXCEPTION);
            }
        } catch (InvalidAlgorithmParameterException e) {
            throw new BizException(ErrCode.WEIXIN_DATA_PARSE_EXCEPTION);
        }
    }

}