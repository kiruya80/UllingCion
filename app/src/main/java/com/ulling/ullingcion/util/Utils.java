package com.ulling.ullingcion.util;

public class Utils {

    public static int NUMBER_TEN = 1;
    public static int NUMBER_HUNDRED = 2;
    public static int NUMBER_THOUSAND = 3;

    /**
     * number자리수 반올림
     * 1 : 십단위
     * 2 : 백단위
     * @param value
     * @param number
     * @return
     */
    public static double getRound(double value, int number) {
        if (number <= 0)
            return value;

        int intValue = (int) Math.ceil(value);
        int length = (int) (Math.log10(intValue) + 1);
        if (length <= number)
            return value;

        double roundNum = 1;
        for (int i = 0; i < number; i++) {
            roundNum = roundNum * 10d;
        }
        return Math.round(value / roundNum) * roundNum;
    }

}
