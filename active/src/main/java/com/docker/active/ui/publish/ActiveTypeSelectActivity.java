package com.docker.active.ui.publish;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.active.R;
import com.docker.active.databinding.ProActiveBannerPreviewActivityBinding;
import com.docker.active.vm.ActiveCommonViewModel;
import com.docker.active.vo.LinkageVo;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;

@Route(path = AppRouter.ACTIVE_TYPE_SELECT)
public class ActiveTypeSelectActivity extends NitCommonActivity<NitEmptyViewModel, ProActiveBannerPreviewActivityBinding> {

    public ActiveCommonViewModel innerVm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.falg = 0;
        commonListOptions.isActParent = true;
        commonListOptions.refreshState = Constant.KEY_REFRESH_PURSE;
        commonListOptions.ReqParam.put("keyid", "3568");
        commonListOptions.ReqParam.put("parentid", "0");
        NitBaseProviderCard.providerCoutainerForFrame(getSupportFragmentManager(), R.id.common_frame, commonListOptions);
    }


    @Override
    public void initView() {

        mToolbar.setTitle("活动类型");
        mToolbar.setTvRight("完成", v -> {
            if (innerVm.mItems.size() == 0) {
                return;
            }

            LinkageVo linkageVo = null;
            for (int i = 0; i < innerVm.mItems.size(); i++) {
                if (((LinkageVo) innerVm.mItems.get(i)).isChecked()) {
                    linkageVo = (LinkageVo) innerVm.mItems.get(i);
                    break;
                }
            }
            if (linkageVo == null) {
                ToastUtils.showShort("请先选择");
            } else {
                Intent intent = new Intent();
                intent.putExtra("linkageVo", linkageVo);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return ActiveCommonViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                innerVm = (ActiveCommonViewModel) commonListVm;
            }
        };
        return nitDelegetCommand;
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_frame;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyViewModel.class);
    }

}

