package com.example.crmmobile.OrderDirectory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.R;
import com.example.crmmobile.OrderDirectory.ProductLine;
import com.example.crmmobile.SanPhamDirectory.SanPham;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductLineAdapter extends RecyclerView.Adapter<ProductLineAdapter.VH> {

    // ✅ callback để fragment cập nhật tổng tiền/empty view
    public interface OnChanged {
        void onChanged();
    }

    // ✅ click item để mở bottomsheet chỉnh sửa
    public interface OnItemClickListener {
        void onItemClick(ProductLine line);
    }

    private final List<ProductLine> data;
    private final OnChanged onChanged;
    private OnItemClickListener itemClickListener;

    private final NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

    // ✅ Constructor 1 tham số (để không bị lỗi nếu bạn gọi new Adapter(data))
    public ProductLineAdapter(List<ProductLine> data) {
        this.data = data;
        this.onChanged = null;
    }

    // ✅ Constructor 2 tham số (đúng “Expected 2 arguments”)
    public ProductLineAdapter(List<ProductLine> data, OnChanged onChanged) {
        this.data = data;
        this.onChanged = onChanged;
    }

    // ✅ Hàm bạn đang gọi trong SOProductsFragment
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_product_line, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        ProductLine p = data.get(position);

        h.tvName.setText(p.getName());
        h.tvQty.setText(String.valueOf(p.getQty()));
        h.tvPrice.setText(nf.format(p.getPrice()) + " đ");
        h.tvTotal.setText("Thành tiền: " + nf.format(p.getThanhTien()) + " đ");

        // ✅ click item mở bottomsheet
        h.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) itemClickListener.onItemClick(p);
        });

        // ✅ tăng/giảm/xóa (nếu bạn đang cần)
        h.btnMinus.setOnClickListener(v -> {
            int q = Math.max(1, p.getQty() - 1);
            p.setQty(q);
            notifyItemChanged(h.getAdapterPosition());
            if (onChanged != null) onChanged.onChanged();
        });

        h.btnPlus.setOnClickListener(v -> {
            p.setQty(p.getQty() + 1);
            notifyItemChanged(h.getAdapterPosition());
            if (onChanged != null) onChanged.onChanged();
        });

        h.btnDelete.setOnClickListener(v -> {
            int pos = h.getAdapterPosition();
            if (pos >= 0 && pos < data.size()) {
                data.remove(pos);
                notifyItemRemoved(pos);
                if (onChanged != null) onChanged.onChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQty, tvTotal;
        TextView btnMinus, btnPlus, btnDelete;

        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvTotal = itemView.findViewById(R.id.tvTotal);

            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus  = itemView.findViewById(R.id.btnPlus);
            btnDelete= itemView.findViewById(R.id.btnDelete);
        }
    }
}
