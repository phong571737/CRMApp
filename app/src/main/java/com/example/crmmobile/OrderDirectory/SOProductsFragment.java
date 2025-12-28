package com.example.crmmobile.OrderDirectory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.R;
import com.example.crmmobile.OrderDirectory.ProductLine;
import com.example.crmmobile.OrderDirectory.ProductLineAdapter;
import com.example.crmmobile.OrderDirectory.EditGlobalDiscountBottomSheet;
import com.example.crmmobile.OrderDirectory.EditProductBottomSheet;
import com.example.crmmobile.OrderDirectory.ProductPickActivity;
import com.example.crmmobile.OrderDirectory.EditTaxBottomSheet;
import com.example.crmmobile.SanPhamDirectory.SanPham;

import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SOProductsFragment extends Fragment {

    private LinearLayout emptyState;
    private RecyclerView rvProducts;
    private View layoutTongKet;

    // Tổng kết
    private TextView tvTamTinh, tvGiamGiaChung, tvTongGiam, tvTruocThue, tvThue, tvTongThue, tvTongCong;

    // ===== Data =====
    private final List<ProductLine> data = new ArrayList<>();
    private ProductLineAdapter adapter;

    private ActivityResultLauncher<Intent> pickProductLauncher;

    // ===== Summary params =====
    private long globalDiscount = 0L;     // giảm giá chung (đ)
    private double taxRate = 0.0;         // ví dụ 0.1 = 10%

    private final NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ Adapter có callback: mỗi khi qty/price/xóa thay đổi -> updateVisibility() -> updateSummary()
        adapter = new ProductLineAdapter(data, this::updateVisibility);

        adapter.setOnItemClickListener(line -> {
            EditProductBottomSheet sheet = EditProductBottomSheet.newInstance(
                    line.getName(),
                    line.getPrice(),
                    line.getQty(),
                    line.getDiscountAmount()
            );

            sheet.setListener((newQty, discountAmount) -> {
                line.setQty(newQty);
                line.setDiscountAmount(discountAmount);

                if (adapter != null) adapter.notifyDataSetChanged();
                updateVisibility(); // sẽ updateSummary()
            });

            sheet.show(getParentFragmentManager(), "EditProductBottomSheet");
        });



        // ✅ Launcher chọn sản phẩm
        pickProductLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent intent = result.getData();

                        String name  = intent.getStringExtra(ProductPickActivity.EXTRA_NAME);
                        long   price = intent.getLongExtra(ProductPickActivity.EXTRA_PRICE, 0L);
                        String note  = intent.getStringExtra(ProductPickActivity.EXTRA_NOTE);

                        if (name != null && !name.trim().isEmpty()) {
                            ProductLine line = new ProductLine(
                                    name.trim(),
                                    note != null ? note : "",
                                    1,
                                    price
                            );

                            data.add(line);
                            if (adapter != null) adapter.notifyItemInserted(data.size() - 1);

                            // ✅ cập nhật từ trên xuống tổng cộng
                            updateVisibility();
                        }
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_so_products, container, false);

        emptyState    = v.findViewById(R.id.emptyState);
        rvProducts    = v.findViewById(R.id.rvProducts);
        layoutTongKet = v.findViewById(R.id.layoutTongKet);

        if (layoutTongKet != null) {
            tvTamTinh      = layoutTongKet.findViewById(R.id.tvTamTinh);
            tvGiamGiaChung = layoutTongKet.findViewById(R.id.tvGiamGiaChung);
            tvTongGiam     = layoutTongKet.findViewById(R.id.tvTongGiam);
            tvTruocThue    = layoutTongKet.findViewById(R.id.tvTruocThue);
            tvThue         = layoutTongKet.findViewById(R.id.tvThue);
            tvTongThue     = layoutTongKet.findViewById(R.id.tvTongThue);
            tvTongCong     = layoutTongKet.findViewById(R.id.tvTongCong);
        }

        // Setup RV
        if (rvProducts != null) {
            rvProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
            rvProducts.setAdapter(adapter);
        }

        // Nút thêm sản phẩm
        MaterialButton btnAdd = v.findViewById(R.id.btnAddProduct);
        if (btnAdd != null) {
            btnAdd.setOnClickListener(view -> {
                Intent i = new Intent(requireContext(), ProductPickActivity.class);
                pickProductLauncher.launch(i);
            });
        }

        // ==== Thuế ====
        ImageView iconThue = v.findViewById(R.id.iconTooltipThue);
        if (iconThue != null) {
            iconThue.setOnClickListener(view1 -> {
                long baseAmount = tinhTruocThue();     // trước thuế
                int vatDefault = (int) Math.round(taxRate * 100); // ví dụ 10

                EditTaxBottomSheet sheet =
                        EditTaxBottomSheet.newInstance(baseAmount, vatDefault);

                // ✅ nếu BottomSheet hỗ trợ callback, gọi setTaxPercent(percent)
                // sheet.setListener(percent -> setTaxPercent(percent));

                sheet.show(getParentFragmentManager(), "EditTax");
            });
        }

        ImageView iconGiamGia = v.findViewById(R.id.iconTooltipGiamGia);
        if (iconGiamGia != null) {
            iconGiamGia.setOnClickListener(clicked -> {
                long subtotal = tinhTamTinh(); // tổng sau giảm theo dòng (nếu có)

                EditGlobalDiscountBottomSheet sheet =
                        EditGlobalDiscountBottomSheet.newInstance(subtotal, globalDiscount);

                sheet.setListener((long discountAmount) -> {
                    globalDiscount = discountAmount;   // ✅ cập nhật giảm giá chung
                    updateVisibility();                // ✅ updateSummary()
                });

                sheet.show(getParentFragmentManager(), "EditGlobalDiscount");
            });


        }


        // cập nhật lần đầu
        updateVisibility();

        return v;
    }


    // ===== API để BottomSheet gọi lại (nếu bạn muốn dùng callback) =====
    public void setGlobalDiscount(long discountValue) {
        globalDiscount = Math.max(0L, discountValue);
        updateVisibility(); // sẽ gọi updateSummary()
    }


    public void setTaxPercent(int percent) {
        if (percent < 0) percent = 0;
        taxRate = percent / 100.0;
        updateVisibility();
    }

    // ===== Tính toán =====
    private long tinhTamTinh() {
        long sum = 0L;
        for (ProductLine line : data) {
            if (line == null) continue;
            sum += line.getThanhTien();
        }
        return sum;
    }

    private long tinhTruocThue() {
        long subtotal = tinhTamTinh();
        long truocThue = subtotal - Math.max(0L, globalDiscount);
        return Math.max(0L, truocThue);
    }

    private long tinhThue() {
        long truocThue = tinhTruocThue();
        return Math.round(truocThue * taxRate);
    }

    // ===== UI =====
    private void updateVisibility() {
        if (emptyState == null || rvProducts == null) return;

        boolean empty = data.isEmpty();

        emptyState.setVisibility(empty ? View.VISIBLE : View.GONE);
        rvProducts.setVisibility(empty ? View.GONE : View.VISIBLE);

        if (layoutTongKet != null) layoutTongKet.setVisibility(empty ? View.GONE : View.VISIBLE);

        if (!empty) updateSummary();
    }

    private void updateSummary() {
        long subtotal  = tinhTamTinh();

        long giamChung = Math.max(0L, globalDiscount);
        if (giamChung > subtotal) giamChung = subtotal; // chặn không âm

        long tongGiam  = giamChung;

        long truocThue = Math.max(0L, subtotal - tongGiam);
        long thue      = Math.round(truocThue * taxRate);
        long tongThue  = thue;

        long tongCong  = truocThue + tongThue;
        tvGiamGiaChung.setText(nf.format(giamChung) + " đ");

        if (tvTamTinh != null)      tvTamTinh.setText(nf.format(subtotal) + " đ");
        if (tvGiamGiaChung != null) tvGiamGiaChung.setText(nf.format(giamChung) + " đ");
        if (tvTongGiam != null)     tvTongGiam.setText(nf.format(tongGiam) + " đ");
        if (tvTruocThue != null)    tvTruocThue.setText(nf.format(truocThue) + " đ");
        if (tvThue != null)         tvThue.setText(nf.format(thue) + " đ");
        if (tvTongThue != null)     tvTongThue.setText(nf.format(tongThue) + " đ");
        if (tvTongCong != null)     tvTongCong.setText(nf.format(tongCong) + " đ");
    }


    // ✅ Activity sẽ gọi để lấy "Tổng cộng" hiện tại (sau giảm giá chung + thuế)
    public long getCurrentTotal() {
        long subtotal = tinhTamTinh();

        long giamChung = Math.max(0L, globalDiscount);
        if (giamChung > subtotal) giamChung = subtotal;

        long truocThue = Math.max(0L, subtotal - giamChung);
        long thue = Math.round(truocThue * taxRate);

        return truocThue + thue; // ✅ tongCong
    }

}
