package com.example.crmmobile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ChiTietToChucPagerAdapter extends FragmentStateAdapter {

    public ChiTietToChucPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Trả về Fragment tương ứng với vị trí
        if (position == 0) {
            return new ChiTietToChucTongQuanFragment(); // Tab "Tổng quan"
        } else {
            return new ChiTietToChucChiTietFragment(); // Tab "Chi tiết"
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
