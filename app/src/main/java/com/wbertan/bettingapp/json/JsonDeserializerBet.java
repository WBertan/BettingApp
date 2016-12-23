package com.wbertan.bettingapp.json;

import com.wbertan.bettingapp.model.Bet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class JsonDeserializerBet extends JsonDeserializerGeneric<Bet>{
    private JsonDeserializerBet() {}

    private static class SingletonHolder {
        private static final JsonDeserializerBet INSTANCE = new JsonDeserializerBet();
    }

    public static JsonDeserializerBet getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Bet parseFromJson(JSONObject aJsonObject) throws JSONException {
        if(aJsonObject == null) {
            return null;
        }
        Bet bet = new Bet();

        long betId = aJsonObject.getLong("betId");
        String stake = aJsonObject.getString("Stake");
        String odds = aJsonObject.getString("Odds");
        String event = aJsonObject.getString("Event");

        bet.setBetId(betId);
        bet.setStake(stake);
        bet.setOdds(odds);
        bet.setEvent(event);

        return bet;
    }

    @Override
    public String parseToJson(Bet aObject) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
