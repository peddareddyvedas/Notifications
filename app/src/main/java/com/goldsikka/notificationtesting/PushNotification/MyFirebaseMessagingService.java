package com.goldsikka.notificationtesting.PushNotification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.goldsikka.notificationtesting.AnswerReceiveActivity;
import com.goldsikka.notificationtesting.HomeActivity;
import com.goldsikka.notificationtesting.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Rise on 08/01/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Log.e("FirstCalledInfo", "splash screen");
        Log.e("FCMMESSAGE", "MESSAGE RECEIVED!!" + remoteMessage);

        Log.e(TAG, "FCMMESSAGE1 " + remoteMessage.getData());


        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
            Log.e("call", "ENTER1");

        }

        // Check if message contains a data payload.

        if (remoteMessage.getData() != null) {

            if (remoteMessage.getData().size() > 0) {
                Map<String, String> data = remoteMessage.getData();
                Log.e("FCMMESSAGEData", "" + data.keySet());
                String title = data.get("title");
                String message = data.get("body");
                handleDataMessage(title, message);
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            Log.e("call", "ENTER1");

            /*Notification notification = new NotificationCompat.Builder(this, "channel01")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Test")
                    .setContentText("You see me!")
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)   // heads-up
                    .build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0, notification);
*/

        } else {
            // If the app is in background, firebase itself handles the notification

            Log.e("call", "elseENTER1");
        }
    }

    private void handleDataMessage(String title, String message) {
        Log.e(TAG, "push json: " + message);
        Log.e(TAG, "push jsonTitle: " + title);

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            //     LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            Log.e("call", "ENTER5");

        } else {
            // app is in background, show the notification in notification tray
            Log.e("call", "ENTER4");

            Intent notificationIntent = new Intent(getApplicationContext(), HomeActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            notificationIntent.putExtra("message", message);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addParentStack(HomeActivity.class);
            stackBuilder.addNextIntent(notificationIntent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            int requestID = (int) System.currentTimeMillis();
            //PendingIntent contentIntent = PendingIntent.getActivity(this, requestID,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    getApplicationContext());

            showNotificationMessage(getApplicationContext(), message, notificationIntent, pendingIntent);

        }
    }


    /**
     * Showing notification with text only
     */

    private void showNotificationMessage(Context context, String message, Intent intent, PendingIntent pendinIntent) {
        notificationUtils = new NotificationUtils(context);
        notificationUtils.showNotificationMessage(message, intent, pendinIntent);
        Log.e("call", "ENTER7");
    }

    private void showNotificationMessagessages(Context context, String title, String message, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);
        //  notificationUtils.sendNotification(message, title);
        Log.e("call", "ENTER6");

    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent, imageUrl);
    }


}







