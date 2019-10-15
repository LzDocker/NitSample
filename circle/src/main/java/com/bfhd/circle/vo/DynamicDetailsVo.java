package com.bfhd.circle.vo;

import java.io.Serializable;
import java.util.List;

public class DynamicDetailsVo implements Serializable {

    /**
     * dynamicid : 1
     * inputtime : 1455206764
     * memberid : 1
     * uuid :
     * avatar : /var/upload/image/2016/02/20160208165156_11842.jpg
     * nickname : 小海
     * favourNum : 1
     * commentNum : 0
     * content : 今天天气不错
     * resource : [{"t":"1","img":"/var/upload/image/2016/02/20160210004953_90787.jpg","url":"/var/upload/image/2016/02/20160210004953_90787.jpg","sort":"0"},{"t":"2","img":"/var/upload/image/2016/02/20160210004953_90787.jpg","url":"/var/upload/image/2016/02/20160210004953_90787.jpg","sort":"0"}]
     * isFav : 1
     * favsUsers : [{"nickname":"小海"}]
     * commentUsers : [{"commentid":"1","content":"恩恩 又打雷下雨的","memberid":"2","uuid":"","nickname":"张三"},{"commentid":"2","content":"拉倒吧 扯淡","memberid":"1","uuid":"","nickname":"小海"}]
     */

    private String dynamicid;
    private String memberid;
    private String uuid;

    private String circleid;
    private String utid;
    private String type;
    private String favourNum;
    private String commentNum;
    private String seeNum;
    private String cityName;
    private String istop;
    private String inputtime;
    private String classid;
    private String classid2;
    private String content;
    private String dataid;

    private String avatar;
    private String nickname;
    private String circleName;
    private ServiceDataBean.ExtDataBean extData;
    private String isFav;
    private String isFocus;
    private String isCollect;
    private String employeeNum;
    //分类id


    private List<ServiceDataBean.FavsUsersBean> favsUsers;
    private List<ServiceDataBean.CommentUsersBean> commentUsers;


    private ServiceDataBean.ShareBean share;
    private String creattime;
    private String updatetime;
    //创建时间
//
//    private String creattime;
//    private String isComment;
//    private List<ResourceBean> resource;
//    private String isFocus;
//    private String isCollect;
//    String teamid, t, img, updatetime, teamName, employeeNum;


    public String getSeeNum() {
        return seeNum;
    }

    public void setSeeNum(String seeNum) {
        this.seeNum = seeNum;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(String isFocus) {
        this.isFocus = isFocus;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    public String getCircleid() {
        return circleid;
    }

    public void setCircleid(String circleid) {
        this.circleid = circleid;
    }

    public String getClassid2() {
        return classid2;
    }

    public void setClassid2(String classid2) {
        this.classid2 = classid2;
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public ServiceDataBean.ExtDataBean getExtData() {
        return extData;
    }

    public void setExtData(ServiceDataBean.ExtDataBean extData) {
        this.extData = extData;
    }

    public ServiceDataBean.ShareBean getShare() {
        return share;
    }

    public void setShare(ServiceDataBean.ShareBean share) {
        this.share = share;
    }

    public String getDynamicid() {
        return dynamicid;
    }

    public void setDynamicid(String dynamicid) {
        this.dynamicid = dynamicid;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFavourNum() {
        return favourNum;
    }

    public void setFavourNum(String favourNum) {
        this.favourNum = favourNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }


    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }

    public String getIstop() {
        return istop;
    }

    public void setIstop(String istop) {
        this.istop = istop;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }


    public List<ServiceDataBean.FavsUsersBean> getFavsUsers() {
        return favsUsers;
    }

    public void setFavsUsers(List<ServiceDataBean.FavsUsersBean> favsUsers) {
        this.favsUsers = favsUsers;
    }

    public List<ServiceDataBean.CommentUsersBean> getCommentUsers() {
        return commentUsers;
    }

    public void setCommentUsers(List<ServiceDataBean.CommentUsersBean> commentUsers) {
        this.commentUsers = commentUsers;
    }

//    public static class CommentUsersBean implements Serializable {
//        /**
//         * commentid : 1
//         * content : 恩恩 又打雷下雨的
//         * memberid : 2
//         * uuid :
//         * nickname : 张三
//         */
//
//        private String commentid;
//        private String content;
//        private String memberid;
//        private String uuid;
//        private String nickname;
//
//        public String getCommentid() {
//            return commentid;
//        }
//
//        public void setCommentid(String commentid) {
//            this.commentid = commentid;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public void setContent(String content) {
//            this.content = content;
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
//        public String getNickname() {
//            return nickname;
//        }
//
//        public void setNickname(String nickname) {
//            this.nickname = nickname;
//        }
//    }


//    public String getIsFocus() {
//        return isFocus;
//    }
//
//    public void setIsFocus(String isFocus) {
//        this.isFocus = isFocus;
//    }
//
//
//
//    public String getIsCollect() {
//        return isCollect;
//    }
//
//    public void setIsCollect(String isCollect) {
//        this.isCollect = isCollect;
//    }
//
//
//
//    public String getTeamid() {
//        return teamid;
//    }
//
//    public void setTeamid(String teamid) {
//        this.teamid = teamid;
//    }
//
//
//    public String getT() {
//        return t;
//    }
//
//    public void setT(String t) {
//        this.t = t;
//    }
//
//    public String getImg() {
//        return img;
//    }
//
//    public void setImg(String img) {
//        this.img = img;
//    }
//
//    public String getUpdatetime() {
//        return updatetime;
//    }
//
//    public void setUpdatetime(String updatetime) {
//        this.updatetime = updatetime;
//    }
//
//    public String getTeamName() {
//        return teamName;
//    }
//
//    public void setTeamName(String teamName) {
//        this.teamName = teamName;
//    }
//
//    public String getEmployeeNum() {
//        return employeeNum;
//    }
//
//    public void setEmployeeNum(String employeeNum) {
//        this.employeeNum = employeeNum;
//    }


//    public String getCreattime() {
//        return creattime;
//    }
//
//    public void setCreattime(String creattime) {
//        this.creattime = creattime;
//    }




    public String getUtid() {
        return utid;
    }

    public void setUtid(String utid) {
        this.utid = utid;
    }

//    public String getIsComment() {
//        return isComment;
//    }
//
//    public void setIsComment(String isComment) {
//        this.isComment = isComment;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    /**
     * extdata
     */
    public class extData {
        //活动
        private String memberid;
        private String uuid;
        private String title;
        private String thumb;
        private String imgs;
        private String description;
        private String inputtime;
        private String updatetime;
        private List<ServiceDataBean.ExtDataBean.TagsBean> tags;
        private String wapContent;
    }

    /**
     * tag
     */
    public class tagData {
        private String keyname;
        private String value;
        private String sort;
        private String tagid;
    }


    /**
     * sharebean
     */
    public class shareData {
        private String shortUrl;
        private String shareUrl;
        private String shareTit;
        private String shareDesc;
        private String shareImg;

    }
}
