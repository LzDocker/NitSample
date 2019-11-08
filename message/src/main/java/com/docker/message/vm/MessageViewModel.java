package com.docker.message.vm;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.view.View;

import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.message.api.MessageService;
import com.docker.message.vo.MessageListVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

import timber.log.Timber;

public class MessageViewModel extends NitCommonContainerViewModel {

    @Inject
    MessageService messageService;


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
            for (int i = 0; i < data.size(); i++) {
                ((ArrayList<MessageListVo>) data).get(i).style = style;
            }
        }
        return data;
    }
}
