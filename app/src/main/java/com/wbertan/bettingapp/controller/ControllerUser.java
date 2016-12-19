package com.wbertan.bettingapp.controller;

import android.content.Context;
import android.util.Base64;

import com.wbertan.bettingapp.preferences.SharedPreferencesManager;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class ControllerUser extends ControllerGeneric{
    private ControllerUser() {}

    private static class SingletonHolder {
        private static final ControllerUser INSTANCE = new ControllerUser();
    }

    public static ControllerUser getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public boolean isSubscripted(Context aContext) {
        return SharedPreferencesManager.getInstance(aContext).getBoolean("subscripted", false);
    }

    public boolean validatePassword(Context aContext, String aPassword) {
        String rightPassword = SharedPreferencesManager.getInstance(aContext).getString("user_password", null);
        return rightPassword != null && rightPassword.trim().equals(Base64.encodeToString(aPassword.getBytes(), Base64.DEFAULT).trim());
    }

    public void savePassword(Context aContext, String aPassword) {
        SharedPreferencesManager.getInstance(aContext).putString("user_password", Base64.encodeToString(aPassword.getBytes(), Base64.DEFAULT));
    }

    public void saveSubscription(Context aContext, boolean aIsSubscripted) {
        SharedPreferencesManager.getInstance(aContext).putBoolean("subscripted", aIsSubscripted);
    }
}