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
import android.widget.ImageView;
import android.widget.TextView;

import com.wbertan.bettingapp.R;
import com.wbertan.bettingapp.controller.ControllerFingerprint;
import com.wbertan.bettingapp.controller.ControllerUser;
import com.wbertan.bettingapp.generic.CallbackError;
import com.wbertan.bettingapp.generic.ICallback;
import com.wbertan.bettingapp.props.PropsBroadcastReceiver;
import com.wbertan.bettingapp.props.PropsPermissionRequestCode;
import com.wbertan.bettingapp.util.DialogUtil;

import java.util.List;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class FragmentSignIn extends FragmentGeneric implements TextWatcher, View.OnClickListener, ICallback<Boolean> {
    private EditText mEditTextPassword;
    @Override
    public String getFragmentTitle() {
        return getString(R.string.fragment_sign_in_title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater aInflater, @Nullable ViewGroup aContainer, @Nullable Bundle aSavedInstanceState) {
        return aInflater.inflate(R.layout.fragment_sign_in, aContainer, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getView() == null) {
            return;
        }
        TextView textViewFingerprint = (TextView) getView().findViewById(R.id.textViewFingerprint);
        ImageView imageViewFingerprint = (ImageView) getView().findViewById(R.id.imageViewFingerprint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FingerprintManager fingerprintManager = (FingerprintManager) getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.USE_FINGERPRINT}, PropsPermissionRequestCode.FINGERPRINT);
                return;
            }
            if (fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints()) {
                imageViewFingerprint.setOnClickListener(this);
            } else {
                textViewFingerprint.setVisibility(View.INVISIBLE);
                imageViewFingerprint.setVisibility(View.INVISIBLE);
            }
        } else {
            textViewFingerprint.setVisibility(View.INVISIBLE);
            imageViewFingerprint.setVisibility(View.INVISIBLE);
        }

        mEditTextPassword = (EditText) getView().findViewById(R.id.editTextPassword);
        mEditTextPassword.addTextChangedListener(this);
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
        if(aEditable.toString().length() == 4) {
            if(!ControllerUser.getInstance().validatePassword(getActivity(), mEditTextPassword.getEditableText().toString())) {
                mEditTextPassword.setText(null);
                mEditTextPassword.setError(getString(R.string.message_validate_wrong_password));
                return;
            } else {
                mEditTextPassword.setText(null);
                getActivity().sendBroadcast(prepareAction(PropsBroadcastReceiver.LOGIN));
            }
            mEditTextPassword.setError(null);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.imageViewFingerprint) {
            showProgress(getString(R.string.fingerprint_progress_validation_title), getString(R.string.fingerprint_progress_validation_message));
            new ControllerFingerprint().validateFingerprint(getActivity(), this, 0);
        }
    }

    @Override
    public void onSuccess(int aRequestCode, Boolean aObject) {
        dismissProgress();
        if(aObject) {
            getActivity().sendBroadcast(prepareAction(PropsBroadcastReceiver.LOGIN));
        } else {
            DialogUtil.instantiate(getActivity()).withMessage(getString(R.string.fingerprint_message_validation_failed)).show();
        }
    }

    @Override
    public void onSuccess(int aRequestCode, List<Boolean> aObject) {
        /* do nothing */
    }

    @Override
    public void onError(int aRequestCode, CallbackError aCallbackError) {
        dismissProgress();
        DialogUtil.instantiate(getActivity()).withMessage(aCallbackError.getMessage()).show();
    }
}
