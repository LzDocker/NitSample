package com.docker.nitsample.ui.edit;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.ActivityPreviewBinding;

import java.util.ArrayList;

import static com.docker.common.common.router.AppRouter.HOME_preview;

@Route(path = HOME_preview)
public class PreviewEditActivity extends NitCommonActivity<NitCommonContainerViewModel, ActivityPreviewBinding> {

    @Autowired
    String dbTabid;

    @Autowired
    boolean isEdit;

    String title = "";

    private ArrayList<BaseItemModel> config;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    public NitCommonContainerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitCommonContainerViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getSerializableExtra("config") != null) {
            FragmentUtils.add(getSupportFragmentManager(),
                    EditSpaFragment.getInstance((ArrayList<BaseCardVo>) getIntent().getSerializableExtra("config"),
                            getIntent().getBooleanExtra("isEdit", false)),
                    R.id.frame_spa);
        } else {
            FragmentUtils.add(getSupportFragmentManager(),
                    EditSpaFragment.getInstance(getIntent().getStringExtra("dbTabid"),
                            getIntent().getBooleanExtra("isEdit", false)),
                    R.id.frame_spa);
        }
    }

    @Override
    public void initView() {
        if (TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            mToolbar.hide();
        } else {
            mToolbar.setTitle(getIntent().getStringExtra("title"));
        }
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {

        return null;
    }


}
