package com.docker.nitsample.ui;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsSampleAdapter;
import com.bfhd.circle.ui.adapter.ScreenFlowAdapter;
import com.bfhd.circle.vm.CircleDynamicViewModel;
import com.bfhd.circle.vo.HotItemVo;
import com.bfhd.circle.vo.ScreenVo;
import com.bfhd.circle.vo.ScreenVo2;
import com.bfhd.circle.vo.StaDynaVo;
import com.bfhd.circle.vo.TypeVo;
import com.bfhd.circle.vo.bean.GoodsTypeListParam;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.RstServerVo;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.dialog.ConfirmDialog;
import com.docker.common.common.widget.popwindow.CommonPopuwindow;
import com.docker.core.BR;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;
import com.docker.nitsample.R;
import com.docker.nitsample.adapter.FlowAdapter;
import com.docker.nitsample.databinding.ActivityIndexSearchBinding;
import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import static com.dcbfhd.utilcode.utils.ConvertUtils.dp2px;

/*
 * 搜索
 * */

@Route(path = AppRouter.App_SEARCH_index)
public class IndexSearchActivity extends HivsBaseActivity<CircleDynamicViewModel, ActivityIndexSearchBinding> {

    private String keyword = "";

    private boolean isSearching = false;

    @Autowired()
    public String t = "-1";   // type

    public String selectType = "product";

    @Inject
    ViewModelProvider.Factory factory;

    private HivsSampleAdapter hivsSampleAdapter;
    private List<RstServerVo> hotItemVoList;

    private CommonPopuwindow commonPopuwindow;
    private CommonPopuwindow sortCommonPopuwindow;
    private List<HotItemVo> sortItemList;
    private CommonPopuwindow mSelectPop;
    private List<ScreenVo2> screenVoList2;
    private ScreenFlowAdapter screenFlowAdapter;
    private StringBuilder stringBuilder;
    private RecyclerView screenRecyclerView;
    private GoodsTypeListParam goodsTypeListParam;

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index_search;
    }

    @Override
    public CircleDynamicViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleDynamicViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    UserInfoVo userInfoVo = new UserInfoVo();// CacheUtils.getUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isOverrideContentView = true;
        super.onCreate(savedInstanceState);
        userInfoVo.memberid="1";
        userInfoVo.uuid="1";
        mBinding.setViewmodel(mViewModel);
        mBinding.llHistoryList.setVisibility(View.VISIBLE);
        mBinding.llHotList.setVisibility(View.VISIBLE);
        mBinding.refresh.setVisibility(View.GONE);
        mBinding.rlScree.setVisibility(View.GONE);
    }


    private void doSerach() {
        mBinding.llHistoryList.setVisibility(View.GONE);
        mBinding.llHotList.setVisibility(View.GONE);
        mBinding.refresh.setVisibility(View.VISIBLE);
        mBinding.rlScree.setVisibility(View.VISIBLE);
        StaDynaVo staDynaVo2 = new StaDynaVo();
        staDynaVo2.UiType = 2;
        staDynaVo2.ReqType = 1;
        staDynaVo2.ReqParam.put("memberid", userInfoVo.uid);
        staDynaVo2.ReqParam.put("is_search", "1");
        staDynaVo2.ReqParam.put("uuid", userInfoVo.uuid);
        if (!TextUtils.isEmpty(t) && !t.equals("-1")) {
            staDynaVo2.ReqParam.put("t", t);
        }
        staDynaVo2.ReqParam.put("keyword", keyword);
        mViewModel.mStaDy = staDynaVo2;
        mViewModel.mPage = 1;
        mViewModel.getInfoData();
    }

    private void initScreeData(StaDynaVo staDynaVo2) {
        mBinding.llHistoryList.setVisibility(View.GONE);
        mBinding.llHotList.setVisibility(View.GONE);
        mBinding.refresh.setVisibility(View.VISIBLE);
        mBinding.rlScree.setVisibility(View.VISIBLE);
//        UserInfoVo userInfoVo = CacheUtils.getUser();
        staDynaVo2.UiType = 2;
        staDynaVo2.ReqType = 1;
        staDynaVo2.ReqParam.put("memberid", userInfoVo.uid);
        staDynaVo2.ReqParam.put("uuid", userInfoVo.uuid);
        staDynaVo2.ReqParam.put("is_search", "1");
        if (!TextUtils.isEmpty(t) && !t.equals("-1")) {
            staDynaVo2.ReqParam.put("t", t);
        }
        staDynaVo2.ReqParam.put("keyword", keyword);
        mViewModel.mStaDy = staDynaVo2;
        mViewModel.mPage = 1;
        mViewModel.getInfoData();
    }

    @Override
    public void initView() {
        mBinding.refresh.setOnRefreshListener(refreshLayout -> {
            doSerach();
        });

        hotItemVoList = new ArrayList<>();
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        mBinding.hotRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(4)));
        mBinding.hotRecyclerView.setLayoutManager(flowLayoutManager);
//        initFlowLayoutData();
        mViewModel.fetchHotSearch();
        mBinding.refresh.setOnLoadMoreListener(refreshLayout -> mViewModel.getInfoData());
        mBinding.edSerch.setOnEditorActionListener((v, actionId, event) -> {
            keyword = mBinding.edSerch.getText().toString().trim();
            if (TextUtils.isEmpty(keyword)) {
                mBinding.llHistoryList.setVisibility(View.VISIBLE);
                mBinding.llHotList.setVisibility(View.VISIBLE);
                mBinding.refresh.setVisibility(View.GONE);
                mBinding.rlScree.setVisibility(View.GONE);
            } else {
                mBinding.llHistoryList.setVisibility(View.GONE);
                mBinding.llHotList.setVisibility(View.GONE);
                mBinding.refresh.setVisibility(View.VISIBLE);
                mBinding.rlScree.setVisibility(View.VISIBLE);
                StaDynaVo staDynaVo2 = new StaDynaVo();
           //     UserInfoVo userInfoVo = CacheUtils.getUser();
                staDynaVo2.UiType = 2;
                staDynaVo2.ReqType = 1;
                staDynaVo2.ReqParam.put("memberid", userInfoVo.uid);
                staDynaVo2.ReqParam.put("uuid", userInfoVo.uuid);
                if (!TextUtils.isEmpty(t) && !t.equals("-1")) {
                    staDynaVo2.ReqParam.put("t", t);
                }
                staDynaVo2.ReqParam.put("keyword", keyword);
                mViewModel.mStaDy = staDynaVo2;
                mViewModel.mPage = 1;
                if (!isSearching) {
                    mViewModel.items.clear();
                    mViewModel.getInfoData();
                    CacheUtils.saveSerachChache(t, keyword);
                    List<String> lists = CacheUtils.getSerachChache(t);
                    hivsSampleAdapter.clear();
                    hivsSampleAdapter.add(lists);
                    isSearching = true;
                }
            }
            return true;
        });

        mBinding.linBack.setOnClickListener(v -> {
            finish();
        });

        mBinding.circleTvType.setOnClickListener(v -> {
//            ARouter.getInstance().build(AppRouter.CIRCLE_Goods_type)
//                    .withString("speical", "search")
//                    .withSerializable("mStaparam", goodsTypeListParam)
//                    .navigation(this, 1001);
        });

        mBinding.circleTvArea.setOnClickListener(v -> {
            ARouter.getInstance().build(AppRouter.ACCOUNT_COUNTRY).navigation(this, 1002);
        });

        SimpleCommonRecyclerAdapter sortRecyclerAdapter = new SimpleCommonRecyclerAdapter(com.bfhd.circle.R.layout.circle_goods_select_item, com.docker.core.BR.item);
        sortRecyclerAdapter.setOnItemClickListener((view, position) -> {
            TextView textView = view.findViewById(com.bfhd.circle.R.id.tv_select_circle_list_title);
            for (int i = 0; i < sortRecyclerAdapter.getmObjects().size(); i++) {
                HotItemVo hotItemVo = (HotItemVo) sortRecyclerAdapter.getItem(i);
                hotItemVo.setCheck(false);
            }
            HotItemVo hotItemVo1 = (HotItemVo) sortRecyclerAdapter.getItem(position);
            hotItemVo1.setCheck(true);
            sortRecyclerAdapter.notifyDataSetChanged();
            if (5 == position) {
                processLocation();
            } else {
                StaDynaVo staDynaVo2 = new StaDynaVo();
                staDynaVo2.ReqParam.put("sort", hotItemVo1.getId());
                initScreeData(staDynaVo2);
                sortCommonPopuwindow.dismiss();
            }
        });

        mBinding.circleTvSort.setOnClickListener(v -> {
            sortCommonPopuwindow = new CommonPopuwindow.Builder(this)
                    .setView(com.bfhd.circle.R.layout.circle_pop_goods_sort_list)
                    .setViewOnclickListener((view, layoutResId) -> {
                        RecyclerView recyclerView = view.findViewById(com.bfhd.circle.R.id.recycle);
                        LinearLayoutManager hotLayoutManager = new LinearLayoutManager(IndexSearchActivity.this);
                        if (sortItemList != null && sortRecyclerAdapter.getmObjects().size() > 0) {

                        } else {
                            initSortItem();
                            sortRecyclerAdapter.getmObjects().addAll(sortItemList);
                        }
                        recyclerView.setLayoutManager(hotLayoutManager);
                        recyclerView.setAdapter(sortRecyclerAdapter);
                        sortRecyclerAdapter.notifyDataSetChanged();
                    })
                    .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).create();
            sortCommonPopuwindow.showAsDropDown(v);
        });

        mBinding.circleTvSelect.setOnClickListener(v -> {
            if (KeyboardUtils.isSoftInputVisible(IndexSearchActivity.this)) {
                hideSoftKeyBoard();
                v.postDelayed(() -> showSelectPop(v), 200);
            } else {
                showSelectPop(v);
            }
        });


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
                    mBinding.llHistoryList.setVisibility(View.VISIBLE);
                    mBinding.llHotList.setVisibility(View.VISIBLE);
                    mBinding.refresh.setVisibility(View.GONE);
                    mBinding.rlScree.setVisibility(View.GONE);
                    selectType = "product";
                    initScreenView();
                    mBinding.circleTvType.setVisibility(View.VISIBLE);
                    isSearching = false;
                }
            }
        });
        List<String> lists = CacheUtils.getSerachChache(t);
        hivsSampleAdapter = new HivsSampleAdapter(R.layout.item_search, com.docker.core.BR.item);
        hivsSampleAdapter.add(lists);
        mBinding.recyclerView.setAdapter(hivsSampleAdapter);
        hivsSampleAdapter.setOnchildViewClickListener(new int[]{R.id.iv_del, R.id.item_coutainer}, (childview, position) -> {
            int i = childview.getId();
            if (i == R.id.iv_del) {
                CacheUtils.delSerachCache(t, (String) hivsSampleAdapter.getItem(position));
                hivsSampleAdapter.getmObjects().remove(position);
                hivsSampleAdapter.notifyDataSetChanged();
            } else if (i == R.id.item_coutainer) {
                mBinding.edSerch.setText((String) hivsSampleAdapter.getItem(position));
                keyword = (String) hivsSampleAdapter.getItem(position);
                doSerach();
            }
        });
        mBinding.tvDelAll.setOnClickListener(v -> {
            if (hivsSampleAdapter.getmObjects().size() > 0) {
                ConfirmDialog.newInstance("提示", "是否确定删除所有历史记录")
                        .setConfimLietener(new ConfirmDialog.ConfimLietener() {
                            @Override
                            public void onCancle() {

                            }

                            @Override
                            public void onConfim() {
                                hivsSampleAdapter.getmObjects().clear();
                                hivsSampleAdapter.notifyDataSetChanged();
                                CacheUtils.ClearSerachCache();
                            }

                            @Override
                            public void onConfim(String edit) {

                            }
                        }).setMargin(50).show(getSupportFragmentManager());

            } else {
                ToastUtils.showShort("暂无历史记录");
            }
        });
    }

    private void showSelectPop(View v) {
        mViewModel.getScreenData(selectType);
    }

    private void initScreenView() {

//        if ("house".equals(selectType)) {//租房
//            mBinding.circleTvType.setText("租房类型");
//        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams2.addRule(RelativeLayout.CENTER_VERTICAL);
        //0、3、4区域选项位于右侧
        if ("product".equals(selectType) || "house".equals(selectType) || "goods".equals(selectType) || "ziying".equals(selectType)) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mBinding.circleTvArea.setLayoutParams(layoutParams);
            layoutParams2.addRule(RelativeLayout.CENTER_IN_PARENT);
            mBinding.circleTvSort.setLayoutParams(layoutParams2);
        } else {//1、2、5区域选项居中
            mBinding.circleTvType.setVisibility(View.GONE);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mBinding.circleTvArea.setLayoutParams(layoutParams);

            layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mBinding.circleTvSort.setLayoutParams(layoutParams2);
        }


        mSelectPop = new CommonPopuwindow.Builder(this)
                .setView(R.layout.circle_pop_goods_select_layout)
                .setAnimationStyle(R.style.common_right_pop_animation)
                .setViewOnclickListener((view1, layoutResId) -> {
                    view1.findViewById(com.bfhd.circle.R.id.ll_pop_coutainer).setOnClickListener(vs -> {
                        mSelectPop.dismiss();
                    });
                    view1.findViewById(com.bfhd.circle.R.id.card_coutainer).setOnClickListener(vss -> {
                        return;
                    });
                    view1.findViewById(com.bfhd.circle.R.id.circle_tv_reset).setOnClickListener(vss -> {
                        ArrayList<Pair<String, String>> pairList = new ArrayList<>();
                        for (int i = 0; i < screenVoList2.size(); i++) {
                            ScreenVo2 screenVo2 = screenVoList2.get(i);
                            List<HotItemVo> hotItemVoList = screenVo2.getCheck_val();
                            for (int j = 0; j < hotItemVoList.size(); j++) {
                                HotItemVo hotItemVo = hotItemVoList.get(j);
                                hotItemVo.setCheck(false);
                            }
                            Pair<String, String> pair = new Pair<>(screenVo2.getFieldname(), "");
                            pairList.add(pair);
                        }
                        screenFlowAdapter.notifyDataSetChanged();
                    });

                    view1.findViewById(com.bfhd.circle.R.id.circle_tv_sure).setOnClickListener(vss -> {
                        StaDynaVo staDynaVo = new StaDynaVo();
                        for (int i = 0; i < screenVoList2.size(); i++) {
                            ScreenVo2 screenVo2 = screenVoList2.get(i);
                            List<HotItemVo> hotItemVoList = screenVo2.getCheck_val();
                            stringBuilder = new StringBuilder();
                            for (int j = 0; j < hotItemVoList.size(); j++) {
                                HotItemVo hotItemVo = hotItemVoList.get(j);
                                if (hotItemVo.isCheck() == true) {
                                    stringBuilder.append(hotItemVo.getId()).append(",");
                                }
                            }
                            String id = stringBuilder.toString();
                            if (id != null && id.length() > 0) {
                                if (id.contains(",")) {
                                    id = id.substring(0, id.length() - 1);
                                }
                            } else {
                                id = "";
                            }
                            Log.i("gjw", "showSelectPop: " + screenVo2.getFieldname() + " ====== " + id);
                            staDynaVo.ReqParam.put(screenVo2.getFieldname(), id);
                        }
                        initScreeData(staDynaVo);
                        mSelectPop.dismiss();

                    });
                    screenRecyclerView = view1.findViewById(com.bfhd.circle.R.id.recycle);

                })
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).create();
    }

    private void initSortItem() {
        HotItemVo hotItemVo1 = new HotItemVo("1", "价格从低到高");
        HotItemVo hotItemVo2 = new HotItemVo("2", "价格从高到低");
        HotItemVo hotItemVo3 = new HotItemVo("3", "太极豆从低到高");
        HotItemVo hotItemVo4 = new HotItemVo("4", "太极豆从高到低");
        HotItemVo hotItemVo5 = new HotItemVo("5", "最新发布");
        HotItemVo hotItemVo6 = new HotItemVo("6", "离我最近");
        sortItemList = new ArrayList<>();
        sortItemList.add(hotItemVo1);
        sortItemList.add(hotItemVo2);
        sortItemList.add(hotItemVo3);
        sortItemList.add(hotItemVo4);
        sortItemList.add(hotItemVo5);
        sortItemList.add(hotItemVo6);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1001:
                    if (data != null) {
                        goodsTypeListParam = (GoodsTypeListParam) data.getSerializableExtra("mStaparam");
                        TypeVo parentType = goodsTypeListParam.parent;
                        TypeVo childType = goodsTypeListParam.children;
                        StaDynaVo staDynaVo = new StaDynaVo();
                        if (childType == null) {

                        } else {
                            Pair<String, String> parentPair = new Pair<>("shopType1", parentType.linkageid);
                            Pair<String, String> childPair = new Pair<>("shopType2", childType.linkageid);
                            staDynaVo.ReqParam.put("shopType1", parentType.linkageid);
                            staDynaVo.ReqParam.put("shopType2", childType.linkageid);
                        }
                        if (!selectType.equals(parentType.description)) {
                            selectType = parentType.description;
                            initScreenView();
                        }
                        initScreeData(staDynaVo);
                    }
                    break;
                case 1002:
                    if (data != null) {
                        String code = data.getStringExtra("data");
                        String name = data.getStringExtra("name");
                        Pair<String, String> pair = new Pair<>("area", code);
                        StaDynaVo staDynaVo = new StaDynaVo();
                        staDynaVo.ReqParam.put("area", code);
                        initScreeData(staDynaVo);
                    }
                    break;


            }
        }
    }

    private int locationCount = 0;
    private LocationClient mLocationClient;
    private String lat;
    private String lng;

    private void processLocation() {
        locationCount = 0;
        mLocationClient = new LocationClient(this);
        RxPermissions rxPermissions = new RxPermissions(this);
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
                                    if (location.getAddress() != null) {
                                        lat = String.valueOf(location.getLatitude());
                                        lng = String.valueOf(location.getLongitude());
                                        StaDynaVo staDynaVo = new StaDynaVo();
                                        staDynaVo.ReqParam.put("sort", "6");
                                        staDynaVo.ReqParam.put("lat", lat);
                                        staDynaVo.ReqParam.put("lng", lng);
                                        initScreeData(staDynaVo);

                                        if (sortCommonPopuwindow != null && sortCommonPopuwindow.isShowing()) {
                                            sortCommonPopuwindow.dismiss();
                                        }
                                        mLocationClient.stop();
                                    } else {
                                        locationCount++;
                                        if (locationCount > 3) {
                                            mLocationClient.stop();
                                            ToastUtils.showShort("定位失败，请重试");
                                            if (sortCommonPopuwindow != null && sortCommonPopuwindow.isShowing()) {
                                                sortCommonPopuwindow.dismiss();
                                            }
                                        }
                                    }
                                }
                            });
                            mLocationClient.start();
                        } else {
                            if (sortCommonPopuwindow != null && sortCommonPopuwindow.isShowing()) {
                                sortCommonPopuwindow.dismiss();
                            }
                            ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                        }
                    }
                });
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 202:
            case 203:
                hideSoftKeyBoard();
                isSearching = false;
                mBinding.refresh.finishLoadMore();
                mBinding.refresh.finishRefresh();
                break;
            case 204: // 大图预览
//                PictureSelector.create(this).externalPicturePreview(Integer.parseInt(viewEventResouce.message), (List<LocalMedia>) viewEventResouce.data);

                PictureSelector
                        .create(this)
                        .themeStyle(R.style.picture_default_style)
                        .openExternalPreview(Integer.parseInt(viewEventResouce.message), (List<LocalMedia>) viewEventResouce.data);
                break;
            case 1003:
                if (viewEventResouce.data != null && ((Collection) viewEventResouce.data).size() > 0) {
                    FlowAdapter flowAdapter = new FlowAdapter((List<RstServerVo>) viewEventResouce.data, IndexSearchActivity.this, o -> {
                        mBinding.edSerch.setText((String) o);
                        keyword = (String) o;
                        CacheUtils.saveSerachChache(t, (String) o);
                        List<String> lists = CacheUtils.getSerachChache(t);
                        hivsSampleAdapter.clear();
                        hivsSampleAdapter.add(lists);
                        doSerach();
                    });
                    FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
//                    mBinding.hotRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
                    mBinding.hotRecyclerView.setLayoutManager(flowLayoutManager);
                    mBinding.hotRecyclerView.setAdapter(flowAdapter);
                }
                break;
            case 1005:
                if (viewEventResouce.data != null) {
                    List<ScreenVo> screenVoList = (List<ScreenVo>) viewEventResouce.data;
                    initScreenView();
                    mSelectPop.showAsDropDown(mBinding.circleTvSelect);
                    screenVoList2 = new ArrayList<>();
                    ScreenVo2 screenVo2 = null;
                    for (int i = 0; i < screenVoList.size(); i++) {
                        ScreenVo screenVo = screenVoList.get(i);
                        screenVo2 = new ScreenVo2();
                        screenVo2.setType(screenVo.getType());
                        screenVo2.setName(screenVo.getName());
                        screenVo2.setFieldname(screenVo.getFieldname());
                        List<List<String>> lists = screenVo.getCheck_val();
                        List<HotItemVo> hotItemVoList = new ArrayList<>();
                        for (int j = 0; j < lists.size(); j++) {
                            List<String> data = lists.get(j);
                            HotItemVo hotItemVo = new HotItemVo(data.get(1), data.get(0));
                            hotItemVoList.add(hotItemVo);
                        }
                        screenVo2.setCheck_val(hotItemVoList);
                        screenVoList2.add(screenVo2);
                    }

                    screenFlowAdapter = new ScreenFlowAdapter(screenVoList2, IndexSearchActivity.this, o -> {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append((String) o);
                    });
                    FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
                    screenRecyclerView.setLayoutManager(flowLayoutManager);
                    screenRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
                    screenRecyclerView.setAdapter(screenFlowAdapter);
                    screenFlowAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


}
