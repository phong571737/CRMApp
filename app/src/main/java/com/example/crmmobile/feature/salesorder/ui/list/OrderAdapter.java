package com.example.crmmobile.feature.salesorder.ui.list;

import com.example.crmmobile.R;
import com.example.crmmobile.feature.salesorder.ui.list.MainActivity;
import com.example.crmmobile.feature.salesorder.ui.detail.OrderDetailActivity;
import com.example.crmmobile.feature.salesorder.model.Order;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders;
    private Context context;

    public OrderAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
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

        // ✅ Khi click vào item — mở màn chi tiết (OrderDetailActivity)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);

            // Gửi thông tin đơn hàng qua (nếu cần hiển thị ở tab Tổng quan)
            intent.putExtra("orderCode", order.getOrderCode());
            intent.putExtra("company", order.getCompany());
            intent.putExtra("date", order.getDate());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        // ✅ Khi click vào nút 3 chấm — mở bottom sheet
        holder.btnMore.setOnClickListener(v -> {
            if (context instanceof MainActivity) {
                ((MainActivity) context).showBottomSheet();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
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



