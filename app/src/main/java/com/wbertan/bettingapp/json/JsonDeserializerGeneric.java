package com.wbertan.bettingapp.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

public abstract class JsonDeserializerGeneric<T> {
    public abstract T parseFromJson(JSONObject aJsonObject) throws JSONException ;
    public abstract String parseToJson(T aObject);

    public T parseFromJson(String aJson) throws JSONException {
        if(aJson == null || aJson.trim().length() < 1) {
            return null;
        }
        return parseFromJson(new JSONObject(aJson));
    }

    public List<T> parseFromJsonToList(JSONArray jsonArray) throws JSONException {
        if(jsonArray == null || jsonArray.length() < 1) {
            return null;
        }
        List<T> listObject = new ArrayList<>();
        int jsonArraySize = jsonArray.length();
        for(int counter = 0; counter < jsonArraySize; counter++) {
            T object = parseFromJson(jsonArray.getJSONObject(counter).toString());
            listObject.add(object);
        }
        return listObject;
    }
}
