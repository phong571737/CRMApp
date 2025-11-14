package com.example.crmmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetActionQuote {
    private static void addActionItemQuote(Context context, LinearLayout parent, int iconRes, String text){
        View view = LayoutInflater.from(context).inflate(R.layout.item_action, parent, false);

        ImageView icon_action = view.findViewById(R.id.actionIcon);
        TextView text_action = view.findViewById(R.id.actionText);

        icon_action.setImageResource(iconRes);
        text_action.setText(text);

        parent.addView(view);
    }

    public static void ShowBottomSheetQuote(Context context, Quote item, int position ){
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_more_actions, null);

        LinearLayout layoutAction = view.findViewById(R.id.lt_more_actions);
        ImageView btnClose = view.findViewById(R.id.btn_action_close);

        btnClose.setOnClickListener(v -> dialog.dismiss());//exit

        addActionItemQuote(context, layoutAction, R.drawable.ic_pin, "Ghim");
        addActionItemQuote(context, layoutAction, R.drawable.ic_loop, "Chuyển thành đơn hàng");
        addActionItemQuote(context, layoutAction, R.drawable.ic_loop, "Chuyển thành hóa đơn");
        addActionItemQuote(context, layoutAction, R.drawable.ic_export_pdf, "Xuất file pdf");
        addActionItemQuote(context, layoutAction, R.drawable.ic_forward_email, "Gửi email kèm file pdf");
        addActionItemQuote(context, layoutAction, R.drawable.ic_copy, "Nhân đôi");

        dialog.setContentView(view);
        dialog.show();
    }
}
