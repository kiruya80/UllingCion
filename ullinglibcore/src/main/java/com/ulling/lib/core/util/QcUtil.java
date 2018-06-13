package com.ulling.lib.core.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import static android.content.Context.WINDOW_SERVICE;

public class QcUtil {
    public static int NUMBER_TEN = 1;
    public static int NUMBER_HUNDRED = 2;
    public static int NUMBER_THOUSAND = 3;

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

    /**
     * number자리수 반올림
     * 1 : 십단위
     * 2 : 백단위
     *
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
        return df.format(num);
    }

    public static String toNumFormat(long num) {
        return df.format(num);
    }

    public static String toNumFormat(double num) {
        return df.format(num);
    }

    /**
     * 가격 콤마 추가
     */
    public static String setConvertPrice(String value, String unit) {
        int v = 0;
        if (Pattern.matches("^[0-9]*$", value)) {
            v = Integer.parseInt(value);
        }
        return NumberFormat.getNumberInstance(Locale.KOREA).format(v) + unit;
    }

    /**
     * 세자리 컴마 + 소수점 두자리
     *
     * @param num
     * @param decimal 소수점 표시
     * @return
     */
    public static String setConvertComma(float num, boolean decimal) {
        if (decimal) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            return decimalFormat.format(num);
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(num);
        }
    }

    public static String setConvertComma(double num, boolean decimal) {
        if (decimal) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            return decimalFormat.format(num);
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(num);
        }
    }

    public static String setConvertComma(int num, boolean decimal) {
        if (decimal) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            return decimalFormat.format(num);
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            return decimalFormat.format(num);
        }
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

    public static BigDecimal GetDoubleRemainder(double value1, double value2) {
        BigDecimal su1 = new BigDecimal(String.valueOf(value1));
        BigDecimal su2 = new BigDecimal(String.valueOf(value2));

        return su1.remainder(su2);  // 나머지 % 연산
    }

    public static void hiddenSoftKey(Context c, EditText editText) {
        if (editText == null)
            return;
        editText.clearFocus();
        InputMethodManager im = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static float getRatioLength(float length, float ratioWidth, float ratioHeight) {
        return length * ratioHeight / ratioWidth;

    }

    public static int getPixelToDp(Context context, float dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
        return px;
    }

    public static int getStatusBarHeight(final Context context) {
        final Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        } else {
            return (int) Math.ceil((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 24 : 25) *
                    resources.getDisplayMetrics().density);
        }
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isTablet(Context context) {
        boolean bTablet = false;
        int screenSizeType = context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSizeType) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                bTablet = true;
                break;
            default:
                break;
        }

        return bTablet;
    }

    public static int getScreenOrientation(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (context instanceof Activity) {
            ((Activity) context).getWindowManager()
                    .getDefaultDisplay().getMetrics(displayMetrics);

        } else if (context instanceof Application) {
            WindowManager windowManager = (WindowManager) context.getSystemService
                    (WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        } else if (context instanceof Service) {
            WindowManager windowManager = (WindowManager) context.getSystemService
                    (WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }

        return (displayMetrics.widthPixels < displayMetrics.heightPixels) ?
                Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }


    /**
     * 문자열내 특정 문자색 지정
     *
     * @param src
     * @param target
     * @param baseColor
     * @param targetColor
     * @return
     */
    public static String getHighlightedText(String src, String target, String baseColor, String targetColor) {
        String html = "";


        while (src != null && src.length() > 0 && src.indexOf(target) > -1) {
            int targetIdx = src.indexOf(target);

            if (targetIdx == 0) {
                html += "<font color=" + targetColor + ">";
                html += target;
                html += "</font>";
            } else {
                html += "<font color=" + baseColor + ">";
                html += src.substring(0, targetIdx);
                html += "</font>";

                html += "<font color=" + targetColor + ">";
                html += target;
                html += "</font>";
            }

            src = src.substring(targetIdx + target.length(), src.length());
        }

        if (src != null && src.length() > 0) {
            html += "<font color=" + baseColor + ">";
            html += src;
            html += "</font>";
        }

        return html;
    }

    public static boolean isIntegerFromStr(String num) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * TextView 취소선 적용
     *
     * @param tv
     */
    public static void setCancelLine(TextView tv) {
        tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    /**
     * Statusbar Drawable 및 Icon 밝기 적용
     *
     * @param activity
     * @param drawableId
     */
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public static void setStatusBarDrawable(Activity activity, int drawableId, boolean isDark, boolean isFullLayout) {
//        Window window = activity.getWindow();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            if (isFullLayout) {
//                window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            }
//
//            Drawable background = activity.getResources().getDrawable(drawableId);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(activity.getResources().getColor(R.color.transparent));
//            window.setNavigationBarColor(activity.getResources().getColor(R.color.transparent));
//            window.setBackgroundDrawable(background);
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            View decor = window.getDecorView();
//            if (isDark) {
//                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            } else {
//                decor.setSystemUiVisibility(0);
//            }
//        }
//    }

    /**
     * Status bar color 및 Icon 밝기 적용
     */
    public static void setChangeStatusBar(Activity act, int colorId, boolean isDark) {
        Window window = act.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(act, colorId));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = window.getDecorView();
            if (isDark) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decor.setSystemUiVisibility(0);
            }
        }
    }

    /**
     * 소프트키 유무체크
     *
     * @param context
     * @return
     */
    public static boolean hasSoftMenu(Context context) {
        //메뉴버튼 유무
        boolean hasMenuKey = ViewConfiguration.get(context.getApplicationContext()).hasPermanentMenuKey();

        //뒤로가기 버튼 유무
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        // lg폰 소프트키일 경우
// 삼성폰 등.. 메뉴 버튼, 뒤로가기 버튼 존재
        QcLog.e("hasSoftMenu ===== hasMenuKey = " + hasMenuKey + " , hasBackKey = " + hasBackKey);
//        return !hasMenuKey && !hasBackKey;
        if (!hasMenuKey && !hasBackKey) { // lg폰 소프트키일 경우
            return true;
        } else { // 삼성폰 등.. 메뉴 버튼, 뒤로가기 버튼 존재
            return false;
        }
    }


    /**
     * 소프트키 높이 가져오기
     *
     * @param context
     * @return
     */
    public static int getSoftMenuHeight(Context context) {
        if (!hasSoftMenu(context)) {
            QcLog.e("hasSoftMenu !hasSoftMenu(context =====  = ");
            return 0;
        }
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int softKeyHeight = 0;
        if (resourceId > 0) {
            softKeyHeight = resources.getDimensionPixelSize(resourceId);
        }
        return softKeyHeight;
    }
}
