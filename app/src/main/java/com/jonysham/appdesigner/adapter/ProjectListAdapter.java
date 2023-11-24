package com.jonysham.appdesigner.adapter;

import android.content.Context;
import android.view.View;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import com.jonysham.appdesigner.R;
import java.util.List;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.jonysham.appdesigner.databinding.ItemProjectBinding;
import com.jonysham.appdesigner.model.Project;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.Holder> {

	private Context context;
	private List<Project> projects;
	private ClickListener listener;
	
	public ProjectListAdapter(Context context, ClickListener listener) {
		this.context = context;
		this.listener = listener;
	}
	
	public void submitProjects(List<Project> projects) {
		this.projects = projects;
		notifyDataSetChanged();
	}
	
    public class Holder extends RecyclerView.ViewHolder {
		private ItemProjectBinding binding;
        private TextView name;
		private TextView date;
		private ImageButton options;
		
        public Holder(ItemProjectBinding binding) {
            super(binding.getRoot());
			this.binding = binding;
			
            name = binding.name;
			date = binding.date;
			options = binding.options;
        }
    }

    @Override
    public int getItemCount() {
        return projects == null ? 0 : projects.size();
    }

	@Override
    public Holder onCreateViewHolder(ViewGroup parent, int type) {
        return new Holder(ItemProjectBinding.inflate(LayoutInflater.from(context), parent, false));
    }
	
    @Override
    public void onBindViewHolder(Holder holder, int pos) {
		Project project = projects.get(pos);
		holder.name.setText(project.getName());
		holder.date.setText("Last modified: " + project.getCreateDate());
		holder.binding.getRoot().setOnClickListener(v -> listener.onClick(project));
		holder.options.setOnClickListener(v -> showOptions(v, project));
	}
	
	private void showOptions(View anchor, final Project project) {
		var menu = new PopupMenu(context, anchor);
		menu.inflate(R.menu.menu_project);
		menu.setOnMenuItemClickListener(item -> {
			int id = item.getItemId();
				
			if (id == R.id.menu_rename) {
				listener.onRenameClick(project);
				return true;
			} else if (id == R.id.menu_delete) {
				listener.onDeleteClick(project);
				return true;
			}
				
			return false;
		});
		
		if (menu.getMenu() instanceof MenuBuilder) {
			((MenuBuilder) menu.getMenu()).setOptionalIconsVisible(true);
		}
		
		menu.show();
	}
	
	public interface ClickListener {
		
		public void onClick(Project project);
		
		public void onRenameClick(Project project);
		
		public void onDeleteClick(Project project);
	}
}
