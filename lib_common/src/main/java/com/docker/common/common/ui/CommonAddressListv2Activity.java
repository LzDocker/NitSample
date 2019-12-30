package com.docker.common.common.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.R;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.CommonAddressViewModel;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.AddressVo;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.databinding.CommonActivityAddressListBinding;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 桃源公社====地址列表
 **/
@Route(path = AppRouter.COMMON_address_list)
public class CommonAddressListv2Activity extends NitCommonActivity<CommonAddressViewModel, CommonActivityAddressListBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    private List<AddressVo> addressVoList;
    private Disposable disposable;
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.common_activity_address_list;
    }

    @Override
    public CommonAddressViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CommonAddressViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public void initView() {
        mToolbar.setTitle("地址管理");
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.isActParent = true;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.ReqParam.put("", "");
        NitBaseProviderCard.providerCoutainerForFrame(this.getSupportFragmentManager(), R.id.frame, commonListOptions);

        //添加地址
        mBinding.tvAddAddress.setOnClickListener(view -> {

        });

    }


    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {

        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return CommonAddressViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {

            }
        };
        return nitDelegetCommand;
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
