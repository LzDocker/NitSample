package com.bfhd.circle.vo.bean;

import java.io.Serializable;

/**
 * Created by gk on 2017/12/3 0003.
 */

public class ReleaseDyamicBean implements Serializable{
    private String t;
    private String img;
    private String url;
    private int sort;
    private boolean isUpload;
    private int postion;
    private String isUpsecess;
    private boolean isUpLoaded;
    private String imgPath;
    private String height;
    private String width;
    private String locVideoPath;
    private String videoUrl;
    private String videoImgPath;
    private String videoImgUrl;



    public boolean isUpLoaded() {
        return isUpLoaded;
    }

    public void setUpLoaded(boolean upLoaded) {
        isUpLoaded = upLoaded;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public String getIsUpsecess() {
        return isUpsecess;
    }

    public void setIsUpsecess(String isUpsecess) {
        this.isUpsecess = isUpsecess;
    }

    public String getLocVideoPath() {
        return locVideoPath;
    }

    public void setLocVideoPath(String locVideoPath) {
        this.locVideoPath = locVideoPath;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoImgPath() {
        return videoImgPath;
    }

    public void setVideoImgPath(String videoImgPath) {
        this.videoImgPath = videoImgPath;
    }

    public String getVideoImgUrl() {
        return videoImgUrl;
    }

    public void setVideoImgUrl(String videoImgUrl) {
        this.videoImgUrl = videoImgUrl;
    }
}
