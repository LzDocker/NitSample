package com.docker.apps.order.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.text.TextUtils;
import android.util.Log;

import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.circle.base.ViewEventResouce;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.order.api.OrderService;
import com.docker.apps.order.vo.AddressVo;
import com.docker.apps.order.vo.AllLinkageVo;
import com.docker.apps.order.vo.LogisticeVo;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.PayOrederVo;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.WxOrderVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class OrderMakerViewModel extends NitCommonContainerViewModel {


    public final MediatorLiveData<List<AddressVo>> mAddressLv = new MediatorLiveData<>();
    public final MediatorLiveData<WxOrderVo> mWxPrepayLv = new MediatorLiveData<>();


    @Inject
    OrderService orderService;


    @Inject
    public OrderMakerViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return orderService.fetchAdressList(param);
    }

    public void fetchAddress() {
        HashMap<String, String> param = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        param.put("uuid", userInfoVo.uuid);
        param.put("memberid", userInfoVo.uid);
        mAddressLv.addSource(RequestServer(orderService.fetchAdressList(param)), new NitNetBoundObserver<List<AddressVo>>(new NitBoundCallback<List<AddressVo>>() {
            @Override
            public void onComplete(Resource<List<AddressVo>> resource) {
                super.onComplete(resource);
                mAddressLv.setValue(resource.data);
            }
        }));
    }


    public void fechWxPreOrder(HashMap<String, String> param) {
        showDialogWait("请稍后...", false);
        mWxPrepayLv.addSource(RequestServer(orderService.fechWxPreOrder(param)),
                new NitNetBoundObserver<WxOrderVo>(new NitBoundCallback<WxOrderVo>() {
                    @Override
                    public void onComplete(Resource<WxOrderVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mWxPrepayLv.setValue(resource.data);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    public final MediatorLiveData<LogisticeVo> mLogisticeVoLv = new MediatorLiveData<>();

    /**
     * 获取物流详情
     */
    public void getWuliu(HashMap<String, String> params) {
        mLogisticeVoLv.addSource(RequestServer(
                orderService.wuliu(params)), new HivsNetBoundObserver<>(new NetBoundCallback<LogisticeVo>() {
            @Override
            public void onComplete(Resource<LogisticeVo> resource) {
                super.onComplete(resource);
                mLogisticeVoLv.setValue(resource.data);
            }
        }));
    }


    public final MediatorLiveData<PayOrederVo> orederVoMediatorLiveData = new MediatorLiveData<>();

    public void orderMaker(HashMap<String, String> param) {
        showDialogWait("订单生成中...", false);
        orederVoMediatorLiveData.addSource(RequestServer(orderService.orderMaker(param)), new NitNetBoundObserver<PayOrederVo>(new NitBoundCallback<PayOrederVo>() {
            @Override
            public void onComplete(Resource<PayOrederVo> resource) {
                super.onComplete(resource);
                orederVoMediatorLiveData.setValue(resource.data);
                hideDialogWait();
            }

            @Override
            public void onComplete() {
                super.onComplete();
                hideDialogWait();
            }

            @Override
            public void onBusinessError(Resource<PayOrederVo> resource) {
                super.onBusinessError(resource);
                Log.d("sss", "onBusinessError: ");
                orederVoMediatorLiveData.setValue(resource.data);
            }
        }));

    }

}

