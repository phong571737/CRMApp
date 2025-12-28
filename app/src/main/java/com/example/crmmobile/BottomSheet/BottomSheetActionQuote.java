package com.example.crmmobile.BottomSheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.crmmobile.LeadDirectory.Lead;
import com.example.crmmobile.QuoteDirectory.Quote;
import com.example.crmmobile.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetActionQuote {
    public interface OnActionListenerQuote{
        void onDeleteQuote(Quote quote);
        void onConvertOrder(Quote quote);
    }
    private static void addActionItemQuote(Context context, LinearLayout parent, int iconRes, String text, Runnable runnable){
        View view = LayoutInflater.from(context).inflate(R.layout.item_action, parent, false);

        ImageView icon_action = view.findViewById(R.id.actionIcon);
        TextView text_action = view.findViewById(R.id.actionText);

        icon_action.setImageResource(iconRes);
        text_action.setText(text);

        view.setOnClickListener(v -> {
            if (runnable != null) runnable.run();
        });

        parent.addView(view);
    }

    public static void ShowBottomSheetQuote(Context context, Quote item, int position, OnActionListenerQuote listener){
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_more_actions, null);

        LinearLayout layoutAction = view.findViewById(R.id.lt_more_actions);
        ImageView btnClose = view.findViewById(R.id.btn_action_close);

        btnClose.setOnClickListener(v -> dialog.dismiss());//exit

        addActionItemQuote(context, layoutAction, R.drawable.ic_loop, "Chuyển thành đơn hàng", ()->{
            if (listener != null){
                listener.onConvertOrder(item);
            }
            dialog.dismiss();
        });
//        addActionItemQuote(context, layoutAction, R.drawable.ic_loop, "Chuyển thành hóa đơn", null);
        addActionItemQuote(context, layoutAction, R.drawable.delete, "Xóa", ()->{
            if (listener != null){
                listener.onDeleteQuote(item);
            }
            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();
    }
}
