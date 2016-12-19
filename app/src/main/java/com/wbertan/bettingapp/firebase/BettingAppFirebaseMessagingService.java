package com.wbertan.bettingapp.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wbertan.bettingapp.R;
import com.wbertan.bettingapp.props.PropsBroadcastReceiver;

/**
 * Created by william.bertan on 18/12/2016.
 */

public class BettingAppFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage aRemoteMessage) {
        super.onMessageReceived(aRemoteMessage);
        String title = aRemoteMessage.getNotification().getTitle();
        String body = aRemoteMessage.getNotification().getBody();
        String action = aRemoteMessage.getNotification().getClickAction();
        if(action != null && action.equals(PropsBroadcastReceiver.PUSH_RELOAD_ODDS)) {
            Intent intent = new Intent();
            intent.setAction(action);
            sendBroadcast(intent);
        }
        sendNotification(title, body, action);
    }

    private void sendNotification(String aMessageTitle, String aMessageBody, String aIntentAction) {
        Intent intent = new Intent();
        intent.setAction(aIntentAction);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500,500,500,500,500};

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.logo_betting_app)
                .setContentTitle(aMessageTitle)
                .setContentText(aMessageBody)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE,1,1)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
