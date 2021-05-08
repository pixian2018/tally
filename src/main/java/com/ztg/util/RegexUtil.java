package com.ztg.util;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhoutg
 * @Description: 正则匹配工具类
 * @date 2020-12-16 11:28
 */
public class RegexUtil {

    /**
     * 判断字符串是否数值(正), ex: 10|20.1|0.83|10.|.99
     *
     * @param str
     * @return
     */
    public static boolean isNumbers(String str) {
        String regex = "[0-9]+|([0-9]+\\.)+[0-9]*|[0-9]*(\\.[0-9]+)+";
        return str.matches(regex);
    }

    /**
     * 是否有符合正则的数据（大小写不敏感）
     *
     * @param content 文本内容
     * @param regex   表达式
     * @return
     */
    public static Boolean getRegexRes(String content, String regex) {
        return getRegexResCommon(content, regex, false);
    }

    /**
     * 是否有符合正则的数据（大小写敏感）
     *
     * @param content 文本内容
     * @param regex   表达式
     * @param senstive 大小写是否敏感
     * @return
     */
    public static Boolean getRegexRes(String content, String regex, boolean senstive) {
        if (senstive) {
            return getRegexResCommon(content, regex, true);
        }
        return getRegexResCommon(content, regex, false);
    }

    /**
     * 是否有符合正则的数据（内部方法）
     *
     * @param content 文本内容
     * @param regex   表达式
     * @return
     */
    private static Boolean getRegexResCommon(String content, String regex, Boolean sensitive) {
        // 是否有符合的数据
        try {
            if (StringUtil.isBlank(content) || StringUtil.isBlank(regex)) {
                return false;
            }
            Pattern pattern = getPattern(regex, sensitive);
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 获取pattern
     *
     * @param regex 正则表达式
     * @param sensitive 大小写敏感
     * @return
     */
    public static Pattern getPattern(String regex, Boolean sensitive) {
        Pattern pattern = null;
        if (sensitive) {
            pattern = Pattern.compile(regex);
        } else {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        }
        return pattern;
    }

    /**
     * 根据正则获取指定分组数据（大小写不敏感）
     *
     * @param content  文本内容
     * @param regex    表达式
     * @param groupNum 获取第几个内容
     * @return
     */
    public static String getRegexData(String content, String regex, Integer groupNum) {
        return getRegexDataCommon(content, regex, groupNum, false);
    }

    /**
     * 根据正则获取指定分组数据（大小写敏感）
     *
     * @param content  文本内容
     * @param regex    表达式
     * @param groupNum 获取第几个内容
     * @return
     */
    public static String getRegexData(String content, String regex, Integer groupNum, Boolean sensitive) {
        if (sensitive) {
            return getRegexDataCommon(content, regex, groupNum, true);
        }
        return getRegexDataCommon(content, regex, groupNum, false);
    }

    /**
     * 根据正则获取指定分组数据（公共方法）
     *
     * @param content  文本内容
     * @param regex    表达式
     * @param groupNum 获取第几个内容
     * @return
     */
    private static String getRegexDataCommon(String content, String regex, Integer groupNum, Boolean sensitive) {
        // 获取符合的数据
        try {
            if (StringUtil.isBlank(content)) {
                return "";
            }
            Pattern pattern = getPattern(regex, sensitive);
            Matcher matcher = pattern.matcher(content);
            if (matcher.find() && matcher.groupCount() >= groupNum) {
                return matcher.group(groupNum);
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据正则获取所有分组数据（大小写不敏感）
     *
     * @param content
     * @param regex
     * @return
     */
    private static List<String> getRegexData(String content, String regex) {
        return getRegexDataCommon(content, regex, false);
    }

    /**
     * 根据正则获取所有分组数据（大小写敏感）
     *
     * @param content
     * @param regex
     * @return
     */
    private static List<String> getRegexData(String content, String regex, Boolean sensitive) {
        if (sensitive) {
            getRegexDataCommon(content, regex, true);
        }
        return getRegexDataCommon(content, regex, false);
    }

    /**
     * 根据正则获取所有分组数据（内部方法）
     *
     * @param content
     * @param regex
     * @return
     */
    private static List<String> getRegexDataCommon(String content, String regex, Boolean sensitive) {
        List<String> list = Lists.newArrayList();
        try {
            if (StringUtil.isBlank(content)) {
                return list;
            }
            Pattern pattern = getPattern(regex, sensitive);
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    list.add(matcher.group(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        String regex1 = "(血小板计数)\\s*(\\d+(\\.\\d+)?)";
        // System.out.println(getRegexData("血小板计数  30.3", regex1, 2));
        System.out.println(getRegexData("血小板计数  30.3", regex1));

        String s1 = "ABC";
        System.out.println(getRegexRes(s1, "Abc", true));
    }
}
