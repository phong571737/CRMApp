package com.example.crmmobile.BottomSheet;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomActionFragment extends BottomSheetDialogFragment {

    private LinearLayout ghim, chinhsua, xoa, themHoatDong;
    private CaNhan caNhan; // item đang thao tác
    private OnActionListener listener; // callback về Activity
    private CaNhanRepository db;
    private static final String PREFS_NAME = "LoginPrefs";
    private static final String KEY_USER_ROLE = "userRole";

    // Setter từ Activity để truyền item và callback
    public void setCaNhan(CaNhan cn, OnActionListener l) {
        this.caNhan = cn;
        this.listener = l;
    }

    // Interface callback để Activity cập nhật RecyclerView
    public interface OnActionListener {
        void onDelete(CaNhan cn);
        void onEdit(CaNhan cn);
        void onAddHoatDong(CaNhan cn);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_actions, container, false);

        // --- Button đóng ---
        ImageView icCancel = view.findViewById(R.id.ic_cancel);
        icCancel.setOnClickListener(v -> dismiss());

        // --- Khởi tạo repository ---
        db = new CaNhanRepository(requireContext());

        // --- Bind các nút ---
        ghim = view.findViewById(R.id.ghim);
        chinhsua = view.findViewById(R.id.chinhsua);
        xoa = view.findViewById(R.id.xoa);
        themHoatDong = view.findViewById(R.id.themhoatdong);

        // --- Check role và ẩn/hiện nút xóa ---
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString(KEY_USER_ROLE, "");
        if (!"ADMIN".equals(userRole)) {
            // Ẩn nút xóa nếu không phải admin
            xoa.setVisibility(View.GONE);
        }

        // --- Nút ghim ---
        ghim.setOnClickListener(v -> dismiss()); // hiện tại chỉ đóng, có thể mở rộng

        // --- Nút chỉnh sửa ---
        chinhsua.setOnClickListener(v -> {
            if (caNhan != null && listener != null) {
                listener.onEdit(caNhan); // thông báo Activity mở form chỉnh sửa
            }
            dismiss();
        });

        // --- Nút xóa ---
        xoa.setOnClickListener(v -> {
            if (caNhan != null) {
                // Xóa trong DB
                db.delete(caNhan.getId());

                // Callback về Activity để cập nhật RecyclerView
                if (listener != null) {
                    listener.onDelete(caNhan);
                }
            }
            dismiss();
        });

        // --- Nút thêm hoạt động ---
        themHoatDong.setOnClickListener(v -> {
            if (caNhan != null && listener != null) {
                listener.onAddHoatDong(caNhan);
            }
            dismiss();
        });

        return view;
    }
}
