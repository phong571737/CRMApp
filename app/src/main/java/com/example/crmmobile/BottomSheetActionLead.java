package com.example.crmmobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class BottomSheetActionLead {
    private static void addActionItemLead(Context context, LinearLayout parent, int iconRes, String text, Runnable run){
        View view = LayoutInflater.from(context).inflate(R.layout.item_action, parent, false);

        ImageView icon_action = view.findViewById(R.id.img_action_icon);
        TextView text_action = view.findViewById(R.id.tv_action);

        icon_action.setImageResource(iconRes);
        text_action.setText(text);

        view.setOnClickListener(v -> {
            if(run != null) run.run();
        });

        parent.addView(view);
    }

    public static void ShowBottomSheetLead(Context context, List<Lead> item, int position, Runnable reload){
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_more_actions, null);

        LinearLayout layoutAction = view.findViewById(R.id.lt_more_actions);
        ImageView btnClose = view.findViewById(R.id.btn_action_close);

        btnClose.setOnClickListener(v -> dialog.dismiss());//exit

        addActionItemLead(context, layoutAction, R.drawable.ic_pin, "Ghim", null);
        addActionItemLead(context, layoutAction, R.drawable.ic_call, "Gọi điện", null);
        addActionItemLead(context, layoutAction, R.drawable.ic_comment_mess, "Chat", null);
        addActionItemLead(context, layoutAction, R.drawable.ic_sms, "Gửi tin nhắn SMS", null);
        addActionItemLead(context, layoutAction, R.drawable.ic_mail, "Gửi Email", null);
        addActionItemLead(context, layoutAction, R.drawable.ic_calendar, "Thêm hoạt động", null);
        addActionItemLead(context, layoutAction, R.drawable.ic_loop, "Chuyển đổi Lead", ()->{
            Intent intent = new Intent(context, ConvertLead_Activity.class);
            context.startActivity(intent);
            dialog.dismiss();
        });
        addActionItemLead(context, layoutAction, R.drawable.ic_pencil, "Chỉnh sửa", ()->{
            Intent intent = new Intent(context, EditLeadActivity.class);
            context.startActivity(intent);
            dialog.dismiss();
        });
        addActionItemLead(context, layoutAction, R.drawable.ic_garbage, "Xóa", () ->{
            item.remove(position);
            reload.run();
            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();
    }

    public static void DeleteBottomSheet(Context context, Lead item, int position){

    }
}
