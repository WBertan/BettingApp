package com.wbertan.bettingapp.props;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.wbertan.bettingapp.props.PropsRestRequestCode.BET_ADD_TO_FAVORITE;
import static com.wbertan.bettingapp.props.PropsRestRequestCode.BET_LOAD;
import static com.wbertan.bettingapp.props.PropsRestRequestCode.BET_LOAD_FAVORITE;
import static com.wbertan.bettingapp.props.PropsRestRequestCode.BET_REMOVE_FROM_FAVORITE;

/**
 * Created by william.bertan on 22/12/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef (value = {BET_LOAD, BET_LOAD_FAVORITE, BET_ADD_TO_FAVORITE, BET_REMOVE_FROM_FAVORITE})
public @interface PropsRestRequestCode {
    int BET_LOAD = 10;
    int BET_LOAD_FAVORITE = 20;
    int BET_ADD_TO_FAVORITE = 30;
    int BET_REMOVE_FROM_FAVORITE = 40;
}
