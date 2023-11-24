package com.jonysham.appdesigner.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.jonysham.appdesigner.BaseActivity;
import com.jonysham.appdesigner.databinding.ActivityLayoutEditorBinding;
import com.jonysham.appdesigner.R;

public class LayoutEditorActivity extends BaseActivity {
	
	private ActivityLayoutEditorBinding binding;
	private ActionBarDrawerToggle drawerToggle;
	private DrawerLayout drawerLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		binding = ActivityLayoutEditorBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		
		setSupportActionBar(binding.toolbar);
		getSupportActionBar().setTitle(getString(R.string.app_name));
		
		setupDrawer();
	}
	
	private void setupDrawer() {
		drawerLayout = binding.drawerLayout;
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, binding.toolbar, R.string.app_name, R.string.app_name);
		drawerLayout.addDrawerListener(drawerToggle);
		drawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration arg0) {
		super.onConfigurationChanged(arg0);
		drawerToggle.syncState();
	}
}