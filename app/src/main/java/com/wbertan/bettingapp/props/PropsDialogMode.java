package com.wbertan.bettingapp.props;

import android.support.annotation.IntDef;

import com.wbertan.bettingapp.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.wbertan.bettingapp.props.PropsDialogMode.MODE_OK;
import static com.wbertan.bettingapp.props.PropsDialogMode.MODE_YES_NO;

/**
 * Created by william.bertan on 18/12/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef(value = {MODE_OK, MODE_YES_NO})
public @interface PropsDialogMode {
    public static final int MODE_OK = R.layout.dialog_ok;
    public static final int MODE_YES_NO = R.layout.dialog_yes_no;
}
