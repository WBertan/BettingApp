package com.wbertan.bettingapp.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.text.NumberFormat;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class Bet extends BaseObservable {
    private long mBetId;
    private double mStake;
    private String mOdds;
    private String mEvent;

    public Bet(){}

    public Bet(long aBetId, float aStake, String aOdds, String aEvent){
        this.mBetId = aBetId;
        this.mStake = aStake;
        this.mOdds = aOdds;
        this.mEvent = aEvent;
    }

    @Bindable
    public String getFormattedStake(){
        return NumberFormat.getCurrencyInstance().format(getStake());
    }

    public long getBetId() {
        return mBetId;
    }

    public void setBetId(long mBetId) {
        this.mBetId = mBetId;
    }

    public double getStake() {
        return mStake;
    }

    public void setStake(double stake) {
        this.mStake = stake;
    }

    public String getOdds() {
        return mOdds;
    }

    public void setOdds(String odds) {
        this.mOdds = odds;
    }

    public String getEvent() {
        return mEvent;
    }

    public void setEvent(String event) {
        this.mEvent = event;
    }
}
