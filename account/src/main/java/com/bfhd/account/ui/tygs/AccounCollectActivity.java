package com.bfhd.account.ui.tygs;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.account.R;
import com.bfhd.account.databinding.AccountActivityCollectBinding;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.adapter.CommonpagerStateAdapter;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.indector.CommonIndector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.docker.common.common.config.Constant.CommonListParam;

/*
 * 我的收藏表
 **/

@Route(path = AppRouter.ACCOUNT_COLLECT_LIST)
public class AccounCollectActivity extends NitCommonActivity<NitCommonContainerViewModel, AccountActivityCollectBinding> {


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
        return R.layout.account_activity_collect;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("我收藏的");

    }

    @Override
    public void initView() {

        String[] titles = new String[]{"商品", "文章", "动态"};
        UserInfoVo vo = CacheUtils.getUser();
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.RvUi = Constant.KEY_RVUI_GRID2;
        commonListOptions.ReqParam.put("uuid", vo.uuid);
        commonListOptions.ReqParam.put("memberid", vo.uid);
        commonListOptions.ReqParam.put("act", "collect");
        commonListOptions.ReqParam.put("t", "goods");
        fragments.add((Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME).withSerializable(CommonListParam, commonListOptions).navigation());

        CommonListOptions commonListOptions1 = new CommonListOptions();
        commonListOptions1.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions1.ReqParam.put("uuid", vo.uuid);
        commonListOptions1.ReqParam.put("memberid", vo.uid);
        commonListOptions1.ReqParam.put("t", "news");
        commonListOptions1.ReqParam.put("act", "collect");
        fragments.add((Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME).withSerializable(CommonListParam, commonListOptions1).navigation());

        CommonListOptions commonListOptions2 = new CommonListOptions();
        commonListOptions2.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions2.ReqParam.put("uuid", vo.uuid);
        commonListOptions2.ReqParam.put("memberid", vo.uid);
        commonListOptions2.ReqParam.put("t", "dynamic");
        commonListOptions2.ReqParam.put("act", "collect");
        fragments.add((Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME).withSerializable(CommonListParam, commonListOptions2).navigation());


        // magic
        mBinding.viewPager.setAdapter(new CommonpagerStateAdapter(getSupportFragmentManager(), fragments, titles));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(titles, mBinding.viewPager, mBinding.magicIndicator, this);

    }


    @Override
    public void initObserver() {

    }


    @Override
    public void initRouter() {

    }


}
