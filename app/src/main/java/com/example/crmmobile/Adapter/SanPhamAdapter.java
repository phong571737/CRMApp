package com.example.crmmobile.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.OrderDirectory.ProductPickActivity;
import com.example.crmmobile.R;
import com.example.crmmobile.SanPhamDirectory.SanPham;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.VH>{

    private final List<SanPham> full  = new ArrayList<>();
    private final List<SanPham> items = new ArrayList<>();
    public interface OnItemClick{
        void onClick(SanPham sanPham);
    }
    private final OnItemClick onClick;
    private final NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

    public SanPhamAdapter(List<SanPham> data, OnItemClick onClick) {
        if (data != null) {
            full.addAll(data);
            items.addAll(data);
        }
        this.onClick = onClick;
    }

    public void setData(List<SanPham> list){
        full.clear();
        items.clear();

        if (list != null){
            full.addAll(list);
            items.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_pick, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        SanPham sp = items.get(position);
        h.tvName.setText(sp.getName());
//        h.tvPrice.setText(sp.getDongia() + " đ");

        h.itemView.setOnClickListener(v -> {
            if (onClick != null) onClick.onClick(sp);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void filter(String q) {
        items.clear();
        if (q == null || q.trim().isEmpty()) {
            items.addAll(full);
        } else {
            String lower = q.toLowerCase(Locale.ROOT);
            for (SanPham sp : full) {
                if (sp.getName().toLowerCase(Locale.ROOT).contains(lower)) {
                    items.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView imgThumb;
        TextView tvName, tvDesc, tvBadge, tvPrice;

        VH(@NonNull View v) {
            super(v);
            imgThumb = v.findViewById(R.id.imgThumb);
            tvName   = v.findViewById(R.id.tvName);
            tvDesc   = v.findViewById(R.id.tvDesc);
            tvBadge  = v.findViewById(R.id.tvBadge);
            tvPrice  = v.findViewById(R.id.tvPrice);
        }
    }
}
