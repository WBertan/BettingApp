package com.wbertan.bettingapp.controller;

import com.wbertan.bettingapp.generic.ICall;
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

    public void getBets(ICall<List<Bet>> aCall) {
        RepositoryBet.getInstance().getBets(aCall);
    }
}
