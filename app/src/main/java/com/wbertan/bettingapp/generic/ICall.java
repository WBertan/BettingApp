package com.wbertan.bettingapp.generic;

/**
 * Created by william.bertan on 18/12/2016.
 */

public interface ICall<T> {
    void onSuccess(T aObject);
    void onError(CallError aCallError);
}
