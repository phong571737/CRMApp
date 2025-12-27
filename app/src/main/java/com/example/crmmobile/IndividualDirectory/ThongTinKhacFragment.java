package com.example.crmmobile.IndividualDirectory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class ThongTinKhacFragment extends Fragment {
    private AutoCompleteTextView actQuanHuyen, actTinhTP, actGiaoCho;
    private EditText edtQuocGia, edtDiaChi, edtGhiChu, edtMota;

    private CaNhan caNhan; // để populate khi edit
    private ViewModelCanhan viewModelCanhan;
    private int selectedGiaoChoId = 0; // ID của nhân viên được chọn
    private List<com.example.crmmobile.LeadDirectory.Nhanvien> nhanVienList = new ArrayList<>();
    public interface StringUpdater{
        void update(String s);
    }
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
        viewModelCanhan = new ViewModelProvider(requireActivity()).get(ViewModelCanhan.class);
        initViews(view);
        InitAdapter();

        //thêm dữ liệu vào EditText(edit mode)
        bindViewModeltoEditext(viewModelCanhan.quanHuyen, actQuanHuyen);
        bindViewModeltoEditext(viewModelCanhan.tinhTP, actTinhTP);
        bindViewModeltoEditext(viewModelCanhan.giaoCho, actGiaoCho);
        bindViewModeltoEditext(viewModelCanhan.quocGia, edtQuocGia);
        bindViewModeltoEditext(viewModelCanhan.diachi, edtDiaChi);
        bindViewModeltoEditext(viewModelCanhan.ghiChu, edtGhiChu);
        bindViewModeltoEditext(viewModelCanhan.moTa, edtMota);

        //thêm dữ liệu vào viewModel
        bindEditTexttoViewModel(actQuanHuyen, s -> viewModelCanhan.quanHuyen.setValue(s));
        bindEditTexttoViewModel(actTinhTP, s -> viewModelCanhan.tinhTP.setValue(s));
        bindEditTexttoViewModel(actGiaoCho, s -> viewModelCanhan.giaoCho.setValue(s));
        bindEditTexttoViewModel(edtQuocGia, s -> viewModelCanhan.quocGia.setValue(s));
        bindEditTexttoViewModel(edtDiaChi, s -> viewModelCanhan.diachi.setValue(s));
        bindEditTexttoViewModel(edtGhiChu, s -> viewModelCanhan.ghiChu.setValue(s));
        bindEditTexttoViewModel(edtMota, s -> viewModelCanhan.moTa.setValue(s));
        
        // Bind giaoChoID vào viewModel khi edit mode
        viewModelCanhan.giaoChoID.observe(getViewLifecycleOwner(), giaoChoID -> {
            if (giaoChoID != null && giaoChoID > 0) {
                selectedGiaoChoId = giaoChoID;
            }
        });
    }

    private void bindViewModeltoEditext(MutableLiveData<String> title, EditText editText) {
        title.observe(getViewLifecycleOwner(), v->{
            if (v == null) return;
            String curr = editText.getText() != null ? editText.getText().toString() : "";
            if (!v.equals(curr)){
                if(editText instanceof MaterialAutoCompleteTextView){
                    ((MaterialAutoCompleteTextView)editText).setText(v,false);
                }else{
                    if (!editText.hasFocus()){
                        editText.setText(v);
                    }
                }
            }
        });
    }

    private void bindEditTexttoViewModel(EditText editText, StringUpdater updater) {
        editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    updater.update(s.toString());
                }
            }
        );
    }

    private void InitAdapter() {
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
        // Load danh sách nhân viên từ database với ID
        NhanVienRepository nhanVienRepository = new NhanVienRepository(requireContext());
        nhanVienRepository.AddNhanVien(); // Đảm bảo có dữ liệu nhân viên
        nhanVienList = nhanVienRepository.getAllNhanVien();
        
        List<String> nhanVienNames = new ArrayList<>();
        for (Nhanvien nv : nhanVienList) {
            nhanVienNames.add(nv.getHoten());
        }
        
        ArrayAdapter<String> adapterGiaoCho = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                nhanVienNames
        );
        actGiaoCho.setAdapter(adapterGiaoCho);
        actGiaoCho.setFocusable(false);
        actGiaoCho.setClickable(true);
        actGiaoCho.setOnClickListener(v -> actGiaoCho.showDropDown());
        
        // Lưu ID khi chọn nhân viên
        actGiaoCho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < nhanVienList.size()) {
                    selectedGiaoChoId = nhanVienList.get(position).getId();
                    viewModelCanhan.giaoChoID.setValue(selectedGiaoChoId);
                } else {
                    selectedGiaoChoId = 0;
                    viewModelCanhan.giaoChoID.setValue(0);
                }
            }
        });
    }

    private void initViews(View view) {
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
    
    public int getGiaoChoID() { 
        if (viewModelCanhan.giaoChoID.getValue() != null) {
            return viewModelCanhan.giaoChoID.getValue();
        }
        return selectedGiaoChoId; 
    }

}
