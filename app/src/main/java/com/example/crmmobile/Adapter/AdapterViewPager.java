package com.example.crmmobile.Adapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.crmmobile.CalendarDirectory.Calendar;
import com.example.crmmobile.OrderDirectory.OrderFragment;
import com.example.crmmobile.LeadDirectory.leadFragment;
import com.example.crmmobile.MainDirectory.main_screen;

public class AdapterViewPager extends FragmentStateAdapter {
    public static final int TAB_HOME = 0;
    public static final int TAB_LEAD = 1;

    public static final int TAB_ORDER = 2;
    public static final int TAB_CALENDAR = 3;
    public AdapterViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public AdapterViewPager(@NonNull Fragment fragment) {
        super(fragment);
    }

    public AdapterViewPager(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case TAB_HOME:
                return new main_screen();
            case TAB_LEAD:
                return new leadFragment();
            case TAB_ORDER:
                return new OrderFragment();
            case TAB_CALENDAR:
                return new Calendar();
            default:
                return new main_screen();

        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
