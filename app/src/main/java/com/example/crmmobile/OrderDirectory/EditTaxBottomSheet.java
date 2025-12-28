package com.example.crmmobile.OrderDirectory;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.crmmobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.Locale;

public class EditTaxBottomSheet extends BottomSheetDialogFragment {

    public interface Listener {
        void onConfirmed(int vatPercent);
    }

    private Listener listener;
    public void setListener(Listener listener) { this.listener = listener; }

    private static final String ARG_AMOUNT_BEFORE_TAX = "arg_amount_before_tax";
    private static final String ARG_VAT_PERCENT       = "arg_vat_percent";

    public static EditTaxBottomSheet newInstance(long amountBeforeTax, int vatPercent) {
        EditTaxBottomSheet f = new EditTaxBottomSheet();
        Bundle args = new Bundle();
        args.putLong(ARG_AMOUNT_BEFORE_TAX, amountBeforeTax);
        args.putInt(ARG_VAT_PERCENT, vatPercent);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottomsheet_edit_tax, container, false);

        EditText edtVatPercent = v.findViewById(R.id.edtVatPercent);
        TextView tvTaxAmount   = v.findViewById(R.id.tvTaxAmount);
        MaterialButton btnCancel  = v.findViewById(R.id.btnTaxCancel);
        MaterialButton btnConfirm = v.findViewById(R.id.btnTaxConfirm);

        long amountBeforeTax = 0L;
        int vatPercent = 10;

        if (getArguments() != null) {
            amountBeforeTax = getArguments().getLong(ARG_AMOUNT_BEFORE_TAX, 0L);
            vatPercent      = getArguments().getInt(ARG_VAT_PERCENT, 10);
        }

        long finalAmountBeforeTax = amountBeforeTax;

        edtVatPercent.setText(String.valueOf(vatPercent));
        updateTaxAmountText(tvTaxAmount, finalAmountBeforeTax, vatPercent);

        edtVatPercent.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                int p = parsePercentSafe(s != null ? s.toString() : "0");
                updateTaxAmountText(tvTaxAmount, finalAmountBeforeTax, p);
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnCancel.setOnClickListener(view -> dismiss());

        btnConfirm.setOnClickListener(view -> {
            int percent = parsePercentSafe(edtVatPercent.getText().toString());
            if (listener != null) listener.onConfirmed(percent);
            dismiss();
        });

        return v;
    }

    private int parsePercentSafe(String s) {
        try {
            if (s == null) return 0;
            s = s.trim();
            if (s.isEmpty()) return 0;
            int p = Integer.parseInt(s.replaceAll("[^0-9]", ""));
            if (p < 0) p = 0;
            if (p > 100) p = 100;
            return p;
        } catch (Exception e) { return 0; }
    }

    private void updateTaxAmountText(TextView tv, long baseAmount, int percent) {
        long tax = Math.round(baseAmount * (percent / 100.0));
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        tv.setText(nf.format(tax) + " đ");
    }
}
