package com.example.crmmobile.OrderDirectory;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crmmobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.NumberFormat;
import java.util.Locale;

public class EditProductBottomSheet extends BottomSheetDialogFragment {

    public interface Listener {
        void onConfirmed(int newQty, long discountAmount);
    }

    private static final String ARG_NAME  = "arg_name";
    private static final String ARG_PRICE = "arg_price";
    private static final String ARG_QTY   = "arg_qty";
    private static final String ARG_DISCOUNT = "arg_discount";

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public static EditProductBottomSheet newInstance(String name, long price, int qty, long currentDiscount) {
        EditProductBottomSheet f = new EditProductBottomSheet();
        Bundle b = new Bundle();
        b.putString(ARG_NAME, name);
        b.putLong(ARG_PRICE, price);
        b.putInt(ARG_QTY, qty);
        b.putLong(ARG_DISCOUNT, currentDiscount);
        f.setArguments(b);
        return f;
    }

    private final NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet_edit_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        Bundle args = getArguments();
        String name = args != null ? args.getString(ARG_NAME, "") : "";
        long price  = args != null ? args.getLong(ARG_PRICE, 0L) : 0L;
        int qty     = args != null ? args.getInt(ARG_QTY, 1) : 1;
        long currentDiscount = args != null ? args.getLong(ARG_DISCOUNT, 0L) : 0L;

        TextView tvName        = v.findViewById(R.id.tvProductName);
        TextView tvQty         = v.findViewById(R.id.tvQuantity);
        TextView tvLinePrice   = v.findViewById(R.id.tvLinePrice);
        TextView tvDiscountAmt = v.findViewById(R.id.tvDiscountAmount);
        TextView tvFinalAmount = v.findViewById(R.id.tvFinalAmount);

        ImageButton btnMinus   = v.findViewById(R.id.btnMinus);
        ImageButton btnPlus    = v.findViewById(R.id.btnPlus);
        ImageView btnClose     = v.findViewById(R.id.btnClose);
        Button btnCancel       = v.findViewById(R.id.btnCancel);
        Button btnConfirm      = v.findViewById(R.id.btnConfirm);

        // ===== Discount =====
        RadioGroup rgDiscount  = v.findViewById(R.id.rgDiscountType); // có thể không hoạt động nếu radio bị bọc layout, ta sẽ tự xử lý
        RadioButton rbNoDiscount       = v.findViewById(R.id.rbNoDiscount);
        RadioButton rbDiscountPercent  = v.findViewById(R.id.rbDiscountPercent);
        RadioButton rbDiscountDirect   = v.findViewById(R.id.rbDiscountDirect);

        EditText edtPercent = v.findViewById(R.id.edtDiscountPercent);
        EditText edtDirect  = v.findViewById(R.id.edtDiscountDirect);

        // ===== Tax (FE nếu bạn có) =====
        RadioGroup rgTax = v.findViewById(R.id.rgTax);

        tvName.setText(name);
        tvQty.setText(String.valueOf(Math.max(1, qty)));

        // ✅ set trạng thái ban đầu
        if (currentDiscount > 0) {
            rbDiscountDirect.setChecked(true);
            edtDirect.setText(nf.format(currentDiscount) + " đ");
        } else {
            rbNoDiscount.setChecked(true);
            edtPercent.setText("0");
            edtDirect.setText("0 đ");
        }

        Runnable applyEnabled = () -> {
            boolean isPercent = rbDiscountPercent.isChecked();
            boolean isDirect  = rbDiscountDirect.isChecked();

            edtPercent.setEnabled(isPercent);
            edtDirect.setEnabled(isDirect);

            if (!isPercent) edtPercent.setText("0");
            if (!isDirect)  edtDirect.setText("0 đ");
        };

        Runnable recalc = () -> {
            int q = parseIntSafe(tvQty.getText().toString(), 1);
            if (q < 1) q = 1;

            long base = price * q;

            long discount = 0L;
            if (rbDiscountPercent.isChecked()) {
                double p = parsePercentSafe(edtPercent.getText().toString());
                if (p < 0) p = 0;
                if (p > 100) p = 100;
                discount = Math.round(base * (p / 100.0));
            } else if (rbDiscountDirect.isChecked()) {
                discount = parseMoneySafe(edtDirect.getText().toString());
            }

            if (discount < 0) discount = 0;
            if (discount > base) discount = base;

            long afterDiscount = base - discount;

            // tax FE (nếu có rbVAT10)
            long tax = 0L;
            if (rgTax != null && rgTax.getCheckedRadioButtonId() == R.id.rbVAT10) {
                tax = Math.round(afterDiscount * 0.1);
            }

            long finalAmount = afterDiscount + tax;

            tvLinePrice.setText(nf.format(base) + " đ");
            tvDiscountAmt.setText(nf.format(discount) + " đ");
            tvFinalAmount.setText(nf.format(finalAmount) + " đ");
        };

