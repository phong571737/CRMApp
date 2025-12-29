package com.example.crmmobile.OrderDirectory;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.crmmobile.OpportunityDirectory.EmptyFragment;
import com.example.crmmobile.IndividualDirectory.TongQuanFragment;

import java.util.List;

public class OrderDetailPagerAdapter extends FragmentStateAdapter {

    private final List<String> tabTitles;

    public OrderDetailPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<String> tabTitles) {
        super(fragmentActivity);
        this.tabTitles = tabTitles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String title = tabTitles.get(position);
        switch (title) {
            case "Tổng quan":
                return new TongQuanFragment();
            case "Chi tiết":
                return new FragmentChiTiet();
            default:
                return new EmptyFragment();
        }
    }

    @Override
    public int getItemCount() {
        return tabTitles.size();
    }
}
