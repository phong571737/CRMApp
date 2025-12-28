package com.example.crmmobile.IndividualDirectory;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crmmobile.Adapter.AdapterTaoCanNhan;
import com.example.crmmobile.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ThongTinLienHeActivity extends AppCompatActivity {

//    private TextView infoTab, thongTinKhacTab;
    private ImageView icBack;
    private Button btnLuu, btnHuy;
    private TabLayout tablayout;
    private ViewPager2 vp_tab;
    private String mode = "add";
    private int editingId = -1;
    private CaNhan editingCaNhan;
    private ViewModelCanhan viewModelCanhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        viewModelCanhan = new ViewModelProvider(this).get(ViewModelCanhan.class);

        initViews();

        // 2. Xử lý Intent (Edit mode)
        handleIntent();

        // 3. Setup Fragment ban đầu (Add cả 2 nhưng hide Khác)
        setupInitialFragments();

        // 4. Setup Click events
        setupEvents();
    }

    private void initViews() {
        tablayout = findViewById(R.id.tablayout);
        vp_tab = findViewById(R.id.vp_tab);
        View header = findViewById(R.id.tab_header);
        icBack = header.findViewById(R.id.ic_back);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null && "edit".equals(intent.getStringExtra("mode"))) {
            mode = "edit";
            editingId = intent.getIntExtra("id", -1);

            viewModelCanhan.Danhxung.setValue(intent.getStringExtra("danhXung"));
            viewModelCanhan.hoTen.setValue(intent.getStringExtra("hoTen"));
            viewModelCanhan.ten.setValue(intent.getStringExtra("ten"));
            viewModelCanhan.congTy.setValue(intent.getStringExtra("congTy"));
            viewModelCanhan.gioiTinh.setValue(intent.getStringExtra("gioiTinh"));
            viewModelCanhan.diDong.setValue(intent.getStringExtra("diDong"));
            viewModelCanhan.email.setValue(intent.getStringExtra("email"));
            viewModelCanhan.ngaySinh.setValue(intent.getStringExtra("ngaySinh"));
            viewModelCanhan.diachi.setValue(intent.getStringExtra("diaChi"));
            viewModelCanhan.quanHuyen.setValue(intent.getStringExtra("quanHuyen"));
            viewModelCanhan.tinhTP.setValue(intent.getStringExtra("tinhTP"));
            viewModelCanhan.quocGia.setValue(intent.getStringExtra("quocGia"));
            viewModelCanhan.moTa.setValue(intent.getStringExtra("moTa"));
            viewModelCanhan.ghiChu.setValue(intent.getStringExtra("ghiChu"));
            viewModelCanhan.giaoCho.setValue(intent.getStringExtra("giaoCho"));
            // Lưu giaoChoID nếu có
            int giaoChoID = intent.getIntExtra("giaoChoID", 0);
            if (giaoChoID > 0) {
                viewModelCanhan.giaoChoID.setValue(giaoChoID);
            }
        }
    }

    private void setupInitialFragments() {
        AdapterTaoCanNhan adapterTaoCanNhan = new AdapterTaoCanNhan(this);
        vp_tab.setAdapter(adapterTaoCanNhan);
        new TabLayoutMediator(tablayout, vp_tab, ((tab, i) -> {
            if (i == 0) tab.setText("Thông tin Người liên hệ");
            else tab.setText("Thông tin khác");
        })).attach();

    }

    private void setupEvents() {
        icBack.setOnClickListener(v -> finish());
        btnHuy.setOnClickListener(v -> finish());

        btnLuu.setOnClickListener(v -> {
            String hoten = viewModelCanhan.hoTen.getValue();
            String ten = viewModelCanhan.ten.getValue();
            String didong = viewModelCanhan.diDong.getValue();

            if (TextUtils.isEmpty(hoten) || TextUtils.isEmpty(ten) || TextUtils.isEmpty(didong)){
                Toast.makeText(this, "Vui lòng nhập Họ tên, Tên và Di động", Toast.LENGTH_SHORT).show();
                return;
            }
            saveData();
        });
    }

    private void saveData() {
        Intent result = new Intent();

        // Lấy dữ liệu an toàn từ Fragment
        result.putExtra("danhXung", viewModelCanhan.Danhxung.getValue());
        result.putExtra("hoTen", viewModelCanhan.hoTen.getValue());
        result.putExtra("ten", viewModelCanhan.ten.getValue());
        result.putExtra("congTy", viewModelCanhan.congTy.getValue());
        result.putExtra("gioiTinh", viewModelCanhan.gioiTinh.getValue());
        result.putExtra("diDong", viewModelCanhan.diDong.getValue());
        result.putExtra("email", viewModelCanhan.email.getValue());
        result.putExtra("ngaySinh", viewModelCanhan.ngaySinh.getValue());

        result.putExtra("diaChi", viewModelCanhan.diachi.getValue());
        result.putExtra("quanHuyen", viewModelCanhan.quanHuyen.getValue());
        result.putExtra("tinhTP", viewModelCanhan.tinhTP.getValue());
        result.putExtra("giaoCho", viewModelCanhan.giaoCho.getValue());
        result.putExtra("quocGia", viewModelCanhan.quocGia.getValue());
        result.putExtra("ghiChu", viewModelCanhan.ghiChu.getValue());
        result.putExtra("moTa", viewModelCanhan.moTa.getValue());
        
        // Lấy giaoChoID từ ViewModel
        Integer giaoChoID = viewModelCanhan.giaoChoID.getValue();
        if (giaoChoID != null && giaoChoID > 0) {
            result.putExtra("giaoChoID", giaoChoID);
        } else {
            result.putExtra("giaoChoID", 0);
        }

        if ("edit".equals(mode) && editingId != -1) {
            result.putExtra("id", editingId);
        }

        setResult(RESULT_OK, result);
        finish();
    }
}