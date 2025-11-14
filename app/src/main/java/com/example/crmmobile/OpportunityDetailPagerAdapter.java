package com.example.crmmobile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.crmmobile.feature.salesorder.ui.common.EmptyFragment;

import java.util.List;

public class OpportunityDetailPagerAdapter extends FragmentStateAdapter {

    private final List<String> tabTitles;
    private final Opportunity opportunity;

    public OpportunityDetailPagerAdapter(@NonNull FragmentActivity fragmentActivity,
                                         List<String> tabTitles,
                                         Opportunity opportunity) {
        super(fragmentActivity);
        this.tabTitles = tabTitles;
        this.opportunity = opportunity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String title = tabTitles.get(position);
        switch (title) {
            case "Tổng quan":
                return OpportunityDetailTabOverviewFragment.newInstance(opportunity);
            case "Chi tiết":
                return OpportunityDetailTabInfoFragment.newInstance(opportunity);
            default:
                return new EmptyFragment();
        }


    }

    @Override
    public int getItemCount() {
        return tabTitles.size();
    }
}