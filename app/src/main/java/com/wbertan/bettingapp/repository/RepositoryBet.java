package com.wbertan.bettingapp.repository;

import com.wbertan.bettingapp.generic.CallbackError;
import com.wbertan.bettingapp.generic.ICallback;
import com.wbertan.bettingapp.generic.IRestCallback;
import com.wbertan.bettingapp.json.JsonDeserializerBet;
import com.wbertan.bettingapp.model.Bet;
import com.wbertan.bettingapp.rest.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class RepositoryBet extends RepositoryGeneric implements IRestCallback<String> {
    private RepositoryBet() {}

    private static class SingletonHolder {
        private static final RepositoryBet INSTANCE = new RepositoryBet();
    }

    public static RepositoryBet getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void getBets(ICallback<List<Bet>> aCallback) {
        RestClient.getInstance().doGet("https://api.myjson.com/bins/153gkl", this, aCallback);
    }

    @Override
    public void onSuccess(String aObject, ICallback aCallBackWhenFinish) {
        try {
            JSONObject jsonObject = new JSONObject(aObject);
            String timeStamp = jsonObject.getString("TimeStamp");
            JSONArray jsonArrayInPlayBets = jsonObject.getJSONArray("InPlayBets");
            List<Bet> listBet = JsonDeserializerBet.getInstance().parseFromJsonToList(jsonArrayInPlayBets);
            aCallBackWhenFinish.onSuccess(listBet);
        } catch (JSONException e) {
            e.printStackTrace();
            aCallBackWhenFinish.onError(new CallbackError(-1, e.getMessage()));
        }
    }

    @Override
    public void onError(CallbackError aCallbackError, ICallback aCallBackWhenFinish) {
        aCallBackWhenFinish.onError(aCallbackError);
    }
}
