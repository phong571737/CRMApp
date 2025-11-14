package com.example.crmmobile.feature.salesorder.ui.discount;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.NumberFormat;
import java.util.Locale;

import com.example.crmmobile.R;

public class EditGlobalDiscountBottomSheet extends BottomSheetDialogFragment {

    private static final String ARG_SUBTOTAL = "arg_subtotal";

    public static EditGlobalDiscountBottomSheet newInstance(long subtotal) {
        EditGlobalDiscountBottomSheet f = new EditGlobalDiscountBottomSheet();
        Bundle b = new Bundle();
        b.putLong(ARG_SUBTOTAL, subtotal);
        f.setArguments(b);
        return f;
    }

    private final NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet_edit_global_discount, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        long subtotal = 0L;
        Bundle args = getArguments();
        if (args != null) subtotal = args.getLong(ARG_SUBTOTAL, 0L);

        RadioGroup rg = v.findViewById(R.id.rgGlobalDiscountType);
        RadioButton rbNo  = v.findViewById(R.id.rbGlobalNoDiscount);
        RadioButton rbPer = v.findViewById(R.id.rbGlobalPercent);
        RadioButton rbDir = v.findViewById(R.id.rbGlobalDirect);

        EditText edtPer  = v.findViewById(R.id.edtGlobalPercent);
        EditText edtDir  = v.findViewById(R.id.edtGlobalDirect);
        TextView tvAmt   = v.findViewById(R.id.tvGlobalDiscountAmount);
        Button btnCancel = v.findViewById(R.id.btnGlobalCancel);
        Button btnOk     = v.findViewById(R.id.btnGlobalConfirm);

        long finalSubtotal = subtotal;

        Runnable recalc = () -> {
            long discount = 0L;
            if (rbPer.isChecked()) {
                String s = edtPer.getText().toString().trim();
                double p = s.isEmpty() ? 0 : Double.parseDouble(s);
                discount = (long) (finalSubtotal * p / 100.0);
            } else if (rbDir.isChecked()) {
                String s = edtDir.getText().toString().trim();
                discount = s.isEmpty() ? 0 : Long.parseLong(s);
            }
            if (discount < 0) discount = 0;
            if (discount > finalSubtotal) discount = finalSubtotal;
            tvAmt.setText(nf.format(discount) + " đ");
        };

        TextWatcher watcher = new SimpleWatcher(recalc);
        edtPer.addTextChangedListener(watcher);
        edtDir.addTextChangedListener(watcher);
        rg.setOnCheckedChangeListener((group, checkedId) -> recalc.run());

        recalc.run();

        btnCancel.setOnClickListener(view -> dismiss());
        btnOk.setOnClickListener(view -> {
            // Hiện tại chỉ đóng sheet (FE).
            // Sau này bạn có thể truyền discount ngược lại Fragment qua listener.
            dismiss();
        });
    }

    private static class SimpleWatcher implements TextWatcher {
        private final Runnable cb;
        SimpleWatcher(Runnable cb) { this.cb = cb; }
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) { cb.run(); }
        @Override public void afterTextChanged(Editable s) {}
    }
}
