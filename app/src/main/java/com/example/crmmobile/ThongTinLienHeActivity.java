package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.widget.ImageView;
import android.content.Intent;



public class ThongTinLienHeActivity extends AppCompatActivity {

    private TextView infoTab, thongTinKhacTab;
    private ImageView icBack;

    private Button btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);

        infoTab = findViewById(R.id.info);
        thongTinKhacTab = findViewById(R.id.thongtinkhac);
        icBack = findViewById(R.id.ic_back);
        btnHuy = findViewById(R.id.btnHuy);

        // Hiển thị fragment mặc định là Thông tin
        loadFragment(new ThongTinNguoiLienHeFragment());
        setActiveTab(infoTab, thongTinKhacTab);

        infoTab.setOnClickListener(v -> {
            loadFragment(new ThongTinNguoiLienHeFragment());
            setActiveTab(infoTab, thongTinKhacTab);
        });

        thongTinKhacTab.setOnClickListener(v -> {
            loadFragment(new ThongTinKhacFragment());
            setActiveTab(thongTinKhacTab, infoTab);
        });

        icBack.setOnClickListener(v -> {
            Intent intent = new Intent(ThongTinLienHeActivity.this, DanhSachCaNhanActivity.class);
            startActivity(intent);
            finish();
        });

        btnHuy.setOnClickListener(v -> {
            Intent intent = new Intent(ThongTinLienHeActivity.this, DanhSachCaNhanActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    private void setActiveTab(TextView active, TextView inactive) {
        active.setTextColor(getResources().getColor(R.color.blue));
        active.setBackgroundResource(R.drawable.edittext_line);

        inactive.setTextColor(getResources().getColor(R.color.grey));
        inactive.setBackgroundResource(android.R.color.transparent);
    }
}
