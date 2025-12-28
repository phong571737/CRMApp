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

    public interface Listener { void onConfirmed(long discountAmount); }

    private static final String ARG_SUBTOTAL = "arg_subtotal";
    private static final String ARG_CURRENT  = "arg_current";
    private static final String ARG_LINE_DISCOUNT_SUM = "arg_line_discount_sum";

    public static EditGlobalDiscountBottomSheet newInstance(long subtotalAfterLineDiscount,
                                                            long currentGlobalExtra,
                                                            long lineDiscountSum) {
        EditGlobalDiscountBottomSheet f = new EditGlobalDiscountBottomSheet();
        Bundle b = new Bundle();
        b.putLong(ARG_SUBTOTAL, subtotalAfterLineDiscount);
        b.putLong(ARG_CURRENT, currentGlobalExtra);
        b.putLong(ARG_LINE_DISCOUNT_SUM, lineDiscountSum);
        f.setArguments(b);
        return f;
    }

    private Listener listener;
    public void setListener(Listener listener) { this.listener = listener; }

    private long subtotal = 0L;
    private long current = 0L;
    private long lineDiscountSum = 0L;

    private RadioButton rbNone, rbPercent, rbDirect;
    private EditText etPercent, etDirect;
    private TextView tvDiscountValue, tvLineDiscountValue;
    private MaterialButton btnCancel, btnConfirm;
    private ImageView btnClose;

    private final NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
    private final boolean[] lock = {false};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.bottomsheet_edit_global_discount, container, false);

        if (getArguments() != null) {
            subtotal = getArguments().getLong(ARG_SUBTOTAL, 0L);
            current  = getArguments().getLong(ARG_CURRENT, 0L);
            lineDiscountSum = getArguments().getLong(ARG_LINE_DISCOUNT_SUM, 0L);
        }

        rbNone    = root.findViewById(R.id.rbGlobalNoDiscount);
        rbPercent = root.findViewById(R.id.rbGlobalPercent);
        rbDirect  = root.findViewById(R.id.rbGlobalDirect);

        etPercent = root.findViewById(R.id.edtGlobalPercent);
        etDirect  = root.findViewById(R.id.edtGlobalDirect);

        tvDiscountValue    = root.findViewById(R.id.tvGlobalDiscountAmount);
        tvLineDiscountValue= root.findViewById(R.id.tvLineDiscountValue);

        btnCancel  = root.findViewById(R.id.btnGlobalCancel);
        btnConfirm = root.findViewById(R.id.btnGlobalConfirm);
        btnClose   = root.findViewById(R.id.btnCloseGlobal);

        if (tvLineDiscountValue != null) {
            tvLineDiscountValue.setText(nf.format(Math.max(0L, lineDiscountSum)) + " đ");
        }

        View rowNone    = root.findViewById(R.id.rowGlobalNoDiscount);
        View rowPercent = root.findViewById(R.id.rowGlobalPercent);
        View rowDirect  = root.findViewById(R.id.rowGlobalDirect);

        if (rowNone != null) rowNone.setOnClickListener(v -> selectNone());
        if (rowPercent != null) rowPercent.setOnClickListener(v -> selectPercent());
        if (rowDirect != null) rowDirect.setOnClickListener(v -> selectDirect());

        rbNone.setOnCheckedChangeListener((b, isChecked) -> { if (!lock[0] && isChecked) selectNone(); });
        rbPercent.setOnCheckedChangeListener((b, isChecked) -> { if (!lock[0] && isChecked) selectPercent(); });
        rbDirect.setOnCheckedChangeListener((b, isChecked) -> { if (!lock[0] && isChecked) selectDirect(); });

        etPercent.setOnClickListener(v -> selectPercent());
        etPercent.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) selectPercent(); });
        etPercent.addTextChangedListener(simpleWatcher(this::updatePreview));

        etDirect.setOnClickListener(v -> selectDirect());
        etDirect.setOnFocusChangeListener((v, hasFocus) -> { if (hasFocus) selectDirect(); });
        etDirect.addTextChangedListener(simpleWatcher(this::updatePreview));

        if (current > 0) {
            selectDirect();
            etDirect.setText(nf.format(current) + " đ");
        } else {
            selectNone();
        }
        updatePreview();

        if (btnClose != null) btnClose.setOnClickListener(v -> dismiss());
        if (btnCancel != null) btnCancel.setOnClickListener(v -> dismiss());

        btnConfirm.setOnClickListener(v -> {
            long discountAmount = calcDiscountAmount();
            if (listener != null) listener.onConfirmed(discountAmount);
            dismiss();
        });

        return root;
    }

    private void selectNone() {
        lock[0] = true;
        rbNone.setChecked(true); rbPercent.setChecked(false); rbDirect.setChecked(false);
        lock[0] = false;
        applyEnabledState();
        updatePreview();
    }

    private void selectPercent() {
        lock[0] = true;
        rbNone.setChecked(false); rbPercent.setChecked(true); rbDirect.setChecked(false);
        lock[0] = false;
        applyEnabledState();
        updatePreview();
    }

    private void selectDirect() {
        lock[0] = true;
        rbNone.setChecked(false); rbPercent.setChecked(false); rbDirect.setChecked(true);
        lock[0] = false;
        applyEnabledState();
        updatePreview();
    }

    private void applyEnabledState() {
        boolean isPercent = rbPercent.isChecked();
        boolean isDirect  = rbDirect.isChecked();
        etPercent.setEnabled(isPercent);
        etDirect.setEnabled(isDirect);
        // không reset text
    }

    private void updatePreview() {
        long amount = calcDiscountAmount();
        tvDiscountValue.setText(nf.format(amount) + " đ");
    }

    private long calcDiscountAmount() {
        if (subtotal < 0) subtotal = 0;

        if (rbNone.isChecked()) return 0L;

        if (rbPercent.isChecked()) {
            int percent = parseIntSafe(etPercent.getText().toString());
            if (percent < 0) percent = 0;
            if (percent > 100) percent = 100;
            long amount = Math.round(subtotal * (percent / 100.0));
            if (amount > subtotal) amount = subtotal;
            return Math.max(0L, amount);
        }

        if (rbDirect.isChecked()) {
            long amount = parseMoneySafe(etDirect.getText().toString());
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
        } catch (Exception e) { return 0; }
    }

    private long parseMoneySafe(String s) {
        try {
            if (s == null) return 0L;
            String digits = s.replaceAll("[^0-9]", "");
            if (digits.isEmpty()) return 0L;
            return Long.parseLong(digits);
        } catch (Exception e) { return 0L; }
    }
}
