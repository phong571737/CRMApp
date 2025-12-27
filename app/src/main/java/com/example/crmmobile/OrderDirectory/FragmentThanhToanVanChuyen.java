package com.example.crmmobile.OrderDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;
import com.example.crmmobile.DataBase.DonHangRepository;

import org.json.JSONObject;

public class FragmentThanhToanVanChuyen extends Fragment {

    private DonHangRepository donHangRepo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_thanh_toan_van_chuyen, container, false);
        donHangRepo = new DonHangRepository(requireContext());

        bind(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        View v = getView();
        if (v != null) bind(v);
    }

    private void bind(View v) {
        int orderId = -1;
        if (getActivity() instanceof OrderDetailActivity) {
            orderId = ((OrderDetailActivity) getActivity()).getOrderId();
        }
        if (orderId <= 0) return;

        DonHang dh = donHangRepo.getById(orderId);
        if (dh == null) return;

        JSONObject extra = safeJson(dh.getExtraJson());

        set(v, R.id.tvGiaoHang, extra.optString("giaoHang", "—"));
        set(v, R.id.tvPhuongThuc, extra.optString("paymentMethod", "—"));
        set(v, R.id.tvTinhTrang, extra.optString("paymentStatus", "—"));
        set(v, R.id.tvXuatHoaDon, extra.optString("xuatHoaDon", "—"));
        set(v, R.id.tvHoaDonDinhKy, extra.optString("hoaDonDinhKy", "—"));

        set(v, R.id.tvTenPhapLy, extra.optString("tenPhapLy", "—"));
        set(v, R.id.tvMaSoThue, extra.optString("maSoThue", "—"));
        set(v, R.id.tvEmailHoaDon, extra.optString("emailHoaDon", "—"));
        set(v, R.id.tvDiaChiHoaDon, extra.optString("diaChiHoaDon", "—"));

        set(v, R.id.tvThoiGianBatDau, extra.optString("startDate", "—"));
        set(v, R.id.tvThoiGianKetThuc, extra.optString("endDate", "—"));
        set(v, R.id.tvChuKyTao, extra.optString("chuKyTao", "—"));
        set(v, R.id.tvPhuongThucTT, extra.optString("paymentMethod", "—"));
        set(v, R.id.tvHanThanhToan, extra.optString("paymentDue", "—"));
        set(v, R.id.tvTinhTrangHoaDon, extra.optString("tinhTrangHoaDon", "—"));
    }

    private void set(View root, int id, String value) {
        TextView tv = root.findViewById(id);
        if (tv != null) tv.setText(value == null || value.trim().isEmpty() ? "—" : value);
    }

    private JSONObject safeJson(String s) {
        try {
            if (s == null || s.trim().isEmpty()) return new JSONObject();
            return new JSONObject(s);
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}
