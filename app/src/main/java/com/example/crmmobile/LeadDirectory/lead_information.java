package com.example.crmmobile.LeadDirectory;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class lead_information extends Fragment {
    private ViewModelLead viewModelLead;
    private TextInputLayout tvTitle, inforname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead_information,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle  = view.findViewById(R.id.tv_title);
        inforname = view.findViewById(R.id.infor_name);

        viewModelLead = new ViewModelProvider(requireActivity()).get(ViewModelLead.class);

        //lưu giá trị mỗi khi user nhập
        Objects.requireNonNull(tvTitle.getEditText())
                .addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        viewModelLead.title = s.toString();
                    }
                });

        Objects.requireNonNull(inforname.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModelLead.name = s.toString();
            }
        });
    }
}
