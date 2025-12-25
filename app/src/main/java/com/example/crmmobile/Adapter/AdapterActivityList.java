package com.example.crmmobile.Adapter;

import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.MainDirectory.InitClass;
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
        holder.tv_start.setText(hd.getThoiGianBatDau());
        holder.tv_end.setText(hd.getThoiGianKetThuc());
        Log.e(TAG, "Tyle: " + hd.getType());
        if ("call".equals(hd.getType())){
            holder.iv_type.setImageResource(R.drawable.ic_call);
        }
        if ("meeting".equals(hd.getType())){
            holder.iv_type.setImageResource(R.drawable.ic_meeting);
        }
        GradientDrawable gradientDrawable = (GradientDrawable) holder.ll_activity.getBackground();
        gradientDrawable.setColor(InitClass.getActivityColor(position % 4));
    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "list size: " + list.size());
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_activity, tv_start, tv_end;
        private ImageView iv_type;
        private LinearLayout ll_activity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_activity = itemView.findViewById(R.id.tv_activity);
            tv_start = itemView.findViewById(R.id.tv_start);
            tv_end = itemView.findViewById(R.id.tv_end);
            ll_activity = itemView.findViewById(R.id.ll_activity);
            iv_type = itemView.findViewById(R.id.iv_type);
        }
    }
}
