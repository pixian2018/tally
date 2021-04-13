package com.ztg.demo1.util;

import java.util.List;

/**
 * @Description: 加解密工具类
 * @author: zhoutg
 * @time: 2019/12/30 11:09
 */
public class CryptUtil {

    private final static char EN_MAX = '\u0080';//128
    private final static int MOVE_NUM = 2;
    private final static char DE_MAX = EN_MAX + MOVE_NUM;

    /**
     * 加密，把一个字符串在原有的基础上+2
     *
     * @param data 需要解密的原字符串
     * @return 返回解密后的新字符串
     */
    public static String encrypt_char(String data) {
        char[] chars = data.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (EN_MAX < chars[i]) {
                chars[i] += MOVE_NUM;
            }
        }
        return new String(chars);
    }

    /**
     * 解密：把一个加密后的字符串在原有基础上-2
     *
     * @param data 加密后的字符串
     * @return 返回解密后的新字符串
     */
    public static String decrypt_char(String data) {
        char[] chars = data.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (DE_MAX < chars[i]) {
                chars[i] -= MOVE_NUM;
            }
        }
        return new String(chars);
    }


    /**
     * 对List<String>进行加密
     *
     * @param list 加密前的list
     * @return 加密后的list
     */
    public static void encryptList(List<String> list) {
        if (ListUtil.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                list.set(i, CryptUtil.encrypt_char(list.get(i)));
            }
        }
    }

    /**
     * 对List<String>进行解密
     * @param list 解密前的list
     * @return 解密后的list
     */
    public static void decryptList(List<String> list) {
        if (ListUtil.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                list.set(i, CryptUtil.decrypt_char(list.get(i)));
            }
        }
    }



    public static void main(String[] args) {
        //加密英文
        String data = "解密后:�dsfaa2132159-4331}~\u007F";
        String charResult = encrypt_char(data);
        System.out.println("加密后:" + charResult);
        //解密
        String charStr = decrypt_char(charResult);
        System.out.println("解密后:" + charStr);


        //加密中文
        data = "跳梁小豆tlxd666，z";
        String result = encrypt_char(data);
        System.out.println("加密后:" + result);
        String str1 = decrypt_char(result);
        System.out.println("解密后:" + str1);

//        int num = 32;
//        while (num <= 128) {
//            System.out.println((char) num + "  (Unicode编码对应的数字为：) " + num);
//            num++;
//        }
    }
}
