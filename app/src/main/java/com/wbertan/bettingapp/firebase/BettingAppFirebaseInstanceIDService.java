package com.wbertan.bettingapp.firebase;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class BettingAppFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("BettingApp", "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceUserFavorite = database.getReference(FirebaseInstanceId.getInstance().getId()).child("token");
        databaseReferenceUserFavorite.child("firebase_message_token").setValue(refreshedToken);
        databaseReferenceUserFavorite.child("firebase_message_token_last_time").setValue(System.currentTimeMillis());
    }
}
