package com.yxf.customviewutils.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import static java.lang.Math.PI;

/**
 * Created by yuxiongfeng.
 * Date: 2019/5/28
 */
public class LoadingViewDemo extends View {

    public static final int LEFT_DOWN = 1;
    public static final int LEFT_UP = 2;
    public static final int RIGHT_DOWN = 3;
    public static final int RIGHT_UP = 4;
    private Paint mPaint1, mPaint2, mPaint3, mPaint4, mPaint5;
    private int currentValue;
    private int currentStatus = LEFT_DOWN;

    private int circleRadius = 10; //小球的半径
    private int distance = 60; //小球开始下落到最低点的距离


    public LoadingViewDemo(Context context) {
        this(context, null);
    }

    public LoadingViewDemo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint1 = getColor(Color.RED);
        mPaint2 = getColor(Color.YELLOW);
        mPaint3 = getColor(Color.GREEN);
        mPaint4 = getColor(Color.BLUE);
        mPaint5 = getColor(Color.CYAN);

        ValueAnimator animator = ValueAnimator.ofInt(0, 90);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue = (int) animation.getAnimatedValue();
                Log.d("addUpdateListener : ", animation.getAnimatedValue().toString());

                invalidate();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                Log.d("addListener repeat : ", String.valueOf(currentValue));
                switch (currentStatus) {
                    case LEFT_DOWN:
                        currentStatus=RIGHT_UP;
                        break;
                    case LEFT_UP:
                        currentStatus=LEFT_DOWN;
                        break;
                    case RIGHT_DOWN:
                        currentStatus=LEFT_UP;
                        break;
                    case RIGHT_UP:
                        currentStatus=RIGHT_DOWN;
                        break;
                }
            }
        });

        animator.setStartDelay(500);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private Paint getColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFourBall(canvas);
        int x, y;
        double cosValue = Math.cos(PI * currentValue / 180);
        double sinValue = Math.sin(PI * currentValue / 180);
        drawFourBall(canvas);
        switch (currentStatus) {
            case LEFT_DOWN://最左边小球往下撞击
                x = circleRadius + (int) ((distance - circleRadius) * (1 - cosValue));
                y = getHeight() - distance + (int) ((distance - circleRadius) * sinValue);
                canvas.drawCircle(x, y, circleRadius, mPaint1);
                break;
            case RIGHT_UP://最右边小球往上撞击
                x = distance + 8 * circleRadius + (int) ((distance - circleRadius) * sinValue);
                y = getHeight() - distance + (int) (cosValue * (distance - circleRadius));
                canvas.drawCircle(x, y, circleRadius, mPaint5);
                break;
            case RIGHT_DOWN://最右边小球往下撞击
                x = distance + 8 * circleRadius + (int) ((distance - circleRadius) * (cosValue));
                y = (getHeight() - distance) + (int) ((distance - circleRadius) * (sinValue));
                canvas.drawCircle(x, y, circleRadius, mPaint5);
                break;
            case LEFT_UP://最左边小球往上撞击
                x = distance - (int) ((distance - circleRadius) * sinValue);
                y = getHeight() - distance + (int) ((distance - circleRadius) * cosValue);
                canvas.drawCircle(x, y, circleRadius, mPaint1);
                break;
        }


    }

    private void drawFourBall(Canvas canvas){
        int y = getHeight() - circleRadius;
        canvas.drawCircle(distance + 2 * circleRadius, y, circleRadius, mPaint2);
        canvas.drawCircle(distance + 4 * circleRadius, y, circleRadius, mPaint3);
        canvas.drawCircle(distance + 6 * circleRadius, y, circleRadius, mPaint4);

        if (currentStatus == LEFT_DOWN || currentStatus == LEFT_UP) {//最左边球运动的时候，要绘制最右边的球
            canvas.drawCircle(distance + 8 * circleRadius, y, circleRadius, mPaint5);
        } else if (currentStatus == RIGHT_DOWN || currentStatus == RIGHT_UP) {//最右边球运动的时候，要绘制最左边的球
            canvas.drawCircle(distance, y, circleRadius, mPaint1);
        }
    }

}
