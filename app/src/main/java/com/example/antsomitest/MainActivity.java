package com.example.antsomitest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.pushnotificationservice.MyCallback;
import com.example.pushnotificationservice.MyPushNotificationSDK;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyPushNotificationSDK instance = new MyPushNotificationSDK();
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyPushNotificationSDK.init(getApplicationContext());
        instance.setPushNotificationCallback(new MyCallback() {
            @Override
            public void onCallback() {
                Log.d(TAG, "ActivityLog");
                Toast.makeText(getApplicationContext(), "Test Callback activated", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        Log.d(TAG, "intent Data: " + intent.getData());
        if (intent.getExtras() != null) {
            MyPushNotificationSDK.handleNotification(getApplicationContext(), intent);
        } else {
            Log.d(TAG, "intent.getExtras()");
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            MyPushNotificationSDK.handleNotification(getApplicationContext(), intent);
        }
    }
}