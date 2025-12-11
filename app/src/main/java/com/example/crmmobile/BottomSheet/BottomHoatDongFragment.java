package com.example.crmmobile.BottomSheet;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crmmobile.CalendarDirectory.Calendar;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.IndividualDirectory.HoatDongFragment;
import com.example.crmmobile.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomHoatDongFragment extends BottomSheetDialogFragment {

    private TextView tvNgayBatDau, tvGioBatDau, tvNgayKetThuc, tvGioKetThuc;

    private CaNhan caNhan;
    private ImageView iccall, icmeeting;
    private HoatDongFragment hoatdongFragment;
    private TextView tvTitle;

    private AutoCompleteTextView actTrangThai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_hoatdong, container, false);

        // --- Nút đóng ---
        ImageView icCancel = view.findViewById(R.id.ic_cancel);
        icCancel.setOnClickListener(v -> dismiss());

        hoatdongFragment = new HoatDongFragment();

        actTrangThai = view.findViewById(R.id.acttrangthai);
        ArrayAdapter<String> adapterTrangThai = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Lên kế hoạch", "Đang diễn ra","Đã kết thúc" }
        );
        actTrangThai.setAdapter(adapterTrangThai);



        // --- Tab ---
        iccall = view.findViewById(R.id.ic_call);
        icmeeting = view.findViewById(R.id.ic_meeting);
        tvTitle = view.findViewById(R.id.tv_title);

        // --- Mặc định hiển thị tab Tổng quan ---
        setFragment(new HoatDongFragment());
        setActiveTab(iccall);

        // --- Click Cuộc gọi ---
        iccall.setOnClickListener(v -> {
            setFragment(new HoatDongFragment());
            setActiveTab(iccall);
            tvTitle.setText("Cuộc gọi");
        });

        // --- Click Cuộc họp ---
        icmeeting.setOnClickListener(v -> {
            setFragment(new HoatDongFragment());
            setActiveTab(icmeeting);
            tvTitle.setText("Cuộc họp");
        });

        tvNgayBatDau = view.findViewById(R.id.ngaybatdau);
        tvGioBatDau = view.findViewById(R.id.giobatdau);
        tvNgayKetThuc = view.findViewById(R.id.ngayketthuc);
        tvGioKetThuc = view.findViewById(R.id.gioketthuc);

        tvNgayBatDau.setOnClickListener(v -> showDatePicker(tvNgayBatDau, tvGioBatDau));
        tvGioBatDau.setOnClickListener(v -> showTimePicker(tvGioBatDau));

        tvNgayKetThuc.setOnClickListener(v -> showDatePicker(tvNgayKetThuc, tvGioKetThuc));
        tvGioKetThuc.setOnClickListener(v -> showTimePicker(tvGioKetThuc));


        return view;

    }

    public void setCaNhan(CaNhan cn) {
        this.caNhan = cn;
    }

    private void setFragment(Fragment fragment) {
        if (fragment instanceof HoatDongFragment && caNhan != null) {
            ((HoatDongFragment) fragment).setCaNhan(caNhan);
        }
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }


    private void setActiveTab(ImageView selectedTab) {
        // Tab chưa chọn: nền xám, icon xanh
        iccall.setBackgroundResource(R.drawable.rounded_input_box_small);
        iccall.setColorFilter(getResources().getColor(R.color.xanhbutton), android.graphics.PorterDuff.Mode.SRC_IN);

        icmeeting.setBackgroundResource(R.drawable.rounded_input_box_small);
        icmeeting.setColorFilter(getResources().getColor(R.color.xanhbutton), android.graphics.PorterDuff.Mode.SRC_IN);

        // Tab được chọn: nền xanh, icon trắng
        selectedTab.setBackgroundResource(R.drawable.rounded_input_box_selected);
        selectedTab.setColorFilter(getResources().getColor(R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

    }

    private void showDatePicker(TextView targetDateView, TextView targetTimeView) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    String date = String.format("%02d-%02d-%d", dayOfMonth, month + 1, year);
                    targetDateView.setText(date);

                    showTimePicker(targetTimeView);
                },
                calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH),
                calendar.get(java.util.Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void showTimePicker(TextView targetTimeView) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                (view, hourOfDay, minute) -> {
                    String time = String.format("%02d:%02d", hourOfDay, minute);
                    targetTimeView.setText(time);
                },
                calendar.get(java.util.Calendar.HOUR_OF_DAY),
                calendar.get(java.util.Calendar.MINUTE),
                true
        );

        timePickerDialog.show();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Full height bottom sheet
        View view = getView();
        if (view != null) {
            View parent = (View) view.getParent();
            BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
            parent.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            behavior.setPeekHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
}
