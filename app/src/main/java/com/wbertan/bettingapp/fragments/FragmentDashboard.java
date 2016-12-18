package com.wbertan.bettingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wbertan.bettingapp.R;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class FragmentDashboard extends FragmentGeneric {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_dashboard, aContainer, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}