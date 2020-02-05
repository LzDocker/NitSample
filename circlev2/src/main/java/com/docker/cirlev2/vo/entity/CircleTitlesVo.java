package com.docker.cirlev2.vo.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.docker.cirlev2.BR;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CircleTitlesVo extends BaseObservable implements Serializable {

    @Bindable
    public boolean getIsEditPro() {
        return isEditPro;
    }

    public void setIsEditPro(boolean editPro) {
        isEditPro = editPro;
        notifyPropertyChanged(BR.isEditPro);
    }

    /**
     * classid : 12
     * update : 0
     * className : 一级分类1
     * listorder : 0
     * child : [{"classid":"13","className":"二级分类1","listorder":"0","update":"0"},{"classid":"14","className":"二级分类3","listorder":"1","update":"0"}]
     */


    @Bindable
    private boolean isEditPro = false;

    private String classid;
    private String isedit;//是否可编辑TitleListBean
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
    private boolean isClicked;//是否选择 前端字段
    private String isDelClcked;//是否显示侧滑删除动画
    private List<CircleTitlesVo> childClass;

    private int showType; // 1.动态 2 h5

    private String h5_url;

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public String getH5_url() {
        return h5_url;
    }

    public void setH5_url(String h5_url) {
        this.h5_url = h5_url;
    }

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

    public List<CircleTitlesVo> getChildClass() {
        return childClass;
    }

    public void setChildClass(List<CircleTitlesVo> childClass) {
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


        /*
        * "id":"39",
"inputtime":"1559545669",
"classname":"1111",
"listorder":"0",
"circleid":"31",
"utid":"b0e3865bdfe61e2ad625763ddf842881",
"parentid":"35",
"ischild":"0",
"isedit":"0",
"apiurlid":"0",
"classid":"39",
"apiurl":"",
"dataType":""
        * */

        private String isedit;
        private String ischild;


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
