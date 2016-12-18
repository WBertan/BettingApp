package com.wbertan.bettingapp.controller;

import com.wbertan.bettingapp.generic.ICallback;
import com.wbertan.bettingapp.model.Bet;
import com.wbertan.bettingapp.repository.RepositoryBet;

import java.util.List;

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

    public void getBets(ICallback<List<Bet>> aCallback) {
        RepositoryBet.getInstance().getBets(aCallback);
    }
}
