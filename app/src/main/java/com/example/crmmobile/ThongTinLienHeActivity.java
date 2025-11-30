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

    private String mode = "add";
    private int editingId = -1;
    private CaNhan editingCaNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);

        infoTab = findViewById(R.id.info);
        thongTinKhacTab = findViewById(R.id.thongtinkhac);
        icBack = findViewById(R.id.ic_back);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);

        // Khởi tạo fragment (lưu tham chiếu)
        infoFragment = new ThongTinNguoiLienHeFragment();
        khacFragment = new ThongTinKhacFragment();

        // Kiểm tra Intent (nếu là edit sẽ có extras)
        Intent intent = getIntent();
        if (intent != null && "edit".equals(intent.getStringExtra("mode"))) {
            mode = "edit";
            editingId = intent.getIntExtra("id", -1);

            // Tạo model tạm từ extras để truyền xuống fragment
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
            editingCaNhan.setSoCuocGoi(intent.getIntExtra("soCuocGoi", 2));
            editingCaNhan.setSoCuocHop(intent.getIntExtra("soCuocHop", 2));
        }

        // Hiển thị fragment mặc định
        loadFragment(infoFragment);
        setActiveTab(infoTab, thongTinKhacTab);

        // Nếu là edit: truyền model xuống cả 2 fragment (hoặc chờ fragment tạo xong)
        if ("edit".equals(mode)) {
            // Nếu fragments đã khởi tạo view, set trực tiếp, ngược lại set khi fragment tạo view bằng setCaNhan
            infoFragment.setCaNhan(editingCaNhan);
            khacFragment.setCaNhan(editingCaNhan);
        }

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
            // Kiểm tra dữ liệu cơ bản (đảm bảo fragment đã load)
            if (infoFragment.getHoVaTenDem() == null || infoFragment.getHoVaTenDem().trim().isEmpty()
                    || infoFragment.getTen() == null || infoFragment.getTen().trim().isEmpty()
                    || infoFragment.getDiDong() == null || infoFragment.getDiDong().trim().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập họ tên, tên và số di động!", Toast.LENGTH_SHORT).show();
                return;
            }
            saveData();
        });
    }

    private void saveData() {
        // Lấy dữ liệu từ fragment
        String danhXung = infoFragment.getDanhXung();
        String hoTen = infoFragment.getHoVaTenDem();
        String ten = infoFragment.getTen();
        String congTy = infoFragment.getCongTy();
        String gioiTinh = infoFragment.getGioiTinh();
        String diDong = infoFragment.getDiDong();
        String email = infoFragment.getEmail();
        String ngaySinh = infoFragment.getNgaySinh();

        String diaChi = khacFragment.getDiaChi();
        String quanHuyen = khacFragment.getQuanHuyen();
        String tinhTP = khacFragment.getTinhTP();
        String giaoCho = khacFragment.getGiaoCho();
        String quocGia = khacFragment.getQuocGia();
        String ghiChu = khacFragment.getGhiChu();
        String moTa = khacFragment.getMoTa();

        Intent result = new Intent();
        result.putExtra("danhXung", danhXung);
        result.putExtra("hoTen", hoTen);
        result.putExtra("ten", ten);
        result.putExtra("congTy", congTy);
        result.putExtra("gioiTinh", gioiTinh);
        result.putExtra("diDong", diDong);
        result.putExtra("email", email);
        result.putExtra("ngaySinh", ngaySinh);

        result.putExtra("diaChi", diaChi);
        result.putExtra("quanHuyen", quanHuyen);
        result.putExtra("tinhTP", tinhTP);
        result.putExtra("giaoCho", giaoCho);
        result.putExtra("quocGia", quocGia);
        result.putExtra("ghiChu", ghiChu);
        result.putExtra("moTa", moTa);

        // Nếu đang edit, trả về id để caller cập nhật
        if ("edit".equals(mode) && editingId != -1) {
            result.putExtra("id", editingId);
        }

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
