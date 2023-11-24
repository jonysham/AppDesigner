package com.jonysham.appdesigner;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.elevation.SurfaceColors;
import java.lang.ref.WeakReference;

public class BaseActivity extends AppCompatActivity {

    public AppDesigner app;
    private WeakReference<Context> ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LogSender.startLogging(this);
        super.onCreate(savedInstanceState);
        ctx = new WeakReference<>(this);
		app = AppDesigner.getInstance();
		
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(ctx));
        getWindow().setStatusBarColor(SurfaceColors.SURFACE_0.getColor(this));
		getWindow().setBackgroundDrawable(new ColorDrawable(SurfaceColors.SURFACE_0.getColor(this)));
    }
	
    @Override
    protected void onDestroy() {
        ctx.clear();
        super.onDestroy();
    }
}
