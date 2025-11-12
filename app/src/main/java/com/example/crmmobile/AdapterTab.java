package com.example.crmmobile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterTab extends FragmentStateAdapter {
    public AdapterTab(@NonNull Fragment fragment) {
        super(fragment);
    }

    public AdapterTab(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public AdapterTab(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new OverViewFragment(); //overview
            case 1: return new DetailFragment(); //detail
            default: return new OverViewFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
