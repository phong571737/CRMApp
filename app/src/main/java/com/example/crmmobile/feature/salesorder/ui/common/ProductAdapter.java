package com.example.crmmobile.feature.salesorder.ui.common;

import com.example.crmmobile.R;
import com.example.crmmobile.feature.salesorder.model.Product;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {

    public interface OnItemClick {
        void onClick(Product p);
    }

    private final List<Product> full = new ArrayList<>();
    private final List<Product> data = new ArrayList<>();
    private final OnItemClick onClick;

    public ProductAdapter(List<Product> items, OnItemClick onClick) {
        if (items != null) {
            full.addAll(items);
            data.addAll(items);
        }
        this.onClick = onClick;
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
        Product p = data.get(position);
        h.tvName.setText(p.name);
        h.tvDesc.setText(p.desc);
        h.tvBadge.setText(p.badge);
        h.tvPrice.setText(p.price);
        h.itemView.setOnClickListener(v -> {
            if (onClick != null) onClick.onClick(p);
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    public void filter(String q) {
        data.clear();
        if (q == null || q.trim().isEmpty()) {
            data.addAll(full);
        } else {
            String s = q.toLowerCase();
            for (Product p : full) {
                if (p.name.toLowerCase().contains(s) || p.desc.toLowerCase().contains(s))
                    data.add(p);
            }
        }
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName, tvDesc, tvBadge, tvPrice;
        VH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgThumb);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvBadge = itemView.findViewById(R.id.tvBadge);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
