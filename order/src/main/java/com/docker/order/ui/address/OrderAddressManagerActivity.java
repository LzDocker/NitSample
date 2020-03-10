package com.docker.order.ui.address;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
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
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.order.R;
import com.docker.order.databinding.ProOrderAddressManagerActivityBinding;
import com.docker.order.vm.OrderAddressViewModel;

import io.reactivex.disposables.Disposable;

/*
 * 地址列表
 **/
@Route(path = AppRouter.ORDER_ADDRESS_MANAGER)
public class OrderAddressManagerActivity extends NitCommonActivity<OrderAddressViewModel, ProOrderAddressManagerActivityBinding> {

    private Disposable disposable;
    private String type;

    private NitCommonFragment mNitCommonFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.pro_order_address_manager_activity;
    }

    @Override
    public OrderAddressViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(OrderAddressViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_address_list")) {
                mNitCommonFragment.onReFresh(null);
            }
        });

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.isActParent = true;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;

        UserInfoVo userInfoVo = CacheUtils.getUser();
        commonListOptions.ReqParam.put("uuid", userInfoVo.uuid);
        commonListOptions.ReqParam.put("memberid", userInfoVo.uid);
        NitBaseProviderCard.providerCoutainerForFrame(getSupportFragmentManager(), R.id.frame_address, commonListOptions);
    }

    @Override
    public void initView() {

        mToolbar.setTitle("地址管理");
        mBinding.tvAddAddress.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ORDER_ADDRESS_EDIT).navigation();
        });
    }

    @Override
    public void initObserver() {


    }

    @Override
    public void initRouter() {

    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {

        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return OrderAddressViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                mNitCommonFragment = nitCommonFragment;
                ((OrderAddressViewModel) commonListVm).mAddressCheckLv.observe(nitCommonFragment, aBoolean -> {
                    Log.d("sss", "initObserver: =====mAddressCheckLv========");

                    if (aBoolean != null && !TextUtils.isEmpty(type)) {
                        Intent intent = new Intent();
                        intent.putExtra("AddressVo", aBoolean);
                        OrderAddressManagerActivity.this.setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        };
        return nitDelegetCommand;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
