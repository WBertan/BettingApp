package com.wbertan.bettingapp.repository;

import com.wbertan.bettingapp.generic.ICall;
import com.wbertan.bettingapp.model.Bet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class RepositoryBet extends RepositoryGeneric{
    private RepositoryBet() {}

    private static class SingletonHolder {
        private static final RepositoryBet INSTANCE = new RepositoryBet();
    }

    public static RepositoryBet getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void getBets(ICall<List<Bet>> aCall) {
        List<Bet> listBets = new ArrayList<Bet>();
        listBets.add(new Bet(1, 10, "a", "i"));
        listBets.add(new Bet(1, 10, "b", "h"));
        listBets.add(new Bet(1, 10, "c", "g"));
        listBets.add(new Bet(1, 10, "d", "f"));
        listBets.add(new Bet(1, 10, "e", "e"));
        listBets.add(new Bet(1, 10, "f", "d"));
        listBets.add(new Bet(1, 10, "g", "c"));
        listBets.add(new Bet(1, 10, "h", "b"));
        listBets.add(new Bet(1, 10, "i", "a"));
        aCall.onSuccess(listBets);
    }
}
