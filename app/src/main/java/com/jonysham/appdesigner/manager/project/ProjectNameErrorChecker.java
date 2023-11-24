package com.jonysham.appdesigner.manager.project;

import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.jonysham.appdesigner.model.Project;

import java.util.List;

public class ProjectNameErrorChecker {

	private AlertDialog dialog;
	private TextInputEditText editText;
	private TextInputLayout inputLayout;
	private List<Project> projects;
	private Project currentProject;
	
	public ProjectNameErrorChecker(final AlertDialog dialog, final TextInputEditText editText, final TextInputLayout inputLayout, final List<Project> projects, final Project currentProject) {
		this.dialog = dialog;
		this.editText = editText;
		this.inputLayout = inputLayout;
		this.projects = projects;
		this.currentProject = currentProject;
	}
	
    public void checkErrors() {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            @Override
            public void afterTextChanged(Editable arg0) {
				String text = editText.getText().toString();
					
				if (text.isEmpty()) {
					disable("Field cannot be empty!");
					return;
				} else if (text.contains("/")) {
					disable("Illegal symbol \"/\"!");
					return;
				} else if (text.length() > ProjectManager.MAX_NAME_LENGTH) {
					disable("Max name length " + ProjectManager.MAX_NAME_LENGTH + "!");
					return;
				}
					
				for (int i=0; i<projects.size(); i++) {
					Project project = projects.get(i);
						
					if (currentProject != null && currentProject.getName().equals(text)) {
						continue;
					}
						
					if (project.getName().equals(text)) {
						disable("Current name is unavailable!");
						return;
					}
				}
					
				enable();
			}
        });
    }
	
	private void disable(String error) {
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
		inputLayout.setErrorEnabled(true);
		inputLayout.setError(error);
	}
	
	private void enable() {
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
		inputLayout.setErrorEnabled(false);
		inputLayout.setError("");
	}
}