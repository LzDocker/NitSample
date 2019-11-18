package com.docker.message.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.dcbfhd.utilcode.utils.CacheMemoryUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.Resource;
import com.docker.message.R;
import com.docker.message.api.MessageService;
import com.docker.message.vo.MessageListVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

public class MessageViewModel extends NitCommonContainerViewModel {

    @Inject
    MessageService messageService;

    public final MediatorLiveData<String> mRearAllLiveData = new MediatorLiveData<String>();

    @Inject
    public MessageViewModel() {

    }

    /*
     * 0 card
     * 1 list
     * */
    public int style;

    /*
     * 消息列表
     *
     * 消息二级列表
     *
     * */
    public int serverType = 0;

    public void setServerType(int serverType) {
        this.serverType = serverType;
        if (serverType == 0) {
            mIsfirstLoad = false;
        }
    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        LiveData<ApiResponse<BaseResponse>> serverFun = null;
        switch (serverType) {
            case 0:
                serverFun = messageService.FsetchmessageList(param);
                break;
            case 1:
                serverFun = messageService.FetchMessageList(param);
                break;
        }
        return serverFun;
    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        if (serverType == 0) {
            return proceeeMessageList((ArrayList<MessageListVo>) data);
        }
        return data;
    }


    /*
     * 全部已读
     * */
    public void readAllMsg() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        mRearAllLiveData.addSource(RequestServer(messageService.readAllMsg(userInfoVo.memberid)),
                new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        loadData();
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络问题，请重试");
                    }

                    @Override
                    public void onBusinessError(Resource<String> resource) {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }

    /*
     *
     * 处理一级列表数据
     * */
    private ArrayList<MessageListVo> proceeeMessageList(ArrayList<MessageListVo> data) {

        ArrayList<MessageListVo> messageVoList = new ArrayList<>();
        if (data != null && data.size() > 0) {
            messageVoList = data;
            if (messageVoList.size() != 5) {
                ArrayList<String> typeList = new ArrayList();
                for (int i = 0; i < data.size(); i++) {
                    typeList.add(data.get(i).getType());
                }
                if (!typeList.contains("1")) {
                    MessageListVo messageListVo = new MessageListVo();
                    messageListVo.setType("1");
                    messageListVo.setContent("暂无数据");
                    messageVoList.add(messageListVo);
                }
                if (!typeList.contains("2")) {
                    MessageListVo messageListVo = new MessageListVo();
                    messageListVo.setType("2");
                    messageListVo.setContent("暂无数据");
                    messageVoList.add(messageListVo);
                }
                if (!typeList.contains("3")) {
                    MessageListVo messageListVo = new MessageListVo();
                    messageListVo.setType("3");
                    messageListVo.setContent("暂无数据");
                    messageVoList.add(messageListVo);
                }
                if (!typeList.contains("4")) {
                    MessageListVo messageListVo = new MessageListVo();
                    messageListVo.setType("4");
                    messageListVo.setContent("暂无数据");
                    messageVoList.add(messageListVo);
                }
                if (!typeList.contains("5")) {
                    MessageListVo messageListVo = new MessageListVo();
                    messageListVo.setType("5");
                    messageListVo.setContent("暂无数据");
                    messageVoList.add(messageListVo);
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                MessageListVo messageListVo = new MessageListVo();
                messageListVo.setType(i + 1 + "");
                messageListVo.setContent("暂无数据");
                messageListVo.style = style;
                messageVoList.add(messageListVo);
            }
        }
        for (int i = 0; i < messageVoList.size(); i++) {
            MessageListVo vo = messageVoList.get(i);
            vo.style = style;
            switch (vo.getType()) {
                case "1":
                    vo.setNotReadMsgNum("9");
                    vo.setIcon(R.mipmap.message_tongzhi_icon);
                    vo.setTitle("系统通知");
                    break;
                case "2":
                    vo.setIcon(R.mipmap.message_pinglun_icon);
                    vo.setTitle("评论");
                    break;
                case "3":
                    vo.setIcon(R.mipmap.message_dianzan_icon);
                    vo.setTitle("点赞");
                    break;
                case "4":
                    vo.setIcon(R.mipmap.message_shoucangg_icon);
                    vo.setTitle("收藏");
                    break;
                case "5":
                    vo.setIcon(R.mipmap.message_guanzhu_icon);
                    vo.setTitle("关注");
                    break;
            }
        }
        return messageVoList;
    }
}
