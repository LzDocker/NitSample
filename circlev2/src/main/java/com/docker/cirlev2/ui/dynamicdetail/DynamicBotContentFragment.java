package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2FragmentDetailBotContentBinding;
import com.docker.cirlev2.databinding.Circlev2FragmentDetailH5Binding;
import com.docker.cirlev2.vm.CircleCommentListViewModel;
import com.docker.cirlev2.vm.CircleDynamicListViewModel;
import com.docker.cirlev2.vm.CircleMinesViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;

import static com.docker.common.common.config.Constant.KEY_RVUI_HOR;

public class DynamicBotContentFragment extends NitCommonFragment<SampleListViewModel, Circlev2FragmentDetailBotContentBinding> {

    public ServiceDataBean serviceDataBean;

    public NitCommonListVm OuterCommentVm;

    public static DynamicBotContentFragment getInstance(ServiceDataBean serviceDataBean) {
        DynamicBotContentFragment dynamicH5Fragment = new DynamicBotContentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataSource", serviceDataBean);
        dynamicH5Fragment.setArguments(bundle);
        return dynamicH5Fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_fragment_detail_bot_content;
    }

    @Override
    protected SampleListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }

    @Override
    protected void initView(View var1) {

    }


    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = null;

        switch (flag) {
            case 1002:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return CircleCommentListViewModel.class;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                        OuterCommentVm = commonListVm;
                        mBinding.get().setCommentVm(OuterCommentVm);
                        CommonListOptions commonListReq = new CommonListOptions();
                        //commonListReq.ReqParam.put("t", "dynamic");
                        commonListReq.ReqParam.put("dynamicid", serviceDataBean.getDynamicid());
                        commonListReq.ReqParam.put("page", "1");
                        commonListReq.externs.put("serverdata", serviceDataBean);
                        commonListVm.initParam(commonListReq);
                    }
                };
                break;
            case 1003:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return CircleDynamicListViewModel.class;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                        CommonListOptions commonListReq = new CommonListOptions();
//        commonListReq.ReqParam.put("t", "dynamic");
                        commonListReq.ReqParam.put("uuid", "420cd8fd09e4ae6cfb8f3b3fdf5b7af4");
                        commonListReq.ReqParam.put("memberid", "67");
                        commonListReq.ReqParam.put("companyid", "1");
                        commonListVm.initParam(commonListReq);
                    }
                };
                break;
        }


        return nitDelegetCommand;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        serviceDataBean = (ServiceDataBean) getArguments().getSerializable("dataSource");
        mBinding.get().setItem(serviceDataBean);

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.isActParent = false;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.falg = 1002;
        NitBaseProviderCard.providerCoutainerNoRefreshForFrame(getChildFragmentManager(), R.id.frame_comment, commonListOptions);
//

//        CommonListOptions commonListOptions1 = new CommonListOptions();
//        commonListOptions1.isActParent = false;
//        commonListOptions1.RvUi = Constant.KEY_RVUI_LINER;
//        commonListOptions1.falg = 1003;
//        NitBaseProviderCard.providerCoutainerNoRefreshForFrame(getChildFragmentManager(), R.id.frame_comment, commonListOptions1);


//        FragmentUtils.add(getChildFragmentManager(), (Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME).navigation(), R.id.frame_recommend);
//        FragmentUtils.add(getChildFragmentManager(), (Fragment) ARouter.getInstance().build(AppRouter.CIRCLE_DYNAMIC_LIST_FRAME).navigation(), R.id.frame_recommend);
    }

    @Override
    public void initImmersionBar() {

    }

}
