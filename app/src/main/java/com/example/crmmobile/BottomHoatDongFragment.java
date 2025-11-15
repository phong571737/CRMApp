package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomHoatDongFragment extends BottomSheetDialogFragment {

    private ImageView iccall, icmeeting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_hoatdong, container, false);

        // --- Nút đóng ---
        ImageView icCancel = view.findViewById(R.id.ic_cancel);
        icCancel.setOnClickListener(v -> dismiss());

        // --- Tab ---
        iccall = view.findViewById(R.id.ic_call);
        icmeeting = view.findViewById(R.id.ic_meeting);

        // --- Mặc định hiển thị tab Tổng quan ---
        setFragment(new HoatDongFragment());
        setActiveTab(iccall);

        // --- Click tab ---
        iccall.setOnClickListener(v -> {
            setFragment(new HoatDongFragment());
            setActiveTab(iccall);
        });

        icmeeting.setOnClickListener(v -> {
            setFragment(new HoatDongFragment());
            setActiveTab(icmeeting);
        });

        return view;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    private void setActiveTab(ImageView selectedTab) {
        // Tab chưa chọn: nền xám, icon xanh
        iccall.setBackgroundResource(R.drawable.rounded_input_box_small);
        iccall.setColorFilter(getResources().getColor(R.color.xanhbutton), android.graphics.PorterDuff.Mode.SRC_IN);

        icmeeting.setBackgroundResource(R.drawable.rounded_input_box_small);
        icmeeting.setColorFilter(getResources().getColor(R.color.xanhbutton), android.graphics.PorterDuff.Mode.SRC_IN);

        // Tab được chọn: nền xanh, icon trắng
        selectedTab.setBackgroundResource(R.drawable.rounded_input_box_selected);
        selectedTab.setColorFilter(getResources().getColor(R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
    }



    @Override
    public void onStart() {
        super.onStart();
        // Full height bottom sheet
        View view = getView();
        if (view != null) {
            View parent = (View) view.getParent();
            BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
            parent.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            behavior.setPeekHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
}
