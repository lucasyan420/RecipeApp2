package com.ibm.recipesapp.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ibm.recipesapp.Controllers.CookingActivity;

//PrefUtil

public class PrefUtil
{
    private static final String PREVIOUS_TIMER_LENGTH_SECONDS_ID = "com.ibm.recipesapp.previous_timer_length";

    public static int getTimerLength(Context context){
        return 5;
    }

    public static Long getPreviousTimerLengthSeconds(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0);
    }

    public static void setPreviousTimerLengthSeconds(Long seconds, Context context)
    {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds);
        editor.apply();
    }

    private static final String Timer_State_ID = "com.ibm.recipesapp.timer_state";

    public static CookingActivity.TimerState getTimerState(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int ordinal = preferences.getInt(Timer_State_ID, 0);
        return CookingActivity.TimerState.values()[ordinal];
    }

    public static void setTimerState(CookingActivity.TimerState state, Context context)
    {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        int ordinal = state.ordinal();
        editor.putInt(Timer_State_ID, ordinal);
        editor.apply();
    }

    private static final String SECONDS_REMAINING_ID = "com.ibm.recipesapp.seconds_remaining";

    public static Long getSecondsRemaining(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(SECONDS_REMAINING_ID, 0);
    }

    public static void setSecondsRemaining(Long seconds, Context context)
    {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putLong(SECONDS_REMAINING_ID, seconds);
        editor.apply();
    }
}
