package com.docker.order.ui.comment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.order.R;
import com.docker.order.databinding.ProOrderCommentsSuccessActivityBinding;
import com.docker.order.vm.OrderCommonViewModel;
import com.docker.order.vo.OrderVoV2;

import io.reactivex.disposables.Disposable;

/*
 * 单个订单包含的商品列表
 **/
@Route(path = AppRouter.ORDER_COMMENT_SUCCESS_LIST)
public class OrderCommentSuccessActivity extends NitCommonActivity<OrderCommonViewModel, ProOrderCommentsSuccessActivityBinding> {

    private OrderCommonViewModel innervm;
    private Disposable disposable;

    private String orderid;

    private OrderVoV2 orderVoV2;

    private String dynamicid;

    @Override
    public OrderCommonViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(OrderCommonViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_order_comments_success_activity;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("评价成功");

        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_buy")) {

            }
        });
    }

    @Override
    public void initView() {

        orderid = getIntent().getStringExtra("orderid");
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.refreshState = Constant.KEY_REFRESH_PURSE;
        commonListOptions.isActParent = true;
        commonListOptions.falg = 1;
        commonListOptions.ReqParam.put("orderid", orderid);
        NitBaseProviderCard.providerCoutainerForFrame(getSupportFragmentManager(), R.id.frame, commonListOptions);

        mBinding.tvPub.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.CIRCLE_dynamic_v2_detail).withString("dynamicId", getIntent().getStringExtra("dynamicid")).navigation();
        });
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = null;

        nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return OrderCommonViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                innervm = (OrderCommonViewModel) commonListVm;
                innervm.type = 1;
                orderVoV2 = (OrderVoV2) getIntent().getSerializableExtra("orderVoV2");
                innervm.orderVoV2 = orderVoV2;
            }
        };


        return nitDelegetCommand;
    }


    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }
}