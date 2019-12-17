package com.docker.nitsample.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bfhd.account.vm.card.ProviderAccountCard;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.ui.safe.DynamicFragment;
import com.bfhd.circle.vo.StaDynaVo;
import com.dcbfhd.utilcode.utils.ConvertUtils;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.ui.base.NitCommonListFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.location.LocationManager;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.WorldNumList;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.indector.CommonIndector;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.FragmentIndexBinding;
import com.docker.nitsample.vm.MainViewModel;
import com.docker.nitsample.vm.card.ProviderAppCard;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class IndexFragment extends NitCommonFragment<MainViewModel, FragmentIndexBinding> {
    public static IndexFragment newInstance() {
        return new IndexFragment();
    }

    private UserInfoVo userInfoVo;
    private DynamicFragment SafeFragment;
    private DynamicFragment dangrousPushFragment;
    private List<Fragment> fragments;
    private WorldNumList.WorldEnty mCurWorldEntity;
    private NitCommonListVm[] innerArr;
    private NitCommonListVm outer;

    private float LL_SEARCH_MIN_TOP_MARGIN,
            LL_SEARCH_MAX_TOP_MARGIN,
            TV_TITLE_MAX_TOP_MARGIN,
            LL_SEARCH_MAX_LEFT_MARGIN,
            LL_SEARCH_MIN_LEFT_MARGIN,
            LL_SEARCH_MIN_RIGHT_MARGIN,
            LL_SEARCH_MAX_SE_HEIGHT,
            LL_SEARCH_MIN_SE_HEIGHT;
    private ViewGroup.MarginLayoutParams searchLayoutParams, titleLayoutParams;

    @Inject
    LocationManager locationManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    public MainViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        userInfoVo = CacheUtils.getUser();
        super.onCreate(savedInstanceStates);
        processLocation();
    }

    private void processLocation() {
        locationManager.processLocation(this.getHoldingActivity(),
                (o) -> {
                    if (o != null) {
                        Log.d("sss", "onCreate: ===============" + ((BDLocation) o).getAddrStr());
                        processViewpager();
                    } else {
                        Log.d("sss", "onCreate: =======定位失败========");
                    }
                },
                (geo) -> {
                    if (geo != null) {
                        Log.d("sss", "==city==" + ((ReverseGeoCodeResult) geo).getAddress());
                    } else {
                        Log.d("sss", "==citycode=failed=");
                    }
                }
        );
    }


    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = null;
        switch (flag) {
            case 1002:
                nitDelegetCommand = new NitDelegetCommand() {
                    @Override
                    public Class providerOuterVm() {
                        return null;
                    }

                    @Override
                    public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonListFragment) {

                        IndexFragment.this.outer = commonListVm;

//                        ProviderAccountCard.providerAccountDefaultCard(commonListVm, innerArr[0], nitCommonListFragment);
                        ProviderAppCard.providerAppDefaultCard(commonListVm, nitCommonListFragment);
                    }
                };
                break;
        }
        return nitDelegetCommand;
    }


    @Override
    protected void initView(View var1) {


        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.refreshState = Constant.KEY_REFRESH_PURSE;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.falg = 1002;

        ProviderAccountCard.providerCardForFrame(getChildFragmentManager(), R.id.frame_mine, commonListOptions);

        // 搜索
        mBinding.get().edSerchs.setOnClickListener(v -> {
//            ARouter.getInstance().build(AppRouter.App_SEARCH_index).withString("t", "search").navigation();


        });
        // 菜单
        // 地址
        mBinding.get().llRig.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACCOUNT_COUNTRY).navigation(IndexFragment.this.getActivity(), 1012);
        });

        mBinding.get().refresh.setEnableLoadMore(false);
        mBinding.get().refresh.setOnRefreshListener(refreshLayout -> {
            if (fragments != null && fragments.size() > 0) {
                ((CommonFragment) fragments.get(mBinding.get().viewPager.getCurrentItem())).OnRefresh(mBinding.get().refresh);
//                mViewModel.getIndexBanner("1");


            } else {
                mBinding.get().refresh.finishRefresh();
            }
        });
        LL_SEARCH_MIN_TOP_MARGIN = ConvertUtils.dp2px(4.5f);//布局关闭时顶部距离
        LL_SEARCH_MAX_TOP_MARGIN = ConvertUtils.dp2px(39f);//布局默认展开时顶部距离
        TV_TITLE_MAX_TOP_MARGIN = ConvertUtils.dp2px(11.5f);
        LL_SEARCH_MIN_RIGHT_MARGIN = ConvertUtils.dp2px(5f);
        LL_SEARCH_MAX_LEFT_MARGIN = ConvertUtils.dp2px(23f);
        LL_SEARCH_MAX_SE_HEIGHT = ConvertUtils.dp2px(30f);//最大高度
        LL_SEARCH_MIN_SE_HEIGHT = ConvertUtils.dp2px(25f);//最小高度
        LL_SEARCH_MIN_LEFT_MARGIN = 0;
        titleLayoutParams = (ViewGroup.MarginLayoutParams) mBinding.get().searchTvTitle.getLayoutParams();
        searchLayoutParams = (ViewGroup.MarginLayoutParams) mBinding.get().searchLlSearch.getLayoutParams();
        mBinding.get().appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            float dy = -verticalOffset * 0.65f;
            float searchLayoutNewTopMargin = (float) (LL_SEARCH_MAX_TOP_MARGIN - dy * 0.5);
            float titleNewTopMargin = (float) (TV_TITLE_MAX_TOP_MARGIN - dy * 0.5);
            float searchLayoutLeftMargin = (float) (LL_SEARCH_MIN_LEFT_MARGIN + dy * 1.2);
            float searchLayoutRightMargin = (float) (LL_SEARCH_MIN_RIGHT_MARGIN + dy * 1.3);
            float searchHeight = (float) (LL_SEARCH_MAX_SE_HEIGHT - dy * 0.5);
            //处理布局的边界问题
            if (searchLayoutNewTopMargin < LL_SEARCH_MIN_TOP_MARGIN) {
                searchLayoutNewTopMargin = LL_SEARCH_MIN_TOP_MARGIN;
            }
            if (searchLayoutLeftMargin < LL_SEARCH_MIN_LEFT_MARGIN) {
                searchLayoutLeftMargin = LL_SEARCH_MIN_LEFT_MARGIN;
            }

            if (searchLayoutRightMargin < LL_SEARCH_MIN_RIGHT_MARGIN) {
                searchLayoutRightMargin = LL_SEARCH_MIN_RIGHT_MARGIN;
            }
            if (searchLayoutLeftMargin > LL_SEARCH_MAX_LEFT_MARGIN) {
                searchLayoutLeftMargin = LL_SEARCH_MAX_LEFT_MARGIN;
            }
            if (searchLayoutRightMargin > mBinding.get().llRig.getWidth() + ConvertUtils.dp2px(5)) {
                searchLayoutRightMargin = mBinding.get().llRig.getWidth() + ConvertUtils.dp2px(5);
            }
            if (searchHeight > LL_SEARCH_MAX_SE_HEIGHT) {
                searchHeight = LL_SEARCH_MAX_SE_HEIGHT;
            }
            if (searchHeight < LL_SEARCH_MIN_SE_HEIGHT) {
                searchHeight = LL_SEARCH_MIN_SE_HEIGHT;
            }
            float titleAlpha = 255 * titleNewTopMargin / TV_TITLE_MAX_TOP_MARGIN;
            if (titleAlpha < 0) {
                titleAlpha = 0;
            }
            mBinding.get().searchTvTitle.setTextColor(mBinding.get().searchTvTitle.getTextColors().withAlpha((int) titleAlpha));
            titleLayoutParams.topMargin = (int) titleNewTopMargin;
            mBinding.get().searchTvTitle.setLayoutParams(titleLayoutParams);
            searchLayoutParams.topMargin = (int) searchLayoutNewTopMargin + ConvertUtils.dp2px((float) 3.5);
            searchLayoutParams.rightMargin = (int) searchLayoutRightMargin;
            searchLayoutParams.leftMargin = (int) searchLayoutLeftMargin;
            searchLayoutParams.height = (int) searchHeight;
            mBinding.get().searchLlSearch.setLayoutParams(searchLayoutParams);
        });
    }


    private void processViewpager() {


        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.uid = "67";
        userInfoVo.uuid = "420cd8fd09e4ae6cfb8f3b3fdf5b7af4";
        userInfoVo.memberid = "67";
        CacheUtils.saveUser(userInfoVo);

        fragments = new ArrayList<>();
        StaDynaVo staDynaVo2 = new StaDynaVo();
        staDynaVo2.UiType = 1;
        staDynaVo2.ReqType = 1;
        staDynaVo2.ReqParam.put("t", "goodsRecommend");
        staDynaVo2.ReqParam.put("isrecommend", "1");
        staDynaVo2.ReqParam.put("goodsui", "goods");
        staDynaVo2.ReqParam.put("cityCode", "131");
        staDynaVo2.special = 1;
        dangrousPushFragment = DynamicFragment.newInstance(staDynaVo2, null);
        fragments.add(dangrousPushFragment);

//        fragments.add(CircleRecommandPersionListFragment.newInstance());
        StaDynaVo staDynaVo = new StaDynaVo();
        staDynaVo.UiType = 1;
        staDynaVo.ReqType = 1;
        staDynaVo.ReqParam.put("t", "project");
        staDynaVo.ReqParam.put("isrecommend", "1");
        staDynaVo.ReqParam.put("goodsui", "project");
        staDynaVo.ReqParam.put("cityCode", "131");
        SafeFragment = DynamicFragment.newInstance(staDynaVo, null);
        fragments.add(SafeFragment);


        StaDynaVo staDynaVo3 = new StaDynaVo();
        staDynaVo3.UiType = 1;
        staDynaVo3.ReqType = 1;
        staDynaVo3.ReqParam.put("uuid", "420cd8fd09e4ae6cfb8f3b3fdf5b7af4");
        staDynaVo3.ReqParam.put("memberid", "67");
        staDynaVo3.ReqParam.put("act", "focus");
        staDynaVo3.ReqParam.put("t", "search");
        staDynaVo3.ReqParam.put("goodsui", "goods");
        fragments.add(DynamicFragment.newInstance(staDynaVo3, null));


        StaDynaVo staDynaVo4 = new StaDynaVo();
        staDynaVo4.UiType = 1;
        staDynaVo4.ReqType = 1;
        staDynaVo4.ReqParam.put("uuid", "420cd8fd09e4ae6cfb8f3b3fdf5b7af4");
        staDynaVo4.ReqParam.put("memberid", "67");
        staDynaVo4.ReqParam.put("act", "collect");
        staDynaVo4.ReqParam.put("t", "search");
        staDynaVo4.ReqParam.put("goodsui", "goods");
        fragments.add(DynamicFragment.newInstance(staDynaVo4, null));
        initMagicIndicator();
    }

    private void initMagicIndicator() {
        String mTitleList[] = new String[]{"商品推荐", "项目推荐", "我的关注", "我的收藏"};
        mBinding.get().viewPager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments, mTitleList));
        CommonIndector commonIndector = new CommonIndector();
        commonIndector.initMagicIndicator(mTitleList, mBinding.get().viewPager, mBinding.get().magicIndicator, this.getHoldingActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1012) {
            WorldNumList.WorldEnty worldEnty = (WorldNumList.WorldEnty) data.getSerializableExtra("datasource");
            updateReqParam(worldEnty);
            mBinding.get().appBar.setExpanded(true, true);
        }
    }


    // 商品推荐 项目推荐 更新参数
    private void updateReqParam(WorldNumList.WorldEnty worldEnty) {
        // 选择地址
        mBinding.get().tvAddress.setText(worldEnty.name);
        ArrayList<Pair<String, String>> pairs = new ArrayList<>();
        pairs.add(new Pair<>("countryid", worldEnty.id));
        pairs.add(new Pair<>("cityCode", worldEnty.cityCode));
        pairs.add(new Pair<>("type", worldEnty.curtype));
        if (fragments != null) {
            ((DynamicFragment) fragments.get(0)).UpdateReqParam(false, pairs);
            ((DynamicFragment) fragments.get(1)).UpdateReqParam(false, pairs);
        }
    }

    @Override
    public void initImmersionBar() {
    }
}
