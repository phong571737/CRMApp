package com.example.crmmobile.BottomSheet;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.crmmobile.LeadDirectory.Lead;
import com.example.crmmobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetActionLead {
    private static final String PREFS_NAME = "LoginPrefs";
    private static final String KEY_USER_ROLE = "userRole";

    public interface OnActionListenerLead{
        void onEdit(Lead lead);
        void onDelete(Lead lead);
        void onConvertLead(Lead lead);
        void onOpenCalls(Lead lead);
        void onSendEmail(Lead lead);
    }

    private static void addActionItemLead(Context context, LinearLayout parent, int iconRes, String text, Runnable run){
        View view = LayoutInflater.from(context).inflate(R.layout.item_action, parent, false);

        ImageView icon_action = view.findViewById(R.id.actionIcon);
        TextView text_action = view.findViewById(R.id.actionText);

        icon_action.setImageResource(iconRes);
        text_action.setText(text);

        view.setOnClickListener(v -> {
            if(run != null) run.run();
        });

        parent.addView(view);
    }

    public static void ShowBottomSheetLead(Context context, Lead lead,  OnActionListenerLead listener){
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_more_actions, null);

        LinearLayout layoutAction = view.findViewById(R.id.lt_more_actions);
        ImageView btnClose = view.findViewById(R.id.btn_action_close);

        btnClose.setOnClickListener(v -> dialog.dismiss());//exit

        addActionItemLead(context, layoutAction, R.drawable.ic_call, "Gọi điện", ()->{
            if (listener != null) listener.onOpenCalls(lead);
            dialog.dismiss();
        });
        addActionItemLead(context, layoutAction, R.drawable.ic_mail, "Gửi Email", ()->{
            if (listener != null) listener.onSendEmail(lead);
            dialog.dismiss();
        });
        addActionItemLead(context, layoutAction, R.drawable.ic_loop, "Chuyển đổi Lead", ()->{
            if (listener != null) listener.onConvertLead(lead);
            dialog.dismiss();
        });
        addActionItemLead(context, layoutAction, R.drawable.ic_pencil, "Chỉnh sửa", ()->{
            if(listener != null) listener.onEdit(lead);
            dialog.dismiss();
        });

        // Chỉ hiển thị action "Xóa" nếu user là ADMIN
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userRole = sharedPreferences.getString(KEY_USER_ROLE, "");
        if ("ADMIN".equals(userRole)) {
        addActionItemLead(context, layoutAction, R.drawable.ic_garbage, "Xóa", () ->{
            if(listener != null) listener.onDelete(lead);
            dialog.dismiss();
        });
        }

        dialog.setContentView(view);
        dialog.show();
    }
}
