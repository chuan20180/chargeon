package com.obast.charer.payment.wechat;

import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class WXPaySignatureCertificateUtil {

    /**
     * 保存微信平台证书
     */
    private static final ConcurrentHashMap<String, AutoUpdateCertificatesVerifier> verifierMap = new ConcurrentHashMap<>();

    /**
     * 功能描述:获取平台证书，自动更新
     * 注意：这个方法内置了平台证书的获取和返回值解密
     */
    static AutoUpdateCertificatesVerifier getVerifier(WechatProperties wechatProperties) {

        String mchSerialNo = wechatProperties.getMerchantSerialNumber();
        AutoUpdateCertificatesVerifier verifier = null;
        if (verifierMap.isEmpty() || !verifierMap.containsKey(mchSerialNo)) {
            verifierMap.clear();
            try {
                //传入证书
                PrivateKey privateKey = getPrivateKey(wechatProperties);
                //刷新
                PrivateKeySigner signer = new PrivateKeySigner(mchSerialNo, privateKey);
                WechatPay2Credentials credentials = new WechatPay2Credentials(wechatProperties.getMerchantId(), signer);
                verifier = new AutoUpdateCertificatesVerifier(credentials , wechatProperties.getApiV3Key().getBytes("utf-8"));
                verifierMap.put(verifier.getValidCertificate().getSerialNumber()+"", verifier);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            verifier = verifierMap.get(mchSerialNo);
        }
        return verifier;
    }

    /**
     * 获取私钥。
     * 证书路径 本地使用如： D:\\微信平台证书工具\\7.9\\apiclient_key.pem
     * 证书路径 线上使用如： /usr/apiclient_key.pem
     * String filename 私钥文件路径  (required)
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey(WechatProperties wechatProperties) {
        String privateKey = wechatProperties.getPrivateKey();

        try {
            privateKey = privateKey.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                    new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }
}