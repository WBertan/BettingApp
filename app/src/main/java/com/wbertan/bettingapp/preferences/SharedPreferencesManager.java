package com.wbertan.bettingapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class SharedPreferencesManager {
    private static final String PREFERENCES_NAME = "Default_SharedPreferences";

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    private SharedPreferencesManager(Context aContext) {
        mContext = aContext;
    }

    public static SharedPreferencesManager getInstance(Context aContext) {
        return new SharedPreferencesManager(aContext);
    }

    private SharedPreferences getSharedPreferences() {
        if(mSharedPreferences == null) {
            mSharedPreferences = mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    private SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }

    public void putString(String aKey, String aValue) {
        getEditor().putString(aKey, aValue).commit();
    }

    public String getString(String aKey) {
        return getString(aKey, null);
    }

    public String getString(String aKey, String aDefaultValue) {
        return getSharedPreferences().getString(aKey, aDefaultValue);
    }

    public void putBoolean(String aKey, Boolean aValue) {
        getEditor().putBoolean(aKey, aValue).commit();
    }

    public Boolean getBoolean(String aKey) {
        return getBoolean(aKey, null);
    }

    public Boolean getBoolean(String aKey, Boolean aDefaultValue) {
        return getSharedPreferences().getBoolean(aKey, aDefaultValue);
    }

    public void putFloat(String aKey, Float aValue) {
        getEditor().putFloat(aKey, aValue).commit();
    }

    public Float getFloat(String aKey) {
        return getFloat(aKey, null);
    }

    public Float getFloat(String aKey, Float aDefaultValue) {
        return getSharedPreferences().getFloat(aKey, aDefaultValue);
    }

    public void putInt(String aKey, Integer aValue) {
        getEditor().putInt(aKey, aValue).commit();
    }

    public Integer getInt(String aKey) {
        return getInt(aKey, null);
    }

    public Integer getInt(String aKey, Integer aDefaultValue) {
        return getSharedPreferences().getInt(aKey, aDefaultValue);
    }

    public void putLong(String aKey, Long aValue) {
        getEditor().putLong(aKey, aValue).commit();
    }

    public Long getLong(String aKey) {
        return getLong(aKey, null);
    }

    public Long getLong(String aKey, Long aDefaultValue) {
        return getSharedPreferences().getLong(aKey, aDefaultValue);
    }

    public void remove(String aKey) {
        getSharedPreferences().edit().remove(aKey).apply();
    }
}