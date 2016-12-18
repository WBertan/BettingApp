package com.wbertan.bettingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wbertan.bettingapp.R;
import com.wbertan.bettingapp.generic.CallbackError;
import com.wbertan.bettingapp.generic.ICallback;
import com.wbertan.bettingapp.props.PropsBroadcastReceiver;

/**
 * Created by william.bertan on 17/12/2016.
 */

public class FragmentLogin extends FragmentGeneric implements View.OnClickListener, ICallback<String> {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_login, aContainer, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button buttonSignIn = (Button) getView().findViewById(R.id.buttonSignIn);
        Button buttonSignUp = (Button) getView().findViewById(R.id.buttonSignUp);

        buttonSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View aView) {
        Intent intent = new Intent();
        if(aView.getId() == R.id.buttonSignIn) {
            intent.setAction(PropsBroadcastReceiver.SIGN_IN);
        } else if(aView.getId() == R.id.buttonSignUp) {
            intent.setAction(PropsBroadcastReceiver.SIGN_UP);
        }
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onSuccess(String aObject) {
        Log.i("BettingApp", "onSuccess: " + aObject);
    }

    @Override
    public void onError(CallbackError aCallbackError) {
        Log.i("BettingApp", "onError: " + aCallbackError.getMessage());
    }
}
