package com.example.crmmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    private List<Goal> goalList;

    public GoalAdapter(List<Goal> goalList) {
        this.goalList = goalList;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_goals_card, parent, false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        Goal goal = goalList.get(position);
        holder.tvTitle.setText(goal.getTitle());
        holder.tvProgress.setText(goal.getProgress());
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    public void addGoal(Goal newGoal) {
        goalList.add(newGoal);
        notifyItemInserted(goalList.size() - 1);
    }

    public static class GoalViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvProgress;

        public GoalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvGoalTitle);
            tvProgress = itemView.findViewById(R.id.tvGoalProgress);
        }
    }
}
