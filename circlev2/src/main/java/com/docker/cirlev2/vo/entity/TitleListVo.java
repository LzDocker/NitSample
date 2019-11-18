package com.docker.cirlev2.vo.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TitleListVo implements Serializable {


    private String classid;
    private String isedit;//是否可编辑
    private String ischild;//是否允许设置子菜单
    private String apiurlid;
    private String circleid;
    private String utid;
    private String apiurl;
    private String inputtime;
    @SerializedName(value = "classname")
    private String name;
    private String update;
    private String listorder;
    private String select;
    private String dataType;
    private boolean isClicked;//是否选择
    private String isDelClcked;//是否显示侧滑删除动画
    private List<ChildBean> childClass;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getApiurl() {
        return apiurl;
    }

    public void setApiurl(String apiurl) {
        this.apiurl = apiurl;
    }

    public String getIsedit() {
        return isedit;
    }

    public void setIsedit(String isedit) {
        this.isedit = isedit;
    }

    public String getIschild() {
        return ischild;
    }

    public void setIschild(String ischild) {
        this.ischild = ischild;
    }

    public String getApiurlid() {
        return apiurlid;
    }

    public void setApiurlid(String apiurlid) {
        this.apiurlid = apiurlid;
    }

    public String getCircleid() {
        return circleid;
    }

    public void setCircleid(String circleid) {
        this.circleid = circleid;
    }

    public String getUtid() {
        return utid;
    }

    public void setUtid(String utid) {
        this.utid = utid;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public List<ChildBean> getChildClass() {
        return childClass;
    }

    public void setChildClass(List<ChildBean> childClass) {
        this.childClass = childClass;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
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

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
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


    public static class ChildBean implements Serializable {
        /**
         * classid : 13
         * className : 二级分类1
         * listorder : 0
         * update : 0
         */

        private String classid;
        @SerializedName(value = "classname")
        private String name;
        private String listorder;
        private String update;
        private String select;
        private String apiurl;
        private String dataType;
        private boolean isClicked;//是否选择
        private String isDelClcked;//是否显示侧滑删除动画

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getApiurl() {
            return apiurl;
        }

        public void setApiurl(String apiurl) {
            this.apiurl = apiurl;
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

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public String getSelect() {
            return select;
        }

        public void setSelect(String select) {
            this.select = select;
        }
    }

}
