package com.docker.active.ui.manager;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.active.R;
import com.docker.active.databinding.ProActiveSearchBinding;
import com.docker.active.vm.ActiveCommonViewModel;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.card.NitBaseProviderCard;

import java.util.HashMap;

/*
 * 活动搜索
 **/
@Route(path = AppRouter.ACTIVE_SEARCH_LIST)
public class ActiveSearchActivity extends NitCommonActivity<NitCommonContainerViewModel, ProActiveSearchBinding> {

    private NitCommonContainerFragmentV2 mCommonFragment;

    @Override
    public NitCommonContainerViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitCommonContainerViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_search;
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.hide();

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.isActParent = true;
        commonListOptions.falg = 101;
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.ReqParam.put("keywords", "");
        NitBaseProviderCard.providerCoutainerForFrame(getSupportFragmentManager(), R.id.frame, commonListOptions);

    }

    @Override
    public void initView() {

        mBinding.edSerch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mBinding.edSerch.getText().toString().trim())) {
                    mCommonFragment.UpdateReqParam(false, new Pair<>("keywords", ""));
                    mCommonFragment.mViewModel.mItems.clear();
                } else {
                    mCommonFragment.UpdateReqParam(false, new Pair<>("keywords", mBinding.edSerch.getText().toString().trim()));
                    mCommonFragment.onReFresh(null);
                }
            }
        });
    }

    @Override
    public void initObserver() {

    }


    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        return new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return ActiveCommonViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                mCommonFragment = (NitCommonContainerFragmentV2) nitCommonFragment;
                ActiveCommonViewModel innerVm = (ActiveCommonViewModel) commonListVm;
                innerVm.apiType = 3;
                innerVm.mActiveSelectVov.observe(nitCommonFragment, activeSelectVo -> {
                    if (activeSelectVo != null) {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("acvitename", activeSelectVo.title);
                        hashMap.put("activeid", activeSelectVo.id);
                        Intent intent = new Intent();
                        intent.putExtra("activeInfo", hashMap);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        };
    }

    @Override
    public void initRouter() {

    }
}
