package com.example.pushnotificationservice;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    private static MyCallback callback;
    public static void setBroadcastCallback(MyCallback callback) {
        MyBroadcastReceiver.callback = callback;
    }

    public boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean foreground = isAppOnForeground(context.getApplicationContext());
        if (!foreground) {
            Intent i = new Intent();
            i.setClassName("com.example.antsomitest", "com.example.antsomitest.MainActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        Log.d(TAG, "TestCallBack: " + callback);
        if (callback != null) {
            callback.onCallback();
        }
    }
}
