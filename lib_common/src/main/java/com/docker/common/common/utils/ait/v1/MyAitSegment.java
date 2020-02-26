package com.docker.common.common.utils.ait.v1;

public class MyAitSegment {

    /**
     * 位于文本起始位置(include)
     */
    public int start;

    /**
     * 位于文本结束位置(include)
     */
    public int end;

    /**
     * 是否坏掉
     */
    public boolean broken = false;

    public MyAitSegment(int start, int end) {
        this.start = start;
        this.end = end;
    }

}
