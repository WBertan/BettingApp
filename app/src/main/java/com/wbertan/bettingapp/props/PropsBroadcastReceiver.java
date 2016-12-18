package com.wbertan.bettingapp.props;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.wbertan.bettingapp.props.PropsBroadcastReceiver.SIGN_IN;
import static com.wbertan.bettingapp.props.PropsBroadcastReceiver.SIGN_UP;

/**
 * Created by william.bertan on 18/12/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef(value = {SIGN_IN, SIGN_UP})
public @interface PropsBroadcastReceiver {
    public static final String SIGN_IN = "com.wbertan.bettingapp.broadcast.SIGN_IN";
    public static final String SIGN_UP = "com.wbertan.bettingapp.broadcast.SIGN_UP";
}
