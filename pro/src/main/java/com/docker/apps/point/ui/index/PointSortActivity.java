package com.docker.apps.point.ui.index;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.databinding.ProPointSortActivityBinding;
import com.docker.apps.point.vm.PonitSortVm;
import com.docker.apps.point.vo.PointTabVo;
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
import com.docker.common.common.widget.empty.EmptyLayout;
import com.docker.common.common.widget.indector.CommonIndector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * 积分榜
 * */
@Route(path = AppRouter.POINT_SORT_INDEX)
public class PointSortActivity extends NitCommonActivity<PonitSortVm, ProPointSortActivityBinding> {


    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    public void initView() {
        mToolbar.setTitle("积分榜");


    }

    @Override
    public void initObserver() {

        mViewModel.fechPointTabdata();

        mViewModel.mPointTabLv.observe(this, pointTabVos -> {
            if (pointTabVos != null && pointTabVos.size() > 0) {
                mBinding.empty.hide();
                processTab(pointTabVos);
            } else {
                mBinding.empty.showError();
                mBinding.empty.setOnretryListener(() -> {
                    mViewModel.fechPointTabdata();
                });
            }
        });
    }


    private void processTab(List<PointTabVo> tabVos) {
        String titles[] = new String[tabVos.size()];

        for (int i = 0; i < tabVos.size(); i++) {
            titles[i] = tabVos.get(i).name;
            fragments.add(PointSortCoutainerFragment.getInstance(tabVos.get(i)));
        }
        // magic
        mBinding.viewpager.setAdapter(new CommonpagerStateAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.viewpager, mBinding.magic, this);
    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_point_sort_activity;
    }

    @Override
    public PonitSortVm getmViewModel() {
        return ViewModelProviders.of(this, factory).get(PonitSortVm.class);
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {

        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return PonitSortVm.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                AppBannerHeaderCardVo BannerHeader = new AppBannerHeaderCardVo(0, 0);
                NitBaseProviderCard.providerCard(commonListVm, BannerHeader, nitCommonFragment);
            }
        };
        return nitDelegetCommand;
    }
}
