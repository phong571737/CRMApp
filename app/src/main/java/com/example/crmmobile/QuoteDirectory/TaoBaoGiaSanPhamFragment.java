package com.example.crmmobile.QuoteDirectory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.OrderDirectory.EditProductBottomSheet;
import com.example.crmmobile.OrderDirectory.ProductLine;
import com.example.crmmobile.OrderDirectory.ProductLineAdapter;
import com.example.crmmobile.OrderDirectory.ProductPickActivity;
import com.example.crmmobile.R;
import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TaoBaoGiaSanPhamFragment extends Fragment {
    private LinearLayout emptyState;
    private RecyclerView rvProducts;
    private View layoutSanPhamContent;
    private ImageView btnToggleSanPham;
    private MaterialButton btnThemSP;
    private CreateQuoteViewModel viewModelQuote;
    private ActivityResultLauncher<Intent> pickProductLauncher;
    private ProductLineAdapter adapter;
    private final List<ProductLine> data = new ArrayList<>();
    private TextView tvTamTinh, tvTongGiam, tvGiamGiaChung, tvTongTruocThue, tvThue, tvTongThue, tvTongCong;

    public interface StringUpdater{
        void update(String s);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelQuote = new ViewModelProvider(requireActivity()).get(CreateQuoteViewModel.class);
        adapter = new ProductLineAdapter(data);

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

    private void updateVisibility() {
        // Có thể được gọi từ callback nên nhớ check null
        if (emptyState == null || rvProducts == null) return;

        if (data.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            rvProducts.setVisibility(View.GONE);
            if (tvTongCong != null) tvTongCong.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            rvProducts.setVisibility(View.VISIBLE);
            if (tvTongCong != null) tvTongCong.setVisibility(View.VISIBLE);
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
        viewModelQuote.TotalAmount.setValue(tongCong);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_taobaogia_sanpham, container, false);
        // Ánh xạ view
        initViews(view);
        //Observe total

        rvProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvProducts.setAdapter(adapter);

        // Xử lý ẩn/hiện khi bấm icon
        setupToggle(btnToggleSanPham, layoutSanPhamContent);
        btnThemSP.setOnClickListener(v -> {
            Intent i = new Intent(requireContext(), ProductPickActivity.class);
            pickProductLauncher.launch(i);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelQuote.TotalAmount.observe(getViewLifecycleOwner(), total->{
            if (total != null){
                NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
                tvTongCong.setText(nf.format(total) + "đ");
            }
        });
    }

    private void initViews(View view) {
        layoutSanPhamContent = view.findViewById(R.id.layoutSanPhamContent);
        btnToggleSanPham = view.findViewById(R.id.btnToggleSanPham);
        btnThemSP = view.findViewById(R.id.btnThemSP);
        tvTamTinh = view.findViewById(R.id.tvTamTinh);
        tvTongGiam = view.findViewById(R.id.tvTongGiam);
        tvGiamGiaChung = view.findViewById(R.id.tvGiamGiaChung);
        tvTongTruocThue = view.findViewById(R.id.tvTongTruocThue);
        tvThue = view.findViewById(R.id.tvThue);
        tvTongThue = view.findViewById(R.id.tvTongThue);
        tvTongCong = view.findViewById(R.id.tvTongCong);
        emptyState = view.findViewById(R.id.emptyState);
        rvProducts = view.findViewById(R.id.rvProducts);
    }

    // Hàm chung để xử lý ẩn/hiện
    private void setupToggle(ImageView button, View contentLayout) {
        button.setOnClickListener(v -> {
            if (contentLayout.getVisibility() == View.VISIBLE) {
                contentLayout.setVisibility(View.GONE);
                button.setImageResource(R.drawable.ic_arrow_down);
            } else {
                contentLayout.setVisibility(View.VISIBLE);
                button.setImageResource(R.drawable.ic_arrow_up);
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
}