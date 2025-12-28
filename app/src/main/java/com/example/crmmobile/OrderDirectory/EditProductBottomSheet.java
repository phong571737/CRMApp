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
        void onConfirmed(int newQty,
                         int discountType,
                         int discountPercent,
                         long discountDirect,
                         int vatPercent);
    }

    private static final String ARG_NAME  = "arg_name";
    private static final String ARG_PRICE = "arg_price";
    private static final String ARG_QTY   = "arg_qty";

    private static final String ARG_DISCOUNT_TYPE    = "arg_discount_type";
    private static final String ARG_DISCOUNT_PERCENT = "arg_discount_percent";
    private static final String ARG_DISCOUNT_DIRECT  = "arg_discount_direct";

    private static final String ARG_VAT_PERCENT      = "arg_vat_percent";

    private Listener listener;
    public void setListener(Listener listener) { this.listener = listener; }

    public static EditProductBottomSheet newInstance(ProductLine line) {
        EditProductBottomSheet f = new EditProductBottomSheet();
        Bundle b = new Bundle();
        b.putString(ARG_NAME, line.getName());
        b.putLong(ARG_PRICE, line.getPrice());
        b.putInt(ARG_QTY, line.getQty());

        b.putInt(ARG_DISCOUNT_TYPE, line.getDiscountType());
        b.putInt(ARG_DISCOUNT_PERCENT, line.getDiscountPercent());
        b.putLong(ARG_DISCOUNT_DIRECT, line.getDiscountDirect());

        b.putInt(ARG_VAT_PERCENT, line.getVatPercent());
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

        int discountType    = args != null ? args.getInt(ARG_DISCOUNT_TYPE, ProductLine.DISCOUNT_NONE) : ProductLine.DISCOUNT_NONE;
        int discountPercent = args != null ? args.getInt(ARG_DISCOUNT_PERCENT, 0) : 0;
        long discountDirect = args != null ? args.getLong(ARG_DISCOUNT_DIRECT, 0L) : 0L;

        int vatPercent = args != null ? args.getInt(ARG_VAT_PERCENT, 0) : 0;

        TextView tvName        = v.findViewById(R.id.tvProductName);
        TextView tvQty         = v.findViewById(R.id.tvQuantity);
        TextView tvLinePrice   = v.findViewById(R.id.tvLinePrice);
        TextView tvDiscountAmt = v.findViewById(R.id.tvDiscountAmount);
        TextView tvTaxAmt      = v.findViewById(R.id.tvTaxAmount);
        TextView tvFinalAmount = v.findViewById(R.id.tvFinalAmount);

        TextView tvVatPercent  = v.findViewById(R.id.tvVatPercent);
        TextView tvVatAmount   = v.findViewById(R.id.tvVatAmount);
        View layoutVatRow      = v.findViewById(R.id.layoutVatRow);

        ImageButton btnMinus   = v.findViewById(R.id.btnMinus);
        ImageButton btnPlus    = v.findViewById(R.id.btnPlus);
        ImageView btnClose     = v.findViewById(R.id.btnClose);
        Button btnCancel       = v.findViewById(R.id.btnCancel);
        Button btnConfirm      = v.findViewById(R.id.btnConfirm);

        // ===== Discount =====
        RadioButton rbNoDiscount       = v.findViewById(R.id.rbNoDiscount);
        RadioButton rbDiscountPercent  = v.findViewById(R.id.rbDiscountPercent);
        RadioButton rbDiscountDirect   = v.findViewById(R.id.rbDiscountDirect);

        EditText edtPercent = v.findViewById(R.id.edtDiscountPercent);
        EditText edtDirect  = v.findViewById(R.id.edtDiscountDirect);

        // ===== Tax =====
        RadioGroup rgTax = v.findViewById(R.id.rgTax);
        RadioButton rbNoTax = v.findViewById(R.id.rbNoTax);
        RadioButton rbVAT10 = v.findViewById(R.id.rbVAT10);

        tvName.setText(name);
        tvQty.setText(String.valueOf(Math.max(1, qty)));

        // ✅ INIT đúng theo state (FIX lỗi reopen)
        if (discountType == ProductLine.DISCOUNT_PERCENT) {
            rbDiscountPercent.setChecked(true);
            edtPercent.setText(String.valueOf(Math.max(0, discountPercent)));
        } else if (discountType == ProductLine.DISCOUNT_DIRECT) {
            rbDiscountDirect.setChecked(true);
            edtDirect.setText(nf.format(Math.max(0L, discountDirect)) + " đ");
        } else {
            rbNoDiscount.setChecked(true);
        }

        // VAT init
        if (vatPercent > 0) {
            if (rbVAT10 != null) rbVAT10.setChecked(true);
        } else {
            if (rbNoTax != null) rbNoTax.setChecked(true);
        }

        // ✅ radio exclusive (tránh RadioGroup bị bọc layout gây lỗi)
        final boolean[] lock = {false};
        CompoundButton.OnCheckedChangeListener exclusive = (button, isChecked) -> {
            if (lock[0] || !isChecked) return;
            lock[0] = true;

            rbNoDiscount.setChecked(button == rbNoDiscount);
            rbDiscountPercent.setChecked(button == rbDiscountPercent);
            rbDiscountDirect.setChecked(button == rbDiscountDirect);

            lock[0] = false;

            applyEnabled(rbDiscountPercent, rbDiscountDirect, edtPercent, edtDirect);
            recalc(price, tvQty, rbDiscountPercent, rbDiscountDirect, edtPercent, edtDirect,
                    rgTax, tvLinePrice, tvDiscountAmt, tvTaxAmt, tvVatPercent, tvVatAmount, layoutVatRow, tvFinalAmount);
        };

        rbNoDiscount.setOnCheckedChangeListener(exclusive);
        rbDiscountPercent.setOnCheckedChangeListener(exclusive);
        rbDiscountDirect.setOnCheckedChangeListener(exclusive);

        // click vào ô nhập => tick đúng radio
        edtPercent.setOnClickListener(x -> rbDiscountPercent.setChecked(true));
        edtDirect.setOnClickListener(x -> rbDiscountDirect.setChecked(true));
        edtPercent.setOnFocusChangeListener((vv, hasFocus) -> { if (hasFocus) rbDiscountPercent.setChecked(true); });
        edtDirect.setOnFocusChangeListener((vv, hasFocus) -> { if (hasFocus) rbDiscountDirect.setChecked(true); });

        TextWatcher watcher = new SimpleTextWatcher(() ->
                recalc(price, tvQty, rbDiscountPercent, rbDiscountDirect, edtPercent, edtDirect,
                        rgTax, tvLinePrice, tvDiscountAmt, tvTaxAmt, tvVatPercent, tvVatAmount, layoutVatRow, tvFinalAmount)
        );
        edtPercent.addTextChangedListener(watcher);
        edtDirect.addTextChangedListener(watcher);

        if (rgTax != null) {
            rgTax.setOnCheckedChangeListener((g, id) ->
                    recalc(price, tvQty, rbDiscountPercent, rbDiscountDirect, edtPercent, edtDirect,
                            rgTax, tvLinePrice, tvDiscountAmt, tvTaxAmt, tvVatPercent, tvVatAmount, layoutVatRow, tvFinalAmount)
            );
        }

        btnMinus.setOnClickListener(view -> {
            int q = parseIntSafe(tvQty.getText().toString(), 1);
            if (q > 1) {
                tvQty.setText(String.valueOf(q - 1));
                recalc(price, tvQty, rbDiscountPercent, rbDiscountDirect, edtPercent, edtDirect,
                        rgTax, tvLinePrice, tvDiscountAmt, tvTaxAmt, tvVatPercent, tvVatAmount, layoutVatRow, tvFinalAmount);
            }
        });

        btnPlus.setOnClickListener(view -> {
            int q = parseIntSafe(tvQty.getText().toString(), 1);
            tvQty.setText(String.valueOf(q + 1));
            recalc(price, tvQty, rbDiscountPercent, rbDiscountDirect, edtPercent, edtDirect,
                    rgTax, tvLinePrice, tvDiscountAmt, tvTaxAmt, tvVatPercent, tvVatAmount, layoutVatRow, tvFinalAmount);
        });

        applyEnabled(rbDiscountPercent, rbDiscountDirect, edtPercent, edtDirect);
        recalc(price, tvQty, rbDiscountPercent, rbDiscountDirect, edtPercent, edtDirect,
                rgTax, tvLinePrice, tvDiscountAmt, tvTaxAmt, tvVatPercent, tvVatAmount, layoutVatRow, tvFinalAmount);

        btnClose.setOnClickListener(view -> dismiss());
        btnCancel.setOnClickListener(view -> dismiss());

        // ✅ CONFIRM: trả đúng kiểu + giá trị gốc (% hoặc direct) + VAT
        btnConfirm.setOnClickListener(view -> {
            int q = parseIntSafe(tvQty.getText().toString(), 1);
            if (q < 1) q = 1;

            int outType = ProductLine.DISCOUNT_NONE;
            int outPercent = 0;
            long outDirect = 0L;

            if (rbDiscountPercent.isChecked()) {
                outType = ProductLine.DISCOUNT_PERCENT;
                outPercent = (int) Math.round(parsePercentSafe(edtPercent.getText().toString()));
                if (outPercent < 0) outPercent = 0;
                if (outPercent > 100) outPercent = 100;
            } else if (rbDiscountDirect.isChecked()) {
                outType = ProductLine.DISCOUNT_DIRECT;
                outDirect = parseMoneySafe(edtDirect.getText().toString());
                if (outDirect < 0) outDirect = 0L;
            }

            int outVatPercent = 0;
            if (rgTax != null && rgTax.getCheckedRadioButtonId() == R.id.rbVAT10) {
                outVatPercent = 10; // theo layout của bạn đang cố định 10%
            }

            if (listener != null) listener.onConfirmed(q, outType, outPercent, outDirect, outVatPercent);
            dismiss();
        });
    }

    private void applyEnabled(RadioButton rbPercent, RadioButton rbDirect, EditText etPercent, EditText etDirect) {
        boolean isPercent = rbPercent.isChecked();
        boolean isDirect  = rbDirect.isChecked();
        etPercent.setEnabled(isPercent);
        etDirect.setEnabled(isDirect);
        // ✅ KHÔNG reset text để tránh mất dữ liệu khi chuyển qua lại
    }

    private void recalc(long price,
                        TextView tvQty,
                        RadioButton rbPercent,
                        RadioButton rbDirect,
                        EditText etPercent,
                        EditText etDirect,
                        RadioGroup rgTax,
                        TextView tvLinePrice,
                        TextView tvDiscountAmt,
                        TextView tvTaxAmt,
                        TextView tvVatPercent,
                        TextView tvVatAmount,
                        View layoutVatRow,
                        TextView tvFinalAmount) {

        int q = parseIntSafe(tvQty.getText().toString(), 1);
        if (q < 1) q = 1;

        long base = price * (long) q;

        long discount = 0L;
        if (rbPercent.isChecked()) {
            double p = parsePercentSafe(etPercent.getText().toString());
            if (p < 0) p = 0;
            if (p > 100) p = 100;
            discount = Math.round(base * (p / 100.0));
        } else if (rbDirect.isChecked()) {
            discount = parseMoneySafe(etDirect.getText().toString());
        }

        if (discount < 0) discount = 0;
        if (discount > base) discount = base;

        long afterDiscount = base - discount;

        int vatPercent = 0;
        if (rgTax != null && rgTax.getCheckedRadioButtonId() == R.id.rbVAT10) {
            vatPercent = 10;
        }

        long tax = (vatPercent > 0) ? Math.round(afterDiscount * (vatPercent / 100.0)) : 0L;
        long finalAmount = afterDiscount + tax;

        if (tvLinePrice != null)   tvLinePrice.setText(nf.format(base) + " đ");
        if (tvDiscountAmt != null) tvDiscountAmt.setText(nf.format(discount) + " đ");
        if (tvTaxAmt != null)      tvTaxAmt.setText(nf.format(tax) + " đ");

        if (layoutVatRow != null) layoutVatRow.setVisibility(vatPercent > 0 ? View.VISIBLE : View.GONE);
        if (tvVatPercent != null) tvVatPercent.setText(vatPercent + " %");
        if (tvVatAmount != null)  tvVatAmount.setText(nf.format(tax) + " đ");

        if (tvFinalAmount != null) tvFinalAmount.setText(nf.format(finalAmount) + " đ");
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

    private long parseMoneySafe(String s) {
        try {
            if (s == null) return 0L;
            String digits = s.replaceAll("[^0-9]", "");
            if (digits.isEmpty()) return 0L;
            return Long.parseLong(digits);
        } catch (Exception e) { return 0L; }
    }
}
