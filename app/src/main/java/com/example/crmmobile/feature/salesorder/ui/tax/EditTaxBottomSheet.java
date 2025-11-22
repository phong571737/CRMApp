package com.example.crmmobile.feature.salesorder.ui.tax;

import android.os.Bundle;
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

        // set dữ liệu ban đầu
        edtVatPercent.setText(String.valueOf(vatPercent));
        updateTaxAmountText(tvTaxAmount, amountBeforeTax, vatPercent);

        long finalAmountBeforeTax = amountBeforeTax;

        // đổi % → cập nhật tiền thuế
        edtVatPercent.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                int percent = 0;
                try {
                    percent = Integer.parseInt(edtVatPercent.getText().toString());
                } catch (Exception ignored) {}
                updateTaxAmountText(tvTaxAmount, finalAmountBeforeTax, percent);
            }
        });

        btnCancel.setOnClickListener(view -> dismiss());

        // Hiện tại FE là chính → xác nhận chỉ đóng bottom sheet
        btnConfirm.setOnClickListener(view -> {
            // TODO: sau này truyền kết quả ngược lại Fragment nếu cần
            dismiss();
        });

        return v;
    }

    private void updateTaxAmountText(TextView tv, long baseAmount, int percent) {
        long tax = baseAmount * percent / 100;
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        String text = nf.format(tax) + " đ";
        tv.setText(text);
    }
}
