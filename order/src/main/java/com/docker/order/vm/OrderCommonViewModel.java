package com.docker.order.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.PayOrederVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.docker.order.api.OrderService;
import com.docker.order.vo.GoodsVo;
import com.docker.order.vo.LogisticeVo;
import com.docker.order.vo.OrderVoV2;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 * <p>
 * 列表
 * 详情
 */

public class OrderCommonViewModel extends NitCommonContainerViewModel {

    public int state;

    public int type = 0;

    @Inject
    OrderService orderService;

    public MediatorLiveData<OrderVoV2> mOrderDetailLv = new MediatorLiveData<>();


    // 订单的商品列表 订单信息
    public OrderVoV2 orderVoV2;


    @Inject
    public OrderCommonViewModel() {

    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        LiveData<ApiResponse<BaseResponse>> serverFun = null;

        switch (type) {
            case 0: // 订单列表
                switch (state) {
                    case 0:
                        param.put("status", "-1"); // 待付款
                        break;
                    case 1:
                        param.put("status", "0"); // 待付款
                        break;
                    case 2:
                        param.put("status", "1,2");// 待收货
                        break;
                    case 3:
                        param.put("status", "4,5");//已完成
                        break;
                    case 4:
                        param.put("status", "3");// 已取消
                        break;
                }
                serverFun = orderService.orderlist(param);
                break;
            case 1: // 订单内包含的商品列表
                serverFun = orderService.orderGoodsList(param);
                break;
            case 5:
                serverFun = orderService.order_info(param);
                break;
        }
        return serverFun;
    }

    @Override
    public void formartData(Resource resource) {
        super.formartData(resource);
        if (type == 5) {
            mOrderDetailLv.setValue((OrderVoV2) resource.data);
            for (int i = 0; i < ((OrderVoV2) resource.data).goods_info.size(); i++) {
                ((OrderVoV2.GoodsInfo) ((OrderVoV2) resource.data).goods_info.get(i)).parent = (OrderVoV2) resource.data;
            }
            resource.data = ((OrderVoV2) resource.data).goods_info;
        }
    }

    public final MediatorLiveData<OrderVoV2> mDelOrderLv = new MediatorLiveData<>();

    // 取消订单
    public void cancelOrder(OrderVoV2 orderVoV2, View view) {
        HashMap<String, String> param = new HashMap<>();
        param.put("memberid", CacheUtils.getUser().uid);
        param.put("uuid", CacheUtils.getUser().uuid);
        param.put("orderid", orderVoV2.id);
        mDelOrderLv.addSource(RequestServer(orderService.orderCancel(param)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                mDelOrderLv.setValue(orderVoV2);
                if (mItems.contains(orderVoV2)) {
                    mItems.remove(orderVoV2);
                }
            }
        }));
    }

    public final MediatorLiveData<OrderVoV2> mRealDelOrderLv = new MediatorLiveData<>();

    // 删除订单
    public void delOrder(OrderVoV2 orderVoV2, View view) {
        HashMap<String, String> param = new HashMap<>();
        param.put("memberid", CacheUtils.getUser().uid);
        param.put("uuid", CacheUtils.getUser().uuid);
        param.put("orderid", orderVoV2.id);
        mRealDelOrderLv.addSource(RequestServer(orderService.prePaymentOrder_del(param)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                mRealDelOrderLv.setValue(orderVoV2);
                mItems.remove(orderVoV2);
            }
        }));
    }


    public final MediatorLiveData<PayOrederVo> mPayOrederLv = new MediatorLiveData<>();

    // 去支付
    public void payOrder(OrderVoV2 orderVoV2, View view) {
        showDialogWait("请稍后...", false);
        HashMap<String, String> param = new HashMap<>();
        param.put("memberid", CacheUtils.getUser().uid);
        param.put("uuid", CacheUtils.getUser().uuid);
        param.put("orderid", orderVoV2.id);
        param.put("payment", "2");
        mPayOrederLv.addSource(RequestServer(orderService.orderPay(param)), new NitNetBoundObserver<PayOrederVo>(new NitBoundCallback<PayOrederVo>() {
            @Override
            public void onComplete(Resource<PayOrederVo> resource) {
                super.onComplete(resource);
                mPayOrederLv.setValue(resource.data);
                hideDialogWait();
            }


            @Override
            public void onComplete() {
                super.onComplete();
                hideDialogWait();
            }
        }));

    }

    // 查看物流
    public void lookLogic(OrderVoV2 orderVoV2, View view) {
        Log.d("sss", "lookLogic: =============="+orderVoV2.goods_info.get(0).img);
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
        param.put("orderid",orderVoV2.id);
        param.put("memberid",CacheUtils.getUser().uid);
        param.put("uuid",CacheUtils.getUser().uuid);
        mTakeGoodsLv.addSource(RequestServer(orderService.takeGoods(param)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mItems.remove(orderVoV2);
                mTakeGoodsLv.setValue("succ");
            }

            @Override
            public void onComplete() {
                super.onComplete();
                hideDialogWait();
            }
        }));
    }

    // 去评价界面
    public void ordercomment(OrderVoV2 orderVoV2, View view) {
        // 新增界面
        ARouter.getInstance().build(AppRouter.ORDER_GOODS_LIST)
                .withString("orderid", orderVoV2.id)
                .withSerializable("orderVoV2", orderVoV2)
                .navigation();

    }

    // 再次购买
    public void getAgain(OrderVoV2 orderVoV2, View view) {
        ARouter.getInstance().build(AppRouter.ORDER_MAKER).withString("orderId", orderVoV2.id).navigation();
    }

    // 再次购买-->进入详情
    public void getAgainEnterGoodsDetail(OrderVoV2.GoodsInfo goodsInfo, View view) {
        ARouter.getInstance().build(AppRouter.CIRCLE_dynamic_v2_detail).withString("dynamicId", goodsInfo.dynamicid).navigation();
    }


    // 待评价商品列表---》评价界面
    public void comment(GoodsVo goodsVo, View view) {
        orderVoV2.dynamicid = goodsVo.dynamicid;
        ARouter.getInstance().build(AppRouter.ORDER_GOODS_COMMENT)
                .withSerializable("orderVoV2", orderVoV2)
                .withSerializable("goodsVo", goodsVo)
                .navigation();
    }

    // 物流信息
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
}

