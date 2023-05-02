package com.example.fallapplication3;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class fallLogic implements SensorEventListener {
    private boolean isRecording = false;
    private int recordingCount = 0;

    private Sensor accelerometer;

    public fallLogic() {
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float[] values = sensorEvent.values;
            double x = values[0];
            double y = values[1];
            double z = values[2];

            // Calculate the magnitude of the acceleration vector
            double accelerationMagnitude = Math.sqrt(x * x + y * y + z * z);

            // Check if the acceleration is less than 5.0 units
            if (accelerationMagnitude < 5.0) {
                // Start recording acceleration values
                isRecording = true;
                recordingCount = 0;
            } else if (isRecording) {
                // Check if the acceleration exceeds 16.5 units for a period of 45 samples
                recordingCount++;
                if (recordingCount >= 10 && accelerationMagnitude > 16.5) {
                    // A fall has occurred
                    Log.d("fall", "A fall has occurred");
                    Log.d("FallDetection", "Acceleration Magnitude: " + accelerationMagnitude);
                    isRecording = false;
                    recordingCount = 0;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Not needed for this application
    }
}