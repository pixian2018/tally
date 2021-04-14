package com.ztg.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 字符串有关帮助类 封装了第三方帮助类
 * @author: zhoutg
 * @time: 2018/8/6 11:15
 */
public class StringUtil {
    /**
     * 判断某字符串是否为空或长度为0或由空白符(whitespace) 构成
     *
     * @param str 需要判断的字符串
     * @return
     */
    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 判断某字符串是否不是为空或长度为0或由空白符(whitespace) 构成
     *
     * @param str 需要判断的字符串
     * @return
     */
    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 判断某字符串是否为空，为空的标准是 str==null 或 str.length()==0
     *
     * @param str 需要判断的字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断某字符串是否不为空，为空的标准是 str==null 或 str.length()==0
     *
     * @param str 需要判断的字符串
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotEmpty(str);
    }

    /**
     * 去除多余的末尾0和.
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
