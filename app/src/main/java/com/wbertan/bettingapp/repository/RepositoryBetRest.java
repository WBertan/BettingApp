package com.wbertan.bettingapp.repository;

import com.google.firebase.iid.FirebaseInstanceId;
import com.wbertan.bettingapp.generic.CallbackError;
import com.wbertan.bettingapp.generic.IRestCallback;
import com.wbertan.bettingapp.json.JsonDeserializerBet;
import com.wbertan.bettingapp.model.Bet;
import com.wbertan.bettingapp.props.PropsRestRequestCode;
import com.wbertan.bettingapp.props.PropsRestRequestUrl;
import com.wbertan.bettingapp.rest.RestClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

class RepositoryBetRest extends RepositoryBet implements IRestCallback<String> {
    private int mRequestCode = 0;
    RepositoryBetRest() {
        super();
    }

    @Override
    public void load(int aRequestCode) {
        mRequestCode = aRequestCode;
        RestClient.getInstance().doGet(PropsRestRequestUrl.BET_LOAD, this, PropsRestRequestCode.BET_LOAD);
    }

    @Override
    public void loadFavorite(int aRequestCode) {
        mRequestCode = aRequestCode;
        RestClient.getInstance().doGet(PropsRestRequestUrl.BET_LOAD_FAVORITE, this, PropsRestRequestCode.BET_LOAD_FAVORITE);
    }

    @Override
    public void addToFavorite(Bet aBet, int aRequestCode) {
        mRequestCode = aRequestCode;
        List<NameValuePair> valuePairList = new ArrayList<>();
        valuePairList.add(new BasicNameValuePair("betId", String.valueOf(aBet.getBetId())));
        valuePairList.add(new BasicNameValuePair("user", FirebaseInstanceId.getInstance().getId()));

        RestClient.getInstance().doPost(PropsRestRequestUrl.BET_ADD_TO_FAVORITE, valuePairList, this, PropsRestRequestCode.BET_ADD_TO_FAVORITE);
    }

    @Override
    public void removeFromFavorite(Bet aBet, int aRequestCode) {
        mRequestCode = aRequestCode;
        List<NameValuePair> valuePairList = new ArrayList<>();
        valuePairList.add(new BasicNameValuePair("betId", String.valueOf(aBet.getBetId())));
        valuePairList.add(new BasicNameValuePair("user", FirebaseInstanceId.getInstance().getId()));

        RestClient.getInstance().doPost(PropsRestRequestUrl.BET_REMOVE_FROM_FAVORITE, valuePairList, this, PropsRestRequestCode.BET_REMOVE_FROM_FAVORITE);
    }

    @Override
    public void onSuccess(int aRestRequestCode, String aObject) {
        if(aRestRequestCode == PropsRestRequestCode.BET_LOAD) {
            try {
                JSONObject jsonObject = new JSONObject(aObject);
                String timeStamp = jsonObject.getString("TimeStamp");
                JSONArray jsonArrayInPlayBets = jsonObject.getJSONArray("InPlayBets");
                List<Bet> listBet = JsonDeserializerBet.getInstance().parseFromJsonToList(jsonArrayInPlayBets);
                onRepositorySuccess(mRequestCode, listBet);
            } catch (JSONException e) {
                e.printStackTrace();
                onRepositoryError(mRequestCode, new CallbackError(-1, e.getMessage()));
            }
        } else if(aRestRequestCode == PropsRestRequestCode.BET_LOAD_FAVORITE) {
            try {
                JSONObject jsonObject = new JSONObject(aObject);
//                String timeStamp = jsonObject.getString("TimeStamp");
                JSONArray jsonArrayInPlayBets = jsonObject.getJSONArray("InPlayBets");
                List<Bet> listBet = JsonDeserializerBet.getInstance().parseFromJsonToList(jsonArrayInPlayBets);
                onRepositorySuccess(mRequestCode, listBet);
            } catch (JSONException e) {
                e.printStackTrace();
                onRepositoryError(mRequestCode, new CallbackError(-1, e.getMessage()));
            }
        } else if(aRestRequestCode == PropsRestRequestCode.BET_ADD_TO_FAVORITE) {
            onRepositorySuccess(mRequestCode, (Bet) null);
        } else if(aRestRequestCode == PropsRestRequestCode.BET_REMOVE_FROM_FAVORITE) {
            onRepositorySuccess(mRequestCode, (Bet) null);
        }
    }

    @Override
    public void onError(int aRestRequestCode, CallbackError aCallbackError) {
        if(aRestRequestCode == PropsRestRequestCode.BET_LOAD) {
            onRepositoryError(mRequestCode, aCallbackError);
        } else if(aRestRequestCode == PropsRestRequestCode.BET_LOAD_FAVORITE) {
            onRepositoryError(mRequestCode, aCallbackError);
        } else if(aRestRequestCode == PropsRestRequestCode.BET_ADD_TO_FAVORITE) {
            onRepositoryError(mRequestCode, aCallbackError);
        } else if(aRestRequestCode == PropsRestRequestCode.BET_REMOVE_FROM_FAVORITE) {
            onRepositoryError(mRequestCode, aCallbackError);
        }
    }
}
