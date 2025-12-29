package com.example.crmmobile.OrderDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crmmobile.Adapter.ChiTietPagerAdapter;
import com.example.crmmobile.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class FragmentChiTiet extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ChiTietPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Gắn layout fragment_chi_tiet.xml
        View view = inflater.inflate(R.layout.fragment_chi_tiet, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        List<String> tabTitles = Arrays.asList(
                "Thông tin chung", "SP/DV", "Thanh toán & vận chuyển"
        );

        pagerAdapter = new ChiTietPagerAdapter(this, tabTitles);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles.get(position))
        ).attach();

        viewPager.setCurrentItem(0);

        return view;
    }
}

