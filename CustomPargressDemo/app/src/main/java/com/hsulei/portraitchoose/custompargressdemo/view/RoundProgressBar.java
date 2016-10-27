package com.hsulei.portraitchoose.custompargressdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * 圆形的进度条
 * Created by 46697 on 2016/10/26.
 */

public class RoundProgressBar extends ProgressBar {
    public RoundProgressBar(Context context) {
        super(context);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
