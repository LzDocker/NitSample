package com.docker.nitsample.ui.index.circle;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2FragmentIndexV2Binding;
import com.docker.cirlev2.vm.CircleIndexViewModel;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonContainerOptions;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.nitsample.vm.card.CircleRecomendListCardVm;
import com.docker.nitsample.vm.circle.CircleJoinListVm;

import java.util.ArrayList;
import java.util.List;

import static com.docker.common.common.config.Constant.KEY_RVUI_HOR;
import static com.docker.common.common.config.Constant.KEY_RVUI_LINER;
import static com.docker.common.common.router.AppRouter.CIRCLE_CLASS_LIST;
import static com.docker.common.common.router.AppRouter.COMMON_CONTAINER;

/*
 *
 * 圈子入口界面
 * */
@Route(path = AppRouter.CIRCLE_INDEX_FRAMEV2)
public class CircleIndexFragmentv2 extends NitCommonFragment<CircleIndexViewModel, Circlev2FragmentIndexV2Binding> {
    NitCommonContainerFragmentV2 myjoinFragment;
    NitCommonContainerFragmentV2 containerFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_fragment_index_v2;
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
        commonListOptions.falg = 103;
        commonListOptions.isActParent = false;
        commonListOptions.ReqParam.put("memberid", CacheUtils.getUser().uid);
        commonListOptions.ReqParam.put("uuid", CacheUtils.getUser().uuid);
        commonListOptions.ReqParam.put("isrecommend", "1");
        myjoinFragment = NitCommonContainerFragmentV2.newinstance(commonListOptions);
        FragmentUtils.add(getChildFragmentManager(), myjoinFragment, R.id.circlev2_mine);

        initTab();


    }

    private void initTab() {
        List<Fragment> fragments = new ArrayList<>();

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = KEY_RVUI_LINER;
        commonListOptions.refreshState = 1;
        commonListOptions.isActParent = false;
        commonListOptions.falg = 104;
        commonListOptions.ReqParam.put("memberid", CacheUtils.getUser().uid);
        commonListOptions.ReqParam.put("uuid", CacheUtils.getUser().uuid);
        containerFragment = NitCommonContainerFragmentV2.newinstance(commonListOptions);
        fragments.add(containerFragment);

        // magic
        String mTitleList[] = new String[]{"分部列表"};
        mBinding.get().viewpager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments, mTitleList));
        // magic

        mBinding.get().refresh.setOnRefreshListener(refreshLayout -> {
            myjoinFragment.onReFresh(mBinding.get().refresh);
            containerFragment.onReFresh(null);
        });

    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = null;

        switch (flag) {
            case 103:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return CircleJoinListVm.class;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                        CircleJoinListVm circleJoinListVm = (CircleJoinListVm) commonListVm;
                        circleJoinListVm.mApitype = 0;

                        //
                        circleJoinListVm.mJoinSucLv.observe(nitCommonFragment, s -> {

                        });
                    }
                };
                break;
            case 104:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return CircleJoinListVm.class;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                        CircleJoinListVm circleJoinListVm = (CircleJoinListVm) commonListVm;
                        circleJoinListVm.mApitype = 1;
                        circleJoinListVm.mJoinSucLv.observe(nitCommonFragment, s -> {

                        });
                    }
                };
                break;
        }


        return nitDelegetCommand;


    }

    @Override
    public void initImmersionBar() {

    }
}
