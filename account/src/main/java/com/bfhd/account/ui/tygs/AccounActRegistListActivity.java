package com.bfhd.account.ui.tygs;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.BR;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityActManagerBinding;
import com.bfhd.account.databinding.AccountActivityActRegistBinding;
import com.bfhd.account.databinding.AccountActivityAttentionListBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.account.vo.FansVo;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.bfhd.circle.vo.bean.StaPersionDetail;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonListActivity;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.indector.CommonIndector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/*
 * 活动报名列表
 **/

@Route(path = AppRouter.ACCOUNT_ACT_REGIST_LIST)
public class AccounActRegistListActivity extends NitCommonActivity<NitCommonContainerViewModel, AccountActivityActManagerBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    private HivsSampleAdapter mAdapter;

    public ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    public NitCommonContainerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitCommonContainerViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_act_manager;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("活动报名");

    }

    @Override
    public void initView() {

    }

    @Override
    public void initObserver() {

        List<CircleTitlesVo> circleTitlesVos = new ArrayList<>();
        CircleTitlesVo circleTitlesVo = new CircleTitlesVo();
        circleTitlesVo.setName("待审核");
        CircleTitlesVo circleTitlesVo1 = new CircleTitlesVo();
        circleTitlesVo1.setName("待参加");
        CircleTitlesVo circleTitlesVo2 = new CircleTitlesVo();
        circleTitlesVo2.setName("已结束");
        circleTitlesVos.add(circleTitlesVo);
        circleTitlesVos.add(circleTitlesVo1);
        circleTitlesVos.add(circleTitlesVo2);
        peocessTab(circleTitlesVos);
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
        mBinding.viewPager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.viewPager, mBinding.magicIndicator, this);

    }

    @Override
    public void initRouter() {

    }


}
