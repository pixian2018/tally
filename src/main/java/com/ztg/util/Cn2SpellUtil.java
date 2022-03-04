package com.ztg.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * @Description: 汉字转拼音
 * @author: zhoutg
 * @time: 2018/12/14 14:20
 */
public class Cn2SpellUtil {

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines) {
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    if (null != pinyinArray && pinyinArray.length > 0 && StringUtil.isNotBlank(pinyinArray[0])) {
                        pinyinName += pinyinArray[0].charAt(0);
                    } else {
                        pinyinName += nameChar[i];
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    /**
     * 汉字转换位汉语拼音，英文字符不变(大写)
     *
     * @param chines 汉字
     * @return 拼音(大写)
     */
    public static String converterToSpell(String chines) {
        return converterToSpellByCaseType(chines, HanyuPinyinCaseType.UPPERCASE);
    }

    /**
     * 汉字转换位汉语拼音，英文字符不变(小写)
     *
     * @param chines 汉字
     * @return 拼音(小写)
     */
    public static String converterToSpellLow(String chines) {
        return converterToSpellByCaseType(chines, HanyuPinyinCaseType.LOWERCASE);
    }

    /**
     * 汉字转换位汉语拼音，英文字符不变
     *
     * @param chines 汉字
     * @return 拼音
     */
    private static String converterToSpellByCaseType(String chines, HanyuPinyinCaseType caseType) {
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(caseType);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    if (null != pinyinArray && pinyinArray.length > 0) {
                        pinyinName += pinyinArray[0];
                    } else {
                        pinyinName += nameChar[i];
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    public static void main(String[] args) {
        System.out.println(converterToFirstSpell("欢迎来到 βJava世界"));
        System.out.println(converterToSpell("欢迎来到 βJava世界"));
        System.out.println(converterToSpellLow("欢迎来到β Java世界"));
    }
}
