package com.wbertan.bettingapp.props;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.wbertan.bettingapp.props.PropsRestRequestUrl.BET_ADD_TO_FAVORITE;
import static com.wbertan.bettingapp.props.PropsRestRequestUrl.BET_LOAD;
import static com.wbertan.bettingapp.props.PropsRestRequestUrl.BET_LOAD_FAVORITE;
import static com.wbertan.bettingapp.props.PropsRestRequestUrl.BET_REMOVE_FROM_FAVORITE;

/**
 * Created by william.bertan on 22/12/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef(value = {BET_LOAD, BET_LOAD_FAVORITE, BET_ADD_TO_FAVORITE, BET_REMOVE_FROM_FAVORITE})
public @interface PropsRestRequestUrl {
    String BET_LOAD = "https://api.myjson.com/bins/153gkl";
    String BET_LOAD_FAVORITE = "https://api.myjson.com/bins/153gkl";
    String BET_ADD_TO_FAVORITE = "https://api.myjson.com/bins/153gkl";
    String BET_REMOVE_FROM_FAVORITE = "https://api.myjson.com/bins/153gkl";
}
