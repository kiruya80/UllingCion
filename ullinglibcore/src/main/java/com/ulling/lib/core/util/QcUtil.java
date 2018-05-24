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


}
