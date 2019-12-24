package com.docker.nitsample.ui.optimization;

import android.view.View;

import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.IndexTygsFragmentBinding;
import com.docker.nitsample.databinding.OptimizationFragmentBinding;

/**
 * 公社优选
 */
public class OptimizationFragment extends NitCommonFragment<NitCommonContainerViewModel, OptimizationFragmentBinding> {



    @Override
    protected int getLayoutId() {
        return R.layout.optimization_fragment;
    }

    @Override
    protected NitCommonContainerViewModel getViewModel() {
        return null;
    }

    @Override
    protected void initView(View var1) {

    }

    @Override
    public void initImmersionBar() {

    }
}
