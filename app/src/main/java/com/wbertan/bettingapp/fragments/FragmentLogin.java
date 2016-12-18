package com.wbertan.bettingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wbertan.bettingapp.R;

/**
 * Created by william.bertan on 17/12/2016.
 */

public class FragmentLogin extends Fragment implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
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
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSignIn) {
            
        } else if(view.getId() == R.id.buttonSignUp) {

        }
    }
}
