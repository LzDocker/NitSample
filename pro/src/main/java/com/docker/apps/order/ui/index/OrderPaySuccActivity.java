package com.docker.apps.order.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.docker.apps.R;
import com.docker.apps.databinding.ProOrderPaySuccActivityBinding;
import com.docker.apps.order.vm.OrderMakerViewModel;
import com.docker.common.common.config.ThiredPartConfig;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/*
 * 订单支付
 **/
@Route(path = AppRouter.ORDER_PAY_SUCC)
public class OrderPaySuccActivity extends NitCommonActivity<OrderMakerViewModel, ProOrderPaySuccActivityBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.pro_order_pay_succ_activity;
    }

    @Override
    public OrderMakerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(OrderMakerViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        mToolbar.setTitle("支付成功");
        mBinding.toIndex.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.HOME).navigation();
            finish();
        });
        mToolbar.setNavigationOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.HOME).navigation();
            finish();
        });
    }

    @Override
    public void initObserver() {

        mViewModel.mWxPrepayLv.observe(this, wxOrderVo -> {
            if (wxOrderVo != null) {
                IWXAPI wxapi = WXAPIFactory.createWXAPI(this, ThiredPartConfig.WxAppid);  //应用ID 即微信开放平台审核通过的应用APPID
                wxapi.registerApp(ThiredPartConfig.WxAppid);
                PayReq req = new PayReq();
                req.appId = wxOrderVo.appid;
                req.partnerId = wxOrderVo.partnerid;
                req.prepayId = wxOrderVo.prepayid;
                req.nonceStr = wxOrderVo.noncestr;
                req.timeStamp = wxOrderVo.timestamp;
                req.packageValue = "Sign=WXPay";
                req.sign = wxOrderVo.sign;
                wxapi.sendReq(req);
            }
        });
    }

    @Override
    public void initRouter() {

    }


}
