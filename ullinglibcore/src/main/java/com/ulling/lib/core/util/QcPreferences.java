/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */
package com.ulling.lib.core.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.reflect.TypeToken;
import com.ulling.lib.core.base.QcBaseApplication;
import com.ulling.lib.core.common.QcDefine;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : KILHO
 * @프로그램 ㄴ 프리퍼런스 헬퍼 클래스
 * @변경이력
 */
public class QcPreferences {
    private static QcPreferences SINGLE_U = null;
    /**
     * preference
     */
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    public static final boolean PREFER_LOG_FLAG = QcDefine.DEBUG_FLAG;
    private static Gson GSON;
    private String APP_NAME;
//	Type typeOfObject = new TypeToken<Object>(){}.getType();
//    public static synchronized void init(Context qCon) {
//        String APP_NAME = qCon.getResources().getString(R.string.app_name);
//        prefsInstances = getInstance(qCon, APP_NAME);
//        QcLog.i("ComplexPreferences init complete !");
//    }
//    public static synchronized void init(Context qCon, String APP_NAME) {
//        prefsInstances = getInstance(qCon, APP_NAME);
//        QcLog.i("ComplexPreferences init complete !");
//    }
//    public static synchronized QcPreferences getInstance(Context qCon, String APP_NAME) {
//        if (prefsInstances == null) {
//            prefsInstances = new QcPreferences(qCon, APP_NAME);
//            QcLog.i("ComplexPreferences init complete !");
//        }
//        return prefsInstances;
//    }
//    private QcPreferences(Context qCon, String APP_NAME) {
//        QcLog.e("QcPreferences == " +  APP_NAME);
////        prefs = qCon.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
//        prefs = QbaseApplication.getInstance().getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
//        editor = prefs.edit();
//        GsonBuilder gsonGsonBuilder = new GsonBuilder();
//        gsonGsonBuilder.setDateFormat(QcDefine.UTC_DATE_FORMAT);
//        gsonGsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
//        gsonGsonBuilder.setPrettyPrinting();
//        GSON = gsonGsonBuilder.create();
//            QcLog.i("ComplexPreferences init complete !");
//    }
//
//    public static synchronized QcPreferences getInstance(Context qCon) {
//        QcLog.e("getInstance == " + qCon.getPackageName());
//        if (prefsInstances == null) {
////            String APP_NAME = qCon.getResources().getString(R.string.app_name);
//            String APP_NAME = qCon.getPackageName();
//            prefsInstances = new QcPreferences(qCon, APP_NAME);
//        }
//        QcLog.e("QbaseApplication.getInstance().getPackageName() == " + QbaseApplication.getInstance().getPackageName());
//        return prefsInstances;
//    }

    public static synchronized QcPreferences getInstance() {
        if (QcBaseApplication.getInstance() == null) {
            QcLog.i("QcPreferences init failed !");
            return null;
        }
        if (SINGLE_U == null) {
            SINGLE_U = new QcPreferences();
        }
        return SINGLE_U;
    }

    private QcPreferences() {
        APP_NAME = QcBaseApplication.getInstance().getPackageName();
        prefs = QcBaseApplication.getInstance().getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        GsonBuilder gsonGsonBuilder = new GsonBuilder();
        gsonGsonBuilder.setDateFormat(QcDefine.UTC_DATE_FORMAT);
        gsonGsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
        gsonGsonBuilder.setPrettyPrinting();
        GSON = gsonGsonBuilder.create();
        QcLog.i("QcPreferences init Success !!" + APP_NAME);
    }

    public String getAPP_NAME() {
        return APP_NAME;
    }

    public void put(String key, String value) {
        editor.putString(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public void put(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public String get(String key, String defValue) {
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getString(key, defValue));
        return prefs.getString(key, defValue);
    }

    public int get(String key, int defValue) {
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getInt(key, defValue));
        return prefs.getInt(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getBoolean(key, defValue));
        return prefs.getBoolean(key, defValue);
    }

    public long getLong(String key, long defValue) {
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getLong(key, defValue));
        return prefs.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + prefs.getFloat(key, defValue));
        return prefs.getFloat(key, defValue);
    }

    public void putSet(String key, ArrayList<String> valueList) {
        valueList = new ArrayList<String>();
        Set<String> value = new HashSet<String>(valueList);
        editor.putStringSet(key, value);
        editor.commit();
        if (PREFER_LOG_FLAG)
            QcLog.e("key :" + key + " , value :" + value);
    }

    public Object putObject(String key, Object object) {
        if (object == null) {
            QcLog.e("Object is null");
            return null;
        }
        if (key.equals("") || key == null) {
            QcLog.e("Key is empty or null");
            return null;
        }
        if (object.equals("")) {
            editor.putString(key, "");
        } else {
            editor.putString(key, GSON.toJson(object));
        }
        return editor.commit();
    }

    public <T> T getObject(String key, Class<T> a) {
        String gson = null;
        try {
            gson = prefs.getString(key, null);
        } catch (Exception e) {
        }
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public <T> List<Object> getObjectSet(String key, Class<T> a) {
        List<Object> gsonList = new ArrayList<Object>();
        String gson = null;
        try {
            gson = prefs.getString(key, null);
        } catch (Exception e) {
        }
        if (gson == null) {
            return null;
        }
        Type type = new TypeToken<List<Object>>() {
        }.getType();
        gsonList = GSON.fromJson(gson, type);
        return gsonList;
    }

    public QcPreferences remove(String key) {
        editor.remove(key);
        editor.commit();
        return this;
    }
}
