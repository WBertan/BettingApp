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
import com.wbertan.bettingapp.util.DialogUtil;

import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class FragmentMain extends FragmentGeneric implements ICallback<List<Bet>> {
    @Override
    public String getActivityTitle() {
        return "Betting App!";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_main, aContainer, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getView() == null) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getSpanCount()));
    }

    @Override
    public void onResume() {
        super.onResume();
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
            if(getView() == null) {
                return;
            }
            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getSpanCount()));
        }
    }

    @Override
    public void onSuccess(List<Bet> aObject) {
        if(getView() == null) {
            return;
        }
        AdapterGeneric<Bet> adapter = new AdapterGeneric<>(R.layout.adapter_bet_item, BR.bet);
        adapter.addAll(aObject);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(CallbackError aCallbackError) {
        DialogUtil.instantiate(getActivity()).withMessage("How embarassing it is... I couldn't load your bet list! Please try again in some minutes...").show();
    }
}