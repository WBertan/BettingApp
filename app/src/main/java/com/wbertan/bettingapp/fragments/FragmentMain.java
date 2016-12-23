package com.wbertan.bettingapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

public class FragmentMain extends FragmentGeneric implements ICallback<Bet>, View.OnClickListener {
    @Override
    public String getFragmentTitle() {
        return getString(R.string.fragment_main_title);
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
        FloatingActionButton floatingActionButtonFavorite = (FloatingActionButton) getView().findViewById(R.id.floatingActionButtonFavorite);
        floatingActionButtonFavorite.setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getSpanCount()));

        AdapterGeneric<Bet> adapter = new AdapterGeneric<>(R.layout.adapter_bet_item, BR.bet);
        recyclerView.setAdapter(adapter);

        showProgress();
        ControllerBet.getInstance().getBets(this, 0);
        ControllerBet.getInstance().getFavoriteBets(this, 1);
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
    public void onSuccess(int aRequestCode, Bet aObject) {
        /* do nothing */
        dismissProgress();
    }

    @Override
    public void onSuccess(int aRequestCode, List<Bet> aObject) {
        if(getView() == null) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
        AdapterGeneric<Bet> adapter = (AdapterGeneric<Bet>) recyclerView.getAdapter();
        if(aRequestCode == 0) { //LOAD
            adapter.addAll(aObject, true);
            DialogUtil.instantiate(getActivity()).withTitle(getString(R.string.dialog_title_success)).withMessage(getString(R.string.dialog_message_load_bet_success)).show();
        } if(aRequestCode == 1) { //LOAD FAVORITE
            adapter.insertOrUpdate(aObject);
        } else if(aRequestCode == 2) { //ADD TO FAVORITE
            adapter.notifyItemChanged(adapter.getSelectedPosition());
            DialogUtil.instantiate(getActivity()).withTitle(getString(R.string.dialog_title_success)).withMessage(getString(R.string.dialog_message_add_favorite_success)).show();
        } else if(aRequestCode == 3) { //REMOVE FROM FAVORITE
            adapter.notifyItemChanged(adapter.getSelectedPosition());
        }
        dismissProgress();
    }

    @Override
    public void onError(int aRequestCode, CallbackError aCallbackError) {
        DialogUtil.instantiate(getActivity()).withMessage(getString(R.string.dialog_message_error_embarassing)).show();
        dismissProgress();
    }

    @Override
    public void onClick(View view) {
        if(getView() == null) {
            return;
        }
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycleViewOdds);
        AdapterGeneric<Bet> adapter = (AdapterGeneric<Bet>) recyclerView.getAdapter();
        Bet bet = adapter.getSelectedItem();

        showProgress();
        if(bet.isFavorite()) {
            ControllerBet.getInstance().removeBetFromFavorites(this, bet, 3);
        } else {
            ControllerBet.getInstance().addBetToFavorites(this, bet, 2);
        }
    }
}