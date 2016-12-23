package com.wbertan.bettingapp.generic;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class CallbackError {
    private long mErrorCode;
    private String mMessage;

    public CallbackError(long aErrorCode, String aMessage) {
        mErrorCode = aErrorCode;
        mMessage = aMessage;
    }

    public long getErrorCode() {
        return mErrorCode;
    }

    public String getMessage() {
        return mMessage;
    }
}
