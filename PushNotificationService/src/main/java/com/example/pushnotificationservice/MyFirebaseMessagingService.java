package com.example.pushnotificationservice;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingService";
    private static MyCallback callback;

    public static void setCallback(MyCallback callback) {
        MyFirebaseMessagingService.callback = callback;
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {
            Map<String, String> data = remoteMessage.getData();
            String title = data.get("title");
            String body = data.get("body");
            String deepLink = data.get("deepLink");
            String img_url = data.get("img");
            URL url = new URL(img_url);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Log.d(TAG, "Message Notification Payload Title: " + title);
            Log.d(TAG, "Message Notification Payload Body: " + body);
            Log.d(TAG, "Message Notification Payload deepLink: " + deepLink);
            sendNotification(this, title, body, deepLink, image);
        } catch (IOException e) {
            System.out.println((e));
        }

//        Notification notification = new Notification();
//        notification.setTitle(title);
//        notification.setMessage(message);
//        notification.setDeepLink(deepLink);

//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//            Map<String, String> dataPayload = remoteMessage.getData();
//            Log.d(TAG, "dataPayload: " + dataPayload);
//            String title = dataPayload.get("title");
//            String body = dataPayload.get("body");
//            Log.d(TAG, "Message Notification Payload Title: " + title);
//            Log.d(TAG, "Message Notification Payload Body: " + body);
//            sendNotification(body);
//        }

//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            Log.d(TAG, "Message Notification Body: " + callback);
//            // Handle notification payload here
//            Log.d(TAG, "remoteMessage.getNotification().getBody(): " + remoteMessage.getNotification());
//            sendNotification(remoteMessage.getNotification().getBody());
//        }
    }

    private void sendNotification(Context context, String title, String body, String deepLink, Bitmap image) {
        String channelId = "fcm_default_channel";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Any description can be given!");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_stat_notification)
                        .setAutoCancel(true)
                        .setPriority(android.app.Notification.PRIORITY_MAX)
                        .setDefaults(android.app.Notification.DEFAULT_ALL)
                        .setSound(defaultSoundUri)
                        .setLargeIcon(image)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(image)
                                .bigLargeIcon(null));



        Intent tappingIntent = new Intent(this, MyBroadcastReceiver.class);

        tappingIntent.setAction(Intent.ACTION_VIEW);
        tappingIntent.setData(Uri.parse(deepLink));
        tappingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent tapNotificationIntent = PendingIntent.getBroadcast(context, 0, tappingIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

//        Intent callbackBroadcastIntent = new Intent(this, MyBroadcastReceiver.class);
//        PendingIntent callbackBroadcastPendingIntent = PendingIntent.getBroadcast(context, 0, callbackBroadcastIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        notificationBuilder
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(tapNotificationIntent);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
