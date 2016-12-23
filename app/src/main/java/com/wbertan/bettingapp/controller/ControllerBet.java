package com.wbertan.bettingapp.controller;

import com.wbertan.bettingapp.generic.ICallback;
import com.wbertan.bettingapp.model.Bet;
import com.wbertan.bettingapp.repository.RepositoryBet;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class ControllerBet extends ControllerGeneric{
    private ControllerBet() {}

    private static class SingletonHolder {
        private static final ControllerBet INSTANCE = new ControllerBet();
    }

    public static ControllerBet getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void getBets(ICallback<Bet> aCallback, int aRequestCode) {
        RepositoryBet.getInstance().addCallback(aCallback);
        RepositoryBet.getInstance().load(aRequestCode);
    }

    public void getFavoriteBets(ICallback<Bet> aCallback, int aRequestCode) {
        RepositoryBet.getInstance().addCallback(aCallback);
        RepositoryBet.getInstance().loadFavorite(aRequestCode);
    }

    public void addBetToFavorites(ICallback<Bet> aCallback, Bet aBet, int aRequestCode) {
        RepositoryBet.getInstance().addCallback(aCallback);
        RepositoryBet.getInstance().addToFavorite(aBet, aRequestCode);
    }

    public void removeBetFromFavorites(ICallback<Bet> aCallback, Bet aBet, int aRequestCode) {
        RepositoryBet.getInstance().addCallback(aCallback);
        RepositoryBet.getInstance().removeFromFavorite(aBet, aRequestCode);
    }
}
