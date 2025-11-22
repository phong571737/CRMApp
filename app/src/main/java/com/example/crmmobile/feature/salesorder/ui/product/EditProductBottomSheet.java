package com.example.crmmobile.feature.salesorder.ui.product;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.NumberFormat;
import java.util.Locale;

import com.example.crmmobile.R;

public class EditProductBottomSheet extends BottomSheetDialogFragment {

    private static final String ARG_NAME  = "arg_name";
    private static final String ARG_PRICE = "arg_price";
    private static final String ARG_QTY   = "arg_qty";

    public static EditProductBottomSheet newInstance(String name, long price, int qty) {
        EditProductBottomSheet f = new EditProductBottomSheet();
        Bundle b = new Bundle();
        b.putString(ARG_NAME, name);
        b.putLong(ARG_PRICE, price);
        b.putInt(ARG_QTY, qty);
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
        String name = args != null ? args.getString(ARG_NAME) : "";
        long price  = args != null ? args.getLong(ARG_PRICE, 0L) : 0L;
        int qty     = args != null ? args.getInt(ARG_QTY, 1) : 1;

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

        RadioGroup rgDiscount  = v.findViewById(R.id.rgDiscountType);
        RadioButton rbNoDiscount       = v.findViewById(R.id.rbNoDiscount);
        RadioButton rbDiscountPercent  = v.findViewById(R.id.rbDiscountPercent);
        RadioButton rbDiscountDirect   = v.findViewById(R.id.rbDiscountDirect);
        EditText edtPercent    = v.findViewById(R.id.edtDiscountPercent);
        EditText edtDirect     = v.findViewById(R.id.edtDiscountDirect);

        RadioGroup rgTax       = v.findViewById(R.id.rgTax);
        // RadioButton rbNoTax  = v.findViewById(R.id.rbNoTax);
        // RadioButton rbVAT10  = v.findViewById(R.id.rbVAT10);

        tvName.setText(name);
        tvQty.setText(String.valueOf(qty));
        tvLinePrice.setText(nf.format(price * qty) + " đ");

        // Hàm tính lại số tiền hiển thị (FE)
        Runnable recalc = () -> {
            int q = Integer.parseInt(tvQty.getText().toString());
            long base = price * q;

            long discount = 0L;
            if (rbDiscountPercent.isChecked()) {
                String s = edtPercent.getText().toString().trim();
                double p = s.isEmpty() ? 0 : Double.parseDouble(s);
                discount = (long) (base * p / 100.0);
            } else if (rbDiscountDirect.isChecked()) {
                String s = edtDirect.getText().toString().trim();
                discount = s.isEmpty() ? 0 : Long.parseLong(s);
            }
            if (discount < 0) discount = 0;
            if (discount > base) discount = base;

            long afterDiscount = base - discount;

            // Thuế: nếu chọn VAT 10% thì +10%
            int checkedTaxId = rgTax.getCheckedRadioButtonId();
            long tax = 0L;
            if (checkedTaxId == R.id.rbVAT10) {
                tax = (long) (afterDiscount * 0.1);
            }

            long finalAmount = afterDiscount + tax;

            tvDiscountAmt.setText(nf.format(discount) + " đ");
            tvFinalAmount.setText(nf.format(finalAmount) + " đ");
            tvLinePrice.setText(nf.format(base) + " đ");
        };

        recalc.run();

        btnMinus.setOnClickListener(view -> {
            int q = Integer.parseInt(tvQty.getText().toString());
            if (q > 1) {
                tvQty.setText(String.valueOf(q - 1));
                recalc.run();
            }
        });

        btnPlus.setOnClickListener(view -> {
            int q = Integer.parseInt(tvQty.getText().toString());
            tvQty.setText(String.valueOf(q + 1));
            recalc.run();
        });

        TextWatcher watcher = new SimpleTextWatcher(recalc);
        edtPercent.addTextChangedListener(watcher);
        edtDirect.addTextChangedListener(watcher);

        rgDiscount.setOnCheckedChangeListener((group, checkedId) -> recalc.run());
        rgTax.setOnCheckedChangeListener((group, checkedId) -> recalc.run());

        btnClose.setOnClickListener(view -> dismiss());
        btnCancel.setOnClickListener(view -> dismiss());

        // Hiện tại chỉ đóng sheet, chưa trả result (FE thôi)
        btnConfirm.setOnClickListener(view -> dismiss());
    }

    // TextWatcher rút gọn
    private static class SimpleTextWatcher implements TextWatcher {
        private final Runnable callback;
        SimpleTextWatcher(Runnable r) { this.callback = r; }
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) { callback.run(); }
        @Override public void afterTextChanged(Editable s) {}
    }
}
