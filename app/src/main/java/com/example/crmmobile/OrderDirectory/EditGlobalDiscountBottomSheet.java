package com.example.crmmobile.OrderDirectory;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crmmobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.Locale;

public class EditGlobalDiscountBottomSheet extends BottomSheetDialogFragment {

    public interface Listener {
        void onConfirmed(long discountAmount);
    }

    private static final String ARG_SUBTOTAL = "arg_subtotal";
    private static final String ARG_CURRENT  = "arg_current";

    public static EditGlobalDiscountBottomSheet newInstance(long subtotal, long currentDiscount) {
        EditGlobalDiscountBottomSheet f = new EditGlobalDiscountBottomSheet();
        Bundle b = new Bundle();
        b.putLong(ARG_SUBTOTAL, subtotal);
        b.putLong(ARG_CURRENT, currentDiscount);
        f.setArguments(b);
        return f;
    }

    private Listener listener;
    public void setListener(Listener listener) { this.listener = listener; }

    private long subtotal = 0L;
    private long current = 0L;

    private View root;
    private RadioButton rbNone, rbPercent, rbDirect;
    private EditText etPercent, etDirect;
    private TextView tvDiscountValue;
    private MaterialButton btnCancel, btnConfirm;
    private ImageView btnClose;

    private final NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
    private final boolean[] lock = {false}; // ✅ chống loop khi setChecked

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.bottomsheet_edit_global_discount, container, false);

        if (getArguments() != null) {
            subtotal = getArguments().getLong(ARG_SUBTOTAL, 0L);
            current  = getArguments().getLong(ARG_CURRENT, 0L);
        }

        // ===== mapping ids đúng như file của bạn =====
        rbNone    = root.findViewById(R.id.rbGlobalNoDiscount);
        rbPercent = root.findViewById(R.id.rbGlobalPercent);
        rbDirect  = root.findViewById(R.id.rbGlobalDirect);

        etPercent = root.findViewById(R.id.edtGlobalPercent);
        etDirect  = root.findViewById(R.id.edtGlobalDirect);

        tvDiscountValue = root.findViewById(R.id.tvGlobalDiscountAmount);

        btnCancel  = root.findViewById(R.id.btnGlobalCancel);
        btnConfirm = root.findViewById(R.id.btnGlobalConfirm);
        btnClose   = root.findViewById(R.id.btnCloseGlobal);

        // Click cả dòng (nếu XML có)
        View rowNone    = root.findViewById(R.id.rowGlobalNoDiscount);
        View rowPercent = root.findViewById(R.id.rowGlobalPercent);
        View rowDirect  = root.findViewById(R.id.rowGlobalDirect);

        if (rowNone != null) rowNone.setOnClickListener(v -> selectNone());
        if (rowPercent != null) rowPercent.setOnClickListener(v -> selectPercent());
        if (rowDirect != null) rowDirect.setOnClickListener(v -> selectDirect());

        // ✅ FIX CHÍNH: ép 3 radio luôn “chọn 1 - bỏ 2”
        if (rbNone != null) {
            rbNone.setOnCheckedChangeListener((b, isChecked) -> {
                if (lock[0] || !isChecked) return;
                selectNone();
            });
        }
        if (rbPercent != null) {
            rbPercent.setOnCheckedChangeListener((b, isChecked) -> {
                if (lock[0] || !isChecked) return;
                selectPercent();
            });
        }
        if (rbDirect != null) {
            rbDirect.setOnCheckedChangeListener((b, isChecked) -> {
                if (lock[0] || !isChecked) return;
                selectDirect();
            });
        }

        // Click/focus vào ô nhập => tự chọn radio đúng
        if (etPercent != null) {
            etPercent.setOnClickListener(v -> selectPercent());
            etPercent.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) selectPercent(); });
            etPercent.addTextChangedListener(simpleWatcher(this::updatePreview));
        }

        if (etDirect != null) {
            etDirect.setOnClickListener(v -> selectDirect());
            etDirect.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) selectDirect(); });
            etDirect.addTextChangedListener(simpleWatcher(this::updatePreview));
        }

        // init state theo current
        if (current > 0) {
            selectDirect();
            if (etDirect != null) etDirect.setText(nf.format(current) + " đ");
        } else {
            selectNone();
        }

        updatePreview();

        if (btnClose != null) btnClose.setOnClickListener(v -> dismiss());
        if (btnCancel != null) btnCancel.setOnClickListener(v -> dismiss());

        if (btnConfirm != null) {
            btnConfirm.setOnClickListener(v -> {
                long discountAmount = calcDiscountAmount();
                if (listener != null) listener.onConfirmed(discountAmount);
                dismiss();
            });
        }

        return root;
    }

    // ===== chọn exclusive =====
    private void selectNone() {
        if (lock[0]) return;
        lock[0] = true;

        if (rbNone != null) rbNone.setChecked(true);
        if (rbPercent != null) rbPercent.setChecked(false);
        if (rbDirect != null) rbDirect.setChecked(false);

        lock[0] = false;

        applyEnabledState();
        updatePreview();
    }

    private void selectPercent() {
        if (lock[0]) return;
        lock[0] = true;

        if (rbNone != null) rbNone.setChecked(false);
        if (rbPercent != null) rbPercent.setChecked(true);
        if (rbDirect != null) rbDirect.setChecked(false);

        lock[0] = false;

        applyEnabledState();
        updatePreview();
    }

    private void selectDirect() {
        if (lock[0]) return;
        lock[0] = true;

        if (rbNone != null) rbNone.setChecked(false);
        if (rbPercent != null) rbPercent.setChecked(false);
        if (rbDirect != null) rbDirect.setChecked(true);

        lock[0] = false;

        applyEnabledState();
        updatePreview();
    }

    private void applyEnabledState() {
        boolean isPercent = rbPercent != null && rbPercent.isChecked();
        boolean isDirect  = rbDirect  != null && rbDirect.isChecked();

        if (etPercent != null) etPercent.setEnabled(isPercent);
        if (etDirect != null) etDirect.setEnabled(isDirect);

        if (!isPercent && etPercent != null) etPercent.setText("0");
        if (!isDirect  && etDirect != null)  etDirect.setText("0 đ");
    }

    private void updatePreview() {
        long amount = calcDiscountAmount();
        if (tvDiscountValue != null) tvDiscountValue.setText(nf.format(amount) + " đ");
    }

    private long calcDiscountAmount() {
        if (subtotal < 0) subtotal = 0;

        // none
        if (rbNone != null && rbNone.isChecked()) return 0L;

        // percent
        if (rbPercent != null && rbPercent.isChecked()) {
            int percent = parseIntSafe(etPercent != null ? etPercent.getText().toString() : "0");
            if (percent < 0) percent = 0;
            if (percent > 100) percent = 100;
            long amount = Math.round(subtotal * (percent / 100.0));
            if (amount > subtotal) amount = subtotal;
            return Math.max(0L, amount);
        }

        // direct
        if (rbDirect != null && rbDirect.isChecked()) {
            long amount = parseMoneySafe(etDirect != null ? etDirect.getText().toString() : "0");
            if (amount > subtotal) amount = subtotal;
            return Math.max(0L, amount);
        }

        return 0L;
    }

    private TextWatcher simpleWatcher(Runnable r) {
        return new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { r.run(); }
            @Override public void afterTextChanged(Editable s) {}
        };
    }

    private int parseIntSafe(String s) {
        try {
            if (s == null) return 0;
            s = s.trim();
            if (s.isEmpty()) return 0;
            return Integer.parseInt(s.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    // ✅ parse "0.00 đ" / "8.180.000 đ" / "8180000"
    private long parseMoneySafe(String s) {
        try {
            if (s == null) return 0L;
            String digits = s.replaceAll("[^0-9]", "");
            if (digits.isEmpty()) return 0L;
            return Long.parseLong(digits);
        } catch (Exception e) {
            return 0L;
        }
    }
}

