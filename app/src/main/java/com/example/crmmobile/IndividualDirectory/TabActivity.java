package com.example.crmmobile.IndividualDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.R;

/**
 * CHỈNH SỬA CLASS TabActivity
 */

public class TabActivity extends AppCompatActivity {

    private TextView tabTongQuan, tabChiTiet, tabHoatDong, tabCoHoi;
    private ImageView icBack;

    private TextView tvHeaderTen, tvHeaderSdt, tvHeaderEmail, tvHeaderCongTy, tvHeaderNguoiTao, tvHeaderNguoiPhuTrach;

    private CaNhan currentCaNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoilienhe);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("CANHAN_OBJECT")) {
            currentCaNhan = (CaNhan) intent.getSerializableExtra("CANHAN_OBJECT");
        }

        initViews();

        fillHeaderData();

        // --- Mặc định hiển thị tab Tổng quan ---
        TongQuanFragment tongQuanFragment = new TongQuanFragment();
        Bundle bundleTongQuan = new Bundle();
        bundleTongQuan.putSerializable("CANHAN_DATA", currentCaNhan);
        tongQuanFragment.setArguments(bundleTongQuan);
        setFragment(tongQuanFragment);

        setActiveTab(tabTongQuan);

        // --- Xử lý sự kiện click các tab ---
        tabTongQuan.setOnClickListener(v -> {
            setFragment(new TongQuanFragment());
            setActiveTab(tabTongQuan);
        });

        tabChiTiet.setOnClickListener(v -> {
            ChiTietFragment fragment = new ChiTietFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("CANHAN_DATA", currentCaNhan);
            fragment.setArguments(bundle);

            setFragment(fragment);
            setActiveTab(tabChiTiet);
        });

        icBack.setOnClickListener(v -> {
            finish();
        });
//        tabHoatDong.setOnClickListener(v -> {
//            setFragment(new HoatDongFragment());
//            setActiveTab(tabHoatDong);
//        });
//
//        tabCoHoi.setOnClickListener(v -> {
//            setFragment(new CoHoiFragment());
//            setActiveTab(tabCoHoi);
//        });
    }

    /**
     * Hàm hiển thị fragment mới trong container
     */
    private void initViews() {
        tabTongQuan = findViewById(R.id.tab_tongquan);
        tabChiTiet = findViewById(R.id.tab_chitiet);
        tabHoatDong = findViewById(R.id.tab_hoatdong);
        tabCoHoi = findViewById(R.id.tab_cohoi);
        icBack = findViewById(R.id.ic_back);

        // Header Views
        tvHeaderTen = findViewById(R.id.tennguoilienhe);
        tvHeaderSdt = findViewById(R.id.sodienthoai);
        tvHeaderEmail = findViewById(R.id.email);
        tvHeaderCongTy = findViewById(R.id.rowcongty);
        tvHeaderNguoiTao = findViewById(R.id.tennguoitao);
        tvHeaderNguoiPhuTrach = findViewById(R.id.tennguoiphutrach);
    }

    private void fillHeaderData() {
        if (currentCaNhan != null) {
            tvHeaderTen.setText(checkNull(currentCaNhan.getHoVaTen() + " " + currentCaNhan.getTen()));
            tvHeaderSdt.setText(checkNull(currentCaNhan.getDiDong()));
            tvHeaderEmail.setText(checkNull(currentCaNhan.getEmail()));
            tvHeaderCongTy.setText(checkNull(currentCaNhan.getCongTy()));
            // Giả sử tên người tạo lấy từ User đăng nhập, ở đây tạm thời set cứng hoặc lấy từ DB nếu có field
            // tvHeaderNguoiTao.setText(...);
            tvHeaderNguoiPhuTrach.setText(checkNull(currentCaNhan.getGiaoCho()));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    /**
     * Hàm đổi màu tab được chọn + đổi viền
     */
    private void setActiveTab(TextView selectedTab) {
        // Reset màu và background tất cả tab về mặc định
        tabTongQuan.setTextColor(getResources().getColor(R.color.grey));
        tabTongQuan.setBackgroundResource(android.R.color.transparent);

        tabChiTiet.setTextColor(getResources().getColor(R.color.grey));
        tabChiTiet.setBackgroundResource(android.R.color.transparent);

        tabHoatDong.setTextColor(getResources().getColor(R.color.grey));
        tabHoatDong.setBackgroundResource(android.R.color.transparent);

        tabCoHoi.setTextColor(getResources().getColor(R.color.grey));
        tabCoHoi.setBackgroundResource(android.R.color.transparent);

        // Tab được chọn hiển thị màu xanh + viền dưới
        selectedTab.setTextColor(getResources().getColor(R.color.blue));
        selectedTab.setBackgroundResource(R.drawable.edittext_line);
    }

    private String checkNull(String text) {
        return text == null ? "" : text;
    }
}
