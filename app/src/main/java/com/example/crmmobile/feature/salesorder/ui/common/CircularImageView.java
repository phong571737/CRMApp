package com.example.crmmobile.feature.salesorder.ui.common;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

public class CircularImageView extends AppCompatImageView {
    private Paint paint;

    public CircularImageView(Context context) {
        super(context);
        init();
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = getBitmapFromDrawable();
        if (bitmap != null) {
            Bitmap circleBitmap = getCircularBitmap(bitmap);
            canvas.drawBitmap(circleBitmap, 0, 0, null);
        } else {
            super.onDraw(canvas);
        }
    }

    private Bitmap getBitmapFromDrawable() {
        if (getDrawable() == null) return null;
        try {
            return Bitmap.createBitmap(getDrawable().getIntrinsicWidth(),
                    getDrawable().getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        } catch (Exception e) {
            return null;
        }
    }

    private Bitmap getCircularBitmap(Bitmap bitmap) {
        int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        float radius = size / 2f;
        canvas.drawCircle(radius, radius, radius, paint);

        return output;
    }
}
