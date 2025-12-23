package com.example.crmmobile.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.R;

import java.util.List;

public class AdapterActivityList extends RecyclerView.Adapter<AdapterActivityList.ViewHolder> {
    private static final String TAG = "ACTIVITY_ADAPTER";
    private List<HoatDong> list;

    public AdapterActivityList(List<HoatDong> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoatDong hd = list.get(position);
        holder.tv_activity.setText(hd.getTenHoatDong());
    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "list size: " + list.size());
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_activity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_activity = itemView.findViewById(R.id.tv_activity);
        }
    }
}
