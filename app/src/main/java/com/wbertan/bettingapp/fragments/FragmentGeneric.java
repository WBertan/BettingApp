package com.wbertan.bettingapp.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class FragmentGeneric extends Fragment {
    public FragmentGeneric() {}

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
}
