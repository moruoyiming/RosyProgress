package com.calypso.rosyprogress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Project：RosyProgress
 * Created：jianz
 * Date：2017/4/14 15:45
 * Summry：
 */

public class RosyProgress extends View {

    private Paint defaultCriclePaint;

    private Paint mProgressPaint;

    private Paint mSmallCircleSolidePaint;

    private Paint mSmallCirclePaint;

    private Paint mTextPaint;

    private Point mCenterPoint;

    private int[] mGradientColors = {Color.parseColor("#1fd360"), Color.parseColor("#277ddf")};

    private SweepGradient mSweepGradient;

    private float mStartSweepValue = -90;

    private float currentAngle;

    private long countdownTime;

    private String textDesc;

    private Path mPath;

    private float extraDistance = 0.7F;

    private float mMaxValue;

    private float mValue;

    private boolean antiAlias;

    private RectF mRectF;
    //默认圆
    private int mCircleSolideColor;

    private int mCircleStrokeColor;

    private int mCircleStrokeWidth;

    private int mCircleRadius;
    //进度条
    private int progressColor;

    private int progressWidth;
    //默认圆边框上面的小圆
    private int mSmallCircleSolideColor;

    private int mSmallCircleStrokeColor;

    private int mSmallCircleStrokeWidth;

    private int smallCircleRadius;
    //最里层的文字
    private int mTextColor;

    private int mTextSize;

    private boolean mIsShowSmall;

    private long mAnimatorTime = 2000;

    public RosyProgress(Context context) {
        this(context, null);
    }

