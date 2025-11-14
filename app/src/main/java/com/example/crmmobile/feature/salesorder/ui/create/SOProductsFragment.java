package com.example.crmmobile.feature.salesorder.ui.create;

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
import com.example.crmmobile.feature.salesorder.model.ProductLine;
import com.example.crmmobile.feature.salesorder.ui.common.ProductLineAdapter;
import com.example.crmmobile.feature.salesorder.ui.discount.EditGlobalDiscountBottomSheet;
import com.example.crmmobile.feature.salesorder.ui.product.EditProductBottomSheet;
import com.example.crmmobile.feature.salesorder.ui.productpick.ProductPickActivity;
import com.example.crmmobile.feature.salesorder.ui.tax.EditTaxBottomSheet;
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SOProductsFragment extends Fragment {

    private LinearLayout emptyState;
    private RecyclerView rvProducts;
    private View layoutTongKet;
    private TextView tvTamTinh, tvGiamGiaChung, tvTongThue, tvTongCong;

    private final List<ProductLine> data = new ArrayList<>();
    private ProductLineAdapter adapter;

    private ActivityResultLauncher<Intent> pickProductLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tạo adapter, chưa đụng gì đến View ở đây
        adapter = new ProductLineAdapter(data);

        // CLICK ITEM → mở bottom sheet điều chỉnh sản phẩm
        adapter.setOnItemClickListener(line -> {
            String name = line.getName();
            long price  = line.getPrice();
            int qty     = line.getQty();

            EditProductBottomSheet sheet =
                    EditProductBottomSheet.newInstance(name, price, qty);
            sheet.show(getParentFragmentManager(), "EditProductBottomSheet");
        });

        // Đăng ký launcher chọn sản phẩm
        pickProductLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent intent = result.getData();

                        String name  = intent.getStringExtra(ProductPickActivity.EXTRA_NAME);
                        long   price = intent.getLongExtra(ProductPickActivity.EXTRA_PRICE, 0L);
                        String note  = intent.getStringExtra(ProductPickActivity.EXTRA_NOTE);

                        if (name != null) {
                            ProductLine line = new ProductLine(
                                    name,
                                    note != null ? note : "",
                                    1,
                                    price
                            );

                            data.add(line);
                            if (adapter != null) {
                                adapter.notifyItemInserted(data.size() - 1);
                            }
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

        // Lấy view từ layout
        emptyState   = v.findViewById(R.id.emptyState);
        rvProducts   = v.findViewById(R.id.rvProducts);
        layoutTongKet = v.findViewById(R.id.layoutTongKet);

        if (layoutTongKet != null) {
            tvTamTinh      = layoutTongKet.findViewById(R.id.tvTamTinh);
            tvGiamGiaChung = layoutTongKet.findViewById(R.id.tvGiamGiaChung);
            tvTongThue     = layoutTongKet.findViewById(R.id.tvTongThue);
            tvTongCong     = layoutTongKet.findViewById(R.id.tvTongCong);
        }

        // Setup RecyclerView (GIỜ mới được đụng rvProducts)
        rvProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvProducts.setAdapter(adapter);

        // Nút "Thêm sản phẩm"
        MaterialButton btnAdd = v.findViewById(R.id.btnAddProduct);
        btnAdd.setOnClickListener(view -> {
            Intent i = new Intent(requireContext(), ProductPickActivity.class);
            pickProductLauncher.launch(i);
        });
        // ==== Click ic_info Thuế mở bottom sheet điều chỉnh thuế ====
        ImageView iconThue = v.findViewById(R.id.iconTooltipThue);
        if (iconThue != null) {
            iconThue.setOnClickListener(view1 -> {
                long baseAmount = tinhTamTinh();  // hoặc tổng trước thuế nếu bạn có hàm riêng
                int vatPercentDefault = 10;

                EditTaxBottomSheet sheet =
                        EditTaxBottomSheet.newInstance(baseAmount, vatPercentDefault);
                sheet.show(getParentFragmentManager(), "EditTax");
            });
        }


        // Click ic_info "Giảm giá chung" → mở BottomSheet giảm giá
        ImageView iconGiamGia = v.findViewById(R.id.iconTooltipGiamGia);
        if (iconGiamGia != null) {
            iconGiamGia.setOnClickListener(view1 -> {
                long subtotal = tinhTamTinh();
                EditGlobalDiscountBottomSheet sheet =
                        EditGlobalDiscountBottomSheet.newInstance(subtotal);
                sheet.show(getParentFragmentManager(), "EditGlobalDiscount");
            });
        }

        // Cập nhật hiển thị ban đầu
        updateVisibility();

        return v;
    }

    // Tính tạm tính = sum(thành tiền từng dòng)
    private long tinhTamTinh() {
        long sum = 0;
        for (ProductLine line : data) {
            sum += line.getThanhTien();
        }
        return sum;
    }

    private void updateVisibility() {
        // Có thể được gọi từ callback nên nhớ check null
        if (emptyState == null || rvProducts == null) return;

        if (data.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            rvProducts.setVisibility(View.GONE);
            if (layoutTongKet != null) layoutTongKet.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            rvProducts.setVisibility(View.VISIBLE);
            if (layoutTongKet != null) layoutTongKet.setVisibility(View.VISIBLE);
            updateSummary();
        }
    }

    private void updateSummary() {
        long subtotal = 0L;

        for (ProductLine line : data) {
            subtotal += (long) line.getQty() * line.getPrice();
        }

        long giamGiaChung = 0L;
        long tongThue     = 0L;
        long tongCong     = subtotal - giamGiaChung + tongThue;

        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        String stSubtotal = nf.format(subtotal) + " đ";
        String stGiam     = nf.format(giamGiaChung) + " đ";
        String stThue     = nf.format(tongThue) + " đ";
        String stTongCong = nf.format(tongCong) + " đ";

        if (tvTamTinh != null)      tvTamTinh.setText(stSubtotal);
        if (tvGiamGiaChung != null) tvGiamGiaChung.setText(stGiam);
        if (tvTongThue != null)     tvTongThue.setText(stThue);
        if (tvTongCong != null)     tvTongCong.setText(stTongCong);
    }
}
