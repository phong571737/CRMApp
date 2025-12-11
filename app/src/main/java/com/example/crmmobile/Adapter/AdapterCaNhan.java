package com.example.crmmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;

import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.R;

import java.util.List;

public class AdapterCaNhan extends RecyclerView.Adapter<AdapterCaNhan.ViewHolder> {
    private Context context;
    private List<CaNhan> caNhanList;
    private OnItemClickListener listener;


    public interface OnItemClickListener {
        void onMoreClick(CaNhan cn);
        void onItemClick(CaNhan cn);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdapterCaNhan(Context context, List<CaNhan> caNhanList) {
        this.context = context;
        this.caNhanList = caNhanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_canhan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CaNhan cn = caNhanList.get(position);
        holder.tvDanhXung.setText(cn.getDanhXung());
        holder.tvHoTen.setText(cn.getHoVaTen());
        holder.tvTen.setText(cn.getTen());
        holder.tvCongTy.setText(cn.getCongTy());
        holder.tvNgay.setText(cn.getNgayTao());
        holder.tvCuocGoi.setText(String.valueOf(cn.getSoCuocGoi()));
        holder.tvMeeting.setText(String.valueOf(cn.getSoCuocHop()));

        // --- Click vào icon "More" ---
        holder.icMore.setOnClickListener(v -> {
            if (listener != null) listener.onMoreClick(cn);
        });

        // --- Click vào toàn item ---
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(cn);
        });

    }

    @Override
    public int getItemCount() {
        return caNhanList.size();
    }

    public void addItem(CaNhan cn) {
        caNhanList.add(cn);
        notifyItemInserted(caNhanList.size() - 1);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDanhXung, tvHoTen, tvTen, tvCongTy, tvNgay, tvCuocGoi, tvMeeting;
        ImageView icMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDanhXung = itemView.findViewById(R.id.tvDanhXung);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvCongTy = itemView.findViewById(R.id.tvCongTy);
            tvNgay = itemView.findViewById(R.id.tvNgay);
            tvCuocGoi = itemView.findViewById(R.id.fill_cuocgoi);
            tvMeeting = itemView.findViewById(R.id.fill_meeting);
            icMore = itemView.findViewById(R.id.ic_more);
        }
    }
}
