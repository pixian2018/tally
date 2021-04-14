package com.ztg.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @Description: 大数工具类
 * @author: zhoutg
 * @time: 2020/4/14 10:21
 */
public class BigDecimalUtil {

    public static int scale = 2;
    public static int roundingMode = BigDecimal.ROUND_HALF_UP;

    /**
     * a < b
     * @param a
     * @param b
     * @return
     */
    public static Boolean lt(BigDecimal a, BigDecimal b) {
        Boolean res = false;
        if (a.compareTo(b) == -1) {
            res = true;
        }
        return res;
    }

    /**
     * a = b
     *
     * @param a
     * @param b
     * @return
     */
    public static Boolean eq(BigDecimal a, BigDecimal b) {
        Boolean res = false;
        if (a.compareTo(b) == 0) {
            res = true;
        }
        return res;

    }

    /**
     * a > b
     *
     * @param a
     * @param b
     * @return
     */
    public static Boolean gt(BigDecimal a, BigDecimal b) {
        Boolean res = false;
        if (a.compareTo(b) == 1) {
            res = true;
        }
        return res;

    }

    /**
     * a >= b
     * @param a
     * @param b
     * @return
     */
    public static Boolean ge(BigDecimal a, BigDecimal b) {
        Boolean res = false;
        if (a.compareTo(b) > -1) {
            res = true;
        }
        return res;
    }

    /**
     * a <= b
     * @param a
     * @param b
     * @return
     */
    public static Boolean le(BigDecimal a, BigDecimal b) {
        Boolean res = false;
        if (a.compareTo(b) < 1) {
            res = true;
        }
        return res;
    }

    /**
     * 保留小数后是否相同
     *
     * @param a
     * @param b
     * @param scaleInt
     * @return
     */
    public static boolean isEquals(BigDecimal a, BigDecimal b, int scaleInt) {
        return a.setScale(scaleInt, roundingMode).equals(
                b.setScale(scaleInt, roundingMode)
        );
    }

    /**
     * 保留小数后是否相同——重载
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isEquals(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) {
            return true;
        }
        if ((a == null && b != null) || (a != null && b == null)) {
            return false;
        }
        return a.setScale(scale, roundingMode).equals(
                b.setScale(scale, roundingMode)
        );
    }

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("#.00");
        DecimalFormat df2 = new DecimalFormat("#0.00");
        BigDecimal a = new BigDecimal(0.55);
        BigDecimal b = new BigDecimal(11.55);
        System.out.println(df.format(a));
        System.out.println(df2.format(a));
        System.out.println(df.format(b));
        System.out.println(df2.format(b));

        System.out.println("====a小于b====");
        System.out.println(lt(new BigDecimal(1.1), new BigDecimal(1)) == false);
        System.out.println(lt(new BigDecimal(1), new BigDecimal(1)) == false);
        System.out.println(lt(new BigDecimal(0.9), new BigDecimal(1)) == true);

        System.out.println("====a等于b====");
        System.out.println(eq(new BigDecimal(1.1), new BigDecimal(1)) == false);
        System.out.println(eq(new BigDecimal(1), new BigDecimal(1)) == true);
        System.out.println(eq(new BigDecimal(0.9), new BigDecimal(1)) == false);

        System.out.println("====a大于b====");
        System.out.println(gt(new BigDecimal(1.1), new BigDecimal(1)) == true);
        System.out.println(gt(new BigDecimal(1), new BigDecimal(1)) == false);
        System.out.println(gt(new BigDecimal(0.9), new BigDecimal(1)) == false);

        System.out.println("====a大于等于b====");
        System.out.println(ge(new BigDecimal(1.1), new BigDecimal(1)) == true);
        System.out.println(ge(new BigDecimal(1), new BigDecimal(1)) == true);
        System.out.println(ge(new BigDecimal(0.9), new BigDecimal(1)) == false);

        System.out.println("====a小于等于b====");
        System.out.println(le(new BigDecimal(1.1), new BigDecimal(1)) == false);
        System.out.println(le(new BigDecimal(1), new BigDecimal(1)) == true);
        System.out.println(le(new BigDecimal(0.9), new BigDecimal(1)) == true);

    }
}
