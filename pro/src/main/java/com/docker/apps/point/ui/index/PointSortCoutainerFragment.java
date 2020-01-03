package com.docker.apps.point.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.apps.R;
import com.docker.apps.databinding.ProPointSortActivityBinding;
import com.docker.apps.databinding.ProPointSortAouFragmentBinding;
import com.docker.apps.point.vm.PonitSortVm;
import com.docker.apps.point.vo.card.PonitHeadCardVo;
import com.docker.cirlev2.vo.card.AppBannerHeaderCardVo;
import com.docker.common.common.adapter.CommonpagerAdapter;
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

import java.util.ArrayList;

/*
 * 积分榜
 * */
public class PointSortCoutainerFragment extends NitCommonFragment<NitEmptyViewModel, ProPointSortAouFragmentBinding> {


    public static PointSortCoutainerFragment getInstance() {
        return new PointSortCoutainerFragment();
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
//                AppBannerHeaderCardVo BannerHeader = new AppBannerHeaderCardVo(0, 0);
                PonitHeadCardVo ponitHeadCardVo = new PonitHeadCardVo(0, 0);
                NitBaseProviderCard.providerCard(commonListVm, ponitHeadCardVo, nitCommonFragment);
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
    protected void initView(View var1) {
        String titles[] = new String[]{"月榜", "总榜"};


        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.falg = 0;
        commonListOptions.isActParent = false;
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.ReqParam.put("t", "goods");
        commonListOptions.ReqParam.put("uuid", "8621e837a2a1579710a95143e5862424");
        commonListOptions.ReqParam.put("memberid", "64");
        commonListOptions.ReqParam.put("companyid", "1");

        NitCommonContainerFragmentV2 nitCommonContainerFragmentV2 = NitCommonContainerFragmentV2.newinstance(commonListOptions);
        fragments.add(nitCommonContainerFragmentV2);


        CommonListOptions commonListOptions1 = new CommonListOptions();
        commonListOptions1.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions1.falg = 1;
        commonListOptions1.isActParent = false;
        commonListOptions1.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions1.ReqParam.put("t", "goods");
        commonListOptions1.ReqParam.put("uuid", "8621e837a2a1579710a95143e5862424");
        commonListOptions1.ReqParam.put("memberid", "64");
        commonListOptions1.ReqParam.put("companyid", "1");
        NitCommonContainerFragmentV2 nitCommonContainerFragmentV21 = NitCommonContainerFragmentV2.newinstance(commonListOptions1);
        fragments.add(nitCommonContainerFragmentV21);
        mBinding.get().segment.setTabData(titles);

        mBinding.get().viewpager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments));

        mBinding.get().viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mBinding.get().segment.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mBinding.get().segment.setCurrentTab(0);
    }

    @Override
    public void initImmersionBar() {

    }
}
