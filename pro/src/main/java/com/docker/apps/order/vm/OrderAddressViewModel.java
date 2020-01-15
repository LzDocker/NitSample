package com.docker.apps.order.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.text.TextUtils;

import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.order.api.OrderService;
import com.docker.apps.order.vo.AddressVo;
import com.docker.apps.order.vo.AllLinkageVo;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class OrderAddressViewModel extends NitCommonContainerViewModel {


    public final MediatorLiveData<String> mAddaddressLv = new MediatorLiveData<>();
    public final MediatorLiveData<String> mDeladdressLv = new MediatorLiveData<>();
    public final MediatorLiveData<AllLinkageVo> mCitysLv = new MediatorLiveData<>();

    @Inject
    OrderService orderService;

    @Inject
    public OrderAddressViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return orderService.fetchAdressList(param);
    }


    /**
     * 添加、编辑地址
     */
    public void addAdress(AddressVo addressVo) {
        showDialogWait("加载中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        String id = addressVo.getId();
        if (TextUtils.isEmpty(id)) {
            id = "";
        }
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("id", id);
        params.put("name", addressVo.getName());
        params.put("phone", addressVo.getPhone());
        params.put("location", addressVo.getLocation());
        params.put("address", addressVo.getAddress());
        params.put("is_moren", addressVo.getIs_moren());
        params.put("region1", addressVo.getRegion1());
        params.put("region2", addressVo.getRegion2());
        params.put("region3", addressVo.getRegion3());
        mAddaddressLv.addSource(RequestServer(orderService.addAdress(params)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                mAddaddressLv.setValue(resource.data);
                hideDialogWait();
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                ToastUtils.showShort("网络问题，请重试");
                hideDialogWait();
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onBusinessError(resource);
                ToastUtils.showShort("保存失败，请重试");
                hideDialogWait();
            }
        }));
    }

    /**
     * 删除地址
     */
    public void deleteAdress(AddressVo addressVo) {
        showDialogWait("加载中", false);
        HashMap<String, String> params = new HashMap<>();
        String id = addressVo.getId();
        params.put("addressid", id);
        mDeladdressLv.addSource(RequestServer(orderService.deleteAdress(params)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mDeladdressLv.setValue(resource.data);
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onBusinessError(resource);
                hideDialogWait();
                ToastUtils.showShort("删除失败，请重试");
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                ToastUtils.showShort("网络问题，请重试");
            }
        }));
    }


    public void getCityList() {
        showDialogWait("加载中", false);
        HashMap<String, String> params = new HashMap<>();
        params.put("keyid", "1");
        params.put("parentid", "0");
        mCitysLv.addSource(RequestServer(orderService.cityChoose(params)), new NitNetBoundObserver<AllLinkageVo>(new NitBoundCallback<AllLinkageVo>() {
            @Override
            public void onComplete(Resource<AllLinkageVo> resource) {
                super.onComplete(resource);
                mCitysLv.setValue(resource.data);
                hideDialogWait();
            }

            @Override
            public void onNetworkError(Resource<AllLinkageVo> resource) {
                super.onNetworkError(resource);
                ToastUtils.showShort("网络问题，请重试");
                hideDialogWait();
            }

            @Override
            public void onBusinessError(Resource<AllLinkageVo> resource) {
                super.onBusinessError(resource);
                ToastUtils.showShort("网络问题，请重试");
                hideDialogWait();
            }
        }));

    }

}

