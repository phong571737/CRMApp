package com.example.crmmobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ThongTinLienHeActivity extends AppCompatActivity {

    private TextView infoTab, thongTinKhacTab;
    private ImageView icBack;
    private Button btnLuu, btnHuy;

    private ThongTinNguoiLienHeFragment infoFragment;
    private ThongTinKhacFragment khacFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);

        infoTab = findViewById(R.id.info);
        thongTinKhacTab = findViewById(R.id.thongtinkhac);
        icBack = findViewById(R.id.ic_back);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);

        // Khởi tạo fragment
        infoFragment = new ThongTinNguoiLienHeFragment();
        khacFragment = new ThongTinKhacFragment();

        // Hiển thị fragment mặc định
        loadFragment(infoFragment);
        setActiveTab(infoTab, thongTinKhacTab);

        infoTab.setOnClickListener(v -> {
            loadFragment(infoFragment);
            setActiveTab(infoTab, thongTinKhacTab);
        });

        thongTinKhacTab.setOnClickListener(v -> {
            loadFragment(khacFragment);
            setActiveTab(thongTinKhacTab, infoTab);
        });

        // Nút quay lại và Hủy
        icBack.setOnClickListener(v -> finish());
        btnHuy.setOnClickListener(v -> finish());

        // Nút Lưu
        btnLuu.setOnClickListener(v -> {
            // Kiểm tra dữ liệu cơ bản
            if (infoFragment.getHoVaTenDem().isEmpty() || infoFragment.getTen().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập họ tên!", Toast.LENGTH_SHORT).show();
                return;
            }
            saveData();
        });
    }

    private void saveData() {
        // Lấy dữ liệu từ fragment
        String hoTen = infoFragment.getHoVaTenDem();
        String ten = infoFragment.getTen();
        String congTy = infoFragment.getCongTy();

        Intent result = new Intent();
        result.putExtra("hoTen", hoTen);
        result.putExtra("congTy", congTy);

        setResult(RESULT_OK, result);
        finish();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    private void setActiveTab(TextView active, TextView inactive) {
        infoTab.setTextColor(getResources().getColor(R.color.grey));
        infoTab.setBackgroundResource(android.R.color.transparent);

        thongTinKhacTab.setTextColor(getResources().getColor(R.color.grey));
        thongTinKhacTab.setBackgroundResource(android.R.color.transparent);

        active.setTextColor(getResources().getColor(R.color.blue));
        active.setBackgroundResource(R.drawable.edittext_line);
    }
}
