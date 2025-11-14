package com.example.crmmobile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ToChucLuaChonHanhDongSheet extends BottomSheetDialogFragment {

    // 1. Định nghĩa interface
    public interface ItemClickListener {
        void onActionGhim();
        void onActionXemTongQuan();
        void onActionThemHoatDong();
        void onActionChinhSua();
        void onActionXoa();
    }

    private ItemClickListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Gán listener từ Fragment cha
        if (getParentFragment() instanceof ItemClickListener) {
            mListener = (ItemClickListener) getParentFragment();
        } else {
            throw new RuntimeException("Parent Fragment must implement ItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Load layout đã tạo ở Bước 2
        return inflater.inflate(R.layout.bottomsheet_tochuc_luachonhanhdong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 2. Gán sự kiện click cho từng item
        view.findViewById(R.id.btn_close).setOnClickListener(v -> dismiss());

        view.findViewById(R.id.action_ghim).setOnClickListener(v -> {
            mListener.onActionGhim();
            dismiss();
        });
        view.findViewById(R.id.action_xem_tong_quan).setOnClickListener(v -> {
            mListener.onActionXemTongQuan();
            dismiss();
        });
        view.findViewById(R.id.action_them_hoat_dong).setOnClickListener(v -> {
            mListener.onActionThemHoatDong();
            dismiss();
        });
        view.findViewById(R.id.action_chinh_sua).setOnClickListener(v -> {
            mListener.onActionChinhSua();
            dismiss();
        });
        view.findViewById(R.id.action_xoa).setOnClickListener(v -> {
            mListener.onActionXoa();
            dismiss();
        });
    }
}
