package com.example.crmmobile.feature.salesorder.ui.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.R; // sửa cho đúng
import com.example.crmmobile.feature.salesorder.model.ProductLine; // sửa cho đúng

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class ProductLineAdapter extends RecyclerView.Adapter<ProductLineAdapter.VH> {

    public interface OnItemClickListener {
        void onItemClick(ProductLine line);
    }

    private final List<ProductLine> data;
    private final NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
    private OnItemClickListener itemClickListener;

    public ProductLineAdapter(List<ProductLine> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.itemClickListener = l;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_san_pham, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        ProductLine p = data.get(position);

        // TODO: chỉnh getter cho đúng với model của bạn
        String name  = p.getName();
        String note  = p.getNote();
        int qty      = p.getQty();
        long price   = p.getPrice();

        long thanhTien = qty * price;

        String giaStr       = nf.format(price) + " đ";
        String thanhTienStr = nf.format(thanhTien) + " đ";

        h.tvTenSanPham.setText(name);
        h.tvLoaiSanPham.setText(
                (note != null && !note.isEmpty()) ? note : "Công nghệ"
        );
        h.tvSoLuong.setText(qty + " x " + giaStr);
        h.tvGiaGoc.setText("Giá gốc: " + giaStr);
        h.tvGiamGia.setText("Giảm giá: 0 đ");
        h.tvThue.setText("Thuế: 0 đ");
        h.tvThanhTien.setText(thanhTienStr);

        h.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTenSanPham, tvLoaiSanPham,
                tvSoLuong, tvGiaGoc, tvGiamGia, tvThue, tvThanhTien;

        VH(@NonNull View v) {
            super(v);
            tvTenSanPham  = v.findViewById(R.id.tvTenSanPham);
            tvLoaiSanPham = v.findViewById(R.id.tvLoaiSanPham);
            tvSoLuong     = v.findViewById(R.id.tvSoLuong);
            tvGiaGoc      = v.findViewById(R.id.tvGiaGoc);
            tvGiamGia     = v.findViewById(R.id.tvGiamGia);
            tvThue        = v.findViewById(R.id.tvThue);
            tvThanhTien   = v.findViewById(R.id.tvThanhTien);
        }
    }
}
