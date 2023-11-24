package com.jonysham.appdesigner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;

public class AppDesigner extends Application {
    
    private static AppDesigner instance;
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
    
    public static AppDesigner getInstance() {
        return instance;
    }
    
    public void updateTheme(Activity activity, boolean isNight) {
        int mode = isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(mode);
        activity.recreate();
    }
    
    public Context getContext() {
        return getApplicationContext();
    }
}