package com.docker.active.ui.manager;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.docker.active.R;
import com.docker.active.databinding.ProActiveManagerBinding;
import com.docker.active.vm.ActiveCommonViewModel;
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
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.indector.CommonIndector;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;

/*
 * 活动管理列表
 **/

@Route(path = AppRouter.ACTIVE_MANAGER_LIST)
public class ActiveManagerListActivity extends NitCommonActivity<NitCommonContainerViewModel, ProActiveManagerBinding> {

    public ArrayList<Fragment> fragments = new ArrayList<>();

    private Disposable disposable;

    @Override
    public NitCommonContainerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitCommonContainerViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_manager;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("活动管理");

        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("activedel")
                    || rxEvent.getT().equals("activeStusUpdate")
                    || rxEvent.getT().equals("active_refresh")
                    || rxEvent.getT().equals("activemodify")) {
                ((NitCommonFragment) fragments.get(mBinding.viewPager.getCurrentItem())).onReFresh(null);
            }
        });

    }

    @Override
    public void initView() {
        peocessTab();
    }

    @Override
    public void initObserver() {

    }

    public void peocessTab() {
        String[] titles = new String[]{"进行中", "已结束"};

        /*
        * 进行中：activityStatus=1&filter={“memberid”:”938”}
已结束：activityStatus=-1&filter={“memberid”:”938”}

        * */
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.isActParent = true;
        commonListOptions.falg = 101;
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.ReqParam.put("activityStatus", "1");
        commonListOptions.ReqParam.put("showFields", "*");
        HashMap<String, String> filterMap = new HashMap<>();
        filterMap.put("memberid", CacheUtils.getUser().uid);
        commonListOptions.ReqParam.put("filter", GsonUtils.toJson(filterMap));
        NitCommonContainerFragmentV2 nitCommonContainerFragmentV2 = NitCommonContainerFragmentV2.newinstance(commonListOptions);
        fragments.add(nitCommonContainerFragmentV2);


        CommonListOptions commonListOptions1 = new CommonListOptions();
        commonListOptions1.isActParent = true;
        commonListOptions1.falg = 101;
        commonListOptions1.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions1.ReqParam.put("activityStatus", "-1");
        HashMap<String, String> filterMap1 = new HashMap<>();
        filterMap1.put("memberid", CacheUtils.getUser().uid);
        commonListOptions1.ReqParam.put("filter", GsonUtils.toJson(filterMap1));
        commonListOptions1.ReqParam.put("showFields", "*");
        NitCommonContainerFragmentV2 nitCommonContainerFragmentV21 = NitCommonContainerFragmentV2.newinstance(commonListOptions1);
        fragments.add(nitCommonContainerFragmentV21);

        // magic
        mBinding.viewPager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.viewPager, mBinding.magicIndicator, this);
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        return new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return ActiveCommonViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                ActiveCommonViewModel innerVm = (ActiveCommonViewModel) commonListVm;
                innerVm.apiType = 1;
                innerVm.scope = 2;
            }
        };
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
