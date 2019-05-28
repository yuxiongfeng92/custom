package com.yxf.customviewutils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yuxiongfeng.
 * Date: 2019/5/28
 */
public class WaveView extends View {
    float minRadius;
    float maxRadius;
    int duration = 3000;
    int speed = 500;
    private Paint mPaint;
    private List<Circle> circleList = new ArrayList<>();
    private long lastTime;
    public boolean mIsRunning;
    private LinearInterpolator interpolator;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private Runnable mCreateCircle = new Runnable() {
        @Override
        public void run() {
            if (mIsRunning) {
                createCircle();
                postDelayed(mCreateCircle, speed);
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Iterator<Circle> iterator = circleList.iterator();
        while (iterator.hasNext()) {
            Circle circle = iterator.next();
            if (System.currentTimeMillis()-circle.mCreateTime<duration) {
                float radius = circle.getRadius();
                int alpha = circle.getAlpha();
                mPaint.setAlpha(alpha);
                canvas.drawCircle(getWidth()/2,getHeight()/2,radius,mPaint);
            }else {
                iterator.remove();
            }
        }

        if (circleList.size()>0) {
            postInvalidate();
        }

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            maxRadius = Math.min(w, h) / 2.0f;
    }

    public void start(){
        if (!mIsRunning) {
            mIsRunning=true;
            mCreateCircle.run();
        }
    }

    public void stop(){
        if (mIsRunning) {
            mIsRunning=false;
        }
    }

    /**
     * 创建圆
     */
    public void createCircle() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastTime < speed) {
            return;
        }
        Circle circle = new Circle();
        circleList.add(circle);
        postInvalidate();
        lastTime=currentTimeMillis;
    }

    class Circle {

        /**
         * 用于标识百分比
         */
        private long mCreateTime;

        public Circle() {
            this.mCreateTime = System.currentTimeMillis();
        }

        private float getRadius() {
            return minRadius + (maxRadius - minRadius) * getPercent();
        }

        public int getAlpha() {
            return (int) (1-getPercent()*255);
        }

        private float getPercent() {
            interpolator=new LinearInterpolator();
            return interpolator.getInterpolation((System.currentTimeMillis()-mCreateTime)* 1.0f/duration);
        }

    }

}
