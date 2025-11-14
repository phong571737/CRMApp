package com.example.crmmobile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterQuoteTab extends FragmentStateAdapter {
    public AdapterQuoteTab(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public AdapterQuoteTab(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public AdapterQuoteTab(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new QuoteOverView();
            case 1:
                return new DetailQuote();
            default:
                return new QuoteOverView();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
