package com.example.crmmobile.OrganizationDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.OrganizationDirectory.ToChuc;

import com.example.crmmobile.R;
import java.util.Locale;

public class ChiTietToChucChiTietFragment extends Fragment {
    // 1. Khai báo repository
    private CompanyRepository companyRepository;

    private static final String ARG_COMPANY_ID = "company_id";
    // 2. Sử dụng 'companyId' đã nhận được
    private int companyId;

    // View Toggle
    private RelativeLayout sectionThongTinCongTy, sectionDiaChi, sectionMoTa, sectionMuaHang, sectionQuanLy, sectionHeThong;
    private LinearLayout contentThongTinCongTy, contentDiaChi, contentMoTa, contentMuaHang, contentQuanLy, contentHeThong;
    private ImageView toggleThongTinCongTy, toggleDiaChi, toggleMoTa, toggleMuaHang, toggleQuanLy, toggleHeThong;

    // View hiển thị dữ liệu
    private TextView tvTenCTy, tvTrangThai, tvWebsite, tvDienThoai, tvEmail, tvNganhNghe;
    private TextView tvDiaChi, tvQuanHuyen, tvTinhTP, tvQuocGia;
    private TextView tvTinhTrangMua, tvSoDon, tvNgayDau, tvNgayCuoi, tvDoanhThu, tvGiaoCho;

    public ChiTietToChucChiTietFragment() {}

    public static ChiTietToChucChiTietFragment newInstance(int companyId) {
        ChiTietToChucChiTietFragment fragment = new ChiTietToChucChiTietFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COMPANY_ID, companyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 3. Khởi tạo repository ở đây
        if (getActivity() != null) {
            companyRepository = new CompanyRepository(getActivity().getApplication());
        }

        if (getArguments() != null) {
            companyId = getArguments().getInt(ARG_COMPANY_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiettochuc_chitiet, container, false);
        return view; // Chỉ return view ở đây
    }

    // 4. Di chuyển logic vào onViewCreated
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setupToggles();
        loadData(); // Bây giờ loadData() sẽ hoạt động đúng
    }

    private void initViews(View view) {
        // Ánh xạ Toggle Sections
        sectionThongTinCongTy = view.findViewById(R.id.section_thong_tin_cong_ty);
        contentThongTinCongTy = view.findViewById(R.id.content_thong_tin_cong_ty);
        toggleThongTinCongTy = view.findViewById(R.id.toggle_thong_tin_cong_ty);

        sectionDiaChi = view.findViewById(R.id.section_dia_chi);
        contentDiaChi = view.findViewById(R.id.content_dia_chi);
        toggleDiaChi = view.findViewById(R.id.toggle_dia_chi);

        sectionMoTa = view.findViewById(R.id.section_mo_ta);
        contentMoTa = view.findViewById(R.id.content_mo_ta);
        toggleMoTa = view.findViewById(R.id.toggle_mo_ta);

        sectionMuaHang = view.findViewById(R.id.section_mua_hang);
        contentMuaHang = view.findViewById(R.id.content_mua_hang);
        toggleMuaHang = view.findViewById(R.id.toggle_mua_hang);

        sectionQuanLy = view.findViewById(R.id.section_quan_ly);
        contentQuanLy = view.findViewById(R.id.content_quan_ly);
        toggleQuanLy = view.findViewById(R.id.toggle_quan_ly);

        sectionHeThong = view.findViewById(R.id.section_he_thong);
        contentHeThong = view.findViewById(R.id.content_he_thong);
        toggleHeThong = view.findViewById(R.id.toggle_he_thong);

        // Ánh xạ các TextView hiển thị dữ liệu
        tvTenCTy = view.findViewById(R.id.tv_detail_ten_cty);
        tvTrangThai = view.findViewById(R.id.tv_detail_trang_thai);
        tvWebsite = view.findViewById(R.id.tv_detail_website);
        tvDienThoai = view.findViewById(R.id.tv_detail_dien_thoai);
        tvEmail = view.findViewById(R.id.tv_detail_email);
        tvNganhNghe = view.findViewById(R.id.tv_detail_nganh_nghe);

        tvDiaChi = view.findViewById(R.id.tv_detail_dia_chi);
        tvQuanHuyen = view.findViewById(R.id.tv_detail_quan_huyen);
        tvTinhTP = view.findViewById(R.id.tv_detail_tinh_tp);
        tvQuocGia = view.findViewById(R.id.tv_detail_quoc_gia);

        tvTinhTrangMua = view.findViewById(R.id.tv_detail_tinh_trang_mua);
        tvSoDon = view.findViewById(R.id.tv_detail_so_don);
        tvNgayDau = view.findViewById(R.id.tv_detail_ngay_dau);
        tvNgayCuoi = view.findViewById(R.id.tv_detail_ngay_cuoi);
        tvDoanhThu = view.findViewById(R.id.tv_detail_doanh_thu);
        tvGiaoCho = view.findViewById(R.id.tv_detail_giao_cho);
    }

    private void setupToggles() {
        setupToggle(sectionThongTinCongTy, contentThongTinCongTy, toggleThongTinCongTy);
        setupToggle(sectionDiaChi, contentDiaChi, toggleDiaChi);
        setupToggle(sectionMoTa, contentMoTa, toggleMoTa);
        setupToggle(sectionMuaHang, contentMuaHang, toggleMuaHang);
        setupToggle(sectionQuanLy, contentQuanLy, toggleQuanLy);
        setupToggle(sectionHeThong, contentHeThong, toggleHeThong);
    }

    private void loadData() {
        // 5. Sửa điều kiện kiểm tra
        if (companyId == -1 || companyRepository == null) return;

        // 6. Sửa tên hàm lấy dữ liệu
        ToChuc tc = companyRepository.getCompanyByID(companyId);

        if (tc != null) {
            // Đổ dữ liệu vào các TextView
            updateText(tvTenCTy, tc.getCompanyName());

            if (tc.getTrangThai() != null) {
                updateText(tvTrangThai, tc.getTrangThai().toString());
            } else {
                updateText(tvTrangThai, null);
            }

            updateText(tvWebsite, tc.getWebsite());
            updateText(tvDienThoai, tc.getPhone());
            updateText(tvEmail, tc.getEmail());
            updateText(tvNganhNghe, tc.getIndustry());

            updateText(tvDiaChi, tc.getAddress());
            updateText(tvQuanHuyen, tc.getDistrict());
            updateText(tvTinhTP, tc.getCity());
            updateText(tvQuocGia, tc.getCountry());

            updateText(tvTinhTrangMua, tc.getBuyingStatus());
            updateText(tvSoDon, String.valueOf(tc.getOrderCount()));
            updateText(tvNgayDau, tc.getFirstPurchaseDate());
            updateText(tvNgayCuoi, tc.getLastPurchaseDate());
            updateText(tvDoanhThu, String.format(Locale.getDefault(), "%,.0f đ", tc.getTotalRevenue()));
            updateText(tvGiaoCho, tc.getAssignedTo());
        }
    }


    private void updateText(TextView tv, String value) {
        if (tv != null) {
            tv.setText(value != null && !value.isEmpty() ? value : "---");
        }
    }

    private void setupToggle(View header, View content, ImageView arrow) {
        content.setVisibility(View.VISIBLE);
        arrow.setImageResource(R.drawable.ic_arrow_up);

        header.setOnClickListener(v -> {
            if (content.getVisibility() == View.VISIBLE) {
                content.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.ic_arrow_down);
            } else {
                content.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.ic_arrow_up);
            }
        });
    }
}

