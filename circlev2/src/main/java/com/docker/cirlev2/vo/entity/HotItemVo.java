package com.docker.cirlev2.vo.entity;

public class HotItemVo {
    private  String id;
    private  String name;
    private  boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public HotItemVo(String name) {
        this.name = name;
    }

    public HotItemVo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
