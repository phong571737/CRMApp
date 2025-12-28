package com.example.crmmobile.OrderDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONObject;

public class ThanhToanFragment extends Fragment {

    private TextView tvPaymentMethod, tvPaymentStatus;
    private View rowPaymentMethod, rowPaymentStatus, btnAddMore;

    private final ActivityResultLauncher<Intent> create4Launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() != android.app.Activity.RESULT_OK || result.getData() == null) return;

                Intent data = result.getData();
                String coHoi = data.getStringExtra(SOCreate4Activity.EXTRA_COHoi);
                String baoGia = data.getStringExtra(SOCreate4Activity.EXTRA_BAOGIA);
                String moTa = data.getStringExtra(SOCreate4Activity.EXTRA_MOTA);
                String dieuKhoan = data.getStringExtra(SOCreate4Activity.EXTRA_DIEUKHOAN);
                String giaoCho = data.getStringExtra(SOCreate4Activity.EXTRA_GIAOCHO);

                // Lưu vào draftExtra để khi Save Order -> ghi xuống DB
                putExtra("coHoi", coHoi);
                putExtra("baoGia", baoGia);
                putExtra("moTa", moTa);
                putExtra("dieuKhoan", dieuKhoan);
                putExtra("giaoCho", giaoCho);

                Toast.makeText(requireContext(), "Đã cập nhật thông tin liên quan", Toast.LENGTH_SHORT).show();
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thanh_toan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvPaymentMethod = view.findViewById(R.id.tvPaymentMethod);
        tvPaymentStatus = view.findViewById(R.id.tvPaymentStatus);

        rowPaymentMethod = view.findViewById(R.id.rowPaymentMethod);
        rowPaymentStatus = view.findViewById(R.id.rowPaymentStatus);
        btnAddMore       = view.findViewById(R.id.btnAddRecurring);

        // ====== Load draft nếu có ======
        JSONObject draft = getDraftExtra();

        // Phương thức thanh toán: cố định
        String method = draft.optString("paymentMethod", "Thanh toán trực tiếp");
        tvPaymentMethod.setText(method);
        putExtra("paymentMethod", "Thanh toán trực tiếp"); // ép cố định

        // Tình trạng thanh toán: default
        String status = draft.optString("paymentStatus", "Chưa thanh toán");
        tvPaymentStatus.setText(status);
        putExtra("paymentStatus", status);

        // Click phương thức -> chỉ báo là cố định
        rowPaymentMethod.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Phương thức: Thanh toán trực tiếp", Toast.LENGTH_SHORT).show()
        );

        // Click tình trạng -> dialog chọn 1
        rowPaymentStatus.setOnClickListener(v -> showPaymentStatusDialog());

        // + Thêm mới -> mở SOCreate4Activity
        btnAddMore.setOnClickListener(v -> openSOCreate4());
    }

    private void showPaymentStatusDialog() {
        final String[] items = {"Đã thanh toán", "Chưa thanh toán"};
        String current = tvPaymentStatus.getText() != null ? tvPaymentStatus.getText().toString().trim() : "Chưa thanh toán";

        int checked = 1;
        if ("Đã thanh toán".equalsIgnoreCase(current)) checked = 0;

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Tình trạng thanh toán")
                .setSingleChoiceItems(items, checked, (dialog, which) -> {
                    String picked = items[which];
                    tvPaymentStatus.setText(picked);
                    putExtra("paymentStatus", picked);
                    Bundle b = new Bundle();
                    b.putString("paymentStatus", picked);
                    b.putString("paymentMethod", "Thanh toán trực tiếp");
                    getParentFragmentManager().setFragmentResult("PAYMENT_UPDATED", b);

                    dialog.dismiss();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void openSOCreate4() {
        JSONObject draft = getDraftExtra();

        Intent i = new Intent(requireContext(), SOCreate4Activity.class);
        i.putExtra(SOCreate4Activity.EXTRA_COHoi, draft.optString("coHoi", ""));
        i.putExtra(SOCreate4Activity.EXTRA_BAOGIA, draft.optString("baoGia", ""));
        i.putExtra(SOCreate4Activity.EXTRA_MOTA, draft.optString("moTa", ""));
        i.putExtra(SOCreate4Activity.EXTRA_DIEUKHOAN, draft.optString("dieuKhoan", ""));
        i.putExtra(SOCreate4Activity.EXTRA_GIAOCHO, draft.optString("giaoCho", ""));

        create4Launcher.launch(i);
    }

    private JSONObject getDraftExtra() {
        try {
            if (requireActivity() instanceof SOCreate1Activity) {
                return ((SOCreate1Activity) requireActivity()).getDraftExtra();
            }
        } catch (Exception ignored) {}
        return new JSONObject();
    }

    private void putExtra(String key, String value) {
        try {
            if (requireActivity() instanceof SOCreate1Activity) {
                ((SOCreate1Activity) requireActivity()).putExtra(key, value == null ? "" : value);
            }
        } catch (Exception ignored) {}
    }
}
