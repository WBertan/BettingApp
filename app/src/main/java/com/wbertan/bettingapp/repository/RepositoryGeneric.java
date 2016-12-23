package com.wbertan.bettingapp.repository;

import com.wbertan.bettingapp.generic.CallbackError;
import com.wbertan.bettingapp.generic.ICallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class RepositoryGeneric<T> {
    private List<ICallback<T>> mListCallbacks = new ArrayList<>();

    public void addCallback(ICallback<T> aCallback) {
        if(!mListCallbacks.contains(aCallback)) {
            mListCallbacks.add(aCallback);
        }
    }

    public boolean removeCallback(ICallback<T> aCallback) {
        return mListCallbacks.remove(aCallback);
    }

    List<ICallback<T>> getListCallbacks() {
        return mListCallbacks;
    }

    void onRepositorySuccess(int aRequestCode, T aObject) {
        for(ICallback<T> callback : mListCallbacks) {
            callback.onSuccess(aRequestCode, aObject);
        }
    }

    void onRepositorySuccess(int aRequestCode, List<T> aObject) {
        for(ICallback<T> callback : mListCallbacks) {
            callback.onSuccess(aRequestCode, aObject);
        }
    }

    void onRepositoryError(int aRequestCode, CallbackError aCallbackError) {
        for(ICallback<T> callback : mListCallbacks) {
            callback.onError(aRequestCode, aCallbackError);
        }
    }
}
