package com.example.fallapplication3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class callNotification {
    private Context c;
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_NAME = "FALL_NOTIFICATION";
    private static final String ACTION_CANCEL = "com.example.fallapplication3.ACTION_CANCEL";
    private Timer timer;
    private boolean isCancelled = false; // Keep track of whether notification is cancelled

    // Add your BroadcastReceiver here
    private final BroadcastReceiver cancelReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent){
            if(ACTION_CANCEL.equals(intent.getAction())){
                cancelButton();
            }
        }
    };

    public callNotification(Context c, Timer timer){
        this.c = c;
        this.timer = timer;

        // Register your BroadcastReceiver
        IntentFilter filter = new IntentFilter(ACTION_CANCEL);
        this.c.registerReceiver(cancelReceiver, filter);
    }

    public void notificationAfterFall(Context c, String cID){
        Log.d("NotificationService", "Building the notification...");

        RemoteViews remoteViews = new RemoteViews(c.getPackageName(), R.layout.notification_timer);

        // Timer set to the time within the settings
        int timerSeconds = Timer.getTimerSeconds(c);
        remoteViews.setTextViewText(R.id.timerid, String.format("%02d", timerSeconds));

        // Cancel Button
        Intent cancelIntent = new Intent(c, cancelHelper.class);
        cancelIntent.setAction(cancelHelper.ACTION_CANCEL_TIMER);
        PendingIntent cancelPendingIntent = PendingIntent.getService(c, 0, cancelIntent, PendingIntent.FLAG_IMMUTABLE);
        remoteViews.setOnClickPendingIntent(R.id.cancelButton, cancelPendingIntent);

        // NotificationCompatBuilder
        NotificationCompat.Builder b = new NotificationCompat.Builder(c, cID)
                .setSmallIcon(R.drawable.ic_setting)
                .setContentTitle("TimerNotification")
                .setContentText("Your timer has finished.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCustomContentView(remoteViews)
                .setAutoCancel(true);

        // Build the notification
        Notification notification = b.build();

        // Get the NotificationManager service
        NotificationManager notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);

        // Launch the notification
        notificationManager.notify(NOTIFICATION_ID, notification);

        new CountDownTimer(timerSeconds*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Update the RemoteViews with the new time
                remoteViews.setTextViewText(R.id.timerid, String.format("%02d", millisUntilFinished / 1000));

                // Update the notification with the updated RemoteViews
                b.setCustomContentView(remoteViews);
                Notification notification = b.build();
                notificationManager.notify(NOTIFICATION_ID, notification);
            }

            public void onFinish() {
                afterTimerEnds();
            }
        }.start();
    }

    // Call this method after the timer runs out
    public void afterTimerEnds(){
        if(!isCancelled){
            callEmergency.makeEmergencyCall(c);
        }
    }

    public void cancelButton(){
        isCancelled = true; // Set isCancelled to true when the notification is cancelled
        timer.cancelTimer();

        NotificationManager notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);

        // Unregister your BroadcastReceiver
        c.unregisterReceiver(cancelReceiver);
    }
}
