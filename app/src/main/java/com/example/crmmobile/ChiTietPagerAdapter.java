package com.example.crmmobile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.crmmobile.feature.salesorder.ui.common.EmptyFragment;
import com.example.crmmobile.feature.salesorder.ui.detail.FragmentKhac;
import com.example.crmmobile.feature.salesorder.ui.detail.FragmentSPDV;
import com.example.crmmobile.feature.salesorder.ui.detail.FragmentThanhToanVanChuyen;

import java.util.List;

public class ChiTietPagerAdapter extends FragmentStateAdapter {

    private List<String> tabTitles;

    public ChiTietPagerAdapter(@NonNull Fragment fragment, List<String> tabTitles) {
        super(fragment);
        this.tabTitles = tabTitles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ThongTinChungFragment();
            case 1:
                return new FragmentSPDV();
            case 2:
                return new FragmentThanhToanVanChuyen();
            case 3:
                return new FragmentKhac();
            default:
                return new EmptyFragment(); // fallback
        }
    }

    @Override
    public int getItemCount() {
        return tabTitles.size();
    }
}
