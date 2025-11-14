package com.example.crmmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetActionLead {
    private static void addActionItemLead(Context context, LinearLayout parent, int iconRes, String text){
        View view = LayoutInflater.from(context).inflate(R.layout.item_action, parent, false);

        ImageView icon_action = view.findViewById(R.id.actionIcon);
        TextView text_action = view.findViewById(R.id.actionText);

        icon_action.setImageResource(iconRes);
        text_action.setText(text);

        parent.addView(view);
    }

    public static void ShowBottomSheetLead(Context context, Lead item, int position ){
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_more_actions, null);

        LinearLayout layoutAction = view.findViewById(R.id.lt_more_actions);
        ImageView btnClose = view.findViewById(R.id.btn_action_close);

        btnClose.setOnClickListener(v -> dialog.dismiss());//exit

        addActionItemLead(context, layoutAction, R.drawable.ic_pin, "Ghim");
        addActionItemLead(context, layoutAction, R.drawable.ic_call, "Gọi điện");
        addActionItemLead(context, layoutAction, R.drawable.ic_comment_mess, "Chat" );
        addActionItemLead(context, layoutAction, R.drawable.ic_sms, "Gửi tin nhắn SMS");
        addActionItemLead(context, layoutAction, R.drawable.ic_mail, "Gửi Email");
        addActionItemLead(context, layoutAction, R.drawable.ic_calendar, "Thêm hoạt động");
        addActionItemLead(context, layoutAction, R.drawable.ic_loop, "Chuyển đổi Lead");
        addActionItemLead(context, layoutAction, R.drawable.ic_pencil, "Chỉnh sửa");
        addActionItemLead(context, layoutAction, R.drawable.ic_garbage, "Xóa");

        dialog.setContentView(view);
        dialog.show();
    }
}
