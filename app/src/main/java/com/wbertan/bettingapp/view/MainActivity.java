package com.wbertan.bettingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wbertan.bettingapp.R;
import com.wbertan.bettingapp.fragments.FragmentLogin;
import com.wbertan.bettingapp.fragments.FragmentMain;
import com.wbertan.bettingapp.props.PropsBroadcastReceiver;

public class MainActivity extends ActivityGeneric {
    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(R.layout.activity_login);

        setFragment(new FragmentLogin());
    }

    @Override
    protected Fragment processBroadcastReceiver(Intent aIntent) {
        if(aIntent.getAction().equals(PropsBroadcastReceiver.SIGN_IN)) {
            return new FragmentMain();
        } else if(aIntent.getAction().equals(PropsBroadcastReceiver.SIGN_UP)) {
            return new FragmentMain();
        }
        return null;
    }
}
