package am.andranik.semicirclelayoutmanger.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by anna on 9/13/16.
 */

public class SemiCircleLineView extends View {

    private Paint mPaint;

    private int cx;
    private int cy;
    private int radius;

    public SemiCircleLineView(Context context) {
        this(context, null);
    }

    public SemiCircleLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SemiCircleLineView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(0xffA8B6C6);
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
