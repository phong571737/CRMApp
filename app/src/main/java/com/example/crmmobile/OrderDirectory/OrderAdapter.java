package com.example.crmmobile.OrderDirectory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public interface OnMoreClickListener {
        void onMoreClick(Order order);
    }

    private final List<Order> orders;
    private final Context context;
    private final OnMoreClickListener moreClickListener;

    public OrderAdapter(@NonNull List<Order> orders,
                        @NonNull Context context,
                        OnMoreClickListener moreClickListener) {
        this.orders = orders;
        this.context = context;
        this.moreClickListener = moreClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order o = orders.get(position);

        if (holder.tvOrderCode != null) holder.tvOrderCode.setText(safe(o.getOrderCode()));
        if (holder.tvCompany != null)   holder.tvCompany.setText(safe(o.getCompany()));

        // ✅ Giá: chỉ lấy từ o.getPrice() (đã format trong DonHangRepository)
        if (holder.tvPrice != null) {
            holder.tvPrice.setText(safe(o.getPrice()));
        }

        if (holder.tvDate != null)   holder.tvDate.setText(safe(o.getDate()));
        if (holder.tvStatus != null) holder.tvStatus.setText(safe(o.getPaymentStatus()));

        if (holder.tvTag != null) {
            String tag = safe(o.getOrderType());
            holder.tvTag.setText(tag.isEmpty() ? "Mới" : tag);
        }

        if (holder.btnMore != null) {
            holder.btnMore.setOnClickListener(v -> {
                if (moreClickListener != null) moreClickListener.onMoreClick(o);
            });
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("orderId", o.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderCode, tvCompany, tvPrice, tvDate, tvStatus, tvTag;
        ImageView btnMore;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderCode = itemView.findViewById(R.id.tvOrderCode);
            tvCompany   = itemView.findViewById(R.id.tvCompany);
            tvPrice     = itemView.findViewById(R.id.tvPrice);
            tvDate      = itemView.findViewById(R.id.tvDate);
            tvStatus    = itemView.findViewById(R.id.tvStatus);
            tvTag       = itemView.findViewById(R.id.tvTag);
            btnMore     = itemView.findViewById(R.id.btnMore);
        }
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
