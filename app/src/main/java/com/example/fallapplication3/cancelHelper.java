package com.example.fallapplication3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class cancelHelper extends Service {
    public static final String ACTION_CANCEL_TIMER = "com.example.fallapplication3.CANCEL_TIMER";

    private callNotification notification;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("CancelHelperService", "Service has started..."); // new log statement

        if (ACTION_CANCEL_TIMER.equals(intent.getAction())) {
            notification.cancelButton();
        }
        return START_NOT_STICKY;
    }

    public void setNotification(callNotification notification) {
        this.notification = notification;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}