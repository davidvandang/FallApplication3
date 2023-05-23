package com.example.fallapplication3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class callEmergency {
    private static final String EMERGENCY_NUMBER = "07874063378"; // Replace with the number you need to call

    public static void makeEmergencyCall(Context context) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + EMERGENCY_NUMBER));
        context.startActivity(callIntent);
    }
}