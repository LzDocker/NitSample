package com.docker.nitsample.vo;

import android.databinding.BaseObservable;

public class RecycleTopLayout extends BaseObservable {
    private String title;//左侧的标题
    private String rightContent; //右侧的文字
    private boolean isJump;//是否有右侧的箭头

    public RecycleTopLayout(String title, String rightContent, boolean isJump) {
        this.title = title;
        this.rightContent = rightContent;
        this.isJump = isJump;
    }

    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRightContent() {
        return rightContent;
    }

    public void setRightContent(String rightContent) {
        this.rightContent = rightContent;
    }
}
