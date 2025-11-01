package com.example.crmmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

public class ThongTinKhacActivity extends AppCompatActivity {

    // Biến trạng thái cho từng phần mở rộng / thu gọn
    private boolean isDiaChiExpanded = true;
    private boolean isMoTaExpanded = true;
    private boolean isQuanLyExpanded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtinkhac);

        // ---------------- Nút Back ----------------
        ImageView icBack = findViewById(R.id.ic_back);
        icBack.setOnClickListener(v -> finish());

        // ---------------- Chuyển sang tab "Thông tin" ----------------
        TextView thongTin = findViewById(R.id.info);
        thongTin.setOnClickListener(v -> {
            Intent intent = new Intent(ThongTinKhacActivity.this, HienThiNguoiLienHeActivity.class);
            startActivity(intent);
//            finish();
        });

        // ---------------- SECTION 1: Thông tin địa chỉ ----------------
        ImageView iconArrowDiaChi = findViewById(R.id.icon_arrow_diachi);
        LinearLayout headerDiaChi = findViewById(R.id.headerThongTinDiaChi);
        LinearLayout layoutDiaChiChiTiet = findViewById(R.id.contentThongTin);

        headerDiaChi.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(layoutDiaChiChiTiet, new AutoTransition());
            if (isDiaChiExpanded) {
                layoutDiaChiChiTiet.setVisibility(LinearLayout.GONE);
                iconArrowDiaChi.setImageResource(R.drawable.ic_arrow_up);
            } else {
                layoutDiaChiChiTiet.setVisibility(LinearLayout.VISIBLE);
                iconArrowDiaChi.setImageResource(R.drawable.ic_arrow_down);
            }
            isDiaChiExpanded = !isDiaChiExpanded;
        });

        // ---------------- SECTION 2: Mô tả ----------------
        ImageView iconArrowMoTa = findViewById(R.id.icon_arrow_mota);
        LinearLayout headerMoTa = findViewById(R.id.headerMoTa);
        LinearLayout layoutMoTaChiTiet = findViewById(R.id.contentMota);

        headerMoTa.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(layoutMoTaChiTiet, new AutoTransition());
            if (isMoTaExpanded) {
                layoutMoTaChiTiet.setVisibility(LinearLayout.GONE);
                iconArrowMoTa.setImageResource(R.drawable.ic_arrow_up);
            } else {
                layoutMoTaChiTiet.setVisibility(LinearLayout.VISIBLE);
                iconArrowMoTa.setImageResource(R.drawable.ic_arrow_down);
            }
            isMoTaExpanded = !isMoTaExpanded;
        });

        // ---------------- SECTION 3: Thông tin quản lý ----------------
        ImageView iconArrowQuanLy = findViewById(R.id.icon_arrow_quanly);
        LinearLayout headerQuanLy = findViewById(R.id.headerThongTinQuanLy);
        LinearLayout layoutQuanLyChiTiet = findViewById(R.id.contentQuanLy);

        headerQuanLy.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(layoutQuanLyChiTiet, new AutoTransition());
            if (isQuanLyExpanded) {
                layoutQuanLyChiTiet.setVisibility(LinearLayout.GONE);
                iconArrowQuanLy.setImageResource(R.drawable.ic_arrow_up);
            } else {
                layoutQuanLyChiTiet.setVisibility(LinearLayout.VISIBLE);
                iconArrowQuanLy.setImageResource(R.drawable.ic_arrow_down);
            }
            isQuanLyExpanded = !isQuanLyExpanded;
        });
    }
}
