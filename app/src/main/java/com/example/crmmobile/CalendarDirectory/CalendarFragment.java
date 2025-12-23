package com.example.crmmobile.CalendarDirectory;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crmmobile.Adapter.AdapterCalendar;
import com.example.crmmobile.AppConstant;
import com.example.crmmobile.DataBase.HoatDongRepository;
import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.R;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarFragment extends Fragment implements AdapterCalendar.onItemListener{
    private static final String TAG = "CALENDAR";
    private LocalDate selecteddate;
    private RecyclerView rv_calendar;
    private TextView tv_monthyear;
    private HoatDongRepository hoatDongRepository;
    private boolean decorationAdded = false;
    private int totalRows;
    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        rv_calendar = view.findViewById(R.id.rv_calendar);
        tv_monthyear = view.findViewById(R.id.tv_monthyear);

        selecteddate = LocalDate.now();
        getParentFragmentManager().setFragmentResultListener("REFRESH_HOATDONG",
                this,
                (request, bundle)->{
                    boolean refresh = bundle.getBoolean("REFRESH", false);
                    if (refresh){
                        setMonthView();
                    }
                });
        return view;
    }

    private void setMonthView() {
        selecteddate = selecteddate.withDayOfMonth(1);
        tv_monthyear.setText(monthYearFromDate(selecteddate));
        ArrayList<String> dayinMonth = daysInMonthArry(selecteddate);

        AdapterCalendar adapterCalendar = new AdapterCalendar(requireContext(),dayinMonth,  selecteddate,  this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        rv_calendar.setLayoutManager(layoutManager);
        if (!decorationAdded){
            rv_calendar.addItemDecoration(new CalendarRow(requireContext()));
            decorationAdded = true;
        }

        rv_calendar.setAdapter(adapterCalendar);
    }

    private ArrayList<String> daysInMonthArry(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            yearMonth = YearMonth.from(date);
            int DaysInMonth = yearMonth.lengthOfMonth();
            LocalDate firstofMonth = date.withDayOfMonth(1);
            int firstdayoffset = firstofMonth.getDayOfWeek().getValue() - 1;
            int totalCells = firstdayoffset + DaysInMonth;
            totalRows = (int) Math.ceil(totalCells / 7.0);

            for(int i = 0;i < totalRows * 7; i++){
                if (i < firstdayoffset || i >= DaysInMonth + firstdayoffset){
                    daysInMonthArray.add("");
                }
                else{
                    daysInMonthArray.add(String.valueOf(i - firstdayoffset + 1));
                }
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate selecteddate) {
        DateTimeFormatter formatter = null;
        String ngayBD = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
            ngayBD = selecteddate.format(formatter);
        }
        return ngayBD;
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if (TextUtils.isEmpty(dayText)) return;

        hoatDongRepository = new HoatDongRepository(requireContext());
        int day = Integer.parseInt(dayText);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate clickedDate = selecteddate.withDayOfMonth(day);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String ngayBD = clickedDate.format(formatter);

            ArrayList<HoatDong> list = hoatDongRepository.getHoatDongByDay(ngayBD);
            Log.e(TAG, "List: " + list);
            Log.e(TAG, "Start Day: " + ngayBD);
            Log.e(TAG, "date format: " + formatter);
            if (list.isEmpty()){
                Toast.makeText(getContext(), "Không có hoạt động", Toast.LENGTH_SHORT).show();
            }
            else {
                showHoatDong(ngayBD);
            }
        }
    }

    private void showHoatDong(String date) {
        ActivityListDialog dialog = ActivityListDialog.newInstance(date);
        dialog.show(getParentFragmentManager(), AppConstant.ACTIVITY);
    }

    @Override
    public void onResume() {
        super.onResume();
        setMonthView();
    }
}