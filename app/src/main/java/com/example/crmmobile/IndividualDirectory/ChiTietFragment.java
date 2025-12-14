package com.example.crmmobile.IndividualDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;
/**
 * CHỈNH SỬA CLASS ChiTietFragment
 */
public class ChiTietFragment extends Fragment {

    private CaNhan caNhan;
    private TextView tvFillHoVaTenDem, tvFillTen, tvFillNgaySinh, tvFillCongTy,
            tvFillDienThoai, tvFillEmail,
            tvFillDiaChi, tvFillQuanHuyen, tvFillTinhTP,
            tvFillMoTa, tvFillGhiChu,
            tvFillGiaoCho, tvFillNguoiPhuTrach, tvFillNgayTao, tvFillNgaySua;

    public ChiTietFragment() {
        // Bắt buộc constructor rỗng
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Gắn layout XML của tab Chi tiết
        return inflater.inflate(R.layout.fragment_tab_chitiet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // === THÔNG TIN CHUNG ===
        if (getArguments() != null) {
            caNhan = (CaNhan) getArguments().getSerializable("CANHAN_DATA");
        }

        // 2. Ánh xạ các View
        initViews(view);

        // 3. Setup các nút đóng mở (Toggle)
        setupToggles(view);

        // 4. Hiển thị dữ liệu
        fillData();
    }

    private void initViews(View view) {
        // Thông tin chung
        tvFillHoVaTenDem = view.findViewById(R.id.fillhovatendem);
        tvFillTen = view.findViewById(R.id.fillten);
        tvFillNgaySinh = view.findViewById(R.id.fillngaysinh);
        tvFillCongTy = view.findViewById(R.id.fillcongty);
        tvFillDienThoai = view.findViewById(R.id.filldienthoai);
        tvFillEmail = view.findViewById(R.id.fillemail);

        // Thông tin địa chỉ
        tvFillDiaChi = view.findViewById(R.id.filldiachi);
        tvFillQuanHuyen = view.findViewById(R.id.fillquanhuyen);
        tvFillTinhTP = view.findViewById(R.id.filltinhtp);

        // Thông tin mô tả
        tvFillMoTa = view.findViewById(R.id.fillmota);
        tvFillGhiChu = view.findViewById(R.id.fillghichu);

        // Thông tin quản lý (nếu có trong layout XML)
        tvFillGiaoCho = view.findViewById(R.id.fillgiaocho);
        tvFillNguoiPhuTrach = view.findViewById(R.id.fillnguoiphutrach);

        // Thông tin hệ thống
        tvFillNgayTao = view.findViewById(R.id.fillngaytao);
        tvFillNgaySua = view.findViewById(R.id.fillngaysua);
    }

    private void fillData() {
        if (caNhan == null) return;

        // Điền dữ liệu, dùng hàm checkNull để tránh lỗi nếu dữ liệu null
        setTextOrEmpty(tvFillHoVaTenDem, caNhan.getHoVaTen()); // Tạm dùng họ tên cho trường này
        setTextOrEmpty(tvFillTen, caNhan.getTen());
        setTextOrEmpty(tvFillNgaySinh, caNhan.getNgaySinh());
        setTextOrEmpty(tvFillCongTy, caNhan.getCongTy());
        setTextOrEmpty(tvFillDienThoai, caNhan.getDiDong());
        setTextOrEmpty(tvFillEmail, caNhan.getEmail());

        setTextOrEmpty(tvFillDiaChi, caNhan.getDiaChi());
        setTextOrEmpty(tvFillQuanHuyen, caNhan.getQuanHuyen());
        setTextOrEmpty(tvFillTinhTP, caNhan.getTinhTP());

        setTextOrEmpty(tvFillMoTa, caNhan.getMoTa());
        setTextOrEmpty(tvFillGhiChu, caNhan.getGhiChu());

        setTextOrEmpty(tvFillGiaoCho, caNhan.getGiaoCho());
        setTextOrEmpty(tvFillNguoiPhuTrach, caNhan.getGiaoCho()); // Giả sử người phụ trách giống giao cho

        setTextOrEmpty(tvFillNgayTao, caNhan.getNgayTao());
        setTextOrEmpty(tvFillNgaySua, caNhan.getNgaySua()); // Cần thêm field này vào model CaNhan nếu muốn
    }

    private void setupToggles(View view) {

        setupToggle(view.findViewById(R.id.thongtinchung), view.findViewById(R.id.layoutThongTinChung)); // Cần thêm ID vào XML
        setupToggle(view.findViewById(R.id.thongtindiachi), view.findViewById(R.id.layoutThongTinDiaChi)); // Cần thêm ID vào XML
        setupToggle(view.findViewById(R.id.thongtinmota), view.findViewById(R.id.layoutThongTinMoTa)); // Cần thêm ID vào XML

    }

    private void setupToggle(TextView titleView, LinearLayout detailLayout) {
        if (titleView == null || detailLayout == null) return;

        // Ban đầu hiển thị chi tiết và mũi tên xuống
        detailLayout.setVisibility(View.VISIBLE);
        titleView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);

        // Khi click vào tiêu đề
        titleView.setOnClickListener(v -> {
            boolean isVisible = detailLayout.getVisibility() == View.VISIBLE;

            // Ẩn/hiện layout con
            detailLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);

            // Đổi mũi tên lên/xuống
            titleView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0, 0,
                    isVisible ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down,
                    0
            );
        });
    }

    private void setTextOrEmpty(TextView textView, String text) {
        if (textView != null) {
            textView.setText(text != null ? text : "---");
        }
    }
}
