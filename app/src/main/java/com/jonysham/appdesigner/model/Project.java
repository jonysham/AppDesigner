package com.jonysham.appdesigner.model;

import android.content.Context;

import com.jonysham.appdesigner.util.AndroidUtil;
import com.jonysham.appdesigner.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;


public class Project {
	
	private String name;
	private String path;
	private String pathWithoutName;
	private String createDate;
	
	private Project() {
	}
	
	public static Project loadProject(File file) throws IOException {
		String path = file.getPath();
		Project project = new Project();
		project.path = path;
		project.name = FileUtil.getLastSegmentFromPath(path);
		project.pathWithoutName = path.substring(0, path.lastIndexOf(project.name));
		
		BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		project.createDate = AndroidUtil.getDate(attr.creationTime().toMillis());
		return project;
	}
	
	public void rename(String name) {
		this.name = name;
		this.path = pathWithoutName + "/" + name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	
	public static class ProjectBuilder {
		private Project project;
		private String rootPath;
		
		public ProjectBuilder(String rootFolderPath, String name) {
			rootPath = rootFolderPath;
			project = new Project();
			project.path = rootFolderPath + "/" + name;
			project.pathWithoutName = rootFolderPath;
			project.name = name;
			
			FileUtil.makeDir(project.path);
		}
		
		public ProjectBuilder addDefaultLayout() {
			FileUtil.writeFile(project.path + "/layout/layout_main.xml", "");
			return this;
		}
		
		public Project build() {
			return project;
		}
	}
}