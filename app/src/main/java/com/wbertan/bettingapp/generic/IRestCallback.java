package com.wbertan.bettingapp.generic;

import com.wbertan.bettingapp.props.PropsRestRequestCode;

/**
 * Created by william.bertan on 18/12/2016.
 */

public interface IRestCallback<T> {
    void onSuccess(@PropsRestRequestCode int aRestRequestCode, T aObject);
    void onError(@PropsRestRequestCode int aRestRequestCode, CallbackError aCallbackError);
}
