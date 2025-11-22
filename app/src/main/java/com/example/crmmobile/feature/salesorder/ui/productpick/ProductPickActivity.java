package com.example.crmmobile.feature.salesorder.ui.productpick;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.R; // SỬA R cho đúng app của bạn
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ProductPickActivity extends AppCompatActivity {

    public static final String EXTRA_NAME  = "extra_product_name";
    public static final String EXTRA_PRICE = "extra_product_price";
    public static final String EXTRA_NOTE  = "extra_product_note";

    private PickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_pick);

        MaterialToolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setNavigationOnClickListener(v -> finish());

        RecyclerView rv = findViewById(R.id.rvProducts);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PickAdapter(seed(), p -> {
            Intent out = new Intent();
            out.putExtra(EXTRA_NAME,  p.name);
            out.putExtra(EXTRA_PRICE, p.price);
            out.putExtra(EXTRA_NOTE,  p.badge); // tạm dùng badge làm note
            setResult(RESULT_OK, out);
            finish();
        });
        rv.setAdapter(adapter);

        TextInputEditText edtSearch = findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(String.valueOf(s));
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private List<PickProduct> seed() {
        List<PickProduct> l = new ArrayList<>();
        l.add(new PickProduct("Cam AI HA800", "Camera AI nhận diện thông minh", "Công nghệ", 4_090_000));
        l.add(new PickProduct("Bàn phím cơ Asus ROG Falchion Ace White", "Phím cơ 65%", "Bàn phím", 5_050_000));
        l.add(new PickProduct("CloudWORK - Giải pháp quản lý dự án chuyên nghiệp", "Lorem ipsum ...", "Cloud", 1_290_000));
        l.add(new PickProduct("CloudCheckin", "Chấm công, vào/ra", "Cloud", 890_000));
        l.add(new PickProduct("CloudLead", "Giải pháp quản lý dự án chuyên nghiệp", "Cloud", 1_820_000));
        return l;
    }

    static class PickProduct {
        String name;
        String desc;
        String badge;
        long price;

        PickProduct(String name, String desc, String badge, long price) {
            this.name  = name;
            this.desc  = desc;
            this.badge = badge;
            this.price = price;
        }
    }

    static class PickAdapter extends RecyclerView.Adapter<PickAdapter.VH> {

        interface OnItemClick { void onClick(PickProduct p); }

        private final List<PickProduct> full  = new ArrayList<>();
        private final List<PickProduct> items = new ArrayList<>();
        private final OnItemClick onClick;
        private final NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

        PickAdapter(List<PickProduct> data, OnItemClick onClick) {
            if (data != null) {
                full.addAll(data);
                items.addAll(data);
            }
            this.onClick = onClick;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product_pick, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int position) {
            PickProduct p = items.get(position);
            h.tvName.setText(p.name);
            h.tvDesc.setText(p.desc);
            h.tvBadge.setText(p.badge);
            h.tvPrice.setText(nf.format(p.price) + " đ");

            h.itemView.setOnClickListener(v -> {
                if (onClick != null) onClick.onClick(p);
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        void filter(String q) {
            items.clear();
            if (q == null || q.trim().isEmpty()) {
                items.addAll(full);
            } else {
                String lower = q.toLowerCase(Locale.ROOT);
                for (PickProduct p : full) {
                    if (p.name.toLowerCase(Locale.ROOT).contains(lower)
                            || p.desc.toLowerCase(Locale.ROOT).contains(lower)
                            || p.badge.toLowerCase(Locale.ROOT).contains(lower)) {
                        items.add(p);
                    }
                }
            }
            notifyDataSetChanged();
        }

        static class VH extends RecyclerView.ViewHolder {
            ImageView imgThumb;
            TextView tvName, tvDesc, tvBadge, tvPrice;

            VH(@NonNull View v) {
                super(v);
                imgThumb = v.findViewById(R.id.imgThumb);
                tvName   = v.findViewById(R.id.tvName);
                tvDesc   = v.findViewById(R.id.tvDesc);
                tvBadge  = v.findViewById(R.id.tvBadge);
                tvPrice  = v.findViewById(R.id.tvPrice);
            }
        }
    }
}
