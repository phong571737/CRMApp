package com.example.crmmobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.OrderViewHolder> {

    private List<Order> orders;
    private Context context;

    private onItemViewClickListener itemViewClickListener;

    public interface onItemViewClickListener{
        void onItemClickListener(Order order);
    }

    public AdapterOrder(Context context, List<Order> orders, onItemViewClickListener itemViewClickListener) {
        this.context = context;
        this.orders = orders;
        this.itemViewClickListener = itemViewClickListener;
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
            if(itemViewClickListener != null){
                itemViewClickListener.onItemClickListener(order);
            }
        });

        // Khi click vào nút 3 chấm — mở bottom sheet
        holder.btnMore.setOnClickListener(v -> showBottomSheet());
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

    public void showBottomSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(context);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_more_actions, null, false);

        LinearLayout layoutActions = view.findViewById(R.id.lt_more_actions);

        addActionItem(layoutActions, R.drawable.ic_pin, "Ghim", () ->
                Toast.makeText(context, "Đã ghim đơn hàng", Toast.LENGTH_SHORT).show());
        addActionItem(layoutActions, R.drawable.ic_cached, "Chuyển thành hóa đơn", () ->
                Toast.makeText(context, "Chuyển hóa đơn thành công", Toast.LENGTH_SHORT).show());

        addActionItem(layoutActions, R.drawable.ic_files, "Xuất file PDF", () ->
                Toast.makeText(context, "Xuất file PDF...", Toast.LENGTH_SHORT).show());
        addActionItem(layoutActions, R.drawable.ic_forward_email, "Gửi email kèm file PDF", () ->
                Toast.makeText(context, "Gửi thành công", Toast.LENGTH_SHORT).show());
        addActionItem(layoutActions, R.drawable.ic_copy, "Nhân đôi", () ->
                Toast.makeText(context, "Nhân đôi thành công", Toast.LENGTH_SHORT).show());

        addActionItem(layoutActions, R.drawable.ic_cancel, "Hủy đơn hàng", () ->
                Toast.makeText(context, "Đơn hàng đã bị hủy", Toast.LENGTH_SHORT).show());

        view.findViewById(R.id.btn_action_close).setOnClickListener(v -> dialog.dismiss());

        dialog.setContentView(view);
        dialog.show();
    }

    //Hàm tạo 1 item_action
    private void addActionItem(LinearLayout parent, int iconRes, String text, Runnable onClick) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_action, parent, false);
        ImageView icon = itemView.findViewById(R.id.img_action_icon);
        TextView label = itemView.findViewById(R.id.tv_action);
        icon.setImageResource(iconRes);
        label.setText(text);

        itemView.setOnClickListener(v -> {
            onClick.run();
        });

        parent.addView(itemView);
    }


}



