package com.example.crmmobile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TabActivity extends AppCompatActivity {

    private TextView tabTongQuan, tabChiTiet, tabHoatDong, tabCoHoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoilienhe); // Đây là file XML bạn vừa gửi

        // --- Ánh xạ view ---
        tabTongQuan = findViewById(R.id.tab_tongquan);
        tabChiTiet = findViewById(R.id.tab_chitiet);
        tabHoatDong = findViewById(R.id.tab_hoatdong);
        tabCoHoi = findViewById(R.id.tab_cohoi);

        // --- Mặc định hiển thị tab Tổng quan ---
        setFragment(new TongQuanFragment());
        setActiveTab(tabTongQuan);

        // --- Xử lý sự kiện click các tab ---
        tabTongQuan.setOnClickListener(v -> {
            setFragment(new TongQuanFragment());
            setActiveTab(tabTongQuan);
        });

        tabChiTiet.setOnClickListener(v -> {
            setFragment(new ChiTietFragment());
            setActiveTab(tabChiTiet);
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
        selectedTab.setBackgroundResource(R.drawable.edittext_line); // ✅ Dòng bạn muốn thêm
    }
}
