package com.example.crmmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuickMenuAdapter extends RecyclerView.Adapter<QuickMenuAdapter.QuickMenuViewHolder> {

    private Context context;
    private List<QuickMenuItem> menuList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(QuickMenuItem item);
    }

    public QuickMenuAdapter(Context context, List<QuickMenuItem> menuList, OnItemClickListener listener) {
        this.context = context;
        this.menuList = menuList;
        this.listener = listener;
    }

    @NonNull
//    @Override
//    public QuickMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_home_quick_menu, parent, false);
//        return new QuickMenuViewHolder(view);
//    }
    @Override
    public QuickMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_quick_menu, parent, false);

        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;

        float density = context.getResources().getDisplayMetrics().density;
        int marginPx = (int) (8 * density + 0.5f); // marginEnd trong XML là 8dp

        //  Tính toán chuẩn: trừ bớt 4 marginEnd (vì có 4 item, 3 khoảng trống giữa và 1 margin ngoài)
        int totalMargin = marginPx * 4;

        //  Nếu RecyclerView có paddingStart/paddingEnd = 8dp, trừ thêm
        int paddingPx = (int) (8 * density + 0.5f);
        int totalPadding = paddingPx * 2;

        //  Tổng trừ = margin + padding
        int availableWidth = screenWidth - totalMargin - totalPadding;

        //  Mỗi item chiếm đúng 1/4 phần còn lại
        int itemWidth = availableWidth / 4;

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.MarginLayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            params.width = itemWidth;
        }
        view.setLayoutParams(params);

        return new QuickMenuViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull QuickMenuViewHolder holder, int position) {
        QuickMenuItem item = menuList.get(position);
        holder.icon.setImageResource(item.getIconResId());
        holder.title.setText(item.getTitle());
        holder.cardView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class QuickMenuViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;
        CardView cardView;

        public QuickMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.img_menu_icon);
            title = itemView.findViewById(R.id.tv_menu_title);
            cardView = (CardView) itemView;
        }
    }
}
