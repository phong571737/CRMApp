package com.example.crmmobile.CalendarDirectory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.R;

public class CalendarRow extends RecyclerView.ItemDecoration {
    private Paint paint;
    private int marginStart;
    private int marginEnd;
    private int spanCount = 7;

    public CalendarRow(Context context){
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.gray1));

        paint.setStrokeWidth(TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        1,
                        context.getResources().getDisplayMetrics())
        );

        marginStart = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics()
        );
        marginEnd = marginStart;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();

        for (int i = 0;i < childCount; i++){
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);

            if (position == RecyclerView.NO_POSITION) continue;

            float left = parent.getPaddingLeft() + marginStart;
            float rigt = parent.getWidth() - parent.getPaddingRight() - marginEnd;
            if ((position) % spanCount == 0){
                float top = child.getTop();
                c.drawLine(left, top, rigt, top, paint);
            }
        }
    }
}
