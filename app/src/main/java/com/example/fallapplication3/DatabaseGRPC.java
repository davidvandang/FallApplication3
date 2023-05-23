package com.example.fallapplication3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.grpc.ManagedChannel;

public class DatabaseGRPC {
    private Context context;
    private ManagedChannel channel;

    public DatabaseGRPC(Context context, ManagedChannel channel) {
        this.context = context;
        this.channel = channel;
    }

    public void recordIncident(String userId, String date, String time) {
        // Insert into local SQLite database
        SQLiteOpenHelper dbHelper = new Database(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_USER_ID, userId);
        values.put(Database.COLUMN_DATE, date);
        values.put(Database.COLUMN_TIME, time);

        long newRowId = db.insert(Database.TABLE_NAME, null, values);

      /*  // Send to server-side database
        FallDetectionRequest request = FallDetectionRequest.newBuilder()
                .setUserId(userId)
                .setDate(date)
                .setTime(time)
                .build();

        DatabaseGRPC.FallDetectionBlockingStub blockingStub = FallDetectionGrpc.newBlockingStub(channel);
        FallDetectionReply response = blockingStub.sendFallDetection(request);

        Log.d("FallDetection", "Response: " + response.getMessage());*/
    }
}