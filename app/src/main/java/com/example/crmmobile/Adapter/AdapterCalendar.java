package com.example.crmmobile.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.CalendarDirectory.CalendarViewHolder;
import com.example.crmmobile.R;

import java.util.ArrayList;

public class AdapterCalendar extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<String> dayofMonth;
    private final onItemListener onItemListener;

    public AdapterCalendar(ArrayList<String> dayofMonth, onItemListener onItemListener) {
        this.dayofMonth = dayofMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_day, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.16666666);

        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        String day = dayofMonth.get(position);
        holder.dayofMonth.setText(day);

        if(!day.equals("") && position % 7 == 6){ //sunday is red
            holder.dayofMonth.setTextColor(Color.parseColor("#F44336"));
        }
        else {
            holder.dayofMonth.setTextColor(Color.parseColor("#7E8A9A"));
        }

    }

    @Override
    public int getItemCount() {
        return dayofMonth.size();
    }

    public interface onItemListener{
        void onItemClick(int position, String dayText);
    }
}
