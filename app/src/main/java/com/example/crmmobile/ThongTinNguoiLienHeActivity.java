package com.example.crmmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ThongTinNguoiLienHeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtinnguoilienhe); // Tạo layout riêng nếu cần

        //trở lại trang chính
        findViewById(R.id.ic_back).setOnClickListener(v -> {
            finish();
        });

        //mở trang thông tin khác
        TextView thongtinkhac = findViewById(R.id.thongtinkhac);
        thongtinkhac.setOnClickListener(v -> {
            Intent intent = new Intent(ThongTinNguoiLienHeActivity.this, ThongTinKhacActivity.class);
            startActivity(intent);
//            finish();
        });
    }
}
