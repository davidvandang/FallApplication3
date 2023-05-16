package com.example.fallapplication3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class fallLogic implements SensorEventListener {
    private boolean isRecording = false;
    private int recordingCount = 0;
    private Context context;
    private String channelID;
    callNotification notificationClass;
    private static boolean fallDetectionStatus = true;
    private Sensor accelerometer;
    private callNotification notification;
    private Timer timer;

    public fallLogic(Context context, Timer timer) {
        this.context = context;
        this.timer = timer;
        this.accelerometer = accelerometer;
    }

    public void start() {
        isRecording = false;
        recordingCount = 0;
    }

    public void stop() {
        isRecording = false;
        recordingCount = 0;
    }

    public static void setfallDetectionStatus(boolean activated) {
        fallDetectionStatus = activated;
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER && fallDetectionStatus) {
            float[] values = sensorEvent.values;
            double x = values[0];
            double y = values[1];
            double z = values[2];

            // Calculate the magnitude of the acceleration vector
            double accelerationMagnitude = Math.sqrt(x * x + y * y + z * z);

            // Check if the acceleration is less than 5.0 units
            if (accelerationMagnitude > 16) {
                // A fall has occurred
                Log.d("fall", "A fall has occurred");
                Log.d("FallDetection", "Acceleration Magnitude: " + accelerationMagnitude);
                callNotification callNotificationObj = new callNotification(context, timer);
                String channelId = "1"; // Replace with your actual channel ID
                callNotificationObj.notificationAfterFall(context, channelId);
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Not needed for this application
    }
}