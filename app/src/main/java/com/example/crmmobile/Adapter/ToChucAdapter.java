package com.example.crmmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.R;
import com.example.crmmobile.OrganizationDirectory.ToChuc;

import java.util.List;

public class ToChucAdapter extends RecyclerView.Adapter<ToChucAdapter.ToChucViewHolder> {
    public interface OnMoreOptionsClickListener {
        void onMoreOptionsClicked(int position, ToChuc toChuc);
    }
    public interface OnItemClickListener {
        void onItemClicked(int position, ToChuc toChuc);
    }
    private List<ToChuc> toChucList;
    private Context context;
    private OnMoreOptionsClickListener optionsClickListener;
    private OnItemClickListener itemClickListener;

    public ToChucAdapter(Context context, List<ToChuc> toChucList,
                         OnMoreOptionsClickListener optionsListener,
                         OnItemClickListener itemListener) {
        this.context = context;
        this.toChucList = toChucList;
        this.optionsClickListener = optionsListener;
        this.itemClickListener = itemListener;
    }

    @NonNull
    @Override
    public ToChucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_tochuc, parent, false);
        return new ToChucViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToChucViewHolder holder, int position) {
        ToChuc toChuc = toChucList.get(position);

        // --- GẮN DỮ LIỆU (Chỉ còn các view cơ bản) ---
        holder.tvCompanyName.setText(toChuc.getCompanyName());
        holder.tvIndustry.setText(toChuc.getIndustry());
        holder.tvDate.setText(toChuc.getDate());

        // ĐÃ XÓA CÁC DÒNG SET DATA CHO PHONE, MESSAGE, TRAO ĐỔI

        // Logic cho Tag Trạng thái (Giữ nguyên)
        switch (toChuc.getTrangThai()) {
            case KHONG_QUAN_TAM:
                holder.tvStatusTag.setText("Không quan tâm");
                holder.tvStatusTag.setBackgroundResource(R.drawable.tag_bg_blue);
                holder.tvStatusTag.setTextColor(ContextCompat.getColor(context, R.color.black));
                holder.tvStatusTag.setVisibility(View.VISIBLE);
                break;
            case CO_CO_HOI:
                holder.tvStatusTag.setText("Có cơ hội");
                holder.tvStatusTag.setBackgroundResource(R.drawable.tag_bg_lightblue);
                holder.tvStatusTag.setTextColor(ContextCompat.getColor(context, R.color.black));
                holder.tvStatusTag.setVisibility(View.VISIBLE);
                break;
            case CAN_QUAN_TAM:
                holder.tvStatusTag.setText("Cần quan tâm");
                holder.tvStatusTag.setBackgroundResource(R.drawable.tag_bg_red);
                holder.tvStatusTag.setTextColor(ContextCompat.getColor(context, R.color.white));
                holder.tvStatusTag.setVisibility(View.VISIBLE);
                break;
            case NONE:
            default:
                holder.tvStatusTag.setVisibility(View.GONE);
                break;
        }

        // ĐÃ XÓA LOGIC HIỂN THỊ AVATAR

        // Logic nút 3 chấm (Giữ nguyên)
        holder.ivMoreOptions.setOnClickListener(v -> {
            if (optionsClickListener != null) {
                optionsClickListener.onMoreOptionsClicked(holder.getBindingAdapterPosition(), toChuc);
            }
        });

        // Click item (Giữ nguyên)
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(holder.getBindingAdapterPosition(), toChuc);
            }
        });
    }

    @Override
    public int getItemCount() {
        return toChucList.size();
    }

    // --- VIEW HOLDER (Đã xóa các View không dùng nữa) ---
    public static class ToChucViewHolder extends RecyclerView.ViewHolder {
        // Đã xóa: ivStar, ivPhoneIcon, ivMessageIcon, avatar1-3, divider
        ImageView ivCompanyIcon, ivMoreOptions, ivIndustryIcon, ivDateIcon;
        // Đã xóa: tvPhoneCount, tvMessageCount, tvTraoDoi
        TextView tvCompanyName, tvIndustry, tvDate, tvStatusTag;

        public ToChucViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCompanyIcon = itemView.findViewById(R.id.ivCompanyIcon);
            // ivStar = itemView.findViewById(R.id.ivStar); // ĐÃ XÓA
            ivMoreOptions = itemView.findViewById(R.id.ivMoreOptions);
            ivIndustryIcon = itemView.findViewById(R.id.ivIndustryIcon);
            ivDateIcon = itemView.findViewById(R.id.ivDateIcon);

            // ivPhoneIcon = itemView.findViewById(R.id.ivPhoneIcon); // ĐÃ XÓA
            // ivMessageIcon = itemView.findViewById(R.id.ivMessageIcon); // ĐÃ XÓA

            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvIndustry = itemView.findViewById(R.id.tvIndustry);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatusTag = itemView.findViewById(R.id.tvStatusTag);

            // tvPhoneCount = itemView.findViewById(R.id.tvPhoneCount); // ĐÃ XÓA
            // tvMessageCount = itemView.findViewById(R.id.tvMessageCount); // ĐÃ XÓA
            // tvTraoDoi = itemView.findViewById(R.id.tvTraoDoi); // ĐÃ XÓA

            // avatar1 = itemView.findViewById(R.id.avatar1); // ĐÃ XÓA
            // avatar2 = itemView.findViewById(R.id.avatar2); // ĐÃ XÓA
            // avatar3 = itemView.findViewById(R.id.avatar3); // ĐÃ XÓA
            // divider = itemView.findViewById(R.id.divider); // ĐÃ XÓA
        }
    }
}