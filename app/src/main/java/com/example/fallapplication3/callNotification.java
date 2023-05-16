package com.example.fallapplication3;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class callNotification {
    private Context c;
    private static final int NOTIFICATION_ID = 1;
    private Timer timer;

    public callNotification(Context c, Timer timer) {
        this.c = c;
        this.timer = timer;
    }
    @SuppressLint("DefaultLocale")
    public void notificationAfterFall(Context c, String cID) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(c, cID);

        RemoteViews remoteViews = new RemoteViews(c.getPackageName(), R.layout.notification_timer);
        b.setCustomContentView(remoteViews);

        // Timer set to the time within the settings
        int timerSeconds = Timer.getTimerSeconds(c);
        remoteViews.setTextViewText(R.id.timer, String.format("%02d",timerSeconds));


        // Cancel Button
        Intent cancelIntent = new Intent(c, Timer.class);
        PendingIntent cancelPendingIntent = PendingIntent.getActivity(c, 0, cancelIntent, PendingIntent.FLAG_IMMUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.cancelButton, cancelPendingIntent);


        Notification notification = b.build();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(c,CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_popup_reminder) // Use the built-in timer icon
                .setContentTitle("Timer Notification")
                .setContentText("Your timer has finished.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

    }
    private void cancelButton() {
        timer.cancelTimer();

        NotificationManager notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);

    }

}
