package com.jonysham.appdesigner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.jonysham.appdesigner.databinding.ItemWidgetBinding;

public class WidgetListAdapter extends RecyclerView.Adapter<WidgetListAdapter.Holder> {

	private Context context;
	private LayoutInflater inflater;
	
	public WidgetListAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	
    public class Holder extends RecyclerView.ViewHolder {
		private View root;
		private TextView name;
		private ImageView icon;
		
        public Holder(ItemWidgetBinding binding) {
            super(binding.getRoot());
			root = binding.getRoot();
			name = binding.name;
			icon = binding.icon;
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int arg1) {
        return new Holder(ItemWidgetBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int pos) {
		holder.name.setText("LinearLayout ");
	}

    @Override
    public int getItemCount() {
        return 0;
    }
}
