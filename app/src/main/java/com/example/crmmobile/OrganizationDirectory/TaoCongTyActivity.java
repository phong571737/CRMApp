package com.example.crmmobile.OrganizationDirectory;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.R;
import com.google.android.material.tabs.TabLayout;

public class TaoCongTyActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private TextView tvHeaderTitle;
    private Button btnLuu;

    // Giữ tham chiếu đến 2 fragment
    private TaoCongTyThongTinCongTyFragment fragment1;
    private TaoCongTyThongTinKhacFragment fragment2;

    private CompanyRepository companyRepository;
    private ToChuc currentToChuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taocongty);

        // 1. Khởi tạo Repository
        companyRepository = new CompanyRepository(this);

        tvHeaderTitle = findViewById(R.id.tv_header_title);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // 2. XỬ LÝ NHẬN DỮ LIỆU TỪ INTENT
        if (getIntent().hasExtra("COMPANY_ID")) {
            // === CHẾ ĐỘ CHỈNH SỬA ===
            int id = getIntent().getIntExtra("COMPANY_ID", -1);
            // Lấy dữ liệu từ Database dựa trên ID
            currentToChuc = companyRepository.getCompanyByID(id);

            if(currentToChuc != null) {
                tvHeaderTitle.setText("Chỉnh sửa công ty");
            } else {
                // Phòng hờ lỗi không tìm thấy ID
                currentToChuc = new ToChuc();
                tvHeaderTitle.setText("Tạo công ty (Lỗi ID)");
            }
        } else {
            // === CHẾ ĐỘ TẠO MỚI ===
            currentToChuc = new ToChuc();
            String title = getIntent().getStringExtra("EXTRA_TITLE");
            if (title != null) tvHeaderTitle.setText(title);
        }

        // 3. ĐÓNG GÓI DỮ LIỆU VÀO BUNDLE
        Bundle bundle = new Bundle();
        bundle.putSerializable("COMPANY_DATA", currentToChuc);

        // 4. KHỞI TẠO FRAGMENT VÀ TRUYỀN BUNDLE
        fragment1 = new TaoCongTyThongTinCongTyFragment();
        fragment1.setArguments(bundle); // Truyền data sang Fragment 1

        fragment2 = new TaoCongTyThongTinKhacFragment();
        fragment2.setArguments(bundle); // Truyền data sang Fragment 2

        // 5. SETUP TABLAYOUT (Dùng Show/Hide để giữ data)
        tabLayout = findViewById(R.id.tabLayout);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayoutThongTin, fragment1, "FRAG1")
                .add(R.id.frameLayoutThongTin, fragment2, "FRAG2")
                .hide(fragment2)
                .show(fragment1)
                .commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    switchFragment(fragment1, fragment2);
                } else {
                    switchFragment(fragment2, fragment1);
                }
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        // 6. Xử lý nút LƯU
        btnLuu = findViewById(R.id.btnLuu);
        if (btnLuu != null) {
            btnLuu.setOnClickListener(v -> handleSave());
        }
    }

    private void switchFragment(Fragment showFrag, Fragment hideFrag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!showFrag.isAdded()) ft.add(R.id.frameLayoutThongTin, showFrag);
        ft.hide(hideFrag);
        ft.show(showFrag);
        ft.commit();
    }

    private void handleSave() {
        if (fragment1 != null && fragment1.isAdded()) fragment1.getDataFromForm(currentToChuc);
        if (fragment2 != null && fragment2.isAdded()) fragment2.getDataFromForm(currentToChuc);

        if (currentToChuc.getCompanyName() == null || currentToChuc.getCompanyName().trim().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên công ty", Toast.LENGTH_SHORT).show();
            return;
        }

        long result;
        if (currentToChuc.getId() > 0) {
            result = companyRepository.updateCompany(currentToChuc); // Update
        } else {
            result = companyRepository.addCompany(currentToChuc); // Insert
        }

        if (result > 0) {
            Toast.makeText(this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi lưu", Toast.LENGTH_SHORT).show();
        }
    }
}