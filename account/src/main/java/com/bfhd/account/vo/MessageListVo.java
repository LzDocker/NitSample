package com.bfhd.account.vo;

import android.databinding.BaseObservable;

import java.io.Serializable;
import java.util.Objects;

public class MessageListVo extends BaseObservable implements Serializable {

    /**
     * id : 14
     * addtime : 1559815555
     * type : 1
     * content : 许振关注了你！
     * isread : 0
     * port :
     * isdel : 0
     * onecompany : 0
     * uid : 3
     * params : {"act":"userFocus","memberid":"6","uuid":"76c4922812687874a71ceee5efec8721"}
     * title :
     * mid : 14
     * notReadMsgNum : 8
     */

    private String id;
    private String addtime;
    private String type;
    private String content;



    private String isread;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageListVo that = (MessageListVo) o;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    private String port;
    private String isdel;
    private String onecompany;
    private String uid;
    private ParamsBean params;
    private String title;
    private String mid;
    private String notReadMsgNum;
    private int  icon;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getOnecompany() {
        return onecompany;
    }

    public void setOnecompany(String onecompany) {
        this.onecompany = onecompany;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getNotReadMsgNum() {
        return notReadMsgNum;
    }

    public void setNotReadMsgNum(String notReadMsgNum) {
        this.notReadMsgNum = notReadMsgNum;
    }

    public static class ParamsBean {
        /**
         * act : userFocus
         * memberid : 6
         * uuid : 76c4922812687874a71ceee5efec8721
         */

        private String act;
        private String memberid;
        private String uuid;

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}