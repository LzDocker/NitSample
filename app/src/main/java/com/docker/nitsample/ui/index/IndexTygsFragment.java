package com.docker.nitsample.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.docker.apps.active.vm.ActiveCommonViewModel;
import com.docker.common.common.adapter.CommonpagerStateAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.IndexTygsFragmentBinding;
import com.docker.nitsample.vm.IndexTygsViewModel;
import com.docker.nitsample.vo.LayoutManagerVo;
import com.docker.nitsample.vo.RecycleTopLayout;
import com.docker.nitsample.vo.Tabvo;
import com.docker.nitsample.vo.card.AppBannerCardVo;
import com.docker.nitsample.vo.card.AppRecycleCard2Vo;
import com.docker.nitsample.vo.card.AppRecycleHorizontalCardVo;
import com.docker.nitsample.vo.card.AppRecycleHorizontalCardVo2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

import static com.docker.common.common.config.Constant.CommonListParam;

/**
 * 公社首页
 */
public class IndexTygsFragment extends NitCommonFragment<IndexTygsViewModel, IndexTygsFragmentBinding> {
    private NitCommonListVm outerVm;
    public ArrayList<Fragment> fragments = new ArrayList<>();

    public static IndexTygsFragment newinstance(CommonListOptions commonListReq) {
        IndexTygsFragment IndexTygsFragment = new IndexTygsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.ContainerParam, commonListReq);
        IndexTygsFragment.setArguments(bundle);
        return IndexTygsFragment;
    }

    public Disposable disposable;

    @Override
    protected int getLayoutId() {
        return R.layout.index_tygs_fragment;
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return ActiveCommonViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                if (flag == 101) {
                    ActiveCommonViewModel innerVm = (ActiveCommonViewModel) commonListVm;
                    innerVm.apiType = 2;
                } else {
                    outerVm = commonListVm;
                    AppBannerCardVo appBannerHeaderCardVo = new AppBannerCardVo(0, 0);
                    appBannerHeaderCardVo.mRepParamMap.put("cid", "1");
                    appBannerHeaderCardVo.mRepParamMap.put("companyid", "1001");
//                appBannerHeaderCardVo.isNoNetNeed = true;

//                AppRecycleCardVo appRecycleCardVo = new AppRecycleCardVo(0, 0);
                    AppRecycleCard2Vo appRecycleCardVo = new AppRecycleCard2Vo(0, 1);
                    appRecycleCardVo.mRepParamMap.put("keyid", "3471");


                    AppRecycleHorizontalCardVo appRecycleHorizontalCardVo = new AppRecycleHorizontalCardVo(0, 2,
                            new LayoutManagerVo(0, 0, false),
                            new RecycleTopLayout("热门活动", "更多", true));

                    HashMap<String, String> JXhashMap = new HashMap<>();
                    JXhashMap.put("showFields", "*");
                    Map<String, String> filterMap = new HashMap<>();
                    filterMap.put("is_recommend", "1");
                    JXhashMap.put("filter", GsonUtils.toJson(filterMap));
                    appRecycleHorizontalCardVo.mRepParamMap = JXhashMap;

                    disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
                        if (rxEvent.getT().equals("activedel")
                                || rxEvent.getT().equals("activeStusUpdate")
                                || rxEvent.getT().equals("active_refresh")
                                || rxEvent.getT().equals("activemodify")) {
                            appRecycleHorizontalCardVo.mNitcommonCardViewModel.loadCardData(appRecycleHorizontalCardVo);
                        }
                    });

                    NitBaseProviderCard.providerCard(commonListVm, appRecycleCardVo, nitCommonFragment);
                    NitBaseProviderCard.providerCard(commonListVm, appBannerHeaderCardVo, nitCommonFragment);
                    NitBaseProviderCard.providerCard(commonListVm, appRecycleHorizontalCardVo, nitCommonFragment);

                    UserInfoVo userInfoVo = CacheUtils.getUser();
                    if (userInfoVo != null) {
                        AppRecycleHorizontalCardVo2 appRecycleHorizontalCardVo2 = new AppRecycleHorizontalCardVo2(0, 3,
                                new LayoutManagerVo(0, 0, false),
                                new RecycleTopLayout("分部推荐", "", false));

                        appRecycleHorizontalCardVo2.mRepParamMap.put("memberid", userInfoVo.uid);
                        appRecycleHorizontalCardVo2.mRepParamMap.put("uuid", userInfoVo.uuid);
                        appRecycleHorizontalCardVo2.mRepParamMap.put("isrecommend", "1");
                        NitBaseProviderCard.providerCard(commonListVm, appRecycleHorizontalCardVo2, nitCommonFragment);
                    }
                }

            }
        };
        return nitDelegetCommand;
    }

    @Override
    protected IndexTygsViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(IndexTygsViewModel.class);
    }

    @Override
    protected void initView(View var1) {

        mViewModel.fetchIndexTab();
        mViewModel.mTabvoLiveData.observe(this, tabvos -> {
            if (tabvos != null) {
                if (fragments.size() == 0) {
                    peocessTab(tabvos);
                }
                mBinding.get().emptyTab.hide();
            } else {
                mBinding.get().emptyTab.showError();
                mBinding.get().emptyTab.setOnretryListener(() -> {
                    mBinding.get().emptyTab.showLoading();
                    mBinding.get().refresh.autoRefresh();
                    mViewModel.fetchIndexTab();
                });
            }
        });
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        NitBaseProviderCard.providerCardNoRefreshForFrame(getChildFragmentManager(), com.docker.cirlev2.R.id.frame_header, commonListOptions);

        mBinding.get().tvSearch.setOnClickListener(v -> {
//            ARouter.getInstance().build(AppRouter.App_SEARCH_index).withString("t", "-1").navigation();
            ARouter.getInstance().build(AppRouter.App_SEARCH_index_TYGS).withString("t", "-1").navigation();
        });

        mBinding.get().refresh.setEnableLoadMore(false);
        mBinding.get().refresh.setOnRefreshListener(refreshLayout -> {
            outerVm.onJustRefresh();
            if (fragments.size() > 0) {
                ((NitCommonListFragment) fragments.get(mBinding.get().viewPager.getCurrentItem())).onReFresh(mBinding.get().refresh);
            } else {
                mBinding.get().refresh.finishRefresh(200);
            }
        });
    }

    @Override
    public void onVisible() {

    }

    @Override
    public void initImmersionBar() {

    }

    public void peocessTab(List<Tabvo> tabvos) {
        String[] titles = new String[tabvos.size()];
        for (int i = 0; i < tabvos.size(); i++) {
            titles[i] = tabvos.get(i).name;
            CommonListOptions commonListOptions = new CommonListOptions();
            commonListOptions.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
            if ("goods".equals(tabvos.get(i).type)) {
                commonListOptions.RvUi = Constant.KEY_RVUI_GRID2;
            } else {
                commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
            }
            commonListOptions.ReqParam.put("t", tabvos.get(i).type);
            commonListOptions.ReqParam.put("index_bottom_catid", tabvos.get(i).id);
            if ("activity".equals(tabvos.get(i).type)) {
                commonListOptions.isActParent = false;
                commonListOptions.falg = 101;
                NitCommonContainerFragmentV2 nitCommonContainerFragmentV2 = NitCommonContainerFragmentV2.newinstance(commonListOptions);
                fragments.add(nitCommonContainerFragmentV2);
            } else {
                fragments.add((Fragment) ARouter.getInstance()
                        .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                        .withSerializable(CommonListParam, commonListOptions)
                        .navigation());
            }
        }
        //
        mBinding.get().viewPager.setAdapter(new CommonpagerStateAdapter(getChildFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.get().viewPager, mBinding.get().magicIndicator, this.getHoldingActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
