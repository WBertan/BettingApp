package com.wbertan.bettingapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wbertan.bettingapp.props.PropsBroadcastReceiver;

/**
 * Created by william.bertan on 18/12/2016.
 */

public abstract class FragmentGeneric extends Fragment {
    public abstract String getActivityTitle();
    private static ProgressDialog mProgressDialog;

    @Override
    public void onResume() {
        super.onResume();
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
    }

    protected void showProgress() {
        showProgress("Preparing everything for you!", "Please wait a moment.");
    }

    protected void showProgress(@NonNull String aTitle, @NonNull String aMessage) {
        if(mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.setTitle(aTitle);
            mProgressDialog.setMessage(aMessage);
            mProgressDialog.show();
        }
    }

    protected void dismissProgress() {
        if(mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        dismissProgress();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        dismissProgress();
        super.onStop();
    }

    private void setActivityTitle(String aTitle) {
        getActivity().getActionBar().setTitle(getActivityTitle());
    }

    protected void setChildFragment(int aFragmentContainer, Fragment aFragment) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (childFragmentManager.getFragments() != null) {
            fragmentTransaction.addToBackStack(null);
        }
        Fragment fragmentExistente = childFragmentManager.findFragmentByTag(aFragment.getClass().getSimpleName());
        if(fragmentExistente != null) {
            fragmentTransaction.replace(aFragmentContainer, aFragment, aFragment.getClass().getSimpleName());
        } else {
            fragmentTransaction.add(aFragmentContainer, aFragment, aFragment.getClass().getSimpleName());
        }
        fragmentTransaction.commitAllowingStateLoss();
        childFragmentManager.executePendingTransactions();
    }

    protected Intent prepareAction(@PropsBroadcastReceiver String aAction) {
        Intent intent = new Intent();
        intent.setAction(aAction);
        return intent;
    }
}
