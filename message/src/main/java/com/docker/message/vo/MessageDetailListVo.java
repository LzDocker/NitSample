package com.docker.message.vo;

import android.databinding.Bindable;

import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.vo.entity.ParamsBean;
import com.docker.common.common.vo.entity.ResourceBean;
import com.docker.message.BR;
import com.docker.message.R;
import com.docker.message.util.MessageRouterUtils;

import java.util.List;

/*
 *
 * 评论 点赞 收藏 关注 系统消息
 * */
public class MessageDetailListVo extends BaseSampleItem {

    @Override
    public int getItemLayout() {

        /*
        * case "2":  // 评论列表
                layoutid = R.layout.message_item_comment;
                break;
            case "3": // 点赞列表
                layoutid = R.layout.message_item_comment;
                break;
            case "4": // 收藏
                layoutid = R.layout.message_item_comment;
                break;
            case "5": // 关注
                layoutid = R.layout.message_item_comment;
                break;
        * */

        int layoutid = 0;
        switch (type) {
            case "1":  // 系统通知
                layoutid = R.layout.message_item_system;
                break;
            case "7":
                layoutid = R.layout.message_item_join;
                break;
            default:
                layoutid = R.layout.message_item_comment;
                break;

        }
        return layoutid;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            MessageRouterUtils.Jump(((MessageDetailListVo) item).params, false);
        };
    }

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
    public String fullName;
    public String msgType;


    // 0待审核 展示审核/忽略按钮、1：同意展示“已同意”；2：忽略，展示“已忽略”

    @Bindable
    public int status;

    @Bindable
    public int getStatus() {
        return status;
    }

    @Bindable
    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

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

    private List<ResourceBean> resource;

    public List<ResourceBean> getResource() {
        return resource;
    }

    public void setResource(List<ResourceBean> resource) {
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


}
