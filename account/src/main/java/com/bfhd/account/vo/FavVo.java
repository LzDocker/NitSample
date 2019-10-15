package com.bfhd.account.vo;

import android.databinding.BaseObservable;

import com.bfhd.circle.vo.ServiceDataBean;

import java.io.Serializable;
import java.util.List;

public class FavVo extends BaseObservable implements Serializable {

    /**
     * id : 67
     * addtime : 1560426614
     * type : 3
     * content : 赞了你的动态
     * isread : 0
     * port :
     * isdel : 0
     * onecompany : 0
     * uid : 3
     * params : {"act":"dynamicFavour","utid":"9e185265491dce7899d15e9ddfa0bbd9","circleid":"2","uuid":"76c4922812687874a71ceee5efec8721","memberid":"6","dynamicid":"221"}
     * title :
     * mid : 67
     * nickname : 许振
     * avatar :
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

    private List<ServiceDataBean.ResourceBean> resource;

    public List<ServiceDataBean.ResourceBean> getResource() {
        return resource;
    }

    public void setResource(List<ServiceDataBean.ResourceBean> resource) {
        this.resource = resource;
    }

    public CommentVo.ParamsBean getParams() {
        return params;
    }

    public void setParams(CommentVo.ParamsBean params) {
        this.params = params;
    }

    private String nickname;
    private String avatar;

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

//    public static class ParamsBean {
//        /**
//         * act : dynamicFavour
//         * utid : 9e185265491dce7899d15e9ddfa0bbd9
//         * circleid : 2
//         * uuid : 76c4922812687874a71ceee5efec8721
//         * memberid : 6
//         * dynamicid : 221
//         */
//
//        private String act;
//        private String utid;
//        private String circleid;
//        private String uuid;
//        private String memberid;
//        private String dynamicid;
//
//        public String getAct() {
//            return act;
//        }
//
//        public void setAct(String act) {
//            this.act = act;
//        }
//
//        public String getUtid() {
//            return utid;
//        }
//
//        public void setUtid(String utid) {
//            this.utid = utid;
//        }
//
//        public String getCircleid() {
//            return circleid;
//        }
//
//        public void setCircleid(String circleid) {
//            this.circleid = circleid;
//        }
//
//        public String getUuid() {
//            return uuid;
//        }
//
//        public void setUuid(String uuid) {
//            this.uuid = uuid;
//        }
//
//        public String getMemberid() {
//            return memberid;
//        }
//
//        public void setMemberid(String memberid) {
//            this.memberid = memberid;
//        }
//
//        public String getDynamicid() {
//            return dynamicid;
//        }
//
//        public void setDynamicid(String dynamicid) {
//            this.dynamicid = dynamicid;
//        }
//    }
}
