package com.example.crmmobile.OrganizationDirectory;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ChiTietToChucPagerAdapter extends FragmentStateAdapter {
    private int companyId;
    public ChiTietToChucPagerAdapter(@NonNull FragmentActivity fa, int id) {
        super(fa);
        this.companyId = id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return ChiTietToChucTongQuanFragment.newInstance(companyId);
        else return ChiTietToChucChiTietFragment.newInstance(companyId);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
