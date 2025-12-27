package com.example.crmmobile.OrderDirectory;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crmmobile.R;
import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.DataBase.DonHangRepository;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.OrganizationDirectory.ToChuc;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class OrderDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ORDER_ID = "orderId";

    private int orderId = -1;

    private ImageView iv_back;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    // Header views
    private TextView tvOrderCode, tvCustomer, tvCompany, tvPrice, tvTag, tvStatus;
    private TextView tvCreatorName, tvManagerName;

    // repos
    private DonHangRepository donHangRepo;
    private CompanyRepository companyRepo;
    private CaNhanRepository caNhanRepo;
    private NhanVienRepository nhanVienRepo;

    private final NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
    private DonHang currentDonHang;

    public DonHang getCurrentDonHang() {
        return currentDonHang;
    }

    public int getOrderId() {
        return orderId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderId = getIntent().getIntExtra(EXTRA_ORDER_ID, -1);

        donHangRepo  = new DonHangRepository(this);
        companyRepo  = new CompanyRepository(this);
        caNhanRepo   = new CaNhanRepository(this);
        nhanVienRepo = new NhanVienRepository(this);

        // bind header
        tvOrderCode   = findViewById(R.id.tvOrderCode);
        tvCustomer    = findViewById(R.id.tvCustomer);
        tvCompany     = findViewById(R.id.tvCompany);
        tvPrice       = findViewById(R.id.tvPrice);
        tvTag         = findViewById(R.id.tvTag);
        tvStatus      = findViewById(R.id.tvStatus);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        iv_back   = findViewById(R.id.btnBack);

        List<String> tabTitles = Arrays.asList("Tổng quan", "Chi tiết");
        OrderDetailPagerAdapter pagerAdapter = new OrderDetailPagerAdapter(this, tabTitles);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles.get(position))
        ).attach();

        viewPager.setCurrentItem(0);

        iv_back.setOnClickListener(v -> finish());

        bindHeaderFromDb(); // first load
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindHeaderFromDb(); // ✅ quay lại là refresh
    }

    private void bindHeaderFromDb() {
        if (orderId <= 0) return;

        DonHang dh = donHangRepo.getById(orderId);
        if (dh == null) return;

        // mã đơn = tenDonHang (hoặc bạn đổi sang format SO000xx tuỳ)
        safeSet(tvOrderCode, dh.getTenDonHang());

        // công ty
        String companyName = resolveCompanyName(dh);
        safeSet(tvCompany, companyName);

        // người liên hệ
        String contactName = resolveContactName(dh);
        safeSet(tvCustomer, contactName);

        // tổng tiền
        String money = nf.format(dh.getTongTien()) + " đ";
        safeSet(tvPrice, money);

        // trạng thái
        safeSet(tvStatus, dh.getTinhTrang());

        // tag (nếu chưa có field riêng thì dùng tạm trạng thái/loại)
        safeSet(tvTag, dh.getTinhTrang());

        // người tạo
        String creator = resolveNhanVienName(dh.getNguoiTaoId());
        if (creator.isEmpty()) creator = "—";
        safeSet(tvCreatorName, creator);

        // người phụ trách
        String assignee = resolveNhanVienName(dh.getGiaoChoId());
        if (assignee.isEmpty()) assignee = "—";
        safeSet(tvManagerName, assignee);
    }

    private String resolveCompanyName(DonHang dh) {
        try {
            if (dh.getCongTyId() > 0) {
                ToChuc c = companyRepo.getCompanyByID(dh.getCongTyId());
                if (c != null && c.getCompanyName() != null) return c.getCompanyName();
            }
        } catch (Exception ignored) {}

        // fallback: bạn đang nhét "company - contact" trong MOTA
        String mota = dh.getMoTa();
        if (mota != null && mota.contains(" - ")) return mota.split(" - ")[0].trim();
        return (mota != null && !mota.trim().isEmpty()) ? mota.trim() : "—";
    }

    private String resolveContactName(DonHang dh) {
        try {
            if (dh.getNguoiLienHeId() > 0) {
                CaNhan cn = caNhanRepo.getById(dh.getNguoiLienHeId());
                if (cn != null && cn.getHoVaTen() != null) return cn.getHoVaTen();
            }
        } catch (Exception ignored) {}

        String mota = dh.getMoTa();
        if (mota != null && mota.contains(" - ")) {
            String[] parts = mota.split(" - ");
            if (parts.length >= 2) return parts[1].trim();
        }
        return "—";
    }

    private String resolveNhanVienName(int id) {
        if (id <= 0) return "";
        try {
            return nhanVienRepo.getNameByID(id);
        } catch (Exception ignored) {}
        return "";
    }

    private void safeSet(TextView tv, String s) {
        if (tv != null) tv.setText(s == null || s.trim().isEmpty() ? "—" : s);
    }
}
