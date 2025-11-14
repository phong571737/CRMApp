package com.example.crmmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OpportunityDetailTabInfoFragment extends Fragment {

    public static OpportunityDetailTabInfoFragment newInstance(Opportunity opportunity) {
        OpportunityDetailTabInfoFragment fragment = new OpportunityDetailTabInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable("opportunity", opportunity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Opportunity opportunity = (Opportunity) getArguments().getSerializable("opportunity");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_opportunity_detail_tab_info, container, false);
    }
}