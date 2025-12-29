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

    private View layoutTongKet;
    private TextView tvTamTinh, tvGiamGiaChung, tvTongGiam, tvTruocThue, tvThue, tvTongThue, tvTongCong;

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
        bindTongKetViews(view);

        donHangRepo = new DonHangRepository(requireContext());

        rv = view.findViewById(R.id.rvOrderProducts);
        tvEmpty = view.findViewById(R.id.tvEmptyProducts);

        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ProductLineAdapter(data);
        rv.setAdapter(adapter);

        bind();
        return view;
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

        boolean empty = data.isEmpty();
        if (rv != null) rv.setVisibility(empty ? View.GONE : View.VISIBLE);
        if (tvEmpty != null) tvEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);

        updateTotals(dh);
    }

    private void bindTongKetViews(View root) {
        layoutTongKet = root.findViewById(R.id.layoutTongKet);
        if (layoutTongKet == null) return;

        tvTamTinh      = layoutTongKet.findViewById(R.id.tvTamTinh);
        tvGiamGiaChung = layoutTongKet.findViewById(R.id.tvGiamGiaChung);
        tvTongGiam     = layoutTongKet.findViewById(R.id.tvTongGiam);
        tvTruocThue    = layoutTongKet.findViewById(R.id.tvTruocThue);
        tvThue         = layoutTongKet.findViewById(R.id.tvThue);
        tvTongThue     = layoutTongKet.findViewById(R.id.tvTongThue);
        tvTongCong     = layoutTongKet.findViewById(R.id.tvTongCong);

        View icGiam = layoutTongKet.findViewById(R.id.iconTooltipGiamGia);
        if (icGiam != null) icGiam.setVisibility(View.GONE);
        View icThue = layoutTongKet.findViewById(R.id.iconTooltipThue);
        if (icThue != null) icThue.setVisibility(View.GONE);
    }

    private void updateTotals(DonHang dh) {
        if (layoutTongKet == null || dh == null) return;

        long subtotal = 0L;
        for (ProductLine p : data) {
            if (p == null) continue;
            subtotal += p.getFinalAmount();


        }

        long globalDiscount = 0L;
        long beforeTax      = subtotal;
        long taxAmount      = 0L;
        long grandTotal     = (long) dh.getTongTien();

        // ưu tiên breakdown đã lưu từ SOProductsFragment (EXTRA_JSON)
        try {
            String extraStr = dh.getExtraJson();
            if (extraStr != null && !extraStr.trim().isEmpty()) {
                JSONObject extra = new JSONObject(extraStr);

                subtotal       = optLong(extra, "subtotal", subtotal);
                globalDiscount = optLong(extra, "globalDiscount", 0L);

                if (globalDiscount > subtotal) globalDiscount = subtotal;
                if (globalDiscount < 0) globalDiscount = 0;

                beforeTax  = optLong(extra, "beforeTax", Math.max(0L, subtotal - globalDiscount));
                taxAmount  = optLong(extra, "taxAmount", 0L);
                grandTotal = optLong(extra, "grandTotal", beforeTax + taxAmount);
            }
        } catch (Exception ignored) {}

        if (globalDiscount > subtotal) globalDiscount = subtotal;
        if (globalDiscount < 0) globalDiscount = 0;

        beforeTax  = Math.max(0L, subtotal - globalDiscount);
        taxAmount  = Math.max(0L, taxAmount);
        grandTotal = Math.max(0L, grandTotal);

        if (tvTamTinh != null)      tvTamTinh.setText(nf.format(subtotal) + " đ");
        if (tvGiamGiaChung != null) tvGiamGiaChung.setText(nf.format(globalDiscount) + " đ");
        if (tvTongGiam != null)     tvTongGiam.setText(nf.format(globalDiscount) + " đ");
        if (tvTruocThue != null)    tvTruocThue.setText(nf.format(beforeTax) + " đ");
        if (tvThue != null)         tvThue.setText(nf.format(taxAmount) + " đ");
        if (tvTongThue != null)     tvTongThue.setText(nf.format(taxAmount) + " đ");
        if (tvTongCong != null)     tvTongCong.setText(nf.format(grandTotal) + " đ");
    }

    private long optLong(JSONObject o, String k, long def) {
        try {
            if (!o.has(k) || o.isNull(k)) return def;
            Object v = o.get(k);
            if (v instanceof Number) return ((Number) v).longValue();
            String s = String.valueOf(v).trim();
            if (s.isEmpty()) return def;
            return Long.parseLong(s);
        } catch (Exception e) { return def; }
    }

    private List<ProductLine> parseProductsFromDonHang(DonHang dh) {
        List<ProductLine> list = new ArrayList<>();
        String sp = dh.getSanPham();

        // JSON array
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
                        long discount = o.optLong("discountAmount", 0L);

                        if (!name.isEmpty()) {
                            ProductLine pl = new ProductLine(name, note, qty, price);
                            pl.setDiscountAmount(discount);
                            list.add(pl);
                        }
                    }
                    return list;
                } catch (Exception ignored) {}
            }
        }

        //đơn chỉ có 1 SP
        if (sp != null && !sp.trim().isEmpty()) {
            ProductLine pl = new ProductLine(
                    sp.trim(),
                    "",
                    Math.max(1, dh.getSoLuong()),
                    (long) dh.getDonGia()
            );
            list.add(pl);
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

