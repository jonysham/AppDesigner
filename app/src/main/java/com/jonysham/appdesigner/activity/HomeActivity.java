package com.jonysham.appdesigner.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.jonysham.appdesigner.adapter.ProjectListAdapter;
import com.jonysham.appdesigner.databinding.ActivityHomeBinding;
import com.jonysham.appdesigner.databinding.DialogEdittextBinding;
import com.jonysham.appdesigner.BaseActivity;
import com.jonysham.appdesigner.manager.project.ProjectManager;
import com.jonysham.appdesigner.manager.project.ProjectNameErrorChecker;
import com.jonysham.appdesigner.model.Project;
import com.jonysham.appdesigner.util.AndroidUtil;
import com.jonysham.appdesigner.R;

public class HomeActivity extends BaseActivity {

	static final String GITHUB_URL = "https://github.com/jonysham/AppDesigner";
	
    private ActivityHomeBinding binding;
	private ProjectManager projectManager;
	
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
	private NavigationView navigation;
	
	private RecyclerView recyclerProjects;
	private ProjectListAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		projectManager = ProjectManager.getInstance();
		
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
		
		setupNavigation();
		setupProjectList();
		
		binding.fab.setOnClickListener(v -> showDialogCreateProject());
    }

	@SuppressLint("NonConstantResourceId")
    private void setupNavigation() {
		navigation = binding.navigation;
		navigation.setNavigationItemSelectedListener(item -> {
			int id = item.getItemId();
				
			if (id == R.id.nav_settings) {
				return true;
			} else if (id == R.id.nav_info) {
				return true;
			} else if (id == R.id.nav_github) {
				AndroidUtil.openUrl(GITHUB_URL, HomeActivity.this);
				return true;
			}
				
			return false;
		});
		
        drawerLayout = binding.drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, binding.toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
				
            @Override
            public void onDrawerSlide(View view, float offset) {
				float scale = 1 - offset/10;
                binding.content.setTranslationX(view.getWidth() * (offset/5));
				binding.content.setScaleX(scale);
				binding.content.setScaleY(scale);
        	}
        });
		
		drawerToggle.syncState();
    }
	
	private void setupProjectList() {
		recyclerProjects = binding.projects;
		recyclerProjects.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
		adapter = new ProjectListAdapter(this, new ProjectListAdapter.ClickListener() {
				
			@Override
			public void onClick(Project project) {
				openProject(project);
			}
				
			@Override
			public void onRenameClick(Project project) {
				showDialogRenameProject(project);
			}
				
			@Override
			public void onDeleteClick(Project project) {
				showDialogDeleteProject(project);
			}
		});
		
		recyclerProjects.setAdapter(adapter);
	}
	
	private void updateAdapter() {
		int visible = projectManager.getProjects().size() == 0 ? View.GONE : View.VISIBLE;
		adapter.submitProjects(projectManager.getProjects());
		recyclerProjects.setVisibility(visible);
	}
	
	private void showDialogCreateProject() {
		final var bind = DialogEdittextBinding.inflate(getLayoutInflater());
		final var input = bind.inputLayout;
		final var edit = bind.inputEdit;
		
		input.setHint("Enter name");
		input.setCounterEnabled(true);
		input.setCounterMaxLength(ProjectManager.MAX_NAME_LENGTH);
		edit.setText("NewProject" + projectManager.getProjects().size());
		
		var dialog = new MaterialAlertDialogBuilder(this)
		.setView(bind.getRoot())
		.setTitle("New project")
		.setNegativeButton("Cancel", null)
		.setPositiveButton("Create", (di, which) -> {
			var project = projectManager.createProject(edit.getText().toString());
			openProject(project);
			updateAdapter();
		})
		.create();
		
		dialog.show();
		
		ProjectNameErrorChecker errorChecker = new ProjectNameErrorChecker(dialog, edit, input, projectManager.getProjects(), null);
		errorChecker.checkErrors();
	}
	
	private void showDialogRenameProject(final Project project) {
		final var bind = DialogEdittextBinding.inflate(getLayoutInflater());
		final var input = bind.inputLayout;
		final var edit = bind.inputEdit;
		
		input.setHint("Enter name");
		input.setCounterEnabled(true);
		input.setCounterMaxLength(ProjectManager.MAX_NAME_LENGTH);
		edit.setText(project.getName());
		
		var dialog = new MaterialAlertDialogBuilder(this)
		.setView(bind.getRoot())
		.setTitle("Renaming")
		.setNegativeButton("Cancel", null)
		.setPositiveButton("Rename", (di, which) -> {
			if (!project.getName().equals(edit.getText().toString())) {
				projectManager.renameProject(project, edit.getText().toString());
				updateAdapter();
			}
		})
		.create();
		
		dialog.show();
		
		ProjectNameErrorChecker errorChecker = new ProjectNameErrorChecker(dialog, edit, input, projectManager.getProjects(), project);
		errorChecker.checkErrors();
	}
	
	private void showDialogDeleteProject(final Project project) {
		new MaterialAlertDialogBuilder(this)
		.setTitle("Delete project")
		.setMessage("Are you sure you want to remove this project?")
		.setNegativeButton("No", null)
		.setPositiveButton("Yes", (di, which) -> {
			projectManager.deleteProject(project);
			updateAdapter();
		})
		.show();
	}
	
	private void openProject(Project project) {
		projectManager.openProject(project);
		Intent intent = new Intent(this, LayoutEditorActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		projectManager.loadProjects();
		updateAdapter();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		binding = null;
	}
}