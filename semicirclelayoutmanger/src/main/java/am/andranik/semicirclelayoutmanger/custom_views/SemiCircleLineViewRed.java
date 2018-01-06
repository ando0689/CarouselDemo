package am.andranik.semicirclelayoutmanger.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by anna on 9/13/16.
 */

public class SemiCircleLineViewRed extends View {

    private Paint mPaint;

    private int cx;
    private int cy;
    private int radius;

    public SemiCircleLineViewRed(Context context) {
        this(context, null);
    }

    public SemiCircleLineViewRed(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SemiCircleLineViewRed(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(0xffff3361);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        cx = w;
        cy = h/2;
        radius = w/2 + 100;

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(cx, cy, radius, mPaint);
    }

}
