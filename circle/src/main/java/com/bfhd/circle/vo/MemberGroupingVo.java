package com.bfhd.circle.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MemberGroupingVo implements Serializable {

    private String classid; //分类ID
    @SerializedName(value = "classname")
    private String name;//分类名称s
    private String listorder;//排序
    private boolean isClicked;//是否选择
    private String isDelClcked;//是否显示侧滑删除动画
    private String select;
    private String groupid;
    private String groupName;
    private String selected;


    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListorder() {
        return listorder;
    }

    public void setListorder(String listorder) {
        this.listorder = listorder;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public String getIsDelClcked() {
        return isDelClcked;
    }

    public void setIsDelClcked(String isDelClcked) {
        this.isDelClcked = isDelClcked;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
}
