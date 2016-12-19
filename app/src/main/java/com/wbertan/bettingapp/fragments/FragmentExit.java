package com.wbertan.bettingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wbertan.bettingapp.R;
import com.wbertan.bettingapp.controller.ControllerUser;
import com.wbertan.bettingapp.props.PropsBroadcastReceiver;

/**
 * Created by william.bertan on 17/12/2016.
 */

public class FragmentExit extends FragmentGeneric {
    @Override
    public String getActivityTitle() {
        return "Exit";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_exit, aContainer, false);
    }
}
