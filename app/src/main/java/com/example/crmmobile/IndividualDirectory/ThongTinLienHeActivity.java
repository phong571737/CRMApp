package com.example.crmmobile.IndividualDirectory;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crmmobile.R;
import com.google.android.material.tabs.TabLayout;

public class ThongTinLienHeActivity extends AppCompatActivity {

    private TextView infoTab, thongTinKhacTab;
    private ImageView icBack;
    private Button btnLuu, btnHuy;

    private ThongTinNguoiLienHeFragment infoFragment;
    private ThongTinKhacFragment khacFragment;

    private String mode = "add";
    private int editingId = -1;
    private CaNhan editingCaNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);

        initViews();

        // 1. Khởi tạo Fragment
        infoFragment = new ThongTinNguoiLienHeFragment();
        khacFragment = new ThongTinKhacFragment();

        // 2. Xử lý Intent (Edit mode)
        handleIntent();

        // 3. Setup Fragment ban đầu (Add cả 2 nhưng hide Khác)
        setupInitialFragments();

        // 4. Setup Click events
        setupEvents();
    }

    private void initViews() {
        infoTab = findViewById(R.id.info);
        thongTinKhacTab = findViewById(R.id.thongtinkhac);
        icBack = findViewById(R.id.ic_back);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null && "edit".equals(intent.getStringExtra("mode"))) {
            mode = "edit";
            editingId = intent.getIntExtra("id", -1);

            editingCaNhan = new CaNhan();
            editingCaNhan.setId(editingId);
            editingCaNhan.setDanhXung(intent.getStringExtra("danhXung"));
            editingCaNhan.setHoVaTen(intent.getStringExtra("hoTen"));
            editingCaNhan.setTen(intent.getStringExtra("ten"));
            editingCaNhan.setCongTy(intent.getStringExtra("congTy"));
            editingCaNhan.setGioiTinh(intent.getStringExtra("gioiTinh"));
            editingCaNhan.setDiDong(intent.getStringExtra("diDong"));
            editingCaNhan.setEmail(intent.getStringExtra("email"));
            editingCaNhan.setNgaySinh(intent.getStringExtra("ngaySinh"));
            editingCaNhan.setDiaChi(intent.getStringExtra("diaChi"));
            editingCaNhan.setQuanHuyen(intent.getStringExtra("quanHuyen"));
            editingCaNhan.setTinhTP(intent.getStringExtra("tinhTP"));
            editingCaNhan.setQuocGia(intent.getStringExtra("quocGia"));
            editingCaNhan.setMoTa(intent.getStringExtra("moTa"));
            editingCaNhan.setGhiChu(intent.getStringExtra("ghiChu"));
            editingCaNhan.setGiaoCho(intent.getStringExtra("giaoCho"));

            // Truyền dữ liệu vào Fragment
            infoFragment.setCaNhan(editingCaNhan);
            khacFragment.setCaNhan(editingCaNhan);
        }
    }

    private void setupInitialFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Add cả 2 vào container, nhưng hide cái 'Khác' đi
        transaction.add(R.id.fragmentContainer, infoFragment, "INFO");
        transaction.add(R.id.fragmentContainer, khacFragment, "KHAC");
        transaction.hide(khacFragment);
        transaction.show(infoFragment);
        transaction.commit();

        setActiveTab(infoTab, thongTinKhacTab);
    }

    private void setupEvents() {
        infoTab.setOnClickListener(v -> {
            showFragment(true); // True = hiện info
            setActiveTab(infoTab, thongTinKhacTab);
        });

        thongTinKhacTab.setOnClickListener(v -> {
            showFragment(false); // False = hiện khác
            setActiveTab(thongTinKhacTab, infoTab);
        });

        icBack.setOnClickListener(v -> finish());
        btnHuy.setOnClickListener(v -> finish());

        btnLuu.setOnClickListener(v -> {
            // Validate dữ liệu từ Info Fragment
            // Lưu ý: Các hàm get... phải check null an toàn (xem code Fragment bên dưới)
            String hoTen = infoFragment.getHoVaTenDem();
            String ten = infoFragment.getTen();
            String diDong = infoFragment.getDiDong();

            if (hoTen.isEmpty() || ten.isEmpty() || diDong.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Họ tên đệm, Tên và Di động!", Toast.LENGTH_SHORT).show();
                return;
            }
            saveData();
        });
    }

    // Hàm chuyển đổi hiển thị Fragment thay vì replace
    private void showFragment(boolean showInfo) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (showInfo) {
            transaction.hide(khacFragment);
            transaction.show(infoFragment);
        } else {
            transaction.hide(infoFragment);
            transaction.show(khacFragment);
        }
        transaction.commit();
    }

    private void saveData() {
        Intent result = new Intent();

        // Lấy dữ liệu an toàn từ Fragment
        result.putExtra("danhXung", infoFragment.getDanhXung());
        result.putExtra("hoTen", infoFragment.getHoVaTenDem());
        result.putExtra("ten", infoFragment.getTen());
        result.putExtra("congTy", infoFragment.getCongTy());
        result.putExtra("gioiTinh", infoFragment.getGioiTinh());
        result.putExtra("diDong", infoFragment.getDiDong());
        result.putExtra("email", infoFragment.getEmail());
        result.putExtra("ngaySinh", infoFragment.getNgaySinh());

        result.putExtra("diaChi", khacFragment.getDiaChi());
        result.putExtra("quanHuyen", khacFragment.getQuanHuyen());
        result.putExtra("tinhTP", khacFragment.getTinhTP());
        result.putExtra("giaoCho", khacFragment.getGiaoCho());
        result.putExtra("quocGia", khacFragment.getQuocGia());
        result.putExtra("ghiChu", khacFragment.getGhiChu());
        result.putExtra("moTa", khacFragment.getMoTa());

        if ("edit".equals(mode) && editingId != -1) {
            result.putExtra("id", editingId);
        }

        setResult(RESULT_OK, result);
        finish();
    }

    private void setActiveTab(TextView active, TextView inactive) {
        inactive.setTextColor(getResources().getColor(R.color.grey));
        inactive.setBackgroundResource(android.R.color.transparent);
        active.setTextColor(getResources().getColor(R.color.blue));
        active.setBackgroundResource(R.drawable.edittext_line);
    }
}