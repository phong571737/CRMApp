package com.example.crmmobile;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterViewPager extends FragmentStateAdapter {
    public static final int TAB_HOME = 0;
    public static final int TAB_LEAD = 1;

    public static final int TAB_ORDER = 2;
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
            default:
                return new main_screen();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
