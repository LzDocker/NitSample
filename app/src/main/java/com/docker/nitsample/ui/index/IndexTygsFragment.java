package com.docker.nitsample.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.inter.frame.example.NitDefaultCircleFragment;
import com.docker.cirlev2.vo.card.AppBannerHeaderCardVo;
import com.docker.cirlev2.vo.card.AppImgHeaderCardVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.IndexTygsFragmentBinding;
import com.docker.nitsample.vo.card.AppRecycleCard2Vo;
import com.docker.nitsample.vo.card.AppRecycleCardVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IndexTygsFragment extends NitCommonFragment<NitCommonContainerViewModel, IndexTygsFragmentBinding> {
    private NitCommonListVm outerVm;
    public ArrayList<Fragment> fragments = new ArrayList<>();

    public static IndexTygsFragment newinstance(CommonListOptions commonListReq) {
        IndexTygsFragment IndexTygsFragment = new IndexTygsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.ContainerParam, commonListReq);
        IndexTygsFragment.setArguments(bundle);
        return IndexTygsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.index_tygs_fragment;
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return null;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                outerVm = commonListVm;
                AppBannerHeaderCardVo appBannerHeaderCardVo = new AppBannerHeaderCardVo(0, 0);
//                AppRecycleCardVo appRecycleCardVo = new AppRecycleCardVo(0, 0);
                AppRecycleCard2Vo appRecycleCardVo = new AppRecycleCard2Vo(0, 0);
                NitBaseProviderCard.providerCard(commonListVm, appRecycleCardVo, nitCommonFragment);
                NitBaseProviderCard.providerCard(commonListVm, appBannerHeaderCardVo, nitCommonFragment);

            }
        };
        return nitDelegetCommand;
    }

    @Override
    protected NitCommonContainerViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(NitCommonContainerViewModel.class);
    }

    @Override
    protected void initView(View var1) {
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        NitBaseProviderCard.providerCardNoRefreshForFrame(getChildFragmentManager(), com.docker.cirlev2.R.id.frame_header, commonListOptions);
        List<CircleTitlesVo> circleTitlesVos = new ArrayList<>();
        CircleTitlesVo circleTitlesVo = new CircleTitlesVo();
        circleTitlesVo.setName("我的关注");
        CircleTitlesVo circleTitlesVo1 = new CircleTitlesVo();
        circleTitlesVo1.setName("公社头条");
        CircleTitlesVo circleTitlesVo2 = new CircleTitlesVo();
        circleTitlesVo2.setName("公社活动");
        CircleTitlesVo circleTitlesVo3 = new CircleTitlesVo();
        circleTitlesVo3.setName("全部分舵");
        circleTitlesVos.add(circleTitlesVo);
        circleTitlesVos.add(circleTitlesVo1);
        circleTitlesVos.add(circleTitlesVo2);
        circleTitlesVos.add(circleTitlesVo3);
        peocessTab(circleTitlesVos);
    }

    @Override
    public void onVisible() {

    }

    @Override
    public void initImmersionBar() {

    }

    public void peocessTab(List<CircleTitlesVo> circleTitlesVos) {
        String[] titles = new String[circleTitlesVos.size()];
        for (int i = 0; i < circleTitlesVos.size(); i++) {
            titles[i] = circleTitlesVos.get(i).getName();
            fragments.add((Fragment) ARouter.getInstance()
                    .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME_COUTAINER)
                    .withSerializable("tabVo", (Serializable) circleTitlesVos)
                    .withInt("pos", i)
                    .navigation());
        }
        // magic
        mBinding.get().viewPager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.get().viewPager, mBinding.get().magicIndicator, this.getHoldingActivity());

    }
}
