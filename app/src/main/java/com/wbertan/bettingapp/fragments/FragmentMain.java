package com.wbertan.bettingapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.databinding.library.baseAdapters.BR;
import com.wbertan.bettingapp.R;
import com.wbertan.bettingapp.adapter.AdapterGeneric;
import com.wbertan.bettingapp.controller.ControllerBet;
import com.wbertan.bettingapp.generic.CallbackError;
import com.wbertan.bettingapp.generic.ICallback;
import com.wbertan.bettingapp.model.Bet;

import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class FragmentMain extends FragmentGeneric implements ICallback<List<Bet>> {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_main, aContainer, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getSpanCount()));

        setChildFragment(R.id.frameLayoutDashboard, new FragmentDashboard());

        ControllerBet.getInstance().getBets(this);
    }

    private int getSpanCount() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        Display display = wm.getDefaultDisplay();
        display.getSize(size);
        int width = size.x;
        float cardViewWidth = getActivity().getResources().getDimension(R.dimen.card_view_width);
        float cardViewPadding = getActivity().getResources().getDimension(R.dimen.card_view_padding);
        int spanCount = (int) (width/(cardViewWidth + cardViewPadding));
        return spanCount < 1 ? 1 : spanCount;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
                || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT
                || newConfig.orientation == Configuration.ORIENTATION_UNDEFINED){
            RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getSpanCount()));
        }
    }

    @Override
    public void onSuccess(List<Bet> aObject) {
        AdapterGeneric<Bet> adapter = new AdapterGeneric<>(R.layout.adapter_bet_item, BR.bet);
        adapter.addAll(aObject);
        RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(CallbackError aCallbackError) {

    }
}