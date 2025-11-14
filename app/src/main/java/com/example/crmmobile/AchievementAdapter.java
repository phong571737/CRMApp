package com.example.crmmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_CARD = 0;
    private static final int TYPE_ARROW = 1;

    private final List<Achievement> achievements;
    private final OnArrowClickListener listener;

    public interface OnArrowClickListener {
        void onArrowClick();
    }

    public AchievementAdapter(List<Achievement> achievements, OnArrowClickListener listener) {
        this.achievements = achievements;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        // Thêm 1 item arrow ở cuối
        return (achievements == null ? 0 : achievements.size()) + 1;
    }

    @Override
    public int getItemViewType(int position) {
        // Item cuối cùng là nút arrow
        if (achievements != null && position == achievements.size()) {
            return TYPE_ARROW;
        }
        return TYPE_CARD;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_CARD) {
            View view = inflater.inflate(R.layout.item_home_achievement_card, parent, false);
            return new CardViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_home_achievement_arrow, parent, false);
            return new ArrowViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CardViewHolder && achievements != null && position < achievements.size()) {
            Achievement item = achievements.get(position);
            ((CardViewHolder) holder).bind(item);
        } else if (holder instanceof ArrowViewHolder) {
            ArrowViewHolder arrowHolder = (ArrowViewHolder) holder;
            if (listener != null) {
                arrowHolder.arrow.setOnClickListener(v -> listener.onArrowClick());
            }
        }
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView title, value, rank;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_card_title);
            value = itemView.findViewById(R.id.tv_card_value);
            rank = itemView.findViewById(R.id.tv_card_rank);
        }

        void bind(Achievement item) {
            title.setText(item.getTitle());
            value.setText(item.getValue());
            rank.setText(item.getRank());
        }
    }

    static class ArrowViewHolder extends RecyclerView.ViewHolder {
        ImageView arrow;

        public ArrowViewHolder(@NonNull View itemView) {
            super(itemView);
            arrow = itemView.findViewById(R.id.img_arrow);
        }
    }

}
