package com.example.crmmobile.CalendarDirectory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.Adapter.AdapterActivityList;
import com.example.crmmobile.AppConstant;
import com.example.crmmobile.DataBase.HoatDongRepository;
import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.R;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.Inflater;

public class ActivityListDialog extends DialogFragment {
    private RecyclerView rv_activity;
    private TextView tv_day, tv_month;
    private LocalDate day;
    private String dateofweek, dayinweek;
    private DayOfWeek dayOfWeek;
    public ActivityListDialog() {
    }

    public static ActivityListDialog newInstance(String date){
        Bundle arg = new Bundle();
        arg.putString(AppConstant.ACTIVITY_ARG, date);

        ActivityListDialog fragment = new ActivityListDialog();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_dialogfragment, container, false);
        initViews(view);

        String date = getArguments().getString(AppConstant.ACTIVITY_ARG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            day = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            dateofweek = day.format(DateTimeFormatter.ofPattern("dd"));//lấy ngày
            dayinweek = day.format(DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH));//lấy thứ
        }

        tv_day.setText(dateofweek);
        tv_month.setText(dayinweek);

        HoatDongRepository hoatDongRepository = new HoatDongRepository(requireContext());
        ArrayList<HoatDong> list = hoatDongRepository.getHoatDongByDay(date);

        rv_activity.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_activity.setAdapter(new AdapterActivityList(list));
        return view;
    }

    private void initViews(View view) {
        rv_activity = view.findViewById(R.id.rv_activity);
        tv_month = view.findViewById(R.id.tv_month);
        tv_day = view.findViewById(R.id.tv_day);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null){
            Window window = getDialog().getWindow();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = (int)(displayMetrics.widthPixels * 0.8);
            int height = (int)(displayMetrics.heightPixels * 0.6);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(width, height);
        }
    }
}
