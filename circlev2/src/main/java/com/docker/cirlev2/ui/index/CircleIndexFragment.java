package com.docker.cirlev2.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2FragmentIndexBinding;
import com.docker.cirlev2.databinding.Circlev2SampleActivityBinding;
import com.docker.cirlev2.vm.CircleIndexViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragment;
import com.docker.common.common.widget.indector.CommonIndector;

import java.util.ArrayList;
import java.util.List;

import static com.docker.common.common.config.Constant.KEY_RVUI_HOR;
import static com.docker.common.common.config.Constant.KEY_RVUI_LINER;

/*
 *
 * 圈子入口界面
 * */
@Route(path = AppRouter.CIRCLE_INDEX_FRAME)
public class CircleIndexFragment extends NitCommonFragment<CircleIndexViewModel, Circlev2FragmentIndexBinding> {
    NitCommonContainerFragment myjoinFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_fragment_index;
    }

    @Override
    protected CircleIndexViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleIndexViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        ARouter.getInstance().inject(this);
        mBinding.get().refresh.setEnableLoadMore(false);

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = KEY_RVUI_HOR;
        commonListOptions.falg = 101;
        commonListOptions.ReqParam.put("memberid", "3");
        commonListOptions.ReqParam.put("uuid", "3c29a4eed44db285468df3443790e64a");
        myjoinFragment = NitCommonContainerFragment.newinstance(commonListOptions);
        FragmentUtils.add(getChildFragmentManager(), myjoinFragment, R.id.circlev2_mine);

        initTab();


    }

    private void initTab() {
        List<Fragment> fragments = new ArrayList<>();

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = KEY_RVUI_LINER;
        commonListOptions.refreshState = 1;
        commonListOptions.falg = 102;
        commonListOptions.ReqParam.put("memberid", "3");
        commonListOptions.ReqParam.put("uuid", "3c29a4eed44db285468df3443790e64a");
        NitCommonContainerFragment containerFragment = NitCommonContainerFragment.newinstance(commonListOptions);
        fragments.add(containerFragment);

        CommonListOptions commonListOptions1 = new CommonListOptions();
        commonListOptions1.RvUi = KEY_RVUI_LINER;
        commonListOptions1.refreshState = 1;
        commonListOptions1.falg = 102;
        commonListOptions1.ReqParam.put("memberid", "3");
        commonListOptions1.ReqParam.put("isrecommend", "1");
        commonListOptions1.ReqParam.put("uuid", "3c29a4eed44db285468df3443790e64a");
        NitCommonContainerFragment containerFragment1 = NitCommonContainerFragment.newinstance(commonListOptions1);
        fragments.add(containerFragment1);

        CommonListOptions commonListOptions2 = new CommonListOptions();
        commonListOptions2.RvUi = KEY_RVUI_LINER;
        commonListOptions2.refreshState = 1;
        commonListOptions2.falg = 102;
        commonListOptions2.ReqParam.put("memberid", "3");
        commonListOptions2.ReqParam.put("type", "3");
        commonListOptions2.ReqParam.put("uuid", "3c29a4eed44db285468df3443790e64a");
        NitCommonContainerFragment containerFragment2 = NitCommonContainerFragment.newinstance(commonListOptions2);
        fragments.add(containerFragment2);


        // magic
        String mTitleList[] = new String[]{"附近的", "热门圈子", "国别圈"};
        mBinding.get().viewpager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments, mTitleList));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicatorScroll(mTitleList, mBinding.get().viewpager, mBinding.get().magicIndicator, this.getHoldingActivity());
        // magic

        mBinding.get().refresh.setOnRefreshListener(refreshLayout -> {
            myjoinFragment.onReFresh(mBinding.get().refresh);
            ((NitCommonContainerFragment) (fragments.get(mBinding.get().viewpager.getCurrentItem()))).onReFresh(mBinding.get().refresh);
        });
        mBinding.get().viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                mBinding.get().refresh.finishLoadMore();
                mBinding.get().refresh.finishRefresh();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    @Override
    public void initImmersionBar() {

    }
}
