package com.example.crmmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.HoatDongDirectory.HoatDong;

import com.example.crmmobile.R;

import java.util.List;

public class AdapterHoatDong extends RecyclerView.Adapter<AdapterHoatDong.ViewHolder> {

    private List<HoatDong> hoatDonglist;
    private Context context;
    private AdapterHoatDong.OnItemClickListener listener;


    public void setOnItemClickListener(AdapterHoatDong.OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdapterHoatDong(Context context, List<HoatDong> hoatDonglist) {
        this.context = context;
        this.hoatDonglist = hoatDonglist;
    }

    public interface OnItemClickListener {
        void onMoreClick(HoatDong hd);
        void onItemClick(HoatDong hd);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hoatdong, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHoatDong.ViewHolder holder, int position) {
        HoatDong hd = hoatDonglist.get(position);
        holder.filltencuochop.setText(hd.getTenHoatDong());
        holder.fillgiobatdau.setText(hd.getThoiGianBatDau());
        holder.fillgioketthuc.setText(hd.getThoiGianKetThuc());
        holder.fillngay.setText(hd.getNgayBatDau());
        holder.filltrangthai.setText(hd.getTinhTrang());
        holder.fillbinhluan.setText("2");


//        // --- Click vào icon "More" ---
//        holder.icMore.setOnClickListener(v -> {
//            if (listener != null) listener.onMoreClick(hd);
//        });

        // --- Click vào toàn item ---
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(hd);
        });

    }

    @Override
    public int getItemCount() {
        return hoatDonglist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView filltencuochop, fillgiobatdau, fillgioketthuc, fillngay, filltrangthai, fillbinhluan;


        ViewHolder(View itemView) {
            super(itemView);
            filltencuochop = itemView.findViewById(R.id.filltencuochop);
            fillgiobatdau = itemView.findViewById(R.id.fillgiobatdau);
            fillgioketthuc = itemView.findViewById(R.id.fillgioketthuc);
            fillngay = itemView.findViewById(R.id.fillngay);
            filltrangthai = itemView.findViewById(R.id.filltrangthai);
            fillbinhluan = itemView.findViewById(R.id.fillbinhluan);

        }
    }
}
