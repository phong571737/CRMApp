package com.example.crmmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OpportunityAdapter extends RecyclerView.Adapter<OpportunityAdapter.OpportunityViewHolder> {

    private List<Opportunity> opportunityList = new ArrayList<>();
    private OnMenuClickListener menuListener;
    private OnItemClickListener itemListener;

    // Interface callback
    public interface OnMenuClickListener {
        // position giúp xác định item, anchorView để bottomsheet có thể neo vào view
        void onMenuClick(Opportunity item, int position, View anchorView);
    }

    public interface OnItemClickListener {
        void onItemClick(Opportunity item, int position);
    }

    public OpportunityAdapter(List<Opportunity> opportunityList,
                              OnMenuClickListener menuListener,
                              OnItemClickListener itemListener) {
        if (opportunityList != null) this.opportunityList = opportunityList;
        this.menuListener = menuListener;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public OpportunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_opportunity_body_card, parent, false);
        return new OpportunityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpportunityViewHolder holder, int position) {
        Opportunity item = opportunityList.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvPrice.setText(item.getPrice());
        holder.tvDate.setText(item.getDate());
        holder.tvStatus.setText(item.getStatus());
        holder.tvCallCount.setText(String.valueOf(item.getCallCount()));
        holder.tvMessageCount.setText(String.valueOf(item.getMessageCount()));
        holder.tvExchange.setText(item.getExchangeText());

        // Khi bấm nút menu 3 chấm: truyền cả view làm anchor, và position
        holder.ivMenu.setOnClickListener(v -> {
            if (menuListener != null) menuListener.onMenuClick(item, holder.getAdapterPosition(), v);
        });

        // Khi bấm cả card -> open detail (position cũng truyền)
        holder.itemView.setOnClickListener(v -> {
            if (itemListener != null) itemListener.onItemClick(item, holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return opportunityList.size();
    }

    public void setData(List<Opportunity> newList) {
        this.opportunityList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class OpportunityViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice, tvDate, tvStatus, tvCallCount, tvMessageCount, tvExchange;
        ImageView ivStar, ivMenu;

        public OpportunityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_opportunity_name);
            tvPrice = itemView.findViewById(R.id.tv_opportunity_price);
            tvDate = itemView.findViewById(R.id.tv_opportunity_date);
            tvStatus = itemView.findViewById(R.id.tv_opportunity_status);
            tvCallCount = itemView.findViewById(R.id.tv_call_count);
            tvMessageCount = itemView.findViewById(R.id.tv_message_count);
            tvExchange = itemView.findViewById(R.id.tv_opportunity_exchange);
            ivStar = itemView.findViewById(R.id.img_star);
            ivMenu = itemView.findViewById(R.id.img_menu);
        }
    }
}
