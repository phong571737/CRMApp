    package com.example.crmmobile.OrganizationDirectory;

    import android.app.DatePickerDialog;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.AutoCompleteTextView;
    import android.widget.ImageView;
    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import com.example.crmmobile.DataBase.NhanVienRepository;
    import com.example.crmmobile.LeadDirectory.Nhanvien;
    import com.example.crmmobile.R;
    import com.google.android.material.textfield.TextInputEditText;
    import com.google.android.material.textfield.TextInputLayout;

    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.List;
    import java.util.Locale;

    public class TaoCongTyThongTinKhacFragment extends Fragment {

        // View nhập liệu
        private TextInputEditText edtDiaChi, edtQuanHuyen, edtTinhTP, edtQuocGia;
        private TextInputEditText edtSoDon, edtDoanhThu, edtNgayDau, edtNgayCuoi;
        private AutoCompleteTextView actTinhTrangMua, actGiaoCho;

        // View Toggle
        private View layoutDiaChiContent, layoutMuaHangContent, layoutQuanLyContent;
        private ImageView btnToggleDiaChi, btnToggleMuaHang, btnToggleQuanLy;

        private NhanVienRepository nhanVienRepository;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_taocongty_thongtinkhac, container, false);

            // --- 1. ÁNH XẠ VIEW NHẬP LIỆU  ---
            edtDiaChi = view.findViewById(R.id.edtDiaChi);
            edtQuanHuyen = view.findViewById(R.id.edtQuanHuyen);
            edtTinhTP = view.findViewById(R.id.edtTinhTP);
            edtQuocGia = view.findViewById(R.id.edtQuocGia);
            edtSoDon = view.findViewById(R.id.edtSoDonHangDaMua);
            edtDoanhThu = view.findViewById(R.id.edtTongDoanhThu);
            edtNgayDau = view.findViewById(R.id.edtNgayMuaHangDauTien);
            edtNgayCuoi = view.findViewById(R.id.edtNgayMuaHangGanNhat);
            actTinhTrangMua = view.findViewById(R.id.actTinhTrangMuaHang);
            actGiaoCho = view.findViewById(R.id.actGiaoCho);

            // --- 2. ÁNH XẠ VIEW TOGGLE ---
            layoutDiaChiContent = view.findViewById(R.id.layoutDiaChiContent);
            btnToggleDiaChi = view.findViewById(R.id.btnToggleDiaChi);

            layoutMuaHangContent = view.findViewById(R.id.layoutMuaHangContent);
            btnToggleMuaHang = view.findViewById(R.id.btnToggleMuaHang);

            layoutQuanLyContent = view.findViewById(R.id.layoutQuanLyContent);
            btnToggleQuanLy = view.findViewById(R.id.btnToggleQuanLy);

            // --- 3. THIẾT LẬP SỰ KIỆN ---

            // Setup Toggle
            setupToggle(btnToggleDiaChi, layoutDiaChiContent);
            setupToggle(btnToggleMuaHang, layoutMuaHangContent);
            setupToggle(btnToggleQuanLy, layoutQuanLyContent);
            // Setup DatePicker
            setupDatePicker(edtNgayDau);
            setupDatePicker(edtNgayCuoi);

            // Setup Dropdown
            String[] listTinhTrang = {"Tiềm năng", "Đã mua", "Khách hàng VIP", "Ngừng giao dịch"};
            ArrayAdapter<String> adapterTinhTrang = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, listTinhTrang);
            actTinhTrangMua.setAdapter(adapterTinhTrang);

            setupNhanVienDropdown();

            // --- 4. NHẬN DỮ LIỆU ---
            if (getArguments() != null && getArguments().containsKey("COMPANY_DATA")) {
                ToChuc data = (ToChuc) getArguments().getSerializable("COMPANY_DATA");
                setFormData(data);
            }

            return view;
        }

        private void setupToggle(ImageView btn, View contentLayout) {
            if (btn == null || contentLayout == null) return;

            btn.setOnClickListener(v -> {
                if (contentLayout.getVisibility() == View.VISIBLE) {
                    contentLayout.setVisibility(View.GONE);
                    btn.setImageResource(R.drawable.ic_arrow_down); // Nhớ đổi icon tương ứng
                } else {
                    contentLayout.setVisibility(View.VISIBLE);
                    btn.setImageResource(R.drawable.ic_arrow_up);
                }
            });
        }

        // (Các hàm setFormData, getDataFromForm, setupDatePicker, setupNhanVienDropdown)

        public void setFormData(ToChuc toChuc) {
            if (toChuc == null) return; // Bỏ check view null

            if(edtDiaChi != null) edtDiaChi.setText(toChuc.getAddress());
            if(edtQuanHuyen != null) edtQuanHuyen.setText(toChuc.getDistrict());
            if(edtTinhTP != null) edtTinhTP.setText(toChuc.getCity());
            if(edtQuocGia != null) edtQuocGia.setText(toChuc.getCountry());

            if(actTinhTrangMua != null) actTinhTrangMua.setText(toChuc.getBuyingStatus(), false);
            if(actGiaoCho != null) actGiaoCho.setText(toChuc.getAssignedTo(), false);

            if(edtSoDon != null) edtSoDon.setText(String.valueOf(toChuc.getOrderCount()));
            if(edtDoanhThu != null) edtDoanhThu.setText(String.format(Locale.getDefault(), "%.0f", toChuc.getTotalRevenue()));

            if(edtNgayDau != null) edtNgayDau.setText(toChuc.getFirstPurchaseDate());
            if(edtNgayCuoi != null) edtNgayCuoi.setText(toChuc.getLastPurchaseDate());
        }

        public void getDataFromForm(ToChuc toChuc) {
            if (getView() == null) return;
            toChuc.setAddress(edtDiaChi.getText().toString());
            toChuc.setDistrict(edtQuanHuyen.getText().toString());
            toChuc.setCity(edtTinhTP.getText().toString());
            toChuc.setCountry(edtQuocGia.getText().toString());
            toChuc.setBuyingStatus(actTinhTrangMua.getText().toString());
            toChuc.setAssignedTo(actGiaoCho.getText().toString());
            toChuc.setFirstPurchaseDate(edtNgayDau.getText().toString());
            toChuc.setLastPurchaseDate(edtNgayCuoi.getText().toString());
            try { toChuc.setOrderCount(Integer.parseInt(edtSoDon.getText().toString())); } catch (Exception e) { toChuc.setOrderCount(0); }
            try { toChuc.setTotalRevenue(Double.parseDouble(edtDoanhThu.getText().toString())); } catch (Exception e) { toChuc.setTotalRevenue(0); }
        }

        private void setupDatePicker(TextInputEditText editText) {
            editText.setFocusable(false);
            editText.setClickable(true);
            editText.setCursorVisible(false);
            editText.setOnClickListener(v -> {
                final Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(getContext(), (view, y, m, d) -> {
                    String date = String.format(Locale.getDefault(), "%02d/%02d/%d", d, m + 1, y);
                    editText.setText(date);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            });
        }

        private void setupNhanVienDropdown() {
            nhanVienRepository = new NhanVienRepository(getContext());
            nhanVienRepository.AddNhanVien();
            List<Nhanvien> listObj = nhanVienRepository.getAllNhanVien();
            List<String> listName = new ArrayList<>();
            if (listObj != null) for (Nhanvien nv : listObj) listName.add(nv.getHoten());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, listName);
            actGiaoCho.setAdapter(adapter);
        }
    }