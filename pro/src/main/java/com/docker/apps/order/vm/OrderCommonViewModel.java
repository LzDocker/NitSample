package com.docker.apps.order.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.docker.apps.order.api.OrderService;
import com.docker.apps.order.vo.AddressVo;
import com.docker.apps.order.vo.LogisticeVo;
import com.docker.apps.order.vo.OrderVoV2;
import com.docker.common.common.router.AppRouter;
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
import com.lxj.xpopup.core.ImageViewerPopupView;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 * <p>
 * 列表
 * 详情
 */

public class OrderCommonViewModel extends NitCommonContainerViewModel {

    public int state;

    @Inject
    OrderService orderService;


    @Inject
    public OrderCommonViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        switch (state) {
            case 0:
                break;
            case 1:
                param.put("status", "0"); // 待付款
                break;
            case 2:
                param.put("status", "2");// 待收货
                break;
            case 3:
                param.put("status", "4");//已完成
                break;
            case 4:
                param.put("status", "3");// 已取消
                break;
        }
        return orderService.orderlist(param);
    }


    // 取消订单
    public void cancelOrder(OrderVoV2 orderVoV2, View view) {

        Log.d("sss", "cancelOrder: ====================");


    }

    // 去支付
    public void payOrder(OrderVoV2 orderVoV2, View view) {

    }

    // 查看物流
    public void lookLogic(OrderVoV2 orderVoV2, View view) {

        ARouter.getInstance().build(AppRouter.ORDER_Logistics_LIST)
                .withString("imgurl", orderVoV2.goods_info.get(0).img)
                .withString("phone", orderVoV2.receiveTel)
                .withString("nu", orderVoV2.logisticsNo).navigation();
    }


    public final MediatorLiveData<String> mTakeGoodsLv = new MediatorLiveData<>();

    // 确认收货
    public void sureHandle(OrderVoV2 orderVoV2, View view) {
        showDialogWait("确认收货中...", false);
        HashMap<String, String> param = new HashMap<>();
        mTakeGoodsLv.addSource(RequestServer(orderService.takeGoods(param)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mTakeGoodsLv.setValue(resource.data);
            }

            @Override
            public void onComplete() {
                super.onComplete();
                hideDialogWait();
            }
        }));
    }

    // 评价
    public void ordercomment(OrderVoV2 orderVoV2, View view) {

        // 新增界面
    }

    // 再次购买
    public void getAgain(OrderVoV2 orderVoV2, View view) {


    }


}

