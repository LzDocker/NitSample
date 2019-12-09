//package com.docker.common.common.ui.container;
//
//import android.arch.lifecycle.ViewModelProviders;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.docker.common.common.command.NitDelegetCommand;
//import com.docker.common.common.model.CommonListOptions;
//import com.docker.common.common.router.AppRouter;
//import com.docker.common.common.ui.base.NitCommonActivity;
//import com.docker.common.common.ui.base.NitCommonFragment;
//import com.docker.common.common.ui.base.NitCommonListFragment;
//import com.docker.common.common.vm.container.NitcommonCardViewModel;
//
//import static com.docker.common.common.config.Constant.CommonListParam;
//
///*
// * 通用card容器
// *
// *
// * */
//@Route(path = AppRouter.COMMON_CARD_FRAGMENT)
//public class NitCommonCardFragmentV2 extends NitCommonListFragment<NitcommonCardViewModel> {
//    private NitDelegetCommand delegetCommand;
//    private NitcommonCardViewModel[] nitcommonCardViewModelArr;
//
//    public static NitCommonCardFragmentV2 newinstance(CommonListOptions commonListOptions) {
//        NitCommonCardFragmentV2 nitCommonCardFragment = new NitCommonCardFragmentV2();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(CommonListParam, commonListOptions);
//        nitCommonCardFragment.setArguments(bundle);
//        return nitCommonCardFragment;
//    }
//
//    @Override
//    protected NitcommonCardViewModel getViewModel() {
//        return ViewModelProviders.of(this, factory).get(NitcommonCardViewModel.class);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (commonListReq.isActParent) {
//            delegetCommand = ((NitCommonActivity) getHoldingActivity()).providerNitDelegetCommand(commonListReq.falg);
//            Class[] inners = delegetCommand.providerInnerVm();
//            nitcommonCardViewModelArr = new NitcommonCardViewModel[inners.length];
//            for (int i = 0; i < inners.length; i++) {
//                nitcommonCardViewModelArr[i] = (NitcommonCardViewModel) ViewModelProviders.of(this, factory).get(inners[i]);
////                this.getLifecycle().addObserver(nitcommonCardViewModelArr[i]);
//            }
//        } else {
//            delegetCommand = ((NitCommonFragment) (NitCommonCardFragmentV2.this.getParentFragment())).providerNitDelegetCommand(commonListReq.falg);
//            Class[] inners = delegetCommand.providerInnerVm();
//            nitcommonCardViewModelArr = new NitcommonCardViewModel[inners.length];
//            for (int i = 0; i < inners.length; i++) {
//                nitcommonCardViewModelArr[i] = (NitcommonCardViewModel) ViewModelProviders.of(this, factory).get(inners[i]);
////                this.getLifecycle().addObserver(nitcommonCardViewModelArr[i]);
//            }
//        }
//        delegetCommand.next(mViewModel, nitcommonCardViewModelArr, this);
//    }
//
//
//    @Override
//    public void initImmersionBar() {
//
//    }
//
//    @Override
//    public void onVisible() {
//        super.onVisible();
//    }
//}
