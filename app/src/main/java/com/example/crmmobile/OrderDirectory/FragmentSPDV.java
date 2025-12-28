package com.example.crmmobile.OrderDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.R;
import com.example.crmmobile.DataBase.DonHangRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentSPDV extends Fragment {

    private DonHangRepository donHangRepo;

    private RecyclerView rv;
    private TextView tvEmpty;

    private TextView tvTamTinh, tvThue, tvTongCong; // nếu bạn có các id tổng kết
    private final List<ProductLine> data = new ArrayList<>();
    private ProductLineAdapter adapter;

    private final NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_spdv, container, false);
        setupTopInfoRows(view);


        donHangRepo = new DonHangRepository(requireContext());

        rv = view.findViewById(R.id.rvOrderProducts);
        tvEmpty = view.findViewById(R.id.tvEmptyProducts);

        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ProductLineAdapter(data, this::updateEmptyAndTotals); // ✅
        rv.setAdapter(adapter);


        bind();

        return view;
    }
    private void updateEmptyAndTotals() {
        // 1) Ẩn/hiện empty text
        if (tvEmpty != null && rv != null) {
            boolean empty = (data == null || data.isEmpty());
            tvEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
            rv.setVisibility(empty ? View.GONE : View.VISIBLE);
        }

        // 2) Nếu bạn có phần "tổng kết" (tạm tính/thuế/tổng cộng) thì cập nhật ở đây
        // Ví dụ:
        // long total = 0;
        // for (ProductLine p : data) total += p.getThanhTien();
        // tvTongCong.setText(nf.format(total) + " đ");
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

        data.clear();
        data.addAll(parseProductsFromDonHang(dh));
        adapter.notifyDataSetChanged();

        if (data.isEmpty()) {
            rv.setVisibility(View.GONE);
            if (tvEmpty != null) tvEmpty.setVisibility(View.VISIBLE);
        } else {
            rv.setVisibility(View.VISIBLE);
            if (tvEmpty != null) tvEmpty.setVisibility(View.GONE);
        }

        // Nếu bạn có tổng kết ở fragment_spdv thì tính tại đây
        // long subtotal = ...
        // tvTamTinh.setText(...)
    }

    private List<ProductLine> parseProductsFromDonHang(DonHang dh) {
        List<ProductLine> list = new ArrayList<>();
        String sp = dh.getSanPham();

        // ✅ JSON array case
        if (sp != null) {
            String s = sp.trim();
            if (s.startsWith("[") && s.endsWith("]")) {
                try {
                    JSONArray arr = new JSONArray(s);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject o = arr.getJSONObject(i);
                        String name = o.optString("name", "");
                        String note = o.optString("note", "");
                        int qty = o.optInt("qty", 1);
                        long price = o.optLong("price", 0L);
                        if (!name.isEmpty()) {
                            list.add(new ProductLine(name, note, qty, price));
                        }
                    }
                    return list;
                } catch (Exception ignored) {}
            }
        }

        // ✅ fallback: đơn chỉ có 1 SP
        if (sp != null && !sp.trim().isEmpty()) {
            list.add(new ProductLine(
                    sp.trim(),
                    "",
                    Math.max(1, dh.getSoLuong()),
                    dh.getDonGia()
            ));
        }
        return list;
    }
    private void setupTopInfoRows(View root) {
        View rowCurrency = root.findViewById(R.id.rowCurrency);
        if (rowCurrency != null) {
            TextView lb = rowCurrency.findViewById(R.id.tvLabel);
            TextView vl = rowCurrency.findViewById(R.id.tvValue);
            if (lb != null) lb.setText("Tiền tệ");
            if (vl != null) vl.setText("VND");
        }

        View rowTaxType = root.findViewById(R.id.rowTaxType);
        if (rowTaxType != null) {
            TextView lb = rowTaxType.findViewById(R.id.tvLabel);
            TextView vl = rowTaxType.findViewById(R.id.tvValue);
            if (lb != null) lb.setText("Loại thuế");
            if (vl != null) vl.setText("Trong nước");
        }
    }

}
