package com.obast.charer.plugins.vzicloud.util;

import com.obast.charer.plugins.vzicloud.http.HttpCharacterEncoding;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class EncryptUtil {
    public static String encryptUserInfo(String username, String password, String accessKeySecret) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

            byte[] encryptKey = BinaryUtil.calculateMd5(accessKeySecret.getBytes(HttpCharacterEncoding.DEFAULT_ENCODING));
            SecretKeySpec keySpec = new SecretKeySpec(encryptKey, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            byte[] userinfo = (username + ":" + password).getBytes(HttpCharacterEncoding.DEFAULT_ENCODING);
            int userinfoLength = userinfo.length;
            if (userinfoLength % cipher.getBlockSize() != 0) {
                userinfoLength += cipher.getBlockSize() - (userinfoLength % cipher.getBlockSize());
            }
            byte[] fixedUserinfo = new byte[userinfoLength];
            System.arraycopy(userinfo, 0, fixedUserinfo, 0, userinfo.length);

            byte[] encrypted = cipher.doFinal(fixedUserinfo);

            return BinaryUtil.toBase64String(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
