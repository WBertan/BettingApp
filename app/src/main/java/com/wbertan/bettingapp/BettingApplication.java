package com.wbertan.bettingapp;

import android.app.Application;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by william.bertan on 22/12/2016.
 */

public class BettingApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAnalytics.getInstance(this);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("BettingApp", "Firebase token: " + token);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceUserFavorite = database.getReference(FirebaseInstanceId.getInstance().getId()).child("token");
        databaseReferenceUserFavorite.child("firebase_message_token").setValue(token);
        databaseReferenceUserFavorite.child("firebase_message_token_last_time").setValue(System.currentTimeMillis());
    }
}
