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
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SOProductsFragment extends Fragment {

    private LinearLayout emptyState;
    private RecyclerView rvProducts;
    private View layoutTongKet;

    private TextView tvTamTinh, tvGiamGiaChung, tvTongGiam, tvTruocThue, tvThue, tvTongThue, tvTongCong;

    private final List<ProductLine> data = new ArrayList<>();
    private ProductLineAdapter adapter;

    private ActivityResultLauncher<Intent> pickProductLauncher;

    private long globalDiscountExtra = 0L;
    private double taxRate = 0.0;

    private final NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ProductLineAdapter(data, this::updateVisibility);

        adapter.setOnItemClickListener(line -> {
            EditProductBottomSheet sheet = EditProductBottomSheet.newInstance(line);

            sheet.setListener((newQty, discountType, discountPercent, discountDirect, vatPercent) -> {
                line.setQty(newQty);

                if (discountType == ProductLine.DISCOUNT_PERCENT) {
                    line.setDiscountPercent(discountPercent);
                } else if (discountType == ProductLine.DISCOUNT_DIRECT) {
                    line.setDiscountDirect(discountDirect);
                } else {
                    line.setNoDiscount();
                }

                line.setVatPercent(vatPercent);

                if (adapter != null) adapter.notifyDataSetChanged();
                updateVisibility();
            });

            sheet.show(getParentFragmentManager(), "EditProductBottomSheet");
        });

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

        if (rvProducts != null) {
            rvProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
            rvProducts.setAdapter(adapter);
        }

        MaterialButton btnAdd = v.findViewById(R.id.btnAddProduct);
        if (btnAdd != null) {
            btnAdd.setOnClickListener(view -> {
                Intent i = new Intent(requireContext(), ProductPickActivity.class);
                pickProductLauncher.launch(i);
            });
        }

        ImageView iconThue = v.findViewById(R.id.iconTooltipThue);
        if (iconThue != null) {
            iconThue.setOnClickListener(view1 -> {
                long beforeTax = calcBeforeTax(); // sau giảm (từng SP + chung)
                int vatDefault = (int) Math.round(taxRate * 100);

                long lineTaxSum = calcLineTaxSum();

                EditTaxBottomSheet sheet = EditTaxBottomSheet.newInstance(beforeTax, vatDefault, lineTaxSum);

                sheet.setListener(percent -> setTaxPercent(percent));

                sheet.show(getParentFragmentManager(), "EditTax");
            });
        }

        ImageView iconGiamGia = v.findViewById(R.id.iconTooltipGiamGia);
        if (iconGiamGia != null) {
            iconGiamGia.setOnClickListener(clicked -> {
                long subtotalAfterLineDiscount = calcSubtotalAfterLineDiscount();
                long lineDiscountSum = calcLineDiscountSum();

                EditGlobalDiscountBottomSheet sheet =
                        EditGlobalDiscountBottomSheet.newInstance(subtotalAfterLineDiscount, globalDiscountExtra, lineDiscountSum);

                sheet.setListener(discountAmount -> {
                    globalDiscountExtra = Math.max(0L, discountAmount);
                    updateVisibility();
                });

                sheet.show(getParentFragmentManager(), "EditGlobalDiscount");
            });
        }

        updateVisibility();
        return v;
    }

    public void setTaxPercent(int percent) {
        if (percent < 0) percent = 0;
        if (percent > 100) percent = 100;
        taxRate = percent / 100.0;
        updateVisibility();
    }

    private long calcSubtotalBase() {
        long sum = 0L;
        for (ProductLine p : data) sum += p.getBaseAmount();
        return sum;
    }

    private long calcLineDiscountSum() {
        long sum = 0L;
        for (ProductLine p : data) sum += p.getDiscountAmount();
        return sum;
    }

    private long calcSubtotalAfterLineDiscount() {
        long sum = 0L;
        for (ProductLine p : data) sum += p.getAfterDiscountAmount();
        return sum;
    }

    private long calcBeforeTax() {
        long afterLineDiscount = calcSubtotalAfterLineDiscount();
        long extra = Math.max(0L, globalDiscountExtra);
        if (extra > afterLineDiscount) extra = afterLineDiscount;
        return Math.max(0L, afterLineDiscount - extra);
    }

    private long calcLineTaxSum() {
        long sum = 0L;
        for (ProductLine p : data) sum += p.getTaxAmount();
        return sum;
    }

    private long calcOrderTax(long beforeTax) {
        return Math.round(beforeTax * taxRate);
    }

    private void updateVisibility() {
        if (emptyState == null || rvProducts == null) return;

        boolean empty = data.isEmpty();
        emptyState.setVisibility(empty ? View.VISIBLE : View.GONE);
        rvProducts.setVisibility(empty ? View.GONE : View.VISIBLE);

        if (layoutTongKet != null) layoutTongKet.setVisibility(empty ? View.GONE : View.VISIBLE);

        if (!empty) updateSummary();
    }

    private void updateSummary() {
        long subtotalBase = calcSubtotalBase();

        long lineDiscountSum = calcLineDiscountSum();
        long extraDiscount = Math.max(0L, globalDiscountExtra);

        long afterLineDiscount = calcSubtotalAfterLineDiscount();
        if (extraDiscount > afterLineDiscount) extraDiscount = afterLineDiscount;

        long totalDiscount = lineDiscountSum + extraDiscount;

        long beforeTax = Math.max(0L, afterLineDiscount - extraDiscount);

        long lineTaxSum = calcLineTaxSum();
        long orderTax = calcOrderTax(beforeTax);
        long totalTax = lineTaxSum + orderTax;

        long grandTotal = beforeTax + totalTax;

        if (tvTamTinh != null)      tvTamTinh.setText(nf.format(subtotalBase) + " đ");
        if (tvGiamGiaChung != null) tvGiamGiaChung.setText(nf.format(totalDiscount) + " đ");
        if (tvTongGiam != null)     tvTongGiam.setText(nf.format(totalDiscount) + " đ");
        if (tvTruocThue != null)    tvTruocThue.setText(nf.format(beforeTax) + " đ");
        if (tvThue != null)         tvThue.setText(nf.format(totalTax) + " đ");
        if (tvTongThue != null)     tvTongThue.setText(nf.format(totalTax) + " đ");
        if (tvTongCong != null)     tvTongCong.setText(nf.format(grandTotal) + " đ");
    }

    public long getCurrentTotal() {
        long beforeTax = calcBeforeTax();
        long totalTax = calcLineTaxSum() + calcOrderTax(beforeTax);
        return beforeTax + totalTax;
    }
}
