package com.wbertan.bettingapp.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.text.NumberFormat;

/**
 * Created by william.bertan on 18/12/2016.
 */
@IgnoreExtraProperties
public class Bet extends BaseObservable {
    @PropertyName(value = "betId")
    private long mBetId;
    @PropertyName(value = "Stake")
    private String mStake;
    @PropertyName(value = "Odds")
    private String mOdds;
    @PropertyName(value = "Event")
    private String mEvent;

    @Exclude
    private boolean favorite;

    public Bet(){}

    public Bet(long aBetId, String aStake, String aOdds, String aEvent){
        this.mBetId = aBetId;
        this.mStake = aStake;
        this.mOdds = aOdds;
        this.mEvent = aEvent;
    }

    @Bindable
    @Exclude
    public String getFormattedStake(){
        return NumberFormat.getCurrencyInstance().format(Double.valueOf(getStake()));
    }

    @Bindable
    @Exclude
    public int getImageViewFavoriteVisibiity() {
        return isFavorite() ? View.VISIBLE : View.GONE;
    }

    public long getBetId() {
        return mBetId;
    }

    public void setBetId(long mBetId) {
        this.mBetId = mBetId;
    }

    public String getStake() {
        return mStake;
    }

    public void setStake(String stake) {
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Bet)) {
            return false;
        }

        Bet bet = (Bet) object;

        return bet.getBetId() == getBetId();
    }
}
