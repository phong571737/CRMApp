package com.example.crmmobile.IndividualDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;

public class ThongTinKhacFragment extends Fragment {
    private AutoCompleteTextView actQuanHuyen, actTinhTP, actGiaoCho;
    private EditText edtQuocGia, edtDiaChi, edtGhiChu, edtMota;

    private CaNhan caNhan; // để populate khi edit
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thong_tin_khac, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // --- Khối 1: Thông tin địa chỉ ---
        TextView thongTinDiaChi = view.findViewById(R.id.thongtindiachi);
        LinearLayout layoutDiaChi = view.findViewById(R.id.layoutThongTinDiaChiChiTiet);
        setupToggle(thongTinDiaChi, layoutDiaChi);

        // --- Khối 2: Thông tin mô tả ---
        TextView thongTinMoTa = view.findViewById(R.id.thongtinmota);
        LinearLayout layoutMoTa = view.findViewById(R.id.layoutThongTinMoTaChiTiet);
        setupToggle(thongTinMoTa, layoutMoTa);

        // --- Khối 3: Thông tin quản lý ---
        TextView thongTinQuanLy = view.findViewById(R.id.thongtinquanly);
        LinearLayout layoutQuanLy = view.findViewById(R.id.layoutThongTinQuanLyChiTiet);
        setupToggle(thongTinQuanLy, layoutQuanLy);

        // --- Bind các view ---
        actQuanHuyen = view.findViewById(R.id.actQuanHuyen);
        actTinhTP = view.findViewById(R.id.actTinhTP);
        actGiaoCho = view.findViewById(R.id.actgiaocho);

        edtQuocGia = view.findViewById(R.id.edtquocgia);
        edtDiaChi = view.findViewById(R.id.edtdiachi);
        edtGhiChu = view.findViewById(R.id.edtghichu);
        edtMota = view.findViewById(R.id.edtmota);

        // --- Adapter Quận/Huyện ---
        ArrayAdapter<String> adapterQuanHuyen = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{
                        "Quận 1", "Quận 3", "Quận 4", "Quận 5", "Quận 6", "Quận 7", "Quận 8",
                        "Quận 10", "Quận 11", "Quận 12", "Quận Bình Tân", "Quận Bình Thạnh",
                        "Quận Gò Vấp", "Quận Phú Nhuận", "Quận Tân Bình", "Quận Tân Phú",
                        "Thành phố Thủ Đức",
                        "Huyện Bình Chánh", "Huyện Cần Giờ", "Huyện Củ Chi",
                        "Huyện Hóc Môn", "Huyện Nhà Bè"
                }
        );
        actQuanHuyen.setAdapter(adapterQuanHuyen);
        actQuanHuyen.setFocusable(false);
        actQuanHuyen.setClickable(true);
        actQuanHuyen.setOnClickListener(v -> actQuanHuyen.showDropDown());

        // --- Adapter Tỉnh/Thành ---
        String[] dsTinhThanh = {
                "An Giang","Bà Rịa - Vũng Tàu","Bắc Giang","Bắc Kạn","Bạc Liêu","Bắc Ninh",
                "Bến Tre","Bình Định","Bình Dương","Bình Phước","Bình Thuận","Cà Mau",
                "Cần Thơ","Cao Bằng","Đà Nẵng","Đắk Lắk","Đắk Nông","Điện Biên","Đồng Nai",
                "Đồng Tháp","Gia Lai","Hà Giang","Hà Nam","Hà Nội","Hà Tĩnh","Hải Dương",
                "Hải Phòng","Hậu Giang","Hòa Bình","Hưng Yên","Khánh Hòa","Kiên Giang","Kon Tum",
                "Lai Châu","Lâm Đồng","Lạng Sơn","Lào Cai","Long An","Nam Định","Nghệ An",
                "Ninh Bình","Ninh Thuận","Phú Thọ","Phú Yên","Quảng Bình","Quảng Nam","Quảng Ngãi",
                "Quảng Ninh","Quảng Trị","Sóc Trăng","Sơn La","Tây Ninh","Thái Bình","Thái Nguyên",
                "Thanh Hóa","Thừa Thiên - Huế","Tiền Giang","TP. Hồ Chí Minh","Trà Vinh",
                "Tuyên Quang","Vĩnh Long","Vĩnh Phúc","Yên Bái"
        };
        ArrayAdapter<String> adapterTinhTP = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                dsTinhThanh
        );
        actTinhTP.setAdapter(adapterTinhTP);
        actTinhTP.setFocusable(false);
        actTinhTP.setClickable(true);
        actTinhTP.setOnClickListener(v -> actTinhTP.showDropDown());

        // --- Adapter Giao cho ---
        ArrayAdapter<String> adapterGiaoCho = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Phan Thị Tường Vi", "Nguyễn Hữu Thiện", "Lê Thị Ánh Xuân", "Huỳnh Văn Tuấn Phong", "Nguyễn Đức Thành"}
        );
        actGiaoCho.setAdapter(adapterGiaoCho);
        actGiaoCho.setFocusable(false);
        actGiaoCho.setClickable(true);
        actGiaoCho.setOnClickListener(v -> actGiaoCho.showDropDown());

        // Nếu đã có CaNhan được set -> populate
        if (caNhan != null) {
            populateFromCaNhan();
        }
    }

    private void populateFromCaNhan() {
        if (caNhan == null) return;
        actQuanHuyen.setText(nullToEmpty(caNhan.getQuanHuyen()));
        actTinhTP.setText(nullToEmpty(caNhan.getTinhTP()));
        actGiaoCho.setText(nullToEmpty(caNhan.getGiaoCho()));
        edtQuocGia.setText(nullToEmpty(caNhan.getQuocGia()));
        edtDiaChi.setText(nullToEmpty(caNhan.getDiaChi()));
        edtGhiChu.setText(nullToEmpty(caNhan.getGhiChu()));
        edtMota.setText(nullToEmpty(caNhan.getMoTa()));
    }

    private String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    public void setCaNhan(CaNhan cn) {
        this.caNhan = cn;
        if (getView() != null) {
            populateFromCaNhan();
        }
    }


    /**
     * Hàm gắn sự kiện mở rộng / thu gọn cho 1 tiêu đề và layout chi tiết.
     */
    private void setupToggle(TextView titleView, LinearLayout detailLayout) {
        // Mặc định hiển thị chi tiết + icon mũi tên lên
        detailLayout.setVisibility(View.VISIBLE);
        titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);

        titleView.setOnClickListener(v -> {
            boolean isVisible = detailLayout.getVisibility() == View.VISIBLE;

            // Ẩn hoặc hiện phần chi tiết
            detailLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);

            // Cập nhật icon mũi tên
            titleView.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    isVisible ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down,
                    0
            );
        });
    }

    public String getDiaChi() { return edtDiaChi.getText().toString(); }
    public String getQuanHuyen() { return actQuanHuyen.getText().toString(); }
    public String getTinhTP() { return actTinhTP.getText().toString(); }
    public String getGiaoCho() { return actGiaoCho.getText().toString(); }
    public String getQuocGia() { return edtQuocGia.getText().toString(); }
    public String getGhiChu() { return edtGhiChu.getText().toString(); }
    public String getMoTa() { return edtMota.getText().toString(); }

}
