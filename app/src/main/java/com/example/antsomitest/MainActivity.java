package com.example.antsomitest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.pushnotificationservice.MyCallback;
import com.example.pushnotificationservice.MyPushNotificationSDK;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyPushNotificationSDK instance = new MyPushNotificationSDK();
    private boolean isAppRunning = false;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyPushNotificationSDK.init(getApplicationContext());
        instance.setPushNotificationCallback(new MyCallback() {
            @Override
            public void onCallback() {
                Toast.makeText(getApplicationContext(), "Test Callback activated", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            MyPushNotificationSDK.handleNotification(getApplicationContext(), intent);

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