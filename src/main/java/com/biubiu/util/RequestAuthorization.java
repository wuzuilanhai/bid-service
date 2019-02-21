package com.biubiu.util;

import cn.hutool.core.util.HexUtil;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 张海彪
 * @create 2019-02-21 20:27
 */
public class RequestAuthorization {

    private static final String APP_ID = "app_id";

    private static final String APP_SECRET = "app_secret";

    public static void authorize() {
        String nonce = "";
        String timeStamp = "";
        String uid = "";
        String reqSignature = "";
        String appKey = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String signString = APP_SECRET + nonce + timeStamp + uid;
            String signature = HexUtil.encodeHexStr(messageDigest.digest(signString.getBytes(StandardCharsets.UTF_8)));
            if (!signature.equalsIgnoreCase(reqSignature)) {
                throw new RuntimeException("鉴权失败");
            }
            if (!APP_ID.equals(appKey)) {
                throw new RuntimeException("鉴权失败");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        authorize();
    }

}
