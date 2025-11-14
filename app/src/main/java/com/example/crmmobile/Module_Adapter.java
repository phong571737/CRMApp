package com.example.crmmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Module_Adapter extends RecyclerView.Adapter<Module_Adapter.ModuleViewHolder> {
    private List<item_module> itemModules;
    private onClickItemListener Listener;


    public interface onClickItemListener{
        void onItemClick(int position);
    }

    public interface onClickLeadMenuListener{
        void onLeadMenuClick(int position);
    }

    public Module_Adapter(List<item_module> itemModules, onClickItemListener listener){
        this.itemModules = itemModules;
        this.Listener = listener;
    }

    @NonNull
    @Override
    //inflate XML into View, wrap that view in a viewholder
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_module, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        //get item_module at position in itemModules list
        item_module item = itemModules.get(position);

        //Assign from item_module into TextView in ViewHolder
        holder.tv_icon.setText(item.getName());
        holder.iv_icon.setImageResource(item.getIconimg());

        //Click event for all item
        holder.itemView.setOnClickListener(v -> {
            if(Listener != null) Listener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return itemModules.size();
    }

    static class ModuleViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_icon;
        TextView tv_icon;
        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_icon = itemView.findViewById(R.id.tv_icon);
        }
    }
}
