package com.example.crmmobile.BottomSheet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crmmobile.DataBase.HoatDongRepository;
import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.IndividualDirectory.DanhSachCaNhanActivity;
import com.example.crmmobile.IndividualDirectory.HoatDongFragment;
import com.example.crmmobile.IndividualDirectory.ThongTinLienHeActivity;
import com.example.crmmobile.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.Toast;

import java.util.Calendar;


public class BottomHoatDongFragment extends BottomSheetDialogFragment {
    private CaNhan caNhan;
    private ImageView iccall, icmeeting;
    private TextView tv_title;
    private Button btnLuu;
    private HoatDongFragment hoatdongFragment;
    private Calendar calendar;
    private TextView tvNgayBatDau, tvGioBatDau;
    private TextView tvNgayKetThuc, tvGioKetThuc;



    private AutoCompleteTextView actTrangThai, actNguoiPhuTrach;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_hoatdong, container, false);

        // --- Nút đóng ---
        ImageView icCancel = view.findViewById(R.id.ic_cancel);
        icCancel.setOnClickListener(v -> dismiss());

        tv_title = view.findViewById(R.id.tv_title);
        //hoatdongFragment = new HoatDongFragment();

        actNguoiPhuTrach = view.findViewById(R.id.actnguoiphutrach);
        ArrayAdapter<String> adapterPhuTrach = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Phan Thị Tường Vi", "Lê Thị Ánh Xuân","Nguyễn Hữu Thiện" }
        );
        actNguoiPhuTrach.setAdapter(adapterPhuTrach);

        actTrangThai = view.findViewById(R.id.acttrangthai);
        ArrayAdapter<String> adapterTrangThai = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Lên kế hoạch", "Đang diễn ra","Đã kết thúc" }
        );
        actTrangThai.setAdapter(adapterTrangThai);

        tvNgayBatDau = view.findViewById(R.id.ngaybatdau);
        tvGioBatDau = view.findViewById(R.id.giobatdau);

        tvNgayKetThuc = view.findViewById(R.id.ngayketthuc);
        tvGioKetThuc = view.findViewById(R.id.gioketthuc);

        tvNgayBatDau.setOnClickListener(v ->
                pickDateTime(tvNgayBatDau, tvGioBatDau)
        );

        tvNgayKetThuc.setOnClickListener(v ->
                pickDateTime(tvNgayKetThuc, tvGioKetThuc)
        );

        java.util.Calendar calendar = java.util.Calendar.getInstance();

        // --- Tab ---
        iccall = view.findViewById(R.id.ic_call);
        icmeeting = view.findViewById(R.id.ic_meeting);

        // --- Mặc định hiển thị tab Tổng quan ---
        setFragment(new HoatDongFragment());
        setActiveTab(iccall);

        // --- Click tab ---
        iccall.setOnClickListener(v -> {
            setFragment(new HoatDongFragment());
            setActiveTab(iccall);
            tv_title.setText("Cuộc gọi");
        });

        icmeeting.setOnClickListener(v -> {
            setFragment(new HoatDongFragment());
            setActiveTab(icmeeting);
            tv_title.setText("Cuộc họp");
        });

        btnLuu = view.findViewById(R.id.btnLuu);
        btnLuu.setOnClickListener(v -> saveHoatDong());


        return view;

    }

    private void saveHoatDong() {

        Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (!(currentFragment instanceof HoatDongFragment)) {
            Toast.makeText(requireContext(),
                    "Lỗi: Không tìm thấy form nhập liệu",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        HoatDongFragment currentHoatDongFragment = (HoatDongFragment) currentFragment;

        String tenHoatDong = currentHoatDongFragment.getTieuDe();
        String moTa = currentHoatDongFragment.getMoTa();
        String toChuc = currentHoatDongFragment.getCongTy();

        String ngayBatDau = tvNgayBatDau.getText().toString();
        String thoiGianBatDau = tvGioBatDau.getText().toString();
        String thoiGianKetThuc = tvGioKetThuc.getText().toString();
      //  String giaoCho = actNguoiPhuTrach.getText().toString();

        String tinhTrang = actTrangThai.getText().toString();

        // LẤY ID ĐÚNG
        int nguoiLienHe = currentHoatDongFragment.getNguoiLienHeId();


        if (tenHoatDong == null || tenHoatDong.trim().isEmpty()) {
            Toast.makeText(requireContext(),
                    "Vui lòng nhập tiêu đề hoạt động",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // FK tạm
        int nhanVien = 0;
        int giaoCho = 0;
        int coHoi = 0;

        if (moTa == null) moTa = "";
        if (toChuc == null) toChuc = "";


        HoatDong hoatDong = new HoatDong(
                tenHoatDong,
                thoiGianBatDau,
                thoiGianKetThuc,
                ngayBatDau,
                tinhTrang,
                nhanVien,
                toChuc,
                nguoiLienHe, //
                coHoi,
                moTa,
                giaoCho
        );

        HoatDongRepository repository = new HoatDongRepository(requireContext());
        long result = repository.add(hoatDong);
        Log.d(Long.toString(result), "vi");

        if (result > 0) {
            Toast.makeText(requireContext(),
                    "Lưu hoạt động thành công",
                    Toast.LENGTH_SHORT).show();
            dismiss();
        } else {
            Toast.makeText(requireContext(),
                    "Lưu thất bại",
                    Toast.LENGTH_SHORT).show();
        }
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

    private void pickDateTime(TextView tvDate, TextView tvTime) {

        Calendar calendar = Calendar.getInstance();

        // --- Chọn ngày ---
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {

                    String date = String.format(
                            "%02d-%02d-%04d",
                            dayOfMonth,
                            month + 1,
                            year
                    );
                    tvDate.setText(date);

                    // --- Chọn giờ ---
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            requireContext(),
                            (timeView, hourOfDay, minute) -> {

                                String time = String.format(
                                        "%02d:%02d",
                                        hourOfDay,
                                        minute
                                );
                                tvTime.setText(time);

                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                    );
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
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
