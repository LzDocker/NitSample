package com.docker.common.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MinnerGroup extends LinearLayout {

    public MinnerGroup(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
    }

    public MinnerGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(LinearLayout.VERTICAL);
    }

    public MinnerGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return true;
    }

}
