package com.yxf.customviewutils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yxf.customviewutils.R;

/**
 * Created by yuxiongfeng.
 * Date: 2019/5/17
 */
public class CustomCircle extends View {
    float radius;
    int progress;
    int gb;
    int progressColor;
    int circleColor;
    float strokeWidth;
    private Paint mPaint;
    private boolean isFirst = true;
    private RectF mRectF;
    private float degree;

    public CustomCircle(Context context) {
        this(context, null);
    }

    public CustomCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCircle);

        if (typedArray != null) {
            radius = typedArray.getDimension(R.styleable.CustomCircle_radius, -1);
            progress = typedArray.getInteger(R.styleable.CustomCircle_progress, -1);
            gb = typedArray.getResourceId(R.styleable.CustomCircle_bg, -1);
            progressColor = typedArray.getColor(R.styleable.CustomCircle_progressColor, -1);
            circleColor = typedArray.getColor(R.styleable.CustomCircle_circleColor, -1);
            strokeWidth = typedArray.getFloat(R.styleable.CustomCircle_stroke_width, -1);
            typedArray.recycle();
        }

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setARGB(255, 255, 0, 0);
        mPaint.setStrokeWidth(strokeWidth);

        mRectF = new RectF();
        mRectF.left = strokeWidth / 2;
        mRectF.top = strokeWidth / 2;
        mRectF.right = 2 * radius - strokeWidth / 2;
        mRectF.bottom = 2 * radius - strokeWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            mPaint.setColor(circleColor);
            float realRadius = radius - strokeWidth / 2;
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, realRadius, mPaint);
        }


        mPaint.setColor(progressColor);
        canvas.drawArc(mRectF, 0, degree, false, mPaint);
    }


    public void setProgress(float progress) {
        degree = (progress / 100) * 360;
        postInvalidate();
    }
}
