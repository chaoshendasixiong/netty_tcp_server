package com.csdsx.util;

import io.netty.channel.Channel;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by xxsy on 2016/3/16.
 */
public class GlobalUtil {
    public static String genToken() {
        return Md5(UUID.randomUUID().toString());
    }

    public static String Md5(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new RuntimeException("Token cannot be generated.", e);
        }
    }
    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String toHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }



}
