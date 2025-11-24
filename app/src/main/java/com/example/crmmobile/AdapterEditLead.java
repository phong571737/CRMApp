package com.example.crmmobile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterEditLead extends FragmentStateAdapter {
    public static final int TAB_INFORMATION = 0;
    public static final int TAB_ANOTHER_INFORMATION = 1;

    public AdapterEditLead(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == TAB_INFORMATION){
            return new lead_information();
        }
        else{
            return new another_lead_information();
        }
    }
}