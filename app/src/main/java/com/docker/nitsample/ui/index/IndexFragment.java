package com.docker.nitsample.ui.index;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.ui.safe.DynamicFragment;
import com.bfhd.circle.vo.StaDynaVo;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.dcbfhd.utilcode.utils.ConvertUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.WorldNumList;
import com.docker.common.common.widget.ColorFlipPagerTitleView;
import com.docker.nitsample.R;
import com.docker.nitsample.databinding.FragmentIndexBinding;
import com.docker.nitsample.vm.MainViewModel;
import com.luck.picture.lib.permissions.RxPermissions;
import com.youth.banner.loader.ImageLoader;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class IndexFragment extends NitCommonFragment<MainViewModel, FragmentIndexBinding> {
    public static IndexFragment newInstance() {
        return new IndexFragment();
    }

    private GeoCoder geocode;
    private UserInfoVo userInfoVo;
    private DynamicFragment SafeFragment;
    private DynamicFragment dangrousPushFragment;
    private List<Fragment> fragments;
    public LocationClient mLocationClient = null;
    private WorldNumList.WorldEnty mCurWorldEntity;
    private String lat;
    private String lng;
    private String cityCode = "";
    private float LL_SEARCH_MIN_TOP_MARGIN,
            LL_SEARCH_MAX_TOP_MARGIN,
            TV_TITLE_MAX_TOP_MARGIN,
            LL_SEARCH_MAX_LEFT_MARGIN,
            LL_SEARCH_MIN_LEFT_MARGIN,
            LL_SEARCH_MIN_RIGHT_MARGIN,
            LL_SEARCH_MAX_SE_HEIGHT,
            LL_SEARCH_MIN_SE_HEIGHT;
    private ViewGroup.MarginLayoutParams searchLayoutParams, titleLayoutParams;


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

    }


    @Override
    protected void initView(View var1) {


        // 获取轮播图
        // 定位
        initLocation();
        // 搜索
        mBinding.get().edSerchs.setOnClickListener(v -> {
//            ARouter.getInstance().build(AppRouter.App_SEARCH_index).withString("t", "search").navigation();
            ARouter.getInstance().build(AppRouter.Video_Player)
                    .withString("videoUrl", "https://zaijiaxue.oss-cn-beijing.aliyuncs.com/static2/var/upload/cn/language/6%20%E9%BB%84%E5%B1%B1%E5%A5%87%E6%9D%BE_L%20CN.mp4")
                    .withString("thumbUrl", "http://app.zjxk12.com/var/upload/2019/05/2019050508195443760_600x400.jpg")
                    .navigation();
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

    private void initCityCode() {
        if (TextUtils.isEmpty(cityCode)) {
            if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)) {
                cityCode = "";
                processViewpager();
            } else {
                initGeoCode();
            }
        } else {
            processViewpager();
        }
    }

    protected void initGeoCode() {
        //新建编码查询对象
        geocode = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            /**
             * 反地理编码查询结果回调函数
             * @param result  反地理编码查询结果
             */
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    cityCode = "";
                }
                if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                    //得到位置
                    cityCode = result.getAddressDetail().countryCode + "";
                }
                processViewpager();
            }

            /**
             * 地理编码查询结果回调函数
             * @param result  地理编码查询结果
             */
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                Log.i("gjw", "myWeburl: ");
            }
        };
        //设置查询结果监听者
        geocode.setOnGetGeoCodeResultListener(listener);
        //新建查询对象要查询的条件
        LatLng latLng = new LatLng(Float.parseFloat(lat), Float.parseFloat(lng));
        ReverseGeoCodeOption options = new ReverseGeoCodeOption().location(latLng);
        // 发起反地理编码请求
        geocode.reverseGeoCode(options);
    }

    private void processViewpager() {
//        mBinding.get().empty.setVisibility(View.GONE);


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


        //uuid=420cd8fd09e4ae6cfb8f3b3fdf5b7af4&goodsui=goods&memberid=67
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
        CommonNavigator mCommonNavigator = new CommonNavigator(this.getHoldingActivity());
        mCommonNavigator.setAdjustMode(true);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragments == null ? 0 : fragments.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mTitleList[index]);
                ((ColorFlipPagerTitleView) simplePagerTitleView).setNomalTextSize(13f);
                ((ColorFlipPagerTitleView) simplePagerTitleView).setSelectTextSize(15f);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setOnClickListener(v -> mBinding.get().viewPager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
//                indicator.setLineHeight(UIUtil.dip2px(context, 4));
//                indicator.setLineWidth(UIUtil.dip2px(context, 45));
//                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getResources().getColor(R.color.colorPrimaryDark));
                return indicator;
            }
        });
        mBinding.get().magicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mBinding.get().magicIndicator, mBinding.get().viewPager);
    }

    private void initLocation() {

        processLocation();
//        mLocationClient = new LocationClient(getHoldingActivity());
//        LocationClientOption option = new LocationClientOption();
//        mLocationClient.setLocOption(option);
//        myListener = new MyLocationListener();
//        option.setOpenGps(true); // 打开gps
//        mLocationClient.registerLocationListener(myListener);//注册监听函数
//        mLocationClient.start();
    }

    private int locationCount = 0;
    private String mCountryStr;

    private void processLocation() {
        locationCount = 0;
        mLocationClient = new LocationClient(this.getHoldingActivity());
        RxPermissions rxPermissions = new RxPermissions(this.getHoldingActivity());
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(permission -> {
                    if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LocationClientOption option = new LocationClientOption();
                            option.setIsNeedAddress(true);
                            option.setOpenGps(true); // 打开gps
                            option.setCoorType("bd09ll"); // 设置坐标类型
                            option.setScanSpan(1000);
                            mLocationClient.setLocOption(option);
                            mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
                                @Override
                                public void onReceiveLocation(BDLocation location) {
                                    if (location.getCountry() != null) {
                                        mLocationClient.stop();
                                        mCountryStr = location.getCity();
                                        cityCode = location.getCityCode();
                                        mBinding.get().tvAddress.setText(mCountryStr);
                                        lat = String.valueOf(location.getLatitude());
                                        lng = String.valueOf(location.getLongitude());
                                        processViewpager();
//                                        mViewModel.fetchWordListwj("1");
                                    } else {
                                        locationCount++;
                                        if (locationCount > 1) {
                                            mLocationClient.stop();
                                            mBinding.get().tvAddress.setText("未定位");
                                            processViewpager();
                                        }
                                    }
                                }
                            });
                            mLocationClient.start();
                        } else {
                            ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                            mBinding.get().tvAddress.setText("未定位");
                            processViewpager();
                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        mBinding.get().banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBinding.get().banner.stopAutoPlay();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            RequestOptions options = new RequestOptions();
            options.transforms(new CenterCrop(), new RoundedCorners(18));
//            Glide.with(IndexFragment.this)
//                    .load(Constant.getCompleteImageUrl(((AdVo) path).imgurl))
//                    .apply(options)
//                    .into(imageView);
        }
    }


    @Override

    public void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        if (geocode != null) {
            geocode.destroy();
        }
        super.onDestroy();
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
