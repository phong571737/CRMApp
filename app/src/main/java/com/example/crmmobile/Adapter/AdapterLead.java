package com.example.crmmobile.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.DataBase.HoatDongRepository;
import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.LeadDirectory.Lead;
import com.example.crmmobile.LeadDirectory.ViewModelLead;
import com.example.crmmobile.MainDirectory.InitClass;
import com.example.crmmobile.R;
import com.google.android.material.chip.Chip;

public class AdapterLead extends RecyclerView.Adapter<AdapterLead.LeadViewHolder> {
    private static final String TAG = "ADAPTER_LEAD";
    private List<Lead> dataList;
    private onItemClickListener listener;
    private Map<String, Integer> stateColor;
    private HoatDongRepository hoatDongRepository;

    public interface onItemClickListener {
        void onDotsClick(Lead item, int position);
        void onMenuClick(Lead lead, int id);
    }

    public AdapterLead(Context context, List<Lead> dataList, onItemClickListener listener){
        this.dataList = dataList;
        this.listener = listener;
        this.hoatDongRepository = new HoatDongRepository(context);

        stateColor = new HashMap<>();
        stateColor.put("Mới", Color.parseColor("#BBE2EC"));
        stateColor.put("Chưa liên hệ được", Color.parseColor("#FADA7A"));
        stateColor.put("Liên hệ sau", Color.parseColor("#C5BAFF"));
        stateColor.put("Ngừng chăm sóc", Color.parseColor("#BD2E2D"));
        stateColor.put("Đã chuyển đổi", Color.parseColor("#38A4F9"));
        stateColor.put("Đã liên hệ", Color.parseColor("#78C1F3"));
        stateColor.put("", Color.LTGRAY);
    }

    public static class LeadViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_Company, tv_day, tv_job, tv_number_calls, tv_meeting;
        ImageView iv_created_by, iv_sendto;
        ImageButton ivDots;
        Chip chip_status;

        public LeadViewHolder(View itemView){
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_Company = itemView.findViewById(R.id.tv_Company);
            tv_day = itemView.findViewById(R.id.tv_Day);
            ivDots = itemView.findViewById(R.id.iv_dots);
            chip_status = itemView.findViewById(R.id.chip_status);
            tv_job = itemView.findViewById(R.id.tv_job);
            iv_sendto = itemView.findViewById(R.id.iv_sendto);
            iv_created_by = itemView.findViewById(R.id.iv_created_by);
            tv_number_calls = itemView.findViewById(R.id.tv_number_calls);
            tv_meeting = itemView.findViewById(R.id.tv_meeting);
        }
    }

    //Crete new view
    @Override
    public LeadViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lead, viewGroup, false);
        return new LeadViewHolder(view);
    }

    //Replace the contents of a view
    @Override
    public void onBindViewHolder(LeadViewHolder viewHolder, final int position){
        Lead lead = dataList.get(position);
        String full_name = lead.getTitle() + " " + lead.getHovaTendem() + " " + lead.getTen();
        String status = lead.getTinhTrang();

        if (status == null || status.isEmpty()){
            viewHolder.chip_status.setVisibility(View.GONE);
        }else{
            //set chip color
            viewHolder.chip_status.setVisibility(View.VISIBLE);
            Integer color = stateColor.getOrDefault(status, Color.LTGRAY);
            viewHolder.chip_status.setChipBackgroundColor(ColorStateList.valueOf(color));
            viewHolder.chip_status.setText(status);
        }

        viewHolder.tv_name.setText(full_name);
        viewHolder.tv_Company.setText(lead.getCongty());
        viewHolder.tv_day.setText(lead.getNgayLienHe());
        viewHolder.tv_job.setText(lead.getNganhnghe());
        int level = InitClass.getIconNhanVien(lead.getGiaochoID());
        viewHolder.iv_sendto.setImageLevel(level);
        int create_by_level = InitClass.getIconNhanVien(lead.getNguoitaoID());
        viewHolder.iv_created_by.setImageLevel(create_by_level);
        Log.e(TAG, "ID Giao cho: " + lead.getGiaochoID());
        Log.e(TAG, "ID Người tạo: " + lead.getNguoitaoID());

        // Đếm số cuộc gọi và cuộc họp từ database
        int callCount = 0;
        int meetingCount = 0;

        if (lead.getID() > 0 && hoatDongRepository != null) {
            List<HoatDong> hoatDongList = hoatDongRepository.getHoatDongByNguoiLienHe(lead.getID());
            for (HoatDong hd : hoatDongList) {
                String type = hd.getType();
                if ("call".equalsIgnoreCase(type)) {
                    callCount++;
                } else if ("meeting".equalsIgnoreCase(type)) {
                    meetingCount++;
                }
            }
        }

        viewHolder.tv_number_calls.setText(String.valueOf(callCount));
        viewHolder.tv_meeting.setText(String.valueOf(meetingCount));

        viewHolder.ivDots.setOnClickListener(v -> {
            if(listener != null){
                listener.onDotsClick(lead, position);
            }
        });

        viewHolder.itemView.setOnClickListener(v -> {
            if(listener != null){
                listener.onMenuClick(lead, lead.getID());
            }
        });
    }

    @Override
    public int getItemCount(){
        return dataList.size();
    }
}
