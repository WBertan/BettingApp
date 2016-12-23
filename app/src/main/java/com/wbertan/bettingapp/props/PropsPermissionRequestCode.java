package com.wbertan.bettingapp.props;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.wbertan.bettingapp.props.PropsPermissionRequestCode.FINGERPRINT;
/**
 * Created by william.bertan on 18/12/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef(value = {FINGERPRINT})
public @interface PropsPermissionRequestCode {
   int FINGERPRINT = 10;
}
