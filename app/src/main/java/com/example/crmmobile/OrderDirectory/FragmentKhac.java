package com.example.crmmobile.OrderDirectory;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;
import com.example.crmmobile.DataBase.DonHangRepository;
import com.example.crmmobile.DataBase.NhanVienRepository;

import org.json.JSONObject;

public class FragmentKhac extends Fragment {

    private View root;

    private DonHangRepository donHangRepo;
    private NhanVienRepository nhanVienRepo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_khac, container, false);

        donHangRepo  = new DonHangRepository(requireContext());
        nhanVienRepo = new NhanVienRepository(requireContext());

        bindExpandableText(findTv(R.id.tvDieuKhoan), findTv(R.id.tvXemThemDieuKhoan), 3);
        bindExpandableText(findTv(R.id.tvThongTinMoTa), findTv(R.id.tvXemThemMoTa), 3);

        bind();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        bind();
    }

    private void bind() {
        int orderId = -1;
        if (getActivity() instanceof OrderDetailActivity) {
            orderId = ((OrderDetailActivity) getActivity()).getOrderId();
        }
        if (orderId <= 0) return;

        DonHang dh = donHangRepo.getById(orderId);
        if (dh == null) return;

        JSONObject extra = safeJson(dh.getExtraJson());

        setText(R.id.tvCoHoi, extra.optString("coHoi", "—"));
        setText(R.id.tvBaoGia, extra.optString("baoGia", "—"));

        // Giao cho = người phụ trách
        String giaoCho = "—";
        if (dh.getGiaoChoId() > 0) {
            String name = nhanVienRepo.getNameByID(dh.getGiaoChoId());
            if (name != null && !name.trim().isEmpty()) giaoCho = name;
        }
        setText(R.id.tvGiaoCho, giaoCho);

        setText(R.id.tvNgayHen, extra.optString("ngayHen", "—"));
        setText(R.id.tvNgayTao, extra.optString("ngayTao", "—"));
        setText(R.id.tvNgaySua, extra.optString("ngaySua", "—"));

        // Điều khoản / mô tả: ưu tiên extraJson, fallback dùng dh.getMoTa()
        String dk = extra.optString("dieuKhoan", "");
        if (dk.isEmpty()) dk = "—";
        setText(R.id.tvDieuKhoan, dk);

        String mota = extra.optString("moTa", "");
        if (mota.isEmpty()) mota = (dh.getMoTa() == null || dh.getMoTa().trim().isEmpty()) ? "—" : dh.getMoTa();
        setText(R.id.tvThongTinMoTa, mota);
    }

    private JSONObject safeJson(String s) {
        try {
            if (s == null || s.trim().isEmpty()) return new JSONObject();
            return new JSONObject(s);
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    private TextView findTv(int id) {
        if (root == null) return null;
        View v = root.findViewById(id);
        return (v instanceof TextView) ? (TextView) v : null;
    }

    private void setText(int id, String value) {
        TextView tv = findTv(id);
        if (tv != null) tv.setText(value == null || value.trim().isEmpty() ? "—" : value);
    }

    private void bindExpandableText(@Nullable TextView contentTv,
                                    @Nullable TextView actionTv,
                                    int collapsedLines) {
        if (contentTv == null || actionTv == null) return;

        contentTv.setMaxLines(collapsedLines);
        contentTv.setEllipsize(TextUtils.TruncateAt.END);
        actionTv.setText("Xem thêm");

        actionTv.setOnClickListener(v -> {
            boolean collapsed = contentTv.getMaxLines() == collapsedLines;
            if (collapsed) {
                contentTv.setMaxLines(Integer.MAX_VALUE);
                contentTv.setEllipsize(null);
                actionTv.setText("Thu gọn");
            } else {
                contentTv.setMaxLines(collapsedLines);
                contentTv.setEllipsize(TextUtils.TruncateAt.END);
                actionTv.setText("Xem thêm");
            }
        });
    }
}
