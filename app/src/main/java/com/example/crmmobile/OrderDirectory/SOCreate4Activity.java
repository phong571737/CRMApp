package com.example.crmmobile.OrderDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crmmobile.R;
import com.google.android.material.textfield.TextInputEditText;

public class SOCreate4Activity extends AppCompatActivity {

    public static final String EXTRA_COHoi     = "extra_cohoi";
    public static final String EXTRA_BAOGIA    = "extra_baogia";
    public static final String EXTRA_MOTA      = "extra_mota";
    public static final String EXTRA_DIEUKHOAN = "extra_dieukhoan";
    public static final String EXTRA_GIAOCHO   = "extra_giaocho";

    private TextInputEditText edtCoHoi, edtBaoGia, edtMoTa, edtDieuKhoan, edtGiaoCho;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_create4);

        edtCoHoi     = findViewById(R.id.edtCoHoi);
        edtBaoGia    = findViewById(R.id.edtBaoGia);
        edtMoTa      = findViewById(R.id.edtMoTa);
        edtDieuKhoan = findViewById(R.id.edtDieuKhoan);
        edtGiaoCho   = findViewById(R.id.edtGiaoCho);

        // Prefill
        Intent in = getIntent();
        if (in != null) {
            setText(edtCoHoi, in.getStringExtra(EXTRA_COHoi));
            setText(edtBaoGia, in.getStringExtra(EXTRA_BAOGIA));
            setText(edtMoTa, in.getStringExtra(EXTRA_MOTA));
            setText(edtDieuKhoan, in.getStringExtra(EXTRA_DIEUKHOAN));
            setText(edtGiaoCho, in.getStringExtra(EXTRA_GIAOCHO));
        }

        View btnHuy = findViewById(R.id.btnHuy);
        View btnLuu = findViewById(R.id.btnLuu);

        if (btnHuy != null) btnHuy.setOnClickListener(v -> finish());

        if (btnLuu != null) btnLuu.setOnClickListener(v -> {
            Intent out = new Intent();
            out.putExtra(EXTRA_COHoi, getText(edtCoHoi));
            out.putExtra(EXTRA_BAOGIA, getText(edtBaoGia));
            out.putExtra(EXTRA_MOTA, getText(edtMoTa));
            out.putExtra(EXTRA_DIEUKHOAN, getText(edtDieuKhoan));
            out.putExtra(EXTRA_GIAOCHO, getText(edtGiaoCho));

            setResult(RESULT_OK, out);
            finish();
        });
        com.google.android.material.appbar.MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v -> {
                setResult(RESULT_CANCELED);
                finish();
            });
        }


    }

    private void setText(TextInputEditText edt, String s) {
        if (edt != null) edt.setText(s == null ? "" : s);
    }

    private String getText(TextInputEditText edt) {
        return edt != null && edt.getText() != null ? edt.getText().toString().trim() : "";
    }
}
