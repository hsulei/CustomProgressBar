package com.hsulei.portraitchoose.custompargressdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.hsulei.portraitchoose.custompargressdemo.R;

/**
 * 水平的进度条
 * Created by 46697 on 2016/10/26.
 */

public class HorizontalProgressBar extends ProgressBar {

    private final static String TAG = "HorizontalProgressBar";

    //定义7个自定义属性
    //到达的颜色
    private final static int REACH_COLOR = Color.BLUE;
    //达到的宽度
    private final static float REACH_HEIGHT = 2;
    //显示文字颜色
    private final static int TEXT_COLOR = Color.BLACK;
    //显示文字的大小
    private final static float TEXT_SIZE = 10;
    //没有完成的颜色
    private final static int UNREACH_COLOR = Color.GRAY;
    //没有到达的宽度
    private final static float UNREACH_HEIGHT = 2;
    //文字边上的间距
    private final static float TEXT_MARGIN = 4;


    private int mReachColor = REACH_COLOR;
    private float mReachHeight = dp2px(REACH_HEIGHT);
    private int mTextColor = TEXT_COLOR;
    private float mTextSize = sp2px(TEXT_SIZE);
    private int mUnreachColor = UNREACH_COLOR;
    private float mUnreachHeight = dp2px(UNREACH_HEIGHT);
    private float mTextMargin = dp2px(TEXT_MARGIN);


    private Paint mPaint;

    private int mRealWidth;

    public HorizontalProgressBar(Context context) {
        super(context);
        getAttrs(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
    }


    private void getAttrs(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar);

            int len = array.getIndexCount();
            for (int i = 0; i < len; i++) {
                int attr = array.getIndex(i);
                switch (attr) {
                    case R.styleable.HorizontalProgressBar_reach_color:
                        mReachColor = array.getColor(attr, REACH_COLOR);
                        break;
                    case R.styleable.HorizontalProgressBar_reach_height:
                        mReachHeight = array.getDimension(attr, dp2px(REACH_HEIGHT));
                        break;
                    case R.styleable.HorizontalProgressBar_text_color:
                        mTextColor = array.getColor(attr, TEXT_COLOR);
                        break;
                    case R.styleable.HorizontalProgressBar_text_size:
                        mTextSize = array.getDimension(attr, sp2px(TEXT_SIZE));
                        break;
                    case R.styleable.HorizontalProgressBar_unreach_color:
                        mUnreachColor = array.getColor(attr, UNREACH_COLOR);
                        break;
                    case R.styleable.HorizontalProgressBar_unreach_height:
                        mUnreachHeight = array.getDimension(attr, dp2px(UNREACH_HEIGHT));
                        break;
                    case R.styleable.HorizontalProgressBar_text_margin:
                        mTextMargin = array.getDimension(attr, dp2px(TEXT_MARGIN));
                        break;
                }
            }

            array.recycle();

            mPaint = new Paint();
            mPaint.setAntiAlias(true);//设置抗锯齿
        }
    }


    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);//默认给一个值
        int height = measureHeight(heightMeasureSpec);
        mRealWidth = width - getPaddingLeft() - getPaddingRight();//拿到真正的宽度
        setMeasuredDimension(width, height);

    }

    /**
     * 得到真正的高度
     *
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight(int heightMeasureSpec) {
        int height = 0;
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            //获取文本的高度
            mPaint.setTextSize(mTextSize);
            float textSize = mPaint.descent() - mPaint.ascent();
            //文字，reach，unreach  之间最大的
            height = (int) Math.max(Math.max(mUnreachHeight, mReachHeight), Math.abs(textSize)) + getPaddingTop() + getPaddingBottom();
            if (mode == MeasureSpec.AT_MOST) {
                height = Math.min(height, size);
            }
        }
        return height;
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(), getHeight() / 2);//移动画布到progressbar的中间

        boolean noNeedUnreach = false;//是否需要绘制未到达部分的内容

        //得到当前的总比例
        float ratio = getProgress() * 1.0f / getMax();//得到应该展示的比例
        Log.i(TAG, "progress:" + getProgress());
        Log.i(TAG, "max" + getMax());
        //得到绘制的已完成的长度
        float progressX = ratio * mRealWidth;
        //进去与文字的间隙
        float endX = progressX - mTextMargin / 2;

        String text = getProgress() + "%";
        //得到文字的长度
        float mTextWidth = mPaint.measureText(text);

        if (progressX + mTextWidth >= mRealWidth) {
            progressX = mRealWidth - mTextWidth;//重新赋值
            noNeedUnreach = true;
        }
        if (endX > 0) {
            //画到达的内容
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }
        //画文字内容
        mPaint.setColor(mTextColor);
        float y = -(mPaint.descent() + mPaint.ascent()) / 2;
        canvas.drawText(text, progressX, y, mPaint);


        //画没有完成的部分
        if (!noNeedUnreach) {
            mPaint.setColor(mUnreachColor);
            mPaint.setStrokeWidth(mUnreachHeight);
            canvas.drawLine(progressX + mTextWidth + mTextMargin / 2, 0, mRealWidth, 0, mPaint);
        }

        canvas.restore();
    }

    /**
     * 把dp值转换成px值
     *
     * @param value
     * @return
     */
    private float dp2px(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    /**
     * 把pt值转换成px值
     *
     * @param value
     * @return
     */
    private float sp2px(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }
}
