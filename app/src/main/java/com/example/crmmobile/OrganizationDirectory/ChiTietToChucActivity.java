package com.example.crmmobile.OrganizationDirectory;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.R;
import com.example.crmmobile.OrganizationDirectory.ToChuc;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ChiTietToChucActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    // Khai báo Repository và các View
    private CompanyRepository companyRepository;
    private TextView tvCompanyName, tvPhoneNumber, tvEmail, tvIndustry, tvStatusTag;
    private TextView tvAssigneeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiettochuc);

        // --- KHỞI TẠO REPOSITORY ---
        companyRepository = new CompanyRepository(getApplication());

        // --- ÁNH XẠ CÁC VIEW ---
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        ImageButton btnBack = findViewById(R.id.btn_back);

        // Ánh xạ các TextView trong header
        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvEmail = findViewById(R.id.tvEmail);
        tvIndustry = findViewById(R.id.tvIndustry);
        tvStatusTag = findViewById(R.id.tvStatusTag);
        tvAssigneeName = findViewById(R.id.tvAssigneeName);

        // --- TẢI VÀ HIỂN THỊ DỮ LIỆU ---
        // Nhận ID từ Intent
        int companyId = getIntent().getIntExtra("COMPANY_ID", -1);

        // Gọi hàm load dữ liệu cho header
        loadHeaderData(companyId);

        // Thiết lập Adapter cho ViewPager
        ChiTietToChucPagerAdapter adapter = new ChiTietToChucPagerAdapter(this, companyId);
        viewPager.setAdapter(adapter);

        // Kết nối TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Tổng quan");
            } else {
                tab.setText("Chi tiết");
            }
        }).attach();

        // Xử lý nút Back
        btnBack.setOnClickListener(v -> finish());
    }

    /**
     * Hàm để tải dữ liệu công ty và hiển thị lên header
     * @param companyId ID của công ty cần tải
     */
    private void loadHeaderData(int companyId) {
        if (companyId == -1) return; // Không có ID thì không làm gì cả

        // Dùng Repository để lấy dữ liệu từ DB
        ToChuc company = companyRepository.getCompanyByID(companyId);

        // Cập nhật giao diện nếu có dữ liệu
        if (company != null) {
            tvCompanyName.setText(getOrDefault(company.getCompanyName()));
            tvPhoneNumber.setText(getOrDefault(company.getPhone()));
            tvEmail.setText(getOrDefault(company.getEmail()));
            tvIndustry.setText(getOrDefault(company.getIndustry()));
            tvAssigneeName.setText(getOrDefault(company.getAssignedTo()));

            // Hiển thị trạng thái (nếu có)
            if (company.getTrangThai() != null) {
                tvStatusTag.setText(company.getTrangThai().toString());
            } else {
                tvStatusTag.setText("---");
            }
        }
    }

    /**
     * Hàm tiện ích để trả về "---" nếu chuỗi rỗng hoặc null
     */
    private String getOrDefault(String value) {
        return (value != null && !value.isEmpty()) ? value : "---";
    }
}
