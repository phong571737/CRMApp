package com.example.crmmobile.IndividualDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;
import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.DataBase.DonHangRepository;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.OrderDirectory.DonHang;
import com.example.crmmobile.OrderDirectory.OrderDetailActivity;
import com.example.crmmobile.OrganizationDirectory.ToChuc;

public class ThongTinChungFragment extends Fragment {

    private DonHangRepository donHangRepo;
    private CompanyRepository companyRepo;
    private CaNhanRepository caNhanRepo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin_chung, container, false);

        donHangRepo = new DonHangRepository(requireContext());
        companyRepo = new CompanyRepository(requireContext());
        caNhanRepo  = new CaNhanRepository(requireContext());

        bind(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        View v = getView();
        if (v != null) bind(v);
    }

    private void bind(View view) {
        int orderId = -1;
        if (getActivity() instanceof OrderDetailActivity) {
            orderId = ((OrderDetailActivity) getActivity()).getOrderId();
        }
        if (orderId <= 0) return;

        DonHang dh = donHangRepo.getById(orderId);
        if (dh == null) return;

        String companyName = "—";
        if (dh.getCongTyId() > 0) {
            ToChuc c = companyRepo.getCompanyByID(dh.getCongTyId());
            if (c != null && c.getCompanyName() != null) companyName = c.getCompanyName();
        } else if (dh.getMoTa() != null && dh.getMoTa().contains(" - ")) {
            companyName = dh.getMoTa().split(" - ")[0].trim();
        }

        String contactName = "—";
        String mobile = "—";
        if (dh.getNguoiLienHeId() > 0) {
            CaNhan cn = caNhanRepo.getById(dh.getNguoiLienHeId());
            if (cn != null) {
                if (cn.getHoVaTen() != null) contactName = cn.getHoVaTen();
                if (cn.getDiDong() != null) mobile = cn.getDiDong();
            }
        } else if (dh.getMoTa() != null && dh.getMoTa().contains(" - ")) {
            String[] parts = dh.getMoTa().split(" - ");
            if (parts.length >= 2) contactName = parts[1].trim();
        }

        setRow(view, R.id.row_ma_don_hang, "Mã đơn hàng", dh.getTenDonHang());
        setRow(view, R.id.row_tieu_de, "Tiêu đề", dh.getTenDonHang());
        setRow(view, R.id.row_cong_ty, "Công ty", companyName);
        setRow(view, R.id.row_nguoi_lien_he, "Người liên hệ", contactName);
        setRow(view, R.id.row_di_dong, "Di động", mobile);
        setRow(view, R.id.row_ngay_dat_hang, "Ngày đặt hàng", dh.getNgayDatHang());

        View rowTinhTrang = view.findViewById(R.id.row_tinh_trang);
        ((TextView) rowTinhTrang.findViewById(R.id.tvLabel)).setText("Tình trạng");
        ((TextView) rowTinhTrang.findViewById(R.id.tvValue)).setText(
                dh.getTinhTrang() == null || dh.getTinhTrang().trim().isEmpty() ? "—" : dh.getTinhTrang()
        );
    }

    private void setRow(View parent, int rowId, String label, String value) {
        View row = parent.findViewById(rowId);
        ((TextView) row.findViewById(R.id.tvLabel)).setText(label);
        ((TextView) row.findViewById(R.id.tvValue)).setText(
                value == null || value.trim().isEmpty() ? "—" : value
        );
    }
}
