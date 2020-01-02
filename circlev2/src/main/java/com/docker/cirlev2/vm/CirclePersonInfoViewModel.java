package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.util.AudioPlayerUtils;
import com.docker.cirlev2.vo.entity.CommentVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.vo.ShoppingCarVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class CirclePersonInfoViewModel extends NitCommonContainerViewModel {


    @Inject
    CircleApiService circleApiService;

    private AudioPlayerUtils playerUtils;

    @Inject
    public CirclePersonInfoViewModel() {

    }

    public CommentVo TopCommentVo;

    public void setTopCommon(CommentVo TopCommentVo) {
        this.TopCommentVo = TopCommentVo;
    }

    @Override
    public void loadData() {
//        mEmptycommand.set(EmptyStatus.BdHiden);



    }

    public void ItemClick(ShoppingCarVo item, View view) {


    }




    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {

        return null;
    }


}
