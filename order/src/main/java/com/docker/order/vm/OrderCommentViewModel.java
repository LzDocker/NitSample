package com.docker.order.vm;

import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableField;
import android.view.View;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.RstVo;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.docker.order.api.OrderService;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 * 评论
 */

public class OrderCommentViewModel extends NitCommonVm {


    public ObservableField<Integer> mCommentOb = new ObservableField<>();

    @Inject
    OrderService orderService;

    @Inject
    CircleApiService circleApiService;


    @Inject
    public OrderCommentViewModel() {

    }

    public void commentUi(int position, View view) {
        mCommentOb.set(position);
        mCommentOb.notifyChange();
    }

    public MediatorLiveData<RstVo> mRstLv = new MediatorLiveData<>();

    public void commentServer(HashMap<String, String> param) {
        showDialogWait("请稍后...", false);
        mRstLv.addSource(RequestServer(circleApiService.goodsComment(param)), new NitNetBoundObserver<RstVo>(new NitBoundCallback<RstVo>() {
            @Override
            public void onComplete(Resource<RstVo> resource) {
                super.onComplete(resource);
                mRstLv.setValue(resource.data);
                hideDialogWait();
            }

            @Override
            public void onComplete() {
                super.onComplete();
                hideDialogWait();
            }
        }));

    }
}

