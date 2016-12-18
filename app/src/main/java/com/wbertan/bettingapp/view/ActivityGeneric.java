package com.wbertan.bettingapp.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.wbertan.bettingapp.R;
import com.wbertan.bettingapp.props.PropsBroadcastReceiver;

/**
 * Created by william.bertan on 18/12/2016.
 */

public abstract class ActivityGeneric extends AppCompatActivity {
    MainBroadcastReceiver mMainBroadcastReceiver = null;

    class MainBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context aContext, Intent aIntent) {
            Fragment fragment = processBroadcastReceiver(aIntent);
            if(fragment == null) {
                //wrong broadcast, need to exit the app for secure reasons
                finish();
                return;
            }
            fragment.setArguments(aIntent.getExtras());
            setFragment(fragment);
        }
    }

    protected abstract Fragment processBroadcastReceiver(Intent aIntent);

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PropsBroadcastReceiver.SIGN_IN);
        intentFilter.addAction(PropsBroadcastReceiver.SIGN_UP);
        mMainBroadcastReceiver = new MainBroadcastReceiver();
        registerReceiver(mMainBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        if(mMainBroadcastReceiver != null) {
            unregisterReceiver(mMainBroadcastReceiver);
        }
        super.onPause();
    }

    protected void setFragment(Fragment aFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (fragmentManager.getFragments() != null) {
            fragmentTransaction.addToBackStack(null);
        }
        Fragment fragmentExistente = fragmentManager.findFragmentByTag(aFragment.getClass().getSimpleName());
        if(fragmentExistente != null) {
            fragmentTransaction.replace(R.id.frameLayout, aFragment, aFragment.getClass().getSimpleName());
        } else {
            fragmentTransaction.add(R.id.frameLayout, aFragment, aFragment.getClass().getSimpleName());
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }
}
