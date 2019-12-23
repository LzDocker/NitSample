package com.docker.nitsample.vo;

import android.support.v7.widget.PagerSnapHelper;

public class LayoutManagerVo {

    private int orientation;//如果是LinearLayout  0 水平  1 垂直
    private int spanCount;//如果是grid 每行显示的列数
    private boolean reverseLayout;

    public LayoutManagerVo() {
    }


    public LayoutManagerVo(int orientation, int spanCount, boolean reverseLayout) {
        this.orientation = orientation;
        this.spanCount = spanCount;
        this.reverseLayout = reverseLayout;

    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    public boolean isReverseLayout() {
        return reverseLayout;
    }

    public void setReverseLayout(boolean reverseLayout) {
        this.reverseLayout = reverseLayout;
    }
}
