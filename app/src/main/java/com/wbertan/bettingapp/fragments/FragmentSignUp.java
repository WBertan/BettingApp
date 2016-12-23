package com.wbertan.bettingapp.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wbertan.bettingapp.R;
import com.wbertan.bettingapp.controller.ControllerUser;
import com.wbertan.bettingapp.props.PropsBroadcastReceiver;
import com.wbertan.bettingapp.props.PropsPermissionRequestCode;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class FragmentSignUp extends FragmentGeneric implements TextWatcher {
    private EditText mEditTextPassword;
    private EditText mEditTextPasswordVerify;

    @Override
    public String getFragmentTitle() {
        return getString(R.string.fragment_sign_up_title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_sign_up, aContainer, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getView() == null) {
            return;
        }
        TextView textViewFingerprint = (TextView) getView().findViewById(R.id.textViewFingerprint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FingerprintManager fingerprintManager = (FingerprintManager) getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.USE_FINGERPRINT}, PropsPermissionRequestCode.FINGERPRINT);
                return;
            }
            if (!fingerprintManager.isHardwareDetected() || !fingerprintManager.hasEnrolledFingerprints()) {
                textViewFingerprint.setVisibility(View.INVISIBLE);
            }
        }

        mEditTextPassword = (EditText) getView().findViewById(R.id.editTextPassword);
        mEditTextPassword.addTextChangedListener(this);
        mEditTextPasswordVerify = (EditText) getView().findViewById(R.id.editTextPasswordVerify);
        mEditTextPasswordVerify.addTextChangedListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int aRequestCode, @NonNull String[] aPermissions, @NonNull int[] aGrantResults) {
        super.onRequestPermissionsResult(aRequestCode, aPermissions, aGrantResults);
        if(aRequestCode == PropsPermissionRequestCode.FINGERPRINT) {
            onStart();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence aCharSequence, int aStart, int aCount, int aAfter) {
        /* do nothing */
    }

    @Override
    public void onTextChanged(CharSequence aCharSequence, int aStart, int aBefore, int aCount) {
        /* do nothing */
    }

    @Override
    public void afterTextChanged(Editable aEditable) {
        if(mEditTextPassword.getEditableText().toString().length() == 4
            && mEditTextPasswordVerify.getEditableText().toString().length() == 4) {
            if(!mEditTextPassword.getEditableText().toString().equals(mEditTextPasswordVerify.getEditableText().toString())) {
                mEditTextPassword.setText("");
                mEditTextPasswordVerify.setText("");
                mEditTextPassword.setError(getString(R.string.message_validation_check_both_password));
                mEditTextPassword.requestFocus();
                return;
            } else {
                ControllerUser.getInstance().savePassword(getActivity(), mEditTextPassword.getText().toString());
                ControllerUser.getInstance().saveSubscription(getActivity(), true);
                mEditTextPassword.setText("");
                mEditTextPasswordVerify.setText("");
                getActivity().sendBroadcast(prepareAction(PropsBroadcastReceiver.LOGIN));
            }
            mEditTextPassword.setError(null);
            mEditTextPasswordVerify.setError(null);
        }
    }
}
