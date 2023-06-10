package com.example.pushnotificationservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    private static MyCallback callback;
    public static void setBroadcastCallback(MyCallback callback) {
        MyBroadcastReceiver.callback = callback;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Intent: " + intent);
//        Toast.makeText(context.getApplicationContext(), "Callback activated", Toast.LENGTH_SHORT).show();
        if (callback != null) {
            callback.onCallback();
        }
    }
}
