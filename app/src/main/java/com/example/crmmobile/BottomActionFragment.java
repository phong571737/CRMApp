package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import android.widget.Button;

import android.widget.LinearLayout;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomActionFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_actions, container, false);

        ImageView icCancel = view.findViewById(R.id.ic_cancel);
        icCancel.setOnClickListener(v -> dismiss());

        LinearLayout themHoatDong = view.findViewById(R.id.themhoatdong);
        themHoatDong.setOnClickListener(v -> {
            BottomHoatDongFragment bottomSheet = new BottomHoatDongFragment();
            bottomSheet.show(getParentFragmentManager(), "BottomHoatDong");
            dismiss();
        });

        view.findViewById(R.id.ghim).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.chinhsua).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.xoa).setOnClickListener(v -> dismiss());

        return view;
    }
}
