package com.example.crmmobile.IndividualDirectory;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;


public class TongQuanFragment extends Fragment {
    public TongQuanFragment() {
        // Constructor rỗng là bắt buộc
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // Gắn layout UI của tab Tổng quan
        return inflater.inflate(R.layout.fragment_tong_quan, container, false);
    }


}
