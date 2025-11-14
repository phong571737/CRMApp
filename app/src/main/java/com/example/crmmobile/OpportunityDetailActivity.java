package com.example.crmmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class OpportunityDetailActivity extends AppCompatActivity {
    private ImageView ivBack, detailEdit;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private OpportunityDetailPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunity_detail);

        ivBack = findViewById(R.id.iv_opportunity_detail_back);
        tabLayout = findViewById(R.id.tl_opportunity_detail_tabs);
        viewPager = findViewById(R.id.vp_opportunity_detail_content);
        detailEdit = findViewById(R.id.iv_opportunity_detail_edit);

        // Nhận dữ liệu từ Intent
        Opportunity opportunity = (Opportunity) getIntent().getSerializableExtra("opportunity");

        // Danh sách tab
        List<String> tabTitles = Arrays.asList("Tổng quan", "Chi tiết", "Nhật ký", "Hoạt động");

        // Gắn adapter
        pagerAdapter = new OpportunityDetailPagerAdapter(this, tabTitles, opportunity);
        viewPager.setAdapter(pagerAdapter);

        // Gắn TabLayout với ViewPager
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles.get(position))
        ).attach();

        // Mặc định mở tab đầu tiên
        viewPager.setCurrentItem(0);

        // Nút back
        ivBack.setOnClickListener(v -> finish());

        // Nút chỉnh sửa (cây bút)
        detailEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, OpportunityFormActivity.class);
            intent.putExtra("mode", "update");
            intent.putExtra("opportunity", opportunity);
            intent.putExtra("position", -1);
            startActivity(intent);
        });
    }
}
