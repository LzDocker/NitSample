package com.docker.order.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.module_im.session.SessionHelper;
import com.docker.order.R;
import com.docker.order.databinding.ProOrderDetailActivityBinding;
import com.docker.order.utils.OrderBdUtils;
import com.docker.order.vm.OrderCommonViewModel;
import com.gyf.immersionbar.ImmersionBar;

import java.util.HashMap;

/*
 * 订单详情
 **/
@Route(path = AppRouter.ORDER_DETAIL)
public class OrderDetailActivity extends NitCommonActivity<OrderCommonViewModel, ProOrderDetailActivityBinding> {

    private OrderCommonViewModel innerVm;

    @Override
    protected int getLayoutId() {
        return R.layout.pro_order_detail_activity;
    }

    @Override
    public OrderCommonViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(OrderCommonViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding.ivBack.setOnClickListener(v -> finish());
        mToolbar.hide();
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.falg = 5;
        commonListOptions.isActParent = true;
        commonListOptions.refreshState = Constant.KEY_REFRESH_PURSE;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.ReqParam.put("memberid", CacheUtils.getUser().uid);
        commonListOptions.ReqParam.put("orderid", getIntent().getStringExtra("orderid"));
        commonListOptions.ReqParam.put("falg", String.valueOf(5));
        NitBaseProviderCard.providerCoutainerNoRefreshForFrame(OrderDetailActivity.this.getSupportFragmentManager(), R.id.frame, commonListOptions);
        mBinding.setViewmodel(mViewModel);
    }

    @Override
    public void initView() {
        mBinding.refresh.setEnableLoadMore(false);
        mBinding.cardviewContact.setOnClickListener(v -> {
            if (mBinding.getItem() != null) {
                SessionHelper.startP2PSession(ActivityUtils.getTopActivity(), mBinding.getItem().push_uuid);
            }
        });
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return OrderCommonViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                innerVm = (OrderCommonViewModel) commonListVm;
                innerVm.type = 5;
                innerVm.mOrderDetailLv.observe(nitCommonFragment, orderVoV2 -> {
                    mBinding.setItem(orderVoV2);
//                    mBinding.setVariable(BR.item, orderVoV2);
                    mBinding.tvStatus.setText(OrderBdUtils.getOrderTopStatus(orderVoV2));

                    if (orderVoV2.status == 3) {
                        mBinding.tvWlStatus.setText(OrderBdUtils.getOrderTopDesc(orderVoV2, null));
                    } else if (orderVoV2.status > 1) {
                        innerVm.mLogisticeVoLv.observe(nitCommonFragment.getActivity(), logisticeVo -> {
                            mBinding.tvWlStatus.setText(OrderBdUtils.getOrderTopDesc(orderVoV2, logisticeVo));
                        });
                        HashMap<String, String> params = new HashMap<>();
                        params.put("phone", orderVoV2.receiveTel);
                        params.put("nu", orderVoV2.logisticsNo);
                        innerVm.getWuliu(params);
                    }
                });
            }
        };
        return nitDelegetCommand;
    }


    @Override
    public void initObserver() {

        //确认收货
        mViewModel.mTakeGoodsLv.observe(this, s -> {
            if (!TextUtils.isEmpty(s)) {
                refreshStatus();
                RxBus.getDefault().post(new RxEvent<>("refresh_buy_order", ""));
            }
        });

        // 支付
        mViewModel.mPayOrederLv.observe(this, payOrederVo -> {

        });

        // 取消
        mViewModel.mDelOrderLv.observe(this, orderVoV2 -> {
            if (orderVoV2 != null) {
                finish();
            }
        });

        mViewModel.mPayOrederLv.observe(this, payOrederVo -> {
            if (payOrederVo != null && payOrederVo.alipayStr != null) {
                ARouter.getInstance().build(AppRouter.ORDER_PAY).withSerializable("payOrederVo", payOrederVo).navigation();
            }
        });
    }

    private void refreshStatus() {
        finish();
        ARouter.getInstance().build(AppRouter.ORDER_DETAIL).withString("orderid", mBinding.getItem().id).navigation();

    }

    @Override
    public void initRouter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .navigationBarColor("#FFFFFF")
                .fullScreen(false)
                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色
                .init();
    }
}
