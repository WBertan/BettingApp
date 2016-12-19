package com.wbertan.bettingapp.props;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.wbertan.bettingapp.props.PropsBroadcastReceiver.LOGIN;
import static com.wbertan.bettingapp.props.PropsBroadcastReceiver.PUSH_RELOAD_ODDS;
import static com.wbertan.bettingapp.props.PropsBroadcastReceiver.SIGN_IN;
import static com.wbertan.bettingapp.props.PropsBroadcastReceiver.SIGN_UP;

/**
 * Created by william.bertan on 18/12/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef(value = {SIGN_IN, SIGN_UP, LOGIN, PUSH_RELOAD_ODDS})
public @interface PropsBroadcastReceiver {
    String SIGN_IN = "com.wbertan.bettingapp.broadcast.SIGN_IN";
    String SIGN_UP = "com.wbertan.bettingapp.broadcast.SIGN_UP";
    String LOGIN = "com.wbertan.bettingapp.broadcast.LOGIN";
    String PUSH_RELOAD_ODDS = "com.wbertan.bettingapp.broadcast.PUSH_RELOAD_ODDS";
}
