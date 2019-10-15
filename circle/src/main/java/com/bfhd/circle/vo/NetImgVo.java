package com.bfhd.circle.vo;

import android.text.TextUtils;

import java.io.Serializable;

public class NetImgVo implements Serializable {

    private String thumbURL;
    private String middleURL;
    private String hoverURL;
    private int width;
    private int height;
    private String img;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThumbURL() {
        if (!TextUtils.isEmpty(img)) {
            return img;
        }
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }

    public String getMiddleURL() {
        return middleURL;
    }

    public void setMiddleURL(String middleURL) {
        this.middleURL = middleURL;
    }

    public String getHoverURL() {
        return hoverURL;
    }

    public void setHoverURL(String hoverURL) {
        this.hoverURL = hoverURL;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
