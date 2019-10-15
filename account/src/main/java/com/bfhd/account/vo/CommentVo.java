package com.bfhd.account.vo;

import android.databinding.BaseObservable;

import com.bfhd.circle.vo.ServiceDataBean;

import java.io.Serializable;
import java.util.List;

public class CommentVo extends BaseObservable implements Serializable {

    /**
     * id : 435
     * uid : 138
     * title :
     * type : 4
     * params : {"act":"dynamicCollect","type":"dynamic","memberid":"2","uuid":"5f0fb1927a454ff311a46b0d2e20c453","dynamicid":"10585","dataid":"41"}
     * content : 收藏了你的动态
     * isread : 1
     * addtime : 1558941765
     * mid : 435
     * nickname : 冯民轩
     * avatar : /var/upload/image/2019/03/2019031214442415575_100x100.png
     */

    private String id;
    private String uid;
    private String title;
    private String type;
    private ParamsBean params;
    private String content;
    private String isread;
    private String addtime;
    private String mid;
    private String nickname;
    private String avatar;
    private String port;
    private String onecompany;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getOnecompany() {
        return onecompany;
    }

    public void setOnecompany(String onecompany) {
        this.onecompany = onecompany;
    }

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
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

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
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

    public static class ImgsBean {

        /**
         * img : /20190828/upload/image/1566960338472_500x355.png
         * url : /20190828/upload/image/1566960338472_500x355.png
         * sort : 5
         * t : 1
         */

        private String img;
        private String url;
        private String sort;
        private String t;

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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }
    }

    public static class ParamsBean {
        /**
         * act : dynamicCollect
         * type : dynamic
         * memberid : 2
         * uuid : 5f0fb1927a454ff311a46b0d2e20c453
         * dynamicid : 10585
         * dataid : 41
         */

        private String act;
        private String type;
        private String memberid;
        private String uuid;
        private String dynamicid;
        private String dataid;
        private String push_memberid;
        private String push_uuid;
        private String goodsid;
        public String orderid;
        public String status;


        public String getPush_memberid() {
            return push_memberid;
        }

        public void setPush_memberid(String push_memberid) {
            this.push_memberid = push_memberid;
        }

        public String getPush_uuid() {
            return push_uuid;
        }

        public void setPush_uuid(String push_uuid) {
            this.push_uuid = push_uuid;
        }

        public String getGoodsid() {
            return goodsid;
        }

        public void setGoodsid(String goodsid) {
            this.goodsid = goodsid;
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
