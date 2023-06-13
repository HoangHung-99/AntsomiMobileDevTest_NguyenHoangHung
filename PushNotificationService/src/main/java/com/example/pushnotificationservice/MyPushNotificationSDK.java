package com.example.pushnotificationservice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class MyPushNotificationSDK {
    private static final String TAG = "MyPushNotificationSDK";
    private static MyCallback callback;

    public static void setPushNotificationCallback(MyCallback callback) {
        MyBroadcastReceiver.setBroadcastCallback(callback);
    }


    public static void init(Context context) {
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().subscribeToTopic("my_topic");
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    } else {
                        String token = task.getResult();
                        Log.d(TAG, "FCM registration token: " + token);
                        // Send token to your server or save it locally
                    }
                });
        Log.d(TAG, "callback: " + callback);
    }

    public static void handleNotification(Context context, Intent intent) {
        MyFirebaseMessagingService service = new MyFirebaseMessagingService();
        RemoteMessage remoteMessage = intent.getParcelableExtra("data");
        System.out.println("Test intent" + remoteMessage);
        Log.d(TAG, "callback intent: " + remoteMessage);
        if (remoteMessage != null) {
            service.onMessageReceived(remoteMessage);
        }
    }
}
