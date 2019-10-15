package com.bfhd.account.vo;

import com.bfhd.circle.vo.ServiceDataBean;

import java.util.List;

public class CollectVo {

    /**
     * id : 68
     * addtime : 1560482644
     * type : 4
     * content : 收藏了你的动态
     * isread : 0
     * port :
     * isdel : 0
     * onecompany : 0
     * uid : 3
     * params : {"act":"dynamicCollect","type":"event","memberid":"19","uuid":"0a2246a6ac61f6b470be4621dffdf124","dynamicid":"240","dataid":"39"}
     * title :
     * mid : 68
     * nickname : 今天，
     * avatar : /var/upload/image/2019/06/2019061411025067032.jpg
     */

    private String id;
    private String addtime;
    private String type;
    private String content;
    private String isread;
    private String port;
    private String isdel;
    private String onecompany;
    private String uid;
    private CommentVo.ParamsBean params;
    private String title;
    private String mid;
    private String nickname;
    private String avatar;

    private List<ServiceDataBean.ResourceBean> resource;

    public List<ServiceDataBean.ResourceBean> getResource() {
        return resource;
    }

    public void setResource(List<ServiceDataBean.ResourceBean> resource) {
        this.resource = resource;
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

    public CommentVo.ParamsBean getParams() {
        return params;
    }

    public void setParams(CommentVo.ParamsBean params) {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static class ParamsBean {
        /**
         * act : dynamicCollect
         * type : event
         * memberid : 19
         * uuid : 0a2246a6ac61f6b470be4621dffdf124
         * dynamicid : 240
         * dataid : 39
         */

        private String act;
        private String type;
        private String memberid;
        private String uuid;
        private String dynamicid;
        private String dataid;
        private String messageid;


        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getDynamicid() {
            return dynamicid;
        }

        public void setDynamicid(String dynamicid) {
            this.dynamicid = dynamicid;
        }

        public String getDataid() {
            return dataid;
        }

        public void setDataid(String dataid) {
            this.dataid = dataid;
        }
    }
}
