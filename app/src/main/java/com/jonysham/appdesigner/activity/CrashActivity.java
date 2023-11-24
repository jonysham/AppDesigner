package com.jonysham.appdesigner.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.content.res.AppCompatResources;

import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.google.android.material.snackbar.Snackbar;

import com.jonysham.appdesigner.BaseActivity;
import com.jonysham.appdesigner.databinding.ActivityCrashBinding;
import com.jonysham.appdesigner.R;

public class CrashActivity extends BaseActivity {

    private ActivityCrashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrashBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.topAppBar);
        getSupportActionBar().setTitle("Application failure");

        var error = new StringBuilder();

        error.append("Manufacturer: " + DeviceUtils.getManufacturer() + "\n");
        error.append("Device: " + DeviceUtils.getModel() + "\n");
        error.append(getIntent().getStringExtra("Software"));
        error.append("\n\n");
        error.append(getIntent().getStringExtra("Error"));
        error.append("\n\n");
        error.append(getIntent().getStringExtra("Date"));

        binding.result.setText(error.toString());
        binding.fab.setOnClickListener(v -> {
            ClipboardUtils.copyText(binding.result.getText());
            Snackbar.make(binding.getRoot(), "Copied", Snackbar.LENGTH_SHORT)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
        //	.setAnchorView(binding.fab)
            .show();
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        var close = menu.add("Exit");
        close.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        close.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_logout));
        close.setContentDescription("Close app");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Exit")) {
            finishAffinity();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}