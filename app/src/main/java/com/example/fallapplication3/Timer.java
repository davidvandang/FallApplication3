package com.example.fallapplication3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;

public class Timer {
    private static final int timer_default = 10;
    private int timeLeft;
    private static final String timer_Key = "timer_key";
    private static CountDownTimer timer;

    public Timer(){
        this.timeLeft = timer_default;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    // Get the seconds on the current setting for a new session
    public static int getTimerSeconds(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(timer_Key, timer_default);
    }
    // stores the current timer settings for new sessions
    public static void setTimerSeconds(Context context, int timerSeconds){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(timer_Key, timerSeconds);
        editor.apply();

    }
    // Remaining time on the timer for callNotification
    public void start_Timer(){
        timer  = new CountDownTimer(timeLeft * 1000L, 1000){
            @Override
            public void onTick(long msUntilFinished){
                timeLeft = (int) (msUntilFinished / 1000L);
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }
    public static void cancelTimer(){
        if(timer != null){
            timer.cancel();
        }
    }

}
