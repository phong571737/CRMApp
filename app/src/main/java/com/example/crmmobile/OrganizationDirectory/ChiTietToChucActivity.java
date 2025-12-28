package com.example.crmmobile.OrganizationDirectory;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.R;

public class ChiTietToChucActivity extends AppCompatActivity {

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

        // --- ÁNH XẠ CÁC VIEW (Bỏ ViewPager và TabLayout) ---
        ImageButton btnBack = findViewById(R.id.btn_back);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvEmail = findViewById(R.id.tvEmail);
        tvIndustry = findViewById(R.id.tvIndustry);
        tvStatusTag = findViewById(R.id.tvStatusTag);
        tvAssigneeName = findViewById(R.id.tvAssigneeName);

        // --- TẢI VÀ HIỂN THỊ DỮ LIỆU ---
        int companyId = getIntent().getIntExtra("COMPANY_ID", -1);
        loadHeaderData(companyId); // Load dữ liệu cho header (giữ nguyên)

        // --- LOAD FRAGMENT ---
        if (savedInstanceState == null) { // Chỉ load fragment lần đầu
            loadChiTietFragment(companyId);
        }

        // Xử lý nút Back
        btnBack.setOnClickListener(v -> finish());
    }

    /**
     * Hàm để load ChiTietToChucChiTietFragment vào FrameLayout
     * @param companyId ID của công ty để truyền cho fragment
     */
    private void loadChiTietFragment(int companyId) {
        // Tạo một instance mới của fragment và truyền companyId vào
        ChiTietToChucChiTietFragment fragment = ChiTietToChucChiTietFragment.newInstance(companyId);

        // Dùng FragmentManager để thay thế nội dung của FrameLayout bằng fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }


    /**
     * Hàm để tải dữ liệu công ty và hiển thị lên header
     * @param companyId ID của công ty cần tải
     */
    private void loadHeaderData(int companyId) {
        if (companyId == -1) return;

        ToChuc company = companyRepository.getCompanyByID(companyId);

        if (company != null) {
            tvCompanyName.setText(getOrDefault(company.getCompanyName()));
            tvPhoneNumber.setText(getOrDefault(company.getPhone()));
            tvEmail.setText(getOrDefault(company.getEmail()));
            tvIndustry.setText(getOrDefault(company.getIndustry()));
            tvAssigneeName.setText(getOrDefault(company.getAssignedTo()));

            if (company.getTrangThai() != null) {
                tvStatusTag.setText(company.getTrangThai().toString());
            } else {
                tvStatusTag.setText("---");
            }
        }
    }

    /**
     * Hàm tiện ích để trả về "---" nếu chuỗi rỗng hoặc null (giữ nguyên)
     */
    private String getOrDefault(String value) {
        return (value != null && !value.isEmpty()) ? value : "---";
    }
}
