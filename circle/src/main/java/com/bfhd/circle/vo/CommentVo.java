package com.bfhd.circle.vo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.docker.common.common.utils.cache.CacheUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommentVo extends BaseObservable implements Serializable {

    /*
    * "commentid": "",
        "memberid": "",
        "uuid": "",
        "nickname": "",
        "avatar": "",
        "content": "",
        "praiseNum": "",
        "replyNum": "",
        "inputtime": "",
    *
    * */

    public String commentid;
    public String memberid;
    public String uuid;
    public String nickname;
    public String avatar;
    public String content;

    @Bindable
    public String praiseNum;

    public String replyNum;
    public String inputtime;
    public List<Reply> reply;
    public String audio;
    public String audio_duration;
    public List<ServiceDataBean.ResourceBean> resource;

    @Bindable
    public int isDo = 0;

    @Bindable
    public int getIsDo() {
        return CacheUtils.getCommentLike(getCommentid());
    }

    @Bindable
    public void setIsDo(int isDo) {
        this.isDo = isDo;
        CacheUtils.saveCommentLike(getCommentid(), isDo);
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getAudio_duration() {
        return audio_duration;
    }

    public void setAudio_duration(String audio_duration) {
        this.audio_duration = audio_duration;
    }

    public List<ServiceDataBean.ResourceBean> getResource() {
        return resource;
    }

    public void setResource(List<ServiceDataBean.ResourceBean> resource) {
        this.resource = resource;
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Bindable
    public String getPraiseNum() {
        return praiseNum;
    }

    @Bindable
    public void setPraiseNum(String praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(String replyNum) {
        this.replyNum = replyNum;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public List<Reply> getReply() {
        if (reply == null) {
            reply = new ArrayList<>();
        }
        return reply;
    }

    public void setReply(List<Reply> reply) {
        this.reply = reply;
    }

    public static class Reply extends BaseObservable implements Serializable {

        public String commentid;
        public String memberid;
        public String uuid;
        public String nickname;
        public String reply_nickname;
        public String content;


        public String getCommentid() {
            return commentid;
        }

        public void setCommentid(String commentid) {
            this.commentid = commentid;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getReply_nickname() {
            return reply_nickname;
        }

        public void setReply_nickname(String reply_nickname) {
            this.reply_nickname = reply_nickname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }


}
