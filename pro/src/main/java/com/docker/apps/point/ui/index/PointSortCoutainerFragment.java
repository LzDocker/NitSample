package com.docker.apps.point.ui.index;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.databinding.ProPointSortActivityBinding;
import com.docker.apps.databinding.ProPointSortAouFragmentBinding;
import com.docker.apps.point.vm.PonitSortVm;
import com.docker.apps.point.vo.PointSortItemVo;
import com.docker.apps.point.vo.PointTabVo;
import com.docker.apps.point.vo.card.PonitHeadCardVo;
import com.docker.cirlev2.vo.card.AppBannerHeaderCardVo;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.adapter.CommonpagerStateAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/*
 * 积分榜
 * */
public class PointSortCoutainerFragment extends NitCommonFragment<NitEmptyViewModel, ProPointSortAouFragmentBinding> {


    public String rankType;

    private PonitSortVm ponitSortVm;

    public PointTabVo pointTabVo;


    public static PointSortCoutainerFragment getInstance(String rankType) {
        PointSortCoutainerFragment pointSortCoutainerFragment = new PointSortCoutainerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("rankType", rankType);
        pointSortCoutainerFragment.setArguments(bundle);
        return pointSortCoutainerFragment;
    }

    public static PointSortCoutainerFragment getInstance(PointTabVo pointTabVo) {
        PointSortCoutainerFragment pointSortCoutainerFragment = new PointSortCoutainerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("pointTabVo", pointTabVo);
        pointSortCoutainerFragment.setArguments(bundle);
        return pointSortCoutainerFragment;
    }


    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {

        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return PonitSortVm.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                PonitHeadCardVo ponitHeadCardVo = new PonitHeadCardVo(0, 0);
                NitBaseProviderCard.providerCard(commonListVm, ponitHeadCardVo, nitCommonFragment);

                if (pointTabVo == null) {
                    pointTabVo = (PointTabVo) getArguments().getSerializable("pointTabVo");
                }
                ponitSortVm = (PonitSortVm) commonListVm;
                ponitSortVm.rankType = pointTabVo.param;
                ponitSortVm.mHeadDataLv.observe(PointSortCoutainerFragment.this.getHoldingActivity(), pointSortItemVos -> {
                    ponitHeadCardVo.setTotals((ArrayList<PointSortItemVo>) pointSortItemVos, ponitSortVm.rankType);
                });
            }
        };
        return nitDelegetCommand;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_point_sort_aou_fragment;
    }

    @Override
    protected NitEmptyViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        processView();
    }

    private void processView() {
        pointTabVo = (PointTabVo) getArguments().getSerializable("pointTabVo");
        String titles[] = new String[pointTabVo.rankClass.size()];
        for (int i = 0; i < pointTabVo.rankClass.size(); i++) {
            titles[i] = pointTabVo.rankClass.get(i).name;
            CommonListOptions commonListOptions = new CommonListOptions();
            commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
            commonListOptions.falg = 0;
            commonListOptions.isActParent = false;
            commonListOptions.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
            commonListOptions.ReqParam.put("rankType", pointTabVo.param);
            commonListOptions.ReqParam.put("rankClass", pointTabVo.rankClass.get(i).param);
            NitCommonContainerFragmentV2 nitCommonContainerFragmentV2 = NitCommonContainerFragmentV2.newinstance(commonListOptions);
            fragments.add(nitCommonContainerFragmentV2);
        }
        mBinding.get().segment.setTabData(titles);
        mBinding.get().viewpager.setAdapter(new CommonpagerStateAdapter(getChildFragmentManager(), fragments));
        mBinding.get().viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mBinding.get().segment.setCurrentTab(i);
                mBinding.get().refresh.finishRefresh();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mBinding.get().segment.setCurrentTab(0);

        mBinding.get().segment.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mBinding.get().viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });


    }

    @Override
    protected void initView(View var1) {

        mBinding.get().refresh.setEnableLoadMore(false);
        mBinding.get().refresh.setEnableRefresh(false);
    }

    @Override
    public void initImmersionBar() {

    }
}
