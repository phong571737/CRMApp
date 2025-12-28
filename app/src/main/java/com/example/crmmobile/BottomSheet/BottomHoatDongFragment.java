package com.example.crmmobile.BottomSheet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
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
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.IndividualDirectory.CaNhan;

import com.example.crmmobile.IndividualDirectory.HoatDongFragment;
import com.example.crmmobile.IndividualDirectory.ThongTinLienHeActivity;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.MainDirectory.InitClass;
import com.example.crmmobile.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class BottomHoatDongFragment extends BottomSheetDialogFragment {
    private static final String TAG = "BOTTOM_HOATDONG";
    private CaNhan caNhan;
    private ImageView iccall, icmeeting, icCancel, ic_face2;
    private TextView tv_title;
    private Button btnLuu;
    private Calendar calendar;
    private TextView tvNgayBatDau, tvGioBatDau;
    private TextView tvNgayKetThuc, tvGioKetThuc;
    private String currentType = "call";
    private int LeadID = -1;
    private int quoteID = -1;
    private int opportunityID = -1;

    private HoatDong hoatDong;

    private AutoCompleteTextView actTrangThai, actNguoiPhuTrach;
    private int selectedNguoiPhuTrachId = 0; // ID người phụ trách được chọn

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_hoatdong, container, false);
        initViews(view);

        // --- Nút đóng ---
        icCancel.setOnClickListener(v -> dismiss());

        ArrayAdapter<String> adapterTrangThai = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Lên kế hoạch", "Đang diễn ra","Đã kết thúc" }
        );
        actTrangThai.setAdapter(adapterTrangThai);

        tvNgayBatDau.setOnClickListener(v ->pickDateTime(tvNgayBatDau, tvGioBatDau)
        );

        tvNgayKetThuc.setOnClickListener(v ->
                pickDateTime(tvNgayKetThuc, tvGioKetThuc)
        );

        calendar = Calendar.getInstance();

        // --- Mặc định hiển thị tab Tổng quan ---
        setFragment(new HoatDongFragment());
        setActiveTab(iccall);

        // --- Click tab ---
        iccall.setOnClickListener(v -> {
            setFragment(new HoatDongFragment());
            setActiveTab(iccall);
            currentType = "call";
            tv_title.setText("Cuộc gọi");
        });

        icmeeting.setOnClickListener(v -> {
            setFragment(new HoatDongFragment());
            setActiveTab(icmeeting);
            currentType = "meeting";
            tv_title.setText("Cuộc họp");
        });

        btnLuu.setOnClickListener(v -> saveHoatDong());

        return view;

    }

    private void initViews(View view) {
        icCancel = view.findViewById(R.id.ic_cancel);
        tv_title = view.findViewById(R.id.tv_title);
        actNguoiPhuTrach = view.findViewById(R.id.actnguoiphutrach);
        //ánh xạ tên
        initPerson_in_Charge();
        tvNgayBatDau = view.findViewById(R.id.ngaybatdau);
        tvGioBatDau = view.findViewById(R.id.giobatdau);
        tvNgayKetThuc = view.findViewById(R.id.ngayketthuc);
        tvGioKetThuc = view.findViewById(R.id.gioketthuc);
        btnLuu = view.findViewById(R.id.btnLuu);
        iccall = view.findViewById(R.id.ic_call);
        icmeeting = view.findViewById(R.id.ic_meeting);
        actTrangThai = view.findViewById(R.id.acttrangthai);
        ic_face2 = view.findViewById(R.id.ic_face2);

    }

    private void initPerson_in_Charge() {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());
        executor.execute(()->{
            NhanVienRepository nhanVienRepository = new NhanVienRepository(requireContext());
            nhanVienRepository.AddNhanVien();
            List<Nhanvien> Employees_list = nhanVienRepository.getAllNhanVien();
            mainHandler.post(()->{
                ArrayAdapter<Nhanvien> AdapterEmployer =
                        new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_list_item_1,
                                Employees_list);
                actNguoiPhuTrach.setAdapter(AdapterEmployer);
                actNguoiPhuTrach.setOnItemClickListener(((parent, view1, position, id) -> {
                    Nhanvien nv = (Nhanvien) parent.getItemAtPosition(position);
                    if (nv != null){
                        selectedNguoiPhuTrachId = nv.getId(); // Lưu ID người phụ trách được chọn
                        ic_face2.setVisibility(View.VISIBLE);
                        int level = InitClass.getIconNhanVien(nv.getId());
                        ic_face2.setImageLevel(level);
                    } else {
                        selectedNguoiPhuTrachId = 0;
                    }
                }));
            });
        });
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
        int toChuc = currentHoatDongFragment.getCongTyId();
        int coHoi = currentHoatDongFragment.getCoHoiId();
        int giaoCho = currentHoatDongFragment.getNguoiDungId(); // Lấy ID người dùng được mời

        String ngayBatDau = tvNgayBatDau.getText().toString();
        String thoiGianBatDau = tvGioBatDau.getText().toString();
        String thoiGianKetThuc = tvGioKetThuc.getText().toString();
        String tinhTrang = actTrangThai.getText().toString();

        // LẤY ID ĐÚNG
//        int nguoiLienHe = currentHoatDongFragment.getNguoiLienHeId();
        int nguoiLienHe = 0;
        if (caNhan != null){
            nguoiLienHe = caNhan.getId();
        }
        else if (LeadID > 0){
            nguoiLienHe = LeadID;
        }

        if (TextUtils.isEmpty(tenHoatDong)) {
            Toast.makeText(requireContext(),
                    "Vui lòng nhập tiêu đề hoạt động",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy ID người phụ trách đã chọn
        int nhanVien = selectedNguoiPhuTrachId;

        if (moTa == null) moTa = "";


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
                giaoCho,
                currentType
        );

        HoatDongRepository repository = new HoatDongRepository(requireContext());
        long result = repository.add(hoatDong);
        Log.d(Long.toString(result), "vi");

        if (result > 0) {
            Toast.makeText(requireContext(),
                    "Lưu hoạt động thành công",
                    Toast.LENGTH_SHORT).show();
            Bundle resultBundle = new Bundle();
            resultBundle.putBoolean("REFRESH", true);
            getParentFragmentManager().setFragmentResult("REFRESH_HOATDONG", resultBundle);
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

    public void setLead(int leadID) {
        this.LeadID = leadID;
    }

    public void setQuote(int quoteID){
        this.quoteID = quoteID;
    }

    private void setFragment(Fragment fragment) {
        if (fragment instanceof HoatDongFragment) {
            if (caNhan != null){
                ((HoatDongFragment) fragment).setCaNhan(caNhan);
            }
//            if (LeadID > 0){
//                ((HoatDongFragment) fragment).setLead(LeadID);
//            }
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

//        Calendar calendar = Calendar.getInstance();

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
                    Log.e(TAG, "date: " + tvDate.getText().toString());

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

