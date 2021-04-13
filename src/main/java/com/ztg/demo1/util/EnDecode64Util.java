package com.ztg.demo1.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * @Description: 加解密工具类
 * @author: ztg
 * @time: 2018/11/8 14:38
 */
public class EnDecode64Util {

    /**
     * 采用BASE64算法对字符串进行加密
     *
     * @param str 原字符串
     * @return 加密后的字符串
     */
    public static String encode(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    /**
     * 字符串解密，采用BASE64的算法
     *
     * @param s 需要解密的字符串
     * @return 解密后的字符串
     */
    public static String decode(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String abc = "201811081616";
        String c = encode(abc);
        System.out.println(c);
        System.out.println(decode(c));
    }
}
