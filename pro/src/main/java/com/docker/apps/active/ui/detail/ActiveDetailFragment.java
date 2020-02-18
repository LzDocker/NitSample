package com.docker.apps.active.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.R;
import com.docker.apps.active.ui.index.ActiveContainerFragment;
import com.docker.apps.active.vm.ActiveCommonViewModel;
import com.docker.apps.active.vo.ActiveVo;
import com.docker.apps.active.vo.card.ActiveDetailHeadCard;
import com.docker.apps.databinding.ProActiveDetailFragmentLayoutBinding;
import com.docker.cirlev2.vm.CircleDynamicDetailViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.common.databinding.CommonDetailCoutainerLayoutBinding;

import java.util.ArrayList;
import java.util.HashMap;

import static com.docker.common.common.config.Constant.CommonListParam;

/*
 * 活动详情
 * */
@Route(path = AppRouter.ACTIVE_DEATIL)
public class ActiveDetailFragment extends NitCommonFragment<ActiveCommonViewModel, ProActiveDetailFragmentLayoutBinding> {

    ActiveDetailHeadCard activeDetailHeadCard;
    ActiveVo activeVo;
    public String activityid;

    public int edit = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_detail_fragment_layout;
    }

    @Override
    protected ActiveCommonViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(ActiveCommonViewModel.class);
    }

    @Override
    protected void initView(View var1) {

        mBinding.get().submitArea.setOnClickListener(v -> {
            if (activeVo == null) {
                return;
            }

            if (CacheUtils.getUser() == null) {
                // 去登录
            }
            if (activeVo.status == -1) {
                ToastUtils.showShort("已结束");
                return;
            }

            if (activeVo.signStatus == 1) {
                ToastUtils.showShort("已报名");
                return;
            }
            if (activeVo.enrollNum.equals(activeVo.limitNum)) {
                ToastUtils.showShort("报名人数已满");
                return;
            }
            if (activeVo.status != 1) {

            }

            HashMap<String, String> parm = new HashMap<>();
            parm.put("sign_memberid", CacheUtils.getUser().uid);
            parm.put("activityid", activeVo.dataid);
            parm.put("circleid", activeVo.circleid);
            mViewModel.activeJoin(parm);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edit = getArguments().getInt("edit");
        activityid = getArguments().getString("activityid");
//        if (edit == 1) {
//            mViewModel.mActiveDetailLv.observe(this, activeVo -> {
//
//            });
//            HashMap<String, String> parm = new HashMap<>();
//            parm.put("activityid", activityid);
//            mViewModel.fetchActiveVo(parm);
//        }

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        NitBaseProviderCard.providerCardNoRefreshForFrame(getChildFragmentManager(), com.docker.common.R.id.frame_head, commonListOptions);


//        mBinding.get().refresh.setEnableLoadMore(true);

        mViewModel.mSignSuccLv.observe(this, s -> {
            //
            if (s != null) {
                // succ
                ARouter.getInstance()
                        .build(AppRouter.ACTIVE_SUCC).withSerializable("ActiveSucVo", s)
                        .withString("activityid", activeVo.dataid)
                        .navigation();
                getHoldingActivity().finish();
            }
        });

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
                activeDetailHeadCard = new ActiveDetailHeadCard(0, 0);
                activeDetailHeadCard.mRepParamMap.put("activityid", getArguments().getString("activityid"));
                if (CacheUtils.getUser() != null) {
                    activeDetailHeadCard.mRepParamMap.put("memberid", CacheUtils.getUser().uid);
                }
                NitBaseProviderCard.providerCard(commonListVm, activeDetailHeadCard, nitCommonFragment);
                activeDetailHeadCard.voObservableField.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable sender, int propertyId) {
                        activeVo = activeDetailHeadCard.voObservableField.get();
                        mBinding.get().setItem(activeVo);
                        processTab();
                    }
                });
            }
        };
        return nitDelegetCommand;
    }


    private void processTab() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        String[] titles = new String[]{"活动介绍", "活动动态"};


        fragments.add((Fragment) ARouter.getInstance()
                //webcontent
                .build(AppRouter.CIRCLEV3_COMMONH5)
                .withString("webcontent", activeVo.content)
                .navigation());

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.refreshState = Constant.KEY_REFRESH_ONLY_LOADMORE;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.ReqParam.put("t", "dynamic");
        fragments.add((Fragment) ARouter.getInstance()
                .build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME)
                .withSerializable(CommonListParam, commonListOptions)
                .navigation());

        // magic
        mBinding.get().viewPager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicatorScroll(titles, mBinding.get().viewPager, mBinding.get().magicIndicator, this.getHoldingActivity());
        // magic

    }

    @Override
    public void initImmersionBar() {

    }

}
