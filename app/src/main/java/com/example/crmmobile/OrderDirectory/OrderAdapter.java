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

import com.example.crmmobile.OrderDirectory.OrderFragment;
import com.example.crmmobile.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    public interface OnMoreClickListener {
        void onMoreClick(Order order);
    }

    private final List<Order> orders;
    private final Context context;
    private final OnMoreClickListener moreClickListener;

    public OrderAdapter(List<Order> orders, Context context, OnMoreClickListener moreClickListener) {
        this.orders = orders;
        this.context = context;
        this.moreClickListener = moreClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.tvOrderCode.setText(order.getOrderCode());
        holder.tvCompany.setText(order.getCompany());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("orderId", order.getId()); // ✅ chỉ cần id
            context.startActivity(intent);
        });

        holder.btnMore.setOnClickListener(v -> {
            if (moreClickListener != null) moreClickListener.onMoreClick(order);
        });
    }




    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderCode, tvCompany;
        ImageView btnMore;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderCode = itemView.findViewById(R.id.tvOrderCode);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            btnMore = itemView.findViewById(R.id.btnMore);
        }
    }
}
