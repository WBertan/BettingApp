package com.wbertan.bettingapp.repository;

import com.wbertan.bettingapp.BuildConfig;
import com.wbertan.bettingapp.model.Bet;

/**
 * Created by william.bertan on 18/12/2016.
 */

public abstract class RepositoryBet extends RepositoryGeneric<Bet> {
    RepositoryBet() {}

    private static class SingletonHolder {
        private static final RepositoryBet INSTANCE = BuildConfig.USING_FIREBASE ? new RepositoryBetFirebase() : new RepositoryBetRest();
    }

    public static RepositoryBet getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public abstract void load(int aRequestCode);

    public abstract void loadFavorite(int aRequestCode);

    public abstract void addToFavorite(Bet aBet, int aRequestCode);

    public abstract void removeFromFavorite(Bet aBet, int aRequestCode);
}
