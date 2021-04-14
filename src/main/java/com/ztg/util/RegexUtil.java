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
     * 是否有符合正则的数据(公共方法)
     *
     * @param content 文本内容
     * @param regex   表达式
     * @return
     */
    public static Boolean getRegexRes(String content, String regex) {
        try {
            if (StringUtil.isBlank(content) || StringUtil.isBlank(regex)) {
                return false;
            }
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
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
     * 根据正则获取指定分组数据
     *
     * @param content  文本内容
     * @param regex    表达式
     * @param groupNum 获取第几个内容
     * @return
     */
    public static String getRegexData(String content, String regex, Integer groupNum) {
        try {
            if (StringUtil.isBlank(content)) {
                return "";
            }
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
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
     * 根据正则获取指定所有分组数据
     *
     * @param content
     * @param regex
     * @return
     */
    public static List<String> getRegexData(String content, String regex) {
        List<String> list = Lists.newArrayList();
        try {
            if (StringUtil.isBlank(content)) {
                return list;
            }
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
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
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobileNumber 手机号码
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^((13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证密码
     * 密码必须数字和字母组成，并且要同时含有数字和字母，且长度要在6-16位之间
     *
     * @param password 密码
     * @return
     */
    public static boolean checkPassWord(String password) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\W_]{6,16}$");
            Matcher matcher = regex.matcher(password);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
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
    }
}
