package com.boot.demo.dingding;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


/**
 * @DATE 2019/11/4 19:15
 * @Description 叮叮发送消息 工具类
 * @Author Tian Daguang
 **/
public class DingDingUtil {

    public static void main(String[] args) throws Exception {
        HashMap mp = getDDSignMap("SEC023fb89e7d9e131efed178693d4d61165a16218b0cb221ca0cd56f96136d6dea");
        System.out.println(mp);
    }

    /**
     * 功能描述:  获取自定义机器人加签的时间按戳和签名
     *
     * @return : java.util.HashMap
     * @param: secret  群组密钥
     * @author : Tiandaguang
     * @date : 2019/11/5 9:45
     */

    public static HashMap getDDSignMap(String secret) {

        HashMap<String, Object> map = new HashMap<>();
        Long timestamp = System.currentTimeMillis();
        map.put("timestamp", timestamp);
        String sign = timestamp + "\n" + secret;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(sign.getBytes("UTF-8"));
            sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        map.put("sign", sign);
        return map;
    }
}
