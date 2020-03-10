package com.docker.active.ui.manager;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.active.R;
import com.docker.active.databinding.ProActivePersionManagerBinding;
import com.docker.active.vm.ActiveCommonViewModel;
import com.docker.active.vm.ActivePersionListViewModel;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.indector.CommonIndector;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

/*
 * 活动名单管理列表
 **/
//api.php?m=activity.getJoinList
@Route(path = AppRouter.ACTIVE_MANAGER_PERSION_LIST)
public class ActivePersionManagerListActivity extends NitCommonActivity<ActiveCommonViewModel, ProActivePersionManagerBinding> {

    public ArrayList<Fragment> fragments = new ArrayList<>();

    public String activityid;

    private Disposable disposable;

    @Override
    public ActiveCommonViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(ActiveCommonViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_persion_manager;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("名单管理");
        activityid = getIntent().getStringExtra("activityid");
        peocessTab();

        //ValidateSuccess


    }

    @Override
    public void initView() {

        mBinding.edSerch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (TextUtils.isEmpty(mBinding.edSerch.getText().toString().trim())) {
                    return;
                }
                for (int i = 0; i < fragments.size(); i++) {
                    ((NitCommonContainerFragmentV2) fragments
                            .get(i))
                            .UpdateReqParam(false, new Pair<>("keyword", mBinding.edSerch.getText().toString().trim()));
                    ((NitCommonContainerFragmentV2) fragments
                            .get(i)).onReFresh(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void initObserver() {


    }

    public void peocessTab() {

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.isActParent = true;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.ReqParam.put("activityid", activityid);
        commonListOptions.ReqParam.put("status", "0");
        NitCommonContainerFragmentV2 activeRegistFragment = NitCommonContainerFragmentV2.newinstance(commonListOptions);
        fragments.add(activeRegistFragment);


        CommonListOptions commonListOptions1 = new CommonListOptions();
        commonListOptions1.isActParent = true;
        commonListOptions1.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions1.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions1.ReqParam.put("activityid", activityid);
        commonListOptions1.ReqParam.put("status", "1");
        NitCommonContainerFragmentV2 activeWaitVifirFragment = NitCommonContainerFragmentV2.newinstance(commonListOptions1);
        fragments.add(activeWaitVifirFragment);

        CommonListOptions commonListOptions3 = new CommonListOptions();
        commonListOptions3.isActParent = true;
        commonListOptions3.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions3.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions3.ReqParam.put("activityid", activityid);
        commonListOptions3.ReqParam.put("status", "2");
        NitCommonContainerFragmentV2 activeAleaVifirFragment = NitCommonContainerFragmentV2.newinstance(commonListOptions3);
        fragments.add(activeAleaVifirFragment);

        String[] titles = new String[]{"报名审核", "待核销", "已核销"};

        mBinding.viewPager.setOffscreenPageLimit(3);
        // magic
        mBinding.viewPager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.viewPager, mBinding.magicIndicator, this);
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return ActivePersionListViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                ((ActivePersionListViewModel) commonListVm).mPersionStatusLv.observe(nitCommonFragment, s -> {
                    nitCommonFragment.onReFresh(null);
                });
                ((ActivePersionListViewModel) commonListVm).acctivityid = getIntent().getStringExtra("activityid");

                disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
                    if (rxEvent.getT().equals("ValidateSuccess")) {
                        nitCommonFragment.onReFresh(null);
                    }
                });
            }
        };
        return nitDelegetCommand;
    }

    @Override
    public void initRouter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
