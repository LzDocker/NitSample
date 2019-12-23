package com.docker.nitsample.vo;

public class CarRvHorizontalVo {
    private  String img;
    private  String title;
    private  String address;
    private  String time;
    private  int status;

    public CarRvHorizontalVo(String img, String title, String address, String time, int status) {
        this.img = img;
        this.title = title;
        this.address = address;
        this.time = time;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
