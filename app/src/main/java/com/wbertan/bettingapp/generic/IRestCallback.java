package com.wbertan.bettingapp.generic;

/**
 * Created by william.bertan on 18/12/2016.
 */

public interface IRestCallback<T> {
    void onSuccess(T aObject, ICallback aCallBackWhenFinish);
    void onError(CallbackError aCallbackError, ICallback aCallBackWhenFinish);
}