        // ✅ FIX chính: ép 3 radio “Giảm giá” chọn 1-bỏ 2 (do RadioGroup không quản lý được nếu radio không là con trực tiếp)
        final boolean[] lock = {false};
        CompoundButton.OnCheckedChangeListener exclusive = (button, isChecked) -> {
            if (lock[0] || !isChecked) return;
            lock[0] = true;

            rbNoDiscount.setChecked(button == rbNoDiscount);
            rbDiscountPercent.setChecked(button == rbDiscountPercent);
            rbDiscountDirect.setChecked(button == rbDiscountDirect);

            lock[0] = false;

            applyEnabled.run();
            recalc.run();
        };

        rbNoDiscount.setOnCheckedChangeListener(exclusive);
        rbDiscountPercent.setOnCheckedChangeListener(exclusive);
        rbDiscountDirect.setOnCheckedChangeListener(exclusive);

        // click/focus vào ô nhập => tự tick đúng radio
        edtPercent.setOnClickListener(x -> rbDiscountPercent.setChecked(true));
        edtDirect.setOnClickListener(x -> rbDiscountDirect.setChecked(true));
        edtPercent.setOnFocusChangeListener((vv, hasFocus) -> { if (hasFocus) rbDiscountPercent.setChecked(true); });
        edtDirect.setOnFocusChangeListener((vv, hasFocus) -> { if (hasFocus) rbDiscountDirect.setChecked(true); });

        TextWatcher watcher = new SimpleTextWatcher(recalc);
        edtPercent.addTextChangedListener(watcher);
        edtDirect.addTextChangedListener(watcher);
        if (rgTax != null) rgTax.setOnCheckedChangeListener((g, id) -> recalc.run());

        btnMinus.setOnClickListener(view -> {
            int q = parseIntSafe(tvQty.getText().toString(), 1);
            if (q > 1) {
                tvQty.setText(String.valueOf(q - 1));
                recalc.run();
            }
        });

        btnPlus.setOnClickListener(view -> {
            int q = parseIntSafe(tvQty.getText().toString(), 1);
            tvQty.setText(String.valueOf(q + 1));
            recalc.run();
        });

        applyEnabled.run();
        recalc.run();

        btnClose.setOnClickListener(view -> dismiss());
        btnCancel.setOnClickListener(view -> dismiss());

        // ✅ CONFIRM: trả dữ liệu về Fragment (int + long)
        btnConfirm.setOnClickListener(view -> {
            int q = parseIntSafe(tvQty.getText().toString(), 1);
            if (q < 1) q = 1;

            long base = price * q;
            long discount = 0L;

            if (rbDiscountPercent.isChecked()) {
                double p = parsePercentSafe(edtPercent.getText().toString());
                if (p < 0) p = 0;
                if (p > 100) p = 100;
                discount = Math.round(base * (p / 100.0));
            } else if (rbDiscountDirect.isChecked()) {
                discount = parseMoneySafe(edtDirect.getText().toString());
            } else {
                discount = 0L;
            }

            if (discount < 0) discount = 0;
            if (discount > base) discount = base;

            if (listener != null) listener.onConfirmed(q, discount);
            dismiss();
        });
    }

    private static class SimpleTextWatcher implements TextWatcher {
        private final Runnable callback;
        SimpleTextWatcher(Runnable r) { this.callback = r; }
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) { callback.run(); }
        @Override public void afterTextChanged(Editable s) {}
    }

    private int parseIntSafe(String s, int def) {
        try {
            if (s == null) return def;
            s = s.trim();
            if (s.isEmpty()) return def;
            return Integer.parseInt(s.replaceAll("[^0-9]", ""));
        } catch (Exception e) { return def; }
    }

    // "20", "20.5", "20,5", "20 %"
    private double parsePercentSafe(String s) {
        try {
            if (s == null) return 0.0;
            s = s.trim().replace(',', '.');
            s = s.replaceAll("[^0-9.]", "");
            if (s.isEmpty()) return 0.0;

            int firstDot = s.indexOf('.');
            if (firstDot >= 0) {
                String before = s.substring(0, firstDot + 1);
                String after = s.substring(firstDot + 1).replace(".", "");
                s = before + after;
            }
            return Double.parseDouble(s);
        } catch (Exception e) { return 0.0; }
    }

    // "0.00 đ", "8.180.000 đ" => 8180000
    private long parseMoneySafe(String s) {
        try {
            if (s == null) return 0L;
            String digits = s.replaceAll("[^0-9]", "");
            if (digits.isEmpty()) return 0L;
            return Long.parseLong(digits);
        } catch (Exception e) { return 0L; }
    }
}



