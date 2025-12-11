package com.example.crmmobile.LeadDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.R;
import com.google.android.material.textfield.TextInputEditText;

public class another_lead_information extends Fragment {
    private TextInputEditText company_name;
    private ViewModelLead viewModelLead;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_another_information,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        company_name = view.findViewById(R.id.company_name);
        viewModelLead = new ViewModelProvider(requireActivity()).get(ViewModelLead.class);



    }
}
