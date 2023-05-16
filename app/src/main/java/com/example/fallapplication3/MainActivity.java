package com.example.fallapplication3;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    // Declare necessary variables and objects
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private fallLogic fallDetector;


    private boolean isOnline = true;

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    JournalFragment journalFragment = new JournalFragment();
    SettingFragment settingFragment = new SettingFragment();
    private static final int PERMISSION_REQUEST_CODE = 100;

    // Method to request necessary permissions if not already granted
    private void requestPermissionsIfNeeded() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.BODY_SENSORS}, PERMISSION_REQUEST_CODE);
        }
    }

    // Method to handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, proceed with your app's functionality
            } else {
                // Permissions denied, show a message or gracefully handle the situation
                Toast.makeText(this, "Permissions denied. The app might not work as expected.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    //Navigation Bar
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);

        // Set the home fragment as the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();

        // Initialize sensor manager and accelerometer sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Instantiate the fall class
        Timer timer = new Timer();
        fallDetector = new fallLogic(this, timer);

        // Set up the bottom navigation item selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.miHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                        return true;

                    case R.id.miJournal:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, journalFragment).commit();
                        return true;

                    case R.id.miSetting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, settingFragment).commit();
                        return true;

                }
                return false;
            }
        });
        requestPermissionsIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the accelerometer sensor listener
        sensorManager.registerListener(fallDetector, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the accelerometer sensor listener to save battery when the app is not in use
        sensorManager.unregisterListener(fallDetector);
    }

    // Method to check if the device is online (connected to the internet)
    public boolean isOnline() {
        return isOnline;
    }

    // Method to start fall detection when the device is online
    public void startFallDetection() {
        if (isOnline) {
            fallDetector.start();
        }
    }

    // Method to stop fall detection
    public void stopFallDetection() {
        fallDetector.stop();
    }

}
