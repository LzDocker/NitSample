package com.docker.message.vo;

import android.databinding.Bindable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.CacheMemoryUtils;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.entity.ParamsBean;
import com.docker.message.BR;
import com.docker.message.R;

import java.io.Serializable;

import timber.log.Timber;

public class MessageListVo extends BaseSampleItem implements Serializable {

    /*
     * UI类型
     *
     * */
    public int style;

    @Override
    public int getItemLayout() {
        int layout = R.layout.message_fragment_card_style;
        switch (style) {
            case 0:
                layout = R.layout.message_fragment_card_style;
                break;
            case 1:
                layout = R.layout.message_fragment_list_style;
                break;
        }
        return layout;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            Timber.e("sss=========OnItemClickListener===========");
            CommonListOptions options = new CommonListOptions();
            options.refreshState = 0;
            options.ReqParam.put("type", ((MessageListVo) item).type);
            options.ReqParam.put("memberid", "3");
//            String title = null;
//            switch (type) {
//                case "1":  // 系统通知
//                    title = "系统通知";
//                    break;
//                case "2":  // 评论列表
//                    title = "评论";
//                    break;
//                case "3": // 点赞列表
//                    title = "点赞";
//                    break;
//                case "4": // 收藏
//                    title = "收藏";
//                    break;
//                case "5": // 关注
//                    title = "关注";
//                    break;
//            }
            ((MessageListVo) item).setNotReadMsgNum("0");
            ARouter.getInstance().build(AppRouter.MESSAGELISTACT)
                    .withSerializable(Constant.CommonListParam, options)
                    .withString("title", title)
                    .navigation();
        };
    }


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

    @Bindable
    private String notReadMsgNum;

    private int icon;

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

    @Bindable
    public String getNotReadMsgNum() {
        return notReadMsgNum;
    }

    @Bindable
    public void setNotReadMsgNum(String notReadMsgNum) {
        this.notReadMsgNum = notReadMsgNum;
        notifyPropertyChanged(BR.notReadMsgNum);
    }

}