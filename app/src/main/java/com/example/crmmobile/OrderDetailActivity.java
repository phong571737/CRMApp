package com.example.crmmobile;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    private ImageView iv_back;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private OrderDetailPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        iv_back = findViewById(R.id.btnBack);

        // Danh sách tên tab
        List<String> tabTitles = Arrays.asList(
                "Tổng quan", "Chi tiết", "Nhật ký", "Hoạt động"
        );

        // Gắn adapter cho ViewPager2
        pagerAdapter = new OrderDetailPagerAdapter(this, tabTitles);
        viewPager.setAdapter(pagerAdapter);

        // Liên kết TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles.get(position))
        ).attach();

        // Mặc định mở tab “Tổng quan”
        viewPager.setCurrentItem(0);
        String orderCode = getIntent().getStringExtra("orderCode");
        String company = getIntent().getStringExtra("company");
        String date = getIntent().getStringExtra("date");
        String status = getIntent().getStringExtra("status");

        iv_back.setOnClickListener(v -> {
            finish();
        });
    }
}


