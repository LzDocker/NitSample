package com.docker.apps.order.ui.index;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.databinding.AccountActivityOrderBinding;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.docker.apps.order.vm.OrderCommonViewModel;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.indector.CommonIndector;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 订单支付
 **/
@Route(path = AppRouter.ORDER_LIST)
public class OrderListActivity extends NitCommonActivity<OrderCommonViewModel, AccountActivityOrderBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    private HivsSampleAdapter mAdapter;

    private OrderCommonViewModel innervm;
    public ArrayList<Fragment> fragments = new ArrayList<>();

    private Disposable disposable;

    @Override
    public OrderCommonViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(OrderCommonViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return com.bfhd.account.R.layout.account_activity_order;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("我的订单");
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_buy")) {
                ((NitCommonContainerFragmentV2) fragments.get(mBinding.viewPager.getCurrentItem())).onReFresh(null);
            }
        });
    }

    @Override
    public void initView() {

        List<CircleTitlesVo> circleTitlesVos = new ArrayList<>();
        CircleTitlesVo circleTitlesVo = new CircleTitlesVo();
        circleTitlesVo.setName("全部");
        CircleTitlesVo circleTitlesVo1 = new CircleTitlesVo();
        circleTitlesVo1.setName("待付款");
        CircleTitlesVo circleTitlesVo2 = new CircleTitlesVo();
        circleTitlesVo2.setName("待收货");
        CircleTitlesVo circleTitlesVo3 = new CircleTitlesVo();
        circleTitlesVo3.setName("已完成");
        CircleTitlesVo circleTitlesVo4 = new CircleTitlesVo();
        circleTitlesVo4.setName("已取消");

        circleTitlesVos.add(circleTitlesVo);
        circleTitlesVos.add(circleTitlesVo1);
        circleTitlesVos.add(circleTitlesVo2);
        circleTitlesVos.add(circleTitlesVo3);
        circleTitlesVos.add(circleTitlesVo4);
        peocessTab(circleTitlesVos);

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
                innervm.state = flag;
                innervm.mPayOrederLv.observe(nitCommonFragment, payOrederVo -> {
                    if (payOrederVo != null && payOrederVo.alipayStr != null) {
                        ARouter.getInstance().build(AppRouter.ORDER_PAY).withSerializable("payOrederVo", payOrederVo).navigation();
                    }
                });
                innervm.mDelOrderLv.observe(nitCommonFragment, orderVoV2 -> {
                    innervm.mItems.remove(orderVoV2);
                });
                innervm.mRealDelOrderLv.observe(nitCommonFragment, orderVoV2 -> {
                    Log.d("sss", "next: ============22222========");
                });

//                switch (flag) {
//                    case 0:
//                    case 1:
//                    case 2:
//                    case 3:
//                    case 4:
//
//                        break;
//
//                }
            }
        };


        return nitDelegetCommand;
    }


    @Override
    public void initObserver() {

    }

    public void peocessTab(List<CircleTitlesVo> circleTitlesVos) {
        String[] titles = new String[circleTitlesVos.size()];
        for (int i = 0; i < circleTitlesVos.size(); i++) {
            titles[i] = circleTitlesVos.get(i).getName();
            CommonListOptions commonListOptions = new CommonListOptions();
            commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
            commonListOptions.isActParent = true;
            commonListOptions.falg = i;
            commonListOptions.ReqParam.put("uuid", CacheUtils.getUser().uuid);
            commonListOptions.ReqParam.put("memberid", CacheUtils.getUser().uid);
            commonListOptions.ReqParam.put("is_push", "0");

            NitCommonContainerFragmentV2 nitCommonContainerFragmentV2 = NitCommonContainerFragmentV2.newinstance(commonListOptions);
            fragments.add(nitCommonContainerFragmentV2);
        }

        // magic
        mBinding.viewPager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.viewPager, mBinding.magicIndicator, this);

    }

    @Override
    public void initRouter() {

    }
}