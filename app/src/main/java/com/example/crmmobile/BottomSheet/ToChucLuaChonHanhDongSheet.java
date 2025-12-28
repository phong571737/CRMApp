package com.example.crmmobile.BottomSheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crmmobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ToChucLuaChonHanhDongSheet extends BottomSheetDialogFragment {

    // 1. Định nghĩa interface
    public interface ItemClickListener {
        void onActionChinhSua();
        void onActionXoa();
    }

    private ItemClickListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof ItemClickListener) {
            mListener = (ItemClickListener) getParentFragment();
        } else {
            throw new RuntimeException("Parent Fragment must implement ItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet_tochuc_luachonhanhdong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_close).setOnClickListener(v -> dismiss());

        view.findViewById(R.id.action_chinh_sua).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onActionChinhSua();
            }
            dismiss();
        });
        view.findViewById(R.id.action_xoa).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onActionXoa();
            }
            dismiss();
        });
    }
}
