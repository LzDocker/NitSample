package com.bfhd.account.vo;

import android.databinding.BaseObservable;

import java.io.Serializable;

public class SystemMessageVo extends BaseObservable implements Serializable {


    /**
     * id : 419
     * uid : 138
     * title : 咨询订单
     * type : 1
     * params : {"act":"consultOrder","circleid":"138","utid":"7f9a7f72f6a56c24477deb469d54786c","memberid":"138","uuid":"7f9a7f72f6a56c24477deb469d54786c","dataid":19}
     * content : 有人向您所在的北京世通宣得图书有限公司发送咨询订单，去看一下！
     * isread : 1
     * addtime : 1558922490
     * mid : 419
     */

    private String id;
    private String uid;
    private String title;
    private String type;
    private String content;
    private String isread;
    private String addtime;
    private String mid;
    private CommentVo.ParamsBean params;

    public CommentVo.ParamsBean getParams() {
        return params;
    }

    public void setParams(CommentVo.ParamsBean params) {
        this.params = params;
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

//    public static class ParamsBean {
//        /**
//         * act : consultOrder
//         * circleid : 138
//         * utid : 7f9a7f72f6a56c24477deb469d54786c
//         * memberid : 138
//         * uuid : 7f9a7f72f6a56c24477deb469d54786c
//         * dataid : 19
//         */
//
//        private String act;
//        private String circleid;
//        private String utid;
//        private String memberid;
//        private String uuid;
//        private int dataid;
//
//        public String getAct() {
//            return act;
//        }
//
//        public void setAct(String act) {
//            this.act = act;
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
//        public String getUtid() {
//            return utid;
//        }
//
//        public void setUtid(String utid) {
//            this.utid = utid;
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
//        public String getUuid() {
//            return uuid;
//        }
//
//        public void setUuid(String uuid) {
//            this.uuid = uuid;
//        }
//
//        public int getDataid() {
//            return dataid;
//        }
//
//        public void setDataid(int dataid) {
//            this.dataid = dataid;
//        }
//    }

}
