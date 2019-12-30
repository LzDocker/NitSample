package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.dcbfhd.utilcode.utils.LogUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleReplyListBinding;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleShoppingCarListBinding;
import com.docker.cirlev2.vm.CircleCommentListViewModel;
import com.docker.cirlev2.vm.CircleShoppingCarViewModel;
import com.docker.cirlev2.vo.entity.CommentVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.SoftKeyBroadManager;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnLoadMoreListener;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;

import java.util.HashMap;

import javax.inject.Inject;

/*
 *  购物车列表
 * */
@Route(path = AppRouter.CIRCLE_shopping_car)
public class CircleShoppingCarListActivity extends NitCommonActivity<CircleShoppingCarViewModel, Circlev2ActivityCircleShoppingCarListBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    CommentVo commentVo;
    //    NitAbsSampleAdapter adapter;
    private String replay;
    private ServiceDataBean serviceDataBean;
    private RelativeLayout relativeLayout;
    private NitCommonListVm outerVm;

    public static void startMe(Context context, CommentVo commentVo, ServiceDataBean serviceDataBean) {
        Intent intent = new Intent(context, CircleShoppingCarListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("commentVo", commentVo);
        bundle.putSerializable("serviceDataBean", serviceDataBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_circle_shopping_car_list;
    }

    @Override
    public CircleShoppingCarViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleShoppingCarViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        mToolbar.setTitle("购物车");
        TextView tvRight = mToolbar.getTvRight();
        mToolbar.setTvRight("编辑", view -> {
            String rightContent = tvRight.getText().toString();
            if (rightContent.contains("编辑")) {//点击编辑
                tvRight.setText("完成");
                mBinding.tvAccount.setVisibility(View.GONE);
                mBinding.tvDelete.setVisibility(View.VISIBLE);
                mBinding.linCount.setVisibility(View.GONE);
            } else if (rightContent.contains("完成")) {//点击完成
                tvRight.setText("编辑");
                mBinding.linCount.setVisibility(View.VISIBLE);
                mBinding.tvAccount.setVisibility(View.VISIBLE);
                mBinding.tvDelete.setVisibility(View.GONE);
            }


        });

//        mBinding.recycle.setAdapter(adapter);
        mBinding.tvAccount.setOnClickListener(v -> {

        });
        mBinding.tvDelete.setOnClickListener(view -> {

        });
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.falg = 1;
        commonListOptions.isActParent = true;
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.ReqParam.put("", "");
        NitBaseProviderCard.providerCoutainerForFrame(CircleShoppingCarListActivity.this.getSupportFragmentManager(), R.id.frame, commonListOptions);
    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return CircleShoppingCarViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {
                outerVm = commonListVm;
                mBinding.setViewmodel((CircleShoppingCarViewModel) commonListVm);
            }
        };
        return nitDelegetCommand;
    }

    @Override
    public void initObserver() {
        mViewModel.mServerLiveData.observe(this, o -> {

        });

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }


}
