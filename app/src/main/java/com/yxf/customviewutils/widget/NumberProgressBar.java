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
 * Date: 2019/5/16
 */
public class NumberProgressBar extends View {
    private int currentProgress;
    private int maxProgress = 100;

    private float unReachHeight;
    private float reachHeight;

    private int unReachColor;
    private int reachColor;

    private float textSize;
    private int textColor;
    private int textVisible;
    private float mDrawTextWidth;
    private static final int PROGRESS_TEXT_VISIBLE = 0;

    /**
     * The drawn text start.
     */
    private float mDrawTextStart;

    /**
     * The drawn text end.
     */
    private float mDrawTextEnd;

    /**
     * The prefix.  前缀
     */
    private String mPrefix = "";

    private String mCurrentDrawText;

    /**
     * The suffix of the number.  后缀
     */
    private String mSuffix = "%";


    private Paint mReachPaint;
    private Paint mUnReachPaint;
    private Paint mTxtPaint;

    private RectF mUnReachRecf;
    private RectF mReachRecf;
    private boolean isDrawTextVisibility;

    private OnProgressBarListener mListener;

    public NumberProgressBar(Context context) {
        this(context, null);
    }

    public NumberProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberProgressBar);
        currentProgress = typedArray.getInteger(R.styleable.NumberProgressBar_progress_current, -1);
        maxProgress = typedArray.getInteger(R.styleable.NumberProgressBar_progress_max, -1);

        unReachHeight = typedArray.getDimension(R.styleable.NumberProgressBar_progress_unreach_height, -1);
        reachHeight = typedArray.getDimension(R.styleable.NumberProgressBar_progress_reach_height, -1);

        unReachColor = typedArray.getColor(R.styleable.NumberProgressBar_progress_unreach_color, -1);
        reachColor = typedArray.getColor(R.styleable.NumberProgressBar_progress_reach_color, -1);

        textSize = typedArray.getDimension(R.styleable.NumberProgressBar_progress_text_size, -1);
        textColor = typedArray.getColor(R.styleable.NumberProgressBar_progress_text_color, -1);
        textVisible = typedArray.getInteger(R.styleable.NumberProgressBar_progress_text_visibility, -1);
        if (textVisible==PROGRESS_TEXT_VISIBLE) {
            isDrawTextVisibility=true;
        }
        typedArray.recycle();

        init();
    }

    private void init() {
        mReachPaint = new Paint();
        mReachPaint.setAntiAlias(true);
        mReachPaint.setColor(reachColor);

        mUnReachPaint = new Paint();
        mUnReachPaint.setAntiAlias(true);
        mUnReachPaint.setColor(unReachColor);

        mTxtPaint = new Paint();
        mTxtPaint.setAntiAlias(true);
        mTxtPaint.setColor(textColor);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isDrawTextVisibility) {
            calculateDrawRectF();
            canvas.drawText(mCurrentDrawText,mReachRecf.right,getHeight()/2,mTxtPaint);
        } else {
            calculateDrawRectFWithoutProgressText();
        }

        canvas.drawRect(mReachRecf, mReachPaint);
        canvas.drawRect(mUnReachRecf, mUnReachPaint);
    }

    private void calculateDrawRectF() {
        mCurrentDrawText = String.valueOf(getmPercent() * 100);
        mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
        mDrawTextWidth = mTxtPaint.measureText(mCurrentDrawText);

        int height=getHeight();
        int totalWidth=getWidth();
        float newToalWidth=totalWidth-mDrawTextWidth;
        float reachWidth=newToalWidth*getmPercent();
        mReachRecf = new RectF();
        mReachRecf.left = getPaddingLeft();
        mReachRecf.right = getPaddingLeft() + reachWidth;
        mReachRecf.top = height / 2 - reachHeight / 2;
        mReachRecf.bottom = height / 2 + reachHeight / 2;

        mUnReachRecf = new RectF();
        mUnReachRecf.left = mReachRecf.right+mDrawTextWidth;
        mUnReachRecf.right = totalWidth - getPaddingRight();
        mUnReachRecf.top = height / 2 - unReachHeight / 2;
        mUnReachRecf.bottom = height / 2 + unReachHeight / 2;

    }

    private void calculateDrawRectFWithoutProgressText() {
        int width = getWidth();
        int height = getHeight();

        mReachRecf = new RectF();
        mReachRecf.left = getPaddingLeft();
        mReachRecf.right = getPaddingLeft() + (width - getPaddingLeft() - getPaddingRight()) * getmPercent();
        mReachRecf.top = height / 2 - reachHeight / 2;
        mReachRecf.bottom = height / 2 + reachHeight / 2;

        mUnReachRecf = new RectF();
        mUnReachRecf.left = mReachRecf.right;
        mUnReachRecf.right = width - getPaddingRight();
        mUnReachRecf.top = height / 2 - unReachHeight / 2;
        mUnReachRecf.bottom = height / 2 + unReachHeight / 2;
    }

    private float getmPercent() {
        return currentProgress / (float)maxProgress;
    }

    public void setOnProgressListener(OnProgressBarListener listener) {
        this.mListener = listener;
    }

    public void incrementProgressBy(int by) {
        if (by > 0) {
            setProgress(currentProgress + by);
        }

        if (mListener != null) {
            mListener.onProgressChange(currentProgress, maxProgress);
        }
    }

    public void setProgress(int progress) {
        if (progress <= maxProgress && progress >= 0) {
            this.currentProgress = progress;
            postInvalidate();
        }
    }

}