    public RosyProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RosyProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initPaint();
        initPath();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterPoint.x = getMeasuredWidth() / 2;
        mCenterPoint.y = getMeasuredHeight() / 2;
        mRectF.left = mCenterPoint.x - mCircleRadius;
        mRectF.top = mCenterPoint.y - mCircleRadius;
        mRectF.right = mCenterPoint.x + mCircleRadius;
        mRectF.bottom = mCenterPoint.y + mCircleRadius;
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RosyProgress);
        mIsShowSmall = typedArray.getBoolean(R.styleable.RosyProgress_is_show_small, ProgressConfig.ISSHOW_SMALL);
        antiAlias = typedArray.getBoolean(R.styleable.RosyProgress_antiAlias, true);
        mMaxValue = typedArray.getFloat(R.styleable.RosyProgress_maxValue, ProgressConfig.DEFAULT_MAX_VALUE);
        mValue = typedArray.getFloat(R.styleable.RosyProgress_value, ProgressConfig.DEFAULT_VALUE);
        mCircleSolideColor = typedArray.getColor(R.styleable.RosyProgress_circle_solide_color, Color.parseColor("#e7e3e3"));
        mCircleStrokeColor = typedArray.getColor(R.styleable.RosyProgress_circle_stroke_color, Color.parseColor("#e7e3e3"));
        mCircleStrokeWidth = (int) typedArray.getDimension(R.styleable.RosyProgress_circle_stroke_width, ProgressConfig.CIRCLE_STROKE_WIDTH);
        mCircleRadius = (int) typedArray.getDimension(R.styleable.RosyProgress_circle_radius, dp2px(ProgressConfig.CIRCLE_RADIUS));
        progressColor = typedArray.getColor(R.styleable.RosyProgress_progress_color, Color.RED);
        progressWidth = (int) typedArray.getDimension(R.styleable.RosyProgress_progress_width, ProgressConfig.PROGRESS_WIDTH);
        mSmallCircleSolideColor = typedArray.getColor(R.styleable.RosyProgress_small_circle_solide_color, Color.parseColor("#ffffff"));
        mSmallCircleStrokeColor = typedArray.getColor(R.styleable.RosyProgress_small_circle_stroke_color, Color.parseColor("#6c9bda"));
        mSmallCircleStrokeWidth = (int) typedArray.getDimension(R.styleable.RosyProgress_small_circle_stroke_width, ProgressConfig.SMALL_CIRCLE_STROKE_WIDTH);
        smallCircleRadius = (int) typedArray.getDimension(R.styleable.RosyProgress_small_circle_radius, ProgressConfig.SMALL_CIRCLE_RADIUS);
        mTextSize = (int) typedArray.getDimension(R.styleable.RosyProgress_text_size, dp2px(ProgressConfig.TEXT_SIZE));
        mTextColor = typedArray.getColor(R.styleable.RosyProgress_text_color, Color.WHITE);
        typedArray.recycle();
    }

    private void initPaint() {
        mCenterPoint = new Point();
        mRectF = new RectF();
        defaultCriclePaint = new Paint();
        defaultCriclePaint.setAntiAlias(true);
        defaultCriclePaint.setDither(true);
        defaultCriclePaint.setStyle(Paint.Style.STROKE);
        defaultCriclePaint.setStrokeWidth(mCircleStrokeWidth);
        defaultCriclePaint.setColor(mCircleStrokeColor);

        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setDither(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mCircleStrokeWidth);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        mSmallCirclePaint = new Paint();
        mSmallCirclePaint.setAntiAlias(true);
        mSmallCirclePaint.setDither(true);
        mSmallCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mSmallCirclePaint.setStrokeWidth(mSmallCircleStrokeWidth);
        mSmallCirclePaint.setColor(mSmallCircleStrokeColor);

        mSmallCircleSolidePaint = new Paint();
        mSmallCircleSolidePaint.setAntiAlias(true);
        mSmallCircleSolidePaint.setDither(true);
        mSmallCircleSolidePaint.setStyle(Paint.Style.FILL);
        mSmallCirclePaint.setStrokeWidth(smallCircleRadius - mSmallCircleStrokeWidth);
        mSmallCircleSolidePaint.setColor(mSmallCircleSolideColor);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    private void initPath() {

    }

    public void setValueAnimator(final float start, float end, long animTime) {//final OnCountdownFinishListener countdownFinishListener
        setClickable(false);
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setDuration(animTime);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngle = (float) animation.getAnimatedValue();
                mValue = currentAngle * mMaxValue;
                invalidate();
            }
        });
        animator.start();
    }

    public void setValue(float value) {
        currentAngle = 0;
        if (value > mMaxValue) {
            value = mMaxValue;
        }
        float start = currentAngle;
        float end = value / mMaxValue;
        setValueAnimator(start, end, mAnimatorTime);
    }

    private void drawProgress(Canvas canvas) {
        textDesc = String.format("%.0f%%", currentAngle * 100);
        float textWidth = mTextPaint.measureText(textDesc);
        float textHeight = (mTextPaint.descent() + mTextPaint.ascent()) / 2;
        canvas.drawText(textDesc, getMeasuredWidth()/2 - textWidth / 2, getMeasuredHeight()/2 , mTextPaint);
    }


    private void drawCircle(Canvas canvas) {
        canvas.save();
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mCircleRadius, defaultCriclePaint);
        canvas.rotate(270, mCenterPoint.x, mCenterPoint.y);
        mSweepGradient = new SweepGradient(mCenterPoint.x, mCenterPoint.y, mGradientColors, null);
        mProgressPaint.setShader(mSweepGradient);
        canvas.drawArc(mRectF, 3, 360 * currentAngle, false, mProgressPaint);
        if (mIsShowSmall) {
            float currentDegreeFlag = 360 * currentAngle + extraDistance + 90;
            float smallCircleX = 0, smallCircleY = 0;
            float hudu = (float) Math.abs(Math.PI * currentDegreeFlag / 180);//Math.abs：绝对值 ，Math.PI：表示π ， 弧度 = 度*π / 180
            smallCircleX = (float) Math.abs(Math.sin(hudu) * (mCircleRadius ) + mCircleRadius )+ mCircleStrokeWidth / 2 ;
            smallCircleY = (float) Math.abs(mCircleRadius - Math.cos(hudu) * mCircleRadius)+ mCircleStrokeWidth / 2 + getPaddingTop();
            canvas.drawCircle(smallCircleX, smallCircleY, smallCircleRadius, mSmallCirclePaint);
            canvas.drawCircle(smallCircleX, smallCircleY, smallCircleRadius - mSmallCircleStrokeWidth, mSmallCircleSolidePaint);//画小圆的实心
            canvas.restore();
        } else {
            canvas.restore();
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int strokeWidth = Math.max(mCircleStrokeWidth, progressWidth);
        if (widthMode != MeasureSpec.EXACTLY) {
            int widthSize = getPaddingLeft() + mCircleRadius * 2 + strokeWidth + getPaddingRight();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            int heightSize = getPaddingTop() + mCircleRadius * 2 + strokeWidth + getPaddingBottom();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawProgress(canvas);
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    public int getmCircleRadius() {
        return mCircleRadius;
    }

    public void setmCircleRadius(int mCircleRadius) {
        this.mCircleRadius = mCircleRadius;
        mRectF.left = mCenterPoint.x - mCircleRadius;
        mRectF.top = mCenterPoint.y - mCircleRadius;
        mRectF.right = mCenterPoint.x + mCircleRadius;
        mRectF.bottom = mCenterPoint.y + mCircleRadius;
    }

    public boolean ismIsShowSmall() {
        return mIsShowSmall;
    }

    public void setmIsShowSmall(boolean mIsShowSmall) {
        this.mIsShowSmall = mIsShowSmall;
    }

    public long getmAnimatorTime() {
        return mAnimatorTime;
    }

    public void setmAnimatorTime(long mAnimatorTime) {
        this.mAnimatorTime = mAnimatorTime;
    }

    public int getmCircleStrokeWidth() {
        return mCircleStrokeWidth;
    }

    public void setmCircleStrokeWidth(int mCircleStrokeWidth) {
        this.mCircleStrokeWidth = mCircleStrokeWidth;
        mProgressPaint.setStrokeWidth(mCircleStrokeWidth);
        defaultCriclePaint.setStrokeWidth(mCircleStrokeWidth);
    }
}
