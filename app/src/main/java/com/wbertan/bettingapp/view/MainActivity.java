package com.wbertan.bettingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wbertan.bettingapp.R;
import com.wbertan.bettingapp.fragments.FragmentExit;
import com.wbertan.bettingapp.fragments.FragmentGeneric;
import com.wbertan.bettingapp.fragments.FragmentLogin;
import com.wbertan.bettingapp.fragments.FragmentMain;
import com.wbertan.bettingapp.fragments.FragmentSignIn;
import com.wbertan.bettingapp.fragments.FragmentSignUp;
import com.wbertan.bettingapp.props.PropsBroadcastReceiver;
import com.wbertan.bettingapp.util.DialogUtil;

public class MainActivity extends ActivityGeneric {
    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setFragment(new FragmentExit());
        setFragment(new FragmentLogin());
    }

    @Override
    protected FragmentGeneric processBroadcastReceiver(Intent aIntent) {
        if(aIntent.getAction().equals(PropsBroadcastReceiver.SIGN_IN)) {
            return new FragmentSignIn();
        } else if(aIntent.getAction().equals(PropsBroadcastReceiver.SIGN_UP)) {
            return new FragmentSignUp();
        } else if(aIntent.getAction().equals(PropsBroadcastReceiver.LOGIN)) {
            removeAllFragments();
            return new FragmentMain();
        } else if(aIntent.getAction().equals(PropsBroadcastReceiver.PUSH_RELOAD_ODDS)) {
            Fragment fragment = recoverFragment(FragmentMain.class);
            if(fragment != null) {
                FragmentMain fragmentMain = (FragmentMain) fragment;
                fragmentMain.onResume();
                DialogUtil.instantiate(this).withTitle(getString(R.string.dialog_title_success)).withMessage(getString(R.string.dialog_message_load_bet_success)).show();
                return null; /* do nothing */
            }
        }
        return null;
    }
}
