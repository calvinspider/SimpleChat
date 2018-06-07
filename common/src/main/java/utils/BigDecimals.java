package utils;

import java.math.BigDecimal;


public class BigDecimals {

    /**
     * 与0比较
     */
    public static int compareZero(BigDecimal value) {
        return compare(value, BigDecimal.ZERO);
    }

    /**
     * 比较
     */
    public static int compare(BigDecimal value0, BigDecimal value1) {
        value0 = get(value0);
        value1 = get(value1);
        return value0.compareTo(value1);
    }

    /**
     * 是否等于0
     */
    public static boolean eq0(BigDecimal value) {
        return value != null && compareZero(value) == 0;
    }

    /**
     * 是否小于0
     */
    public static boolean lt0(BigDecimal value) {
        return value != null && compareZero(value) < 0;
    }

    /**
     * 是否大于0
     */
    public static boolean gt0(BigDecimal value) {
        return value != null && compareZero(value) > 0;
    }

    /**
     * 根据double创建
     */
    public static BigDecimal create(double value) {
        return new BigDecimal(value);
    }

    /**
     * 根据int创建
     */
    public static BigDecimal create(long value) {
        return new BigDecimal(value);
    }

    /**
     * 根据int创建
     */
    public static BigDecimal create(int value) {
        return new BigDecimal(value);
    }

    /**
     * 根据String创建
     */
    public static BigDecimal create(String value) {
        BigDecimal number = null;
        try {
            number = new BigDecimal(value);
        } catch (Exception e) {

        }
        return number;
    }

    /**
     * 数字转换，避免值为空
     */
    public static BigDecimal get(BigDecimal value) {
        return value == null ? create(0) : value;
    }

    /**
     * 相加，精度为2，四舍五入
     */
    public static BigDecimal add(BigDecimal value0, BigDecimal value1) {
        return add(value0, value1, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 相加
     */
    public static BigDecimal add(BigDecimal value0, BigDecimal value1, int scale, int roundingMode) {
        value0 = get(value0);
        value1 = get(value1);
        return value0.add(value1).setScale(scale, roundingMode);
    }

    /**
     * 相减，精度为2，四舍五入
     */
    public static BigDecimal subtract(BigDecimal value0, BigDecimal value1) {
        return subtract(value0, value1, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 相减
     */
    public static BigDecimal subtract(BigDecimal value0, BigDecimal value1, int scale, int roundingMode) {
        value0 = get(value0);
        value1 = get(value1);
        return value0.subtract(value1).setScale(scale, roundingMode);
    }

    /**
     * 相乘，精确到小数点2位，四舍五入
     */
    public static BigDecimal multiply(BigDecimal value0, BigDecimal value1) {
        return multiply(value0, value1, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 相乘
     */
    public static BigDecimal multiply(BigDecimal value0, BigDecimal value1, int scale, int roundingMode) {
        value0 = get(value0);
        value1 = get(value1);
        return value0.multiply(value1).setScale(scale, roundingMode);
    }

    /**
     * 相除，精确到小数点2位，四舍五入
     */
    public static BigDecimal divide(BigDecimal value0, BigDecimal value1) {
        return divide(value0, value1, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 相除
     */
    public static BigDecimal divide(BigDecimal value0, BigDecimal value1, int scale, int roundingMode) {
        value0 = get(value0);
        value1 = get(value1);
        return value0.divide(value1, scale, roundingMode).setScale(scale, roundingMode);
    }
}
