package com.wbertan.bettingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.wbertan.bettingapp.R;
import com.wbertan.bettingapp.adapter.AdapterGeneric;
import com.wbertan.bettingapp.controller.ControllerBet;
import com.wbertan.bettingapp.generic.CallError;
import com.wbertan.bettingapp.generic.ICall;
import com.wbertan.bettingapp.model.Bet;

import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class FragmentMain extends FragmentGeneric implements ICall<List<Bet>> {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_main, aContainer, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        setChildFragment(R.id.frameLayoutDashboard, new FragmentDashboard());

        ControllerBet.getInstance().getBets(this);
    }

    @Override
    public void onSuccess(List<Bet> aObject) {
        AdapterGeneric<Bet> adapter = new AdapterGeneric<>(R.layout.adapter_bet_item, BR.bet);
        adapter.addAll(aObject);
        RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(CallError aCallError) {

    }
}