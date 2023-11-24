package com.jonysham.appdesigner.manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;

import androidx.annotation.NonNull;

import com.jonysham.appdesigner.AppDesigner;
import com.jonysham.appdesigner.model.Project;
import com.jonysham.appdesigner.util.FileUtil;

public class ProjectManager {
	
	public static String ROOT_FOLDER;
	
	private static ProjectManager instance;
	
	private Project openedProject;
	private List<Project> projects;
	
	public static ProjectManager getInstance() {
		if (instance == null) {
			instance = new ProjectManager();
		}
		
		return instance;
	}
	
	private ProjectManager() {
		ROOT_FOLDER = FileUtil.getPackageDataDir(AppDesigner.getInstance().getContext()) + "/projects";
		
		if (!FileUtil.isExistFile(ROOT_FOLDER)) {
			FileUtil.makeDir(ROOT_FOLDER);
		}
		
		projects = new ArrayList<>();
	}
	
	public void loadProjects() {
		File root = new File(ROOT_FOLDER);
		File[] files = root.listFiles();
		projects.clear();
		
		try {
			if (files != null) {
				for (File file : files) {
					projects.add(Project.loadProject(file));
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@NonNull
	public Project createProject(String name) {
		Project project = new Project.ProjectBuilder(ROOT_FOLDER, name).
		addDefaultLayout().
		build();
		
		return project;
	}
	
	public void renameProject(Project project, String newName) {
		File oldFile = new File(project.getPath());
		File newFile = new File(ROOT_FOLDER + "/" + newName);
		oldFile.renameTo(newFile);
		project.rename(newName);
	}
	
	public void deleteProject(Project project) {
		FileUtil.deleteFile(project.getPath());
		projects.remove(project);
	}
	
	public void openProject(Project project) {
		openedProject = project;
	}
	
	@NonNull
	public Project getOpenedProject() {
		return openedProject;
	}
	
	public void closeProject() {
		openedProject = null;
	}
	
	public List<Project> getProjects() {
		return projects;
	}
}