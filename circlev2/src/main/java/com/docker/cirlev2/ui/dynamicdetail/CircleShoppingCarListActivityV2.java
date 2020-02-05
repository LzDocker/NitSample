package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleShoppingCarListBinding;
import com.docker.cirlev2.vm.CircleShoppingViewModel;
import com.docker.cirlev2.vm.CircleShoppingViewModelv2;
import com.docker.cirlev2.vo.entity.CommentVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.vo.ShoppingCarVo;
import com.docker.cirlev2.vo.vo.ShoppingCartDbVo;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.cache.DbCacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;

import java.util.ArrayList;

import javax.inject.Inject;

/*
 *  购物车列表
 * */
//@Route(path = AppRouter.CIRCLE_shopping_car)
public class CircleShoppingCarListActivityV2 extends NitCommonActivity<CircleShoppingViewModelv2, Circlev2ActivityCircleShoppingCarListBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    DbCacheUtils dbCacheUtils;

    CommentVo commentVo;
    //    NitAbsSampleAdapter adapter;
    private String replay;
    private ServiceDataBean serviceDataBean;
    private RelativeLayout relativeLayout;
    private NitCommonListVm outerVm;

    public static void startMe(Context context, CommentVo commentVo, ServiceDataBean serviceDataBean) {
        Intent intent = new Intent(context, CircleShoppingCarListActivityV2.class);
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
    public CircleShoppingViewModelv2 getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleShoppingViewModelv2.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        mToolbar.setTitle("购物车");
//        mBinding.setViewmodel(mViewModel);

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
        commonListOptions.ReqParam.put("memberid", CacheUtils.getUser().uid);
        NitBaseProviderCard.providerCoutainerNoRefreshForFrame(CircleShoppingCarListActivityV2.this.getSupportFragmentManager(), R.id.frame, commonListOptions);


    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return CircleShoppingViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {

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
