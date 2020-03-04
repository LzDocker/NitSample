package com.docker.nitsample.ui.edit;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.FragmentEditCoutainerBinding;
import com.docker.nitsample.vm.SampleEditCoutainerViewModel;
public class EditCoutainerFragment extends NitCommonFragment<SampleEditCoutainerViewModel, FragmentEditCoutainerBinding> {


    public static EditCoutainerFragment getInstance() {
        EditCoutainerFragment editSpaFragment = new EditCoutainerFragment();
        return editSpaFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_coutainer;
    }

    @Override
    protected SampleEditCoutainerViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleEditCoutainerViewModel.class);
    }

    @Override
    protected void initView(View var1) {


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.refreshState = Constant.KEY_REFRESH_PURSE;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.falg = 1002;
        NitBaseProviderCard.providerCoutainerForFrame(getChildFragmentManager(), R.id.frame_tools, commonListOptions);
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {

                return SampleEditCoutainerViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonListFragment) {

            }
        };

        return nitDelegetCommand;
    }


}
