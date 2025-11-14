package com.example.crmmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class OpportunityBottomSheetHelper {

    // anchorView có thể dùng nếu bạn muốn neo popup; có thể truyền null
    public static void showBottomSheet(Context context, Opportunity item, int position, View anchorView) {
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_more_actions, null);

        LinearLayout layoutActions = view.findViewById(R.id.lt_more_actions);
        ImageView btnClose = view.findViewById(R.id.btn_action_close);

        btnClose.setOnClickListener(v -> dialog.dismiss());

        addActionItem(context, layoutActions, R.drawable.ic_pin, "Ghim", () -> {
            Toast.makeText(context, "Đã ghim: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        addActionItem(context, layoutActions, R.drawable.ic_calender_blue, "Thêm hoạt động", () -> {
            Toast.makeText(context, "Thêm hoạt động cho " + item.getTitle(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        addActionItem(context, layoutActions, R.drawable.ic_loop, "Chuyển thành báo giá", () -> {
            Toast.makeText(context, "Chuyển thành báo giá cho " + item.getTitle(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        addActionItem(context, layoutActions, R.drawable.ic_loop, "Chuyển thành đơn hàng", () -> {
            Toast.makeText(context, "Chuyển thành đơn hàng cho " + item.getTitle(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        addActionItem(context, layoutActions, R.drawable.ic_garbage, "Xóa", () -> {
            // Xóa ở repository
            OpportunityRepository.getInstance().delete(position);
            Toast.makeText(context, "Đã xóa cơ hội", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();
    }

    private static void addActionItem(Context context, LinearLayout parent, int iconRes,
                                      String text, Runnable onClick) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_action, parent, false);

        ImageView icon = itemView.findViewById(R.id.actionIcon);
        TextView label = itemView.findViewById(R.id.actionText);

        icon.setImageResource(iconRes);
        label.setText(text);
        itemView.setOnClickListener(v -> onClick.run());

        parent.addView(itemView);
    }
}
