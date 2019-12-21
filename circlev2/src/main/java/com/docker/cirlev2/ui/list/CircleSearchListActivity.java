package com.docker.cirlev2.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2SearchActivityBinding;
import com.docker.cirlev2.vm.CircleMinesViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.container.NitCommonContainerFragmentV2;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;

import java.util.ArrayList;

/*
 * 圈子搜索
 * */
@Route(path = AppRouter.CIRCLE_SEARCH_LIST)
public class CircleSearchListActivity extends NitCommonActivity<SampleListViewModel, Circlev2SearchActivityBinding> {

    private String keyword;
    private NitCommonContainerFragmentV2 mSerachFrame;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_search_activity;
    }

    @Override
    public SampleListViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }

    @Override
    public void initView() {
        mToolbar.hide();

        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.falg = 105;
        commonListOptions.isActParent = true;
        commonListOptions.ReqParam.put("uuid", "3c29a4eed44db285468df3443790e64a");
        commonListOptions.ReqParam.put("memberid", "3");

        NitBaseProviderCard.providerCoutainerForFrame(getSupportFragmentManager(), R.id.circle_serach_frame, commonListOptions);
        mBinding.circleTvCancel.setOnClickListener(v -> {
            finish();
        });

        mBinding.circleEditInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keyword = mBinding.circleEditInput.getText().toString().trim();
                    if (TextUtils.isEmpty(keyword)) {
                        mBinding.circleSerachFrame.setVisibility(View.INVISIBLE);
                    } else {
                        mBinding.circleSerachFrame.setVisibility(View.VISIBLE);
                        ArrayList<Pair<String, String>> pairList = new ArrayList<>();
                        pairList.add(new Pair("keyword", keyword));
                        mSerachFrame.UpdateReqParam(false, pairList);
                        mSerachFrame.onReFresh(null);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return CircleMinesViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                mSerachFrame = (NitCommonContainerFragmentV2) nitCommonFragment;
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

}
