package com.example.crmmobile.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.CalendarDirectory.CalendarViewHolder;
import com.example.crmmobile.DataBase.HoatDongRepository;
import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AdapterCalendar extends RecyclerView.Adapter<AdapterCalendar.CalendarViewHolder> {
    private static final String TAG = "ADAPTER_CALENDAR";
    private  ArrayList<String> dayofMonth;
    private onItemListener listener;
    private LocalDate localDate;
    private HoatDongRepository hoatDongRepository;

    public AdapterCalendar(Context context,
                           ArrayList<String> dayofMonth,
                           LocalDate localDate,
                           onItemListener listener) {
        this.localDate = localDate;
        this.dayofMonth = dayofMonth;
        this.listener = listener;
        this.hoatDongRepository = new HoatDongRepository(context);
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.16666666);
        view.setLayoutParams(layoutParams);

        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        String day = dayofMonth.get(position);
        holder.day_text.setText(day);
        holder.layout_activity.removeAllViews();

        if (!day.isEmpty()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String dateStr = localDate.withDayOfMonth(Integer.parseInt(day)).format(formatter);
            ArrayList<HoatDong> list = hoatDongRepository.getHoatDongByDay(dateStr);

            Log.e(TAG, "Day: " + day + " - list size: " + (list == null ? 0 : list.size()));
            if (list != null && !list.isEmpty()){
                Log.e(TAG, "Day: " + day + " - list size: " + (list == null ? 0 : list.size()));
                int max = Math.min(list.size(), 3);
                for (int i = 0;i < max;i++){
                    TextView tv = new TextView(holder.itemView.getContext());

                    tv.setText(list.get(i).getTenHoatDong());
                    tv.setTextSize(12);
                    tv.setTextColor(ContextCompat.getColor(tv.getContext(), R.color.black));
                    tv.setMaxLines(1);
                    tv.setBackgroundResource(R.drawable.bg_activity_calendar);

                    GradientDrawable bg = (GradientDrawable) tv.getBackground();
                    bg.setColor(getActivityColor(i));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    params.topMargin = 4;
                    tv.setLayoutParams(params);

                    holder.layout_activity.addView(tv);
                }

            }
        }

        if(!day.equals("") && position % 7 == 6){ //sunday is red
            holder.day_text.setTextColor(Color.parseColor("#F44336"));
        }
        else {
            holder.day_text.setTextColor(Color.parseColor("#7E8A9A"));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null){
                listener.onItemClick(position, day);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dayofMonth.size();
    }

    public interface onItemListener{
        void onItemClick(int position, String dayText);
    }

    public static class CalendarViewHolder extends RecyclerView.ViewHolder{
        private TextView day_text;
        private LinearLayout layout_activity;
        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            day_text = itemView.findViewById(R.id.day_text);
            layout_activity = itemView.findViewById(R.id.layout_activity);
        }
    }

    private int getActivityColor(int index){
        int[] colors = {
                Color.parseColor("#89CFF0"),
                Color.parseColor("#FA8C16"),
                Color.parseColor("#FFEB3B"),
                Color.parseColor("#4CAF50")
        };
        return colors[index % colors.length];
    }
}
