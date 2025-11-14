package com.example.crmmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class ToChucAdapter extends RecyclerView.Adapter<ToChucAdapter.ToChucViewHolder> {
    // === TẠO INTERFACE MỚI ===
    public interface OnMoreOptionsClickListener {
        void onMoreOptionsClicked(int position, ToChuc toChuc);
    }

    // === TẠO INTERFACE MỚI CHO CLICK ITEM ===
    public interface OnItemClickListener {
        void onItemClicked(int position, ToChuc toChuc);
    }
    private List<ToChuc> toChucList;
    private Context context;
    private OnMoreOptionsClickListener optionsClickListener;
    private OnItemClickListener itemClickListener;

    // === CẬP NHẬT CONSTRUCTOR ===
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
        // --- GẮN DỮ LIỆU ---
        holder.tvCompanyName.setText(toChuc.getCompanyName());
        holder.tvIndustry.setText(toChuc.getIndustry());
        holder.tvDate.setText(toChuc.getDate());
        holder.tvPhoneCount.setText(toChuc.getPhoneCount());
        holder.tvMessageCount.setText(toChuc.getMessageCount());

        // Logic ẩn/hiện "Trao đổi"
        if (toChuc.isShowTraoDoi()) {
            holder.tvTraoDoi.setVisibility(View.VISIBLE);
        } else {
            holder.tvTraoDoi.setVisibility(View.GONE);
        }

        // Logic cho Tag Trạng thái
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

        // Logic cho Avatar
        List<Integer> avatars = toChuc.getAvatarDrawables();
        if (avatars != null) {
            holder.avatar1.setVisibility(avatars.size() >= 1 ? View.VISIBLE : View.GONE);
            holder.avatar2.setVisibility(avatars.size() >= 2 ? View.VISIBLE : View.GONE);
            holder.avatar3.setVisibility(avatars.size() >= 3 ? View.VISIBLE : View.GONE);

            if (avatars.size() >= 1) holder.avatar1.setImageResource(avatars.get(0));

            if (avatars.size() >= 2) holder.avatar2.setImageResource(avatars.get(1));

            if (avatars.size() >= 3) holder.avatar3.setImageResource(avatars.get(2));

        } else {
            holder.avatar1.setVisibility(View.GONE);
            holder.avatar2.setVisibility(View.GONE);
            holder.avatar3.setVisibility(View.GONE);
        }

        // Logic nút 3 chấm
        holder.ivMoreOptions.setOnClickListener(v -> {
            if (optionsClickListener != null) {
                // Báo cho Fragment biết: "Nút 3 chấm ở vị trí này đã được bấm"
                optionsClickListener.onMoreOptionsClicked(holder.getBindingAdapterPosition(), toChuc);
            }
        });

        // === THÊM SỰ KIỆN CLICK CHO TOÀN BỘ ITEM ===
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

    // --- VIEW HOLDER ---
    public static class ToChucViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCompanyIcon, ivStar, ivMoreOptions, ivIndustryIcon, ivDateIcon, ivPhoneIcon, ivMessageIcon;
        TextView tvCompanyName, tvIndustry, tvDate, tvStatusTag, tvPhoneCount, tvMessageCount, tvTraoDoi;
        CircleImageView avatar1, avatar2, avatar3;
        View divider;
        public ToChucViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCompanyIcon = itemView.findViewById(R.id.ivCompanyIcon);
            ivStar = itemView.findViewById(R.id.ivStar);
            ivMoreOptions = itemView.findViewById(R.id.ivMoreOptions);
            ivIndustryIcon = itemView.findViewById(R.id.ivIndustryIcon);
            ivDateIcon = itemView.findViewById(R.id.ivDateIcon);
            ivPhoneIcon = itemView.findViewById(R.id.ivPhoneIcon);
            ivMessageIcon = itemView.findViewById(R.id.ivMessageIcon);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvIndustry = itemView.findViewById(R.id.tvIndustry);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatusTag = itemView.findViewById(R.id.tvStatusTag);
            tvPhoneCount = itemView.findViewById(R.id.tvPhoneCount);
            tvMessageCount = itemView.findViewById(R.id.tvMessageCount);
            tvTraoDoi = itemView.findViewById(R.id.tvTraoDoi);
            avatar1 = itemView.findViewById(R.id.avatar1);
            avatar2 = itemView.findViewById(R.id.avatar2);
            avatar3 = itemView.findViewById(R.id.avatar3);
            divider = itemView.findViewById(R.id.divider);
        }
    }
}