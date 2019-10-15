package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.Constant;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsAbsSampleAdapter;
import com.bfhd.circle.databinding.CircleItemMycircleBinding;
import com.bfhd.circle.databinding.CircleActivityCirclePersionDetailBinding;
import com.bfhd.circle.ui.safe.DynamicFragment;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.CircleCountpageVo;
import com.bfhd.circle.vo.CircleListVo;
import com.bfhd.circle.vo.StaDynaVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.bfhd.circle.vo.bean.StaPersionDetail;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.appbar.AppBarStateChangeListener;
import com.gyf.immersionbar.ImmersionBar;
import java.util.List;

import javax.inject.Inject;

/*
 * 成员主页
 * */
//@Route(path = AppRouter.CIRCLE_persion_detail)
public class CirclePersonDetailActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCirclePersionDetailBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    public StaPersionDetail mStartParam;

    private HivsAbsSampleAdapter adapter;
    private DynamicFragment dynamicFragment;

    private CircleCountpageVo vo;

    public static void startMe(Context context, StaPersionDetail staPersionDetail) {
        Intent intent = new Intent(context, CirclePersonDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", staPersionDetail);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_persion_detail;
    }


    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isOverrideContentView = true;
        super.onCreate(savedInstanceState);
        initIntent();
        mBinding.setViewmodel(mViewModel);
        mBinding.empty.hide();

    }

    private void initIntent() {
        mStartParam = (StaPersionDetail) getIntent().getExtras().getSerializable("mStartParam");
        mViewModel.circlePersionDetail(mStartParam.uid, mStartParam.uuid);
        mViewModel.getCircleJoinList(mStartParam.uid, mStartParam.uuid);

        StaDynaVo staDynaVo = new StaDynaVo();
        staDynaVo.UiType = 1;
        staDynaVo.ReqType = 1;
        UserInfoVo userInfoVo = CacheUtils.getUser();
        staDynaVo.ReqParam.put("memberid", userInfoVo.uid);
        staDynaVo.ReqParam.put("uuid", userInfoVo.uuid);
        staDynaVo.ReqParam.put("t", "circle");
        staDynaVo.ReqParam.put("memberid2", mStartParam.uid);
        staDynaVo.ReqParam.put("uuid2", mStartParam.uuid);
        dynamicFragment = DynamicFragment.newInstance(staDynaVo, null);
        FragmentUtils.add(getSupportFragmentManager(), dynamicFragment, R.id.frame_dynamic);


        if (mStartParam.uuid.equals(userInfoVo.uuid)) {
            mBinding.llFocuCou.setVisibility(View.GONE);
        } else {
            mBinding.llFocuCou.setVisibility(View.VISIBLE);
        }
    }

    public void initView() {

        mBinding.appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state) {
                    case IDLE:
                        mBinding.title.setText("");
                        break;
                    case EXPANDED:
                        mBinding.title.setText("");
                        break;
                    case COLLAPSED:
                        if (!TextUtils.isEmpty(mStartParam.name)) {
                            mBinding.title.setText(mStartParam.name);
                        }
                        break;
                }
            }
        });
        mBinding.tvAlerFocus.setOnClickListener(v -> {
            if (vo != null) {
                mViewModel.focus(mStartParam.uid, mStartParam.uuid, vo);
            }
        });
        mBinding.tvFocu.setOnClickListener(v -> {
            if (vo != null) {
                mViewModel.focus(mStartParam.uid, mStartParam.uuid, vo);
            }
        });
        mBinding.refresh.setOnLoadMoreListener(v -> {
            dynamicFragment.OnLoadMore(mBinding.refresh);
        });
        mBinding.refresh.setOnRefreshListener(v -> {
            mViewModel.getCircleJoinList();
            dynamicFragment.OnRefresh(mBinding.refresh);
        });
        mBinding.ivBack.setOnClickListener(v -> {
            finish();
        });
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mBinding.recyclerView);
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new HivsAbsSampleAdapter(R.layout.circle_item_mycircle, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                CircleItemMycircleBinding itemMyciecleBinding = (CircleItemMycircleBinding) holder.getBinding();
                Glide.with(holder.itemView.getContext()).load(Constant.getCompleteImageUrl(((CircleListVo) getItem(position)).getLogoUrl())).apply(new RequestOptions().error(R.mipmap.circle_icon_empty).placeholder(R.mipmap.circle_icon_empty)).into(itemMyciecleBinding.ivThumb);

            }
        };
        mBinding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view1, position) -> {
            CircleListVo circleListVo = (CircleListVo) adapter.getmObjects().get(position);
            CircleDetailActivity.startMe(this, new StaCirParam(circleListVo.circleid, circleListVo.getUtid(), ""));
        });
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .titleBar(mBinding.toolbar)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                .navigationBarColor("#ffffff")
                .init();
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 107:
                mBinding.refresh.finishRefresh();
                mBinding.refresh.finishLoadMore();
                if((List<CircleListVo>) viewEventResouce.data!=null && ((List<CircleListVo>) viewEventResouce.data).size()>0){
                    adapter.getmObjects().clear();
                    adapter.getmObjects().addAll((List<CircleListVo>) viewEventResouce.data);
                    adapter.notifyDataSetChanged();
                }
                break;
            case 213:
                vo = (CircleCountpageVo) viewEventResouce.data;
                if (vo != null) {
                    mBinding.setItem(vo);
                }
                break;
        }
    }

}
