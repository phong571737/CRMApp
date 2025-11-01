package com.example.crmmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaiLieuAdapter extends RecyclerView.Adapter<TaiLieuAdapter.ViewHolder> {

    private Context context;
    private List<TaiLieu> danhSachTaiLieu;

    public TaiLieuAdapter(Context context, List<TaiLieu> danhSachTaiLieu) {
        this.context = context;
        this.danhSachTaiLieu = danhSachTaiLieu;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tai_lieu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaiLieu taiLieu = danhSachTaiLieu.get(position);

        // Thiết lập icon tự động theo loại file
        int iconRes;
        if (taiLieu.getTenFile().endsWith(".pdf")) {
            iconRes = R.drawable.ic_pdf;
        } else if (taiLieu.getTenFile().endsWith(".xlsx") || taiLieu.getTenFile().endsWith(".xls")) {
            iconRes = R.drawable.ic_excel;
        } else {
            iconRes = R.drawable.ic_files;
        }

        holder.imgFileType.setImageResource(iconRes);
        holder.tvFileName.setText(taiLieu.getTenFile());
        holder.tvFileTime.setText(taiLieu.getNgay() + " • " + taiLieu.getGio());

        holder.tvFileOwner.setText(taiLieu.getNguoiTao());

        // Xử lý click cho từng nút
        holder.btnDownload.setOnClickListener(v ->
                Toast.makeText(context, "Tải xuống: " + taiLieu.getTenFile(), Toast.LENGTH_SHORT).show()
        );

        holder.btnShare.setOnClickListener(v ->
                Toast.makeText(context, "Chia sẻ: " + taiLieu.getTenFile(), Toast.LENGTH_SHORT).show()
        );

        holder.btnDelete.setOnClickListener(v ->
                Toast.makeText(context, "Xóa: " + taiLieu.getTenFile(), Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return danhSachTaiLieu.size();
    }

    // ViewHolder ánh xạ đúng ID trong item_tai_lieu.xml
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFileType, btnDownload, btnShare, btnDelete;
        TextView tvFileName, tvFileTime, tvFileOwner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFileType = itemView.findViewById(R.id.imgFileType);
            tvFileName = itemView.findViewById(R.id.tvFileName);
            tvFileTime = itemView.findViewById(R.id.tvFileTime);
            tvFileOwner = itemView.findViewById(R.id.tvFileOwner);
            btnDownload = itemView.findViewById(R.id.btnDownload);
            btnShare = itemView.findViewById(R.id.btnShare);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}


