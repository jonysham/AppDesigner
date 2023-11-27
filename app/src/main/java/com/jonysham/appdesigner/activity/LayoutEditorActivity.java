package com.jonysham.appdesigner.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.jonysham.appdesigner.BaseActivity;
import com.jonysham.appdesigner.adapter.WidgetListAdapter;
import com.jonysham.appdesigner.databinding.ActivityLayoutEditorBinding;
import com.jonysham.appdesigner.editor.model.Widgets;
import com.jonysham.appdesigner.R;

public class LayoutEditorActivity extends BaseActivity {
	
	private ActivityLayoutEditorBinding binding;
	
	private ActionBarDrawerToggle drawerToggle;
	private DrawerLayout drawerLayout;
	
	private RecyclerView widgetList;
	private WidgetListAdapter widgetListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		binding = ActivityLayoutEditorBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		
		setSupportActionBar(binding.toolbar);
		getSupportActionBar().setTitle(getString(R.string.app_name));
		
		drawerLayout = binding.drawerLayout;
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, binding.toolbar, R.string.app_name, R.string.app_name);
		drawerLayout.addDrawerListener(drawerToggle);
		drawerToggle.syncState();
		
		setupPaletteWidgets();
	}
	
	private void setupPaletteWidgets() {
		List<String> items = new ArrayList<>();
		items.add(Widgets.LINEAR_LAYOUT);
		items.add(Widgets.RELATIVE_LAYOUT);
		items.add(Widgets.FRAME_LAYOUT);
		items.add(Widgets.BUTTON);
		items.add(Widgets.SWITCH);
		
		widgetList = binding.palette;
		widgetList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
		
		widgetListAdapter = new WidgetListAdapter(this);
		widgetListAdapter.setOnStartDragListener(() -> drawerLayout.closeDrawer(GravityCompat.START));
		
		widgetList.setAdapter(widgetListAdapter);
		widgetListAdapter.submitWidgets(items);
	}
	
	@Override
	public void onConfigurationChanged(Configuration arg0) {
		super.onConfigurationChanged(arg0);
		drawerToggle.syncState();
	}
}