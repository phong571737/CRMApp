package com.example.crmmobile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterCreateLead extends FragmentStateAdapter {
    public static final int TAB_INFORMATION = 0;
    public static final int TAB_ANOTHER_INFORMATION = 1;

    public AdapterCreateLead(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public AdapterCreateLead(@NonNull Fragment fragment) {
        super(fragment);
    }

    public AdapterCreateLead(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == TAB_INFORMATION){
            return new lead_information();
        }else {
            return new another_lead_information();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
