package com.docker.apps.order.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.ui.AccounAddAddressActivity;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.R;
import com.docker.apps.databinding.ProOrderAddAddressActivityBinding;
import com.docker.apps.databinding.ProOrderMakerActivityBinding;
import com.docker.apps.order.vm.OrderAddressViewModel;
import com.docker.apps.order.vm.OrderMakerViewModel;
import com.docker.apps.order.vo.AddressVo;
import com.docker.apps.order.vo.AllLinkageVo;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.widget.dialog.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;

/*
 * 填写订单
 **/
@Route(path = AppRouter.ORDER_MAKER)
public class OrderMakeActivity extends NitCommonActivity<OrderMakerViewModel, ProOrderMakerActivityBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.pro_order_maker_activity;
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
        mBinding.empty.hide();
        mToolbar.setTitle("填写订单");

    }

    @Override
    public void initView() {


    }

    @Override
    public void initObserver() {


    }

    @Override
    public void initRouter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
