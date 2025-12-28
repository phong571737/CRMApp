package com.example.crmmobile.IndividualDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.MainDirectory.Recent;
import com.example.crmmobile.MainDirectory.RecentViewModel;
import com.example.crmmobile.R;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.DataBase.CaNhanRepository;

/**
 * CHỈNH SỬA CLASS TabActivity
 */

public class TabActivity extends AppCompatActivity {

    private TextView tabTongQuan, tabChiTiet, tabCoHoi;
    private ImageView icBack;

    private TextView tvHeaderTen, tvHeaderSdt, tvHeaderEmail, tvHeaderCongTy, tvHeaderNguoiTao, tvHeaderNguoiPhuTrach;

    private CaNhan currentCaNhan;
    private ViewModelCanhan viewModelCanhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoilienhe);

        viewModelCanhan = new ViewModelProvider(this).get(ViewModelCanhan.class);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("CANHAN_OBJECT")) {
            currentCaNhan = (CaNhan) intent.getSerializableExtra("CANHAN_OBJECT");
        }

        initViews();
        SetRecentCanhan();
        fillHeaderData();

        // --- Mặc định hiển thị tab Tổng quan ---
        TongQuanFragment tongQuanFragment = new TongQuanFragment();
        Bundle bundleTongQuan = new Bundle();
        bundleTongQuan.putSerializable("CANHAN_DATA", currentCaNhan);
        tongQuanFragment.setArguments(bundleTongQuan);
        setFragment(tongQuanFragment);
        setActiveTab(tabTongQuan);

        // --- Xử lý sự kiện click các tab ---
        tabTongQuan.setOnClickListener(v -> {
            TongQuanFragment fragment = new TongQuanFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("CANHAN_DATA", currentCaNhan);
            fragment.setArguments(bundle);
            setFragment(fragment);
            setActiveTab(tabTongQuan);
        });

        tabChiTiet.setOnClickListener(v -> {
            ChiTietFragment fragment = new ChiTietFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("CANHAN_DATA", currentCaNhan);
            fragment.setArguments(bundle);

            setFragment(fragment);
            setActiveTab(tabChiTiet);
        });

        icBack.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload dữ liệu từ database khi quay lại để đảm bảo dữ liệu luôn mới nhất
        if (currentCaNhan != null && currentCaNhan.getId() > 0) {
            CaNhanRepository caNhanRepository = new CaNhanRepository(this);
            CaNhan updatedCaNhan = caNhanRepository.getById(currentCaNhan.getId());
            if (updatedCaNhan != null) {
                currentCaNhan = updatedCaNhan;
                fillHeaderData();
                // Cập nhật fragment hiện tại với dữ liệu mới
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (currentFragment instanceof ChiTietFragment) {
                    // Reload ChiTietFragment với dữ liệu mới
                    ChiTietFragment fragment = new ChiTietFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CANHAN_DATA", currentCaNhan);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                    setActiveTab(tabChiTiet);
                } else if (currentFragment instanceof TongQuanFragment) {
                    // Reload TongQuanFragment với dữ liệu mới
                    TongQuanFragment fragment = new TongQuanFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CANHAN_DATA", currentCaNhan);
                    fragment.setArguments(bundle);
                    setFragment(fragment);
                    setActiveTab(tabTongQuan);
                }
            }
        }
    }

    private void SetRecentCanhan() {
        if (currentCaNhan == null) return;

        RecentViewModel recentViewModel = new ViewModelProvider(this).get(RecentViewModel.class);

        Recent recent = new Recent();
        recent.setObjectType("CANHAN");
        recent.setObjectID(currentCaNhan.getId());
        String full_name = currentCaNhan.getDanhXung() + " " + currentCaNhan.getHoVaTen() + " " + currentCaNhan.getTen();
        recent.setName(full_name);
        recent.setTime(System.currentTimeMillis());
        recentViewModel.upsertRecent(recent);
        Log.e("RECENT", "Saved recent lead: " + recent.getName());
        Log.e("RECENT", "Saved Name: " + currentCaNhan.getTen());
    }

    /**
     * Hàm hiển thị fragment mới trong container
     */
    private void initViews() {
        tabTongQuan = findViewById(R.id.tab_tongquan);
        tabChiTiet = findViewById(R.id.tab_chitiet);
//        tabHoatDong = findViewById(R.id.tab_hoatdong);
        tabCoHoi = findViewById(R.id.tab_cohoi);
        icBack = findViewById(R.id.ic_back);

        // Header Views
        tvHeaderTen = findViewById(R.id.tennguoilienhe);
        tvHeaderSdt = findViewById(R.id.sodienthoai);
        tvHeaderEmail = findViewById(R.id.email);
        tvHeaderCongTy = findViewById(R.id.rowcongty);
        tvHeaderNguoiTao = findViewById(R.id.tennguoitao);
        tvHeaderNguoiPhuTrach = findViewById(R.id.tennguoiphutrach);
    }

    private void fillHeaderData() {
        if (currentCaNhan != null) {
            tvHeaderTen.setText(checkNull(currentCaNhan.getHoVaTen() + " " + currentCaNhan.getTen()));
            tvHeaderSdt.setText(checkNull(currentCaNhan.getDiDong()));
            tvHeaderEmail.setText(checkNull(currentCaNhan.getEmail()));
            tvHeaderCongTy.setText(checkNull(currentCaNhan.getCongTy()));
            // Giả sử tên người tạo lấy từ User đăng nhập, ở đây tạm thời set cứng hoặc lấy từ DB nếu có field
            // tvHeaderNguoiTao.setText(...);
            // Load tên nhân viên từ giaoChoID
            String tenNhanVien = "";
            if (currentCaNhan.getGiaoChoID() != null && currentCaNhan.getGiaoChoID() > 0) {
                NhanVienRepository nhanVienRepository = new NhanVienRepository(this);
                tenNhanVien = nhanVienRepository.getNameByID(currentCaNhan.getGiaoChoID());
                if (tenNhanVien == null || tenNhanVien.isEmpty()) {
                    tenNhanVien = "";
                }
            }
            tvHeaderNguoiPhuTrach.setText(checkNull(tenNhanVien));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    /**
     * Hàm đổi màu tab được chọn + đổi viền
     */
    private void setActiveTab(TextView selectedTab) {
        // Reset màu và background tất cả tab về mặc định
        tabTongQuan.setTextColor(getResources().getColor(R.color.grey));
        tabTongQuan.setBackgroundResource(android.R.color.transparent);

        tabChiTiet.setTextColor(getResources().getColor(R.color.grey));
        tabChiTiet.setBackgroundResource(android.R.color.transparent);

//        tabHoatDong.setTextColor(getResources().getColor(R.color.grey));
//        tabHoatDong.setBackgroundResource(android.R.color.transparent);
//
        tabCoHoi.setTextColor(getResources().getColor(R.color.grey));
        tabCoHoi.setBackgroundResource(android.R.color.transparent);

        // Tab được chọn hiển thị màu xanh + viền dưới
        selectedTab.setTextColor(getResources().getColor(R.color.blue));
        selectedTab.setBackgroundResource(R.drawable.edittext_line);
    }

    private String checkNull(String text) {
        return text == null ? "" : text;
    }
}