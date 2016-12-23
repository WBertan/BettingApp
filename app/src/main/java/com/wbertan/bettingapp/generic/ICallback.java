package com.wbertan.bettingapp.generic;

import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

public interface ICallback<T> {
    void onSuccess(int aRequestCode, T aObject);
    void onSuccess(int aRequestCode, List<T> aObject);
    void onError(int aRequestCode, CallbackError aCallbackError);
}
