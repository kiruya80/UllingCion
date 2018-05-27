package com.ulling.lib.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class QcUtil {

    private static DecimalFormat df = new DecimalFormat("###,###.##");

    public static Date GetDate(int year, int month, int date, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, date, hour, minute, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static long GetUnixTime(long time) {
        return time / 1000;
    }


//    public void sendSMS(String coinSymbol) {
//        String smsNum = "010-3109-3050";
//        String smsText = coinSymbol + " 원화 상장 시그널 포착!";
//
//        if (smsNum.length() > 0 && smsText.length() > 0) {
//            sendSMS(smsNum, smsText);
//        }
//    }
//
//    private void sendSMS(String phoneNumber, String message) {
//        SmsManager sms = SmsManager.getDefault();
//        sms.sendTextMessage(phoneNumber, null, message, null, null);
//    }


    public static BigDecimal formatBigDecimal(double value) {
        try {
            return new BigDecimal(df.format(value));
        } catch (Exception e) {
            return new BigDecimal(0);
        }
    }

    public static String toNumFormat(int num) {
//        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }

    public static String toNumFormat(long num) {
//        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }

    public static String toNumFormat(double num) {
//        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }

    public static BigDecimal GetDoubleAdd(double value1, double value2) {
        BigDecimal su1 = new BigDecimal(String.valueOf(value1));
        BigDecimal su2 = new BigDecimal(String.valueOf(value2));

        return su1.add(su2);   // 더하기
    }

    public static BigDecimal GetDoubleSubtract(double value1, double value2) {
        BigDecimal su1 = new BigDecimal(String.valueOf(value1));
        BigDecimal su2 = new BigDecimal(String.valueOf(value2));

        return su1.subtract(su2);   // 빼기
    }

    public static BigDecimal GetDoubleMultiply(double value1, double value2) {
        BigDecimal su1 = new BigDecimal(String.valueOf(value1));
        BigDecimal su2 = new BigDecimal(String.valueOf(value2));

        return su1.multiply(su2);   // 곱하기
    }

    public static BigDecimal GetDoubleDivide(double value1, double value2) {
        BigDecimal su1 = new BigDecimal(String.valueOf(value1));
        BigDecimal su2 = new BigDecimal(String.valueOf(value2));

        return su1.divide(su2, BigDecimal.ROUND_UP);  // 나누기 - 소수점 4번째 자리에서 반올림.
    }

    public static BigDecimal GetDoubleDivide(double value1, double value2, int number) {
        BigDecimal su1 = new BigDecimal(String.valueOf(value1));
        BigDecimal su2 = new BigDecimal(String.valueOf(value2));

        return su1.divide(su2, number, BigDecimal.ROUND_UP);  // 나누기 - 소수점 n번째 자리에서 반올림.
    }
}
