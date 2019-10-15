package com.bfhd.circle.ui.safe;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.text.TextUtils;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bfhd.circle.R;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleFragmentBinding;
import com.bfhd.circle.ui.adapter.CircleJoinAdapter;
import com.bfhd.circle.ui.circle.CircleCreateListActivity;
import com.bfhd.circle.ui.circle.CircleDetailActivity;
import com.bfhd.circle.ui.circle.CircleListActivity;
import com.bfhd.circle.ui.circle.CircleListFragment;
import com.bfhd.circle.ui.circle.CircleTypeListActivity;
import com.bfhd.circle.vm.CircleFrameViewModel;
import com.bfhd.circle.vo.CircleListVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.bfhd.circle.vo.bean.StaCircleListParam;
import com.bfhd.circle.widget.RecycleViewLinerLayoutManager;
import com.docker.common.common.adapter.CommonpagerAdapter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.luck.picture.lib.permissions.RxPermissions;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 圈子 入口
 * */
public class CircleFragment extends CommonFragment<CircleFrameViewModel, CircleFragmentBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    Disposable disposable;


    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment;
    }

    public static CircleFragment newInstance() {
        return new CircleFragment();
    }

    @Override
    protected CircleFrameViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleFrameViewModel.class);
    }

    CircleJoinAdapter adapter;

    @Override
    protected void initView(View var1) {

        mBinding.get().recyclerView.setLayoutManager(new RecycleViewLinerLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mBinding.get().recyclerView);
        mBinding.get().recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CircleJoinAdapter();
        mBinding.get().recyclerView.setAdapter(adapter);
        mBinding.get().refresh.setFooterTriggerRate(1.0f);

        adapter.setOnItemClickListener((view1, position) -> {
            if (position == adapter.getmObjects().size() - 1) {
                Intent intent = new Intent(CircleFragment.this.getHoldingActivity(), CircleCreateListActivity.class);
                startActivity(intent);
            } else {
                CircleListVo circleListVo = adapter.getmObjects().get(position);
                CircleDetailActivity.startMe(this.getHoldingActivity(), new StaCirParam(circleListVo.circleid, circleListVo.getUtid(), ""));
            }
        });

        // 更多
        mBinding.get().tvCircleMore.setOnClickListener(v -> {
            Intent intent = new Intent(CircleFragment.this.getHoldingActivity(), CircleTypeListActivity.class);
            startActivity(intent);
        });
        // 我的圈子更多
        mBinding.get().circleMyMore.setOnClickListener(v -> {
            Intent intent = new Intent(CircleFragment.this.getHoldingActivity(), CircleListActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.get().setViewmodel(mViewModel);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("refresh_circle_myjoin")) { // 创建圈子成功后返回
                mViewModel.getData();
            }
        });
        processLocation();
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 103:
                mBinding.get().empty.hide();
                if (viewEventResouce.data != null) {
                    adapter.clear();
                    adapter.setNotifyOnChange(false);
                    adapter.add((List<CircleListVo>) viewEventResouce.data);
                    adapter.add(new CircleListVo());
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                } else {
                    if (adapter.getmObjects().size() == 0) {
                        adapter.add(new CircleListVo());
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }

    CircleListFragment nearFragment;

    private void initTab() {
        List<Fragment> fragments = new ArrayList<>();
        StaCircleListParam sta = new StaCircleListParam();
        sta.Uity = 1;
        sta.ReqType = 1;
        if (TextUtils.isEmpty(lat) && CacheUtils.getLaut() != null) {
            lat = CacheUtils.getLaut()[0];
            lng = CacheUtils.getLaut()[1];
        }
        if (!TextUtils.isEmpty(lat)) {
            sta.paramMap.put("lat", lat);
            sta.paramMap.put("lng", lng);
        }
        nearFragment = CircleListFragment.newInstance(sta);
        fragments.add(nearFragment);
        StaCircleListParam sta1 = new StaCircleListParam();
        sta1.Uity = 1;
        sta1.ReqType = 1;
        sta1.paramMap.put("isrecommend", "1");
        fragments.add(CircleListFragment.newInstance(sta1));

        StaCircleListParam sta2 = new StaCircleListParam();
        sta2.Uity = 1;
        sta2.ReqType = 1;
        sta2.paramMap.put("type", "3");
        fragments.add(CircleListFragment.newInstance(sta2));

        mBinding.get().viewpager.setAdapter(new CommonpagerAdapter(getChildFragmentManager(), fragments,
                new String[]{"附近的", "热门圈子", "国别圈"}));
        mBinding.get().viewpager.setOffscreenPageLimit(3);
        mBinding.get().tabCircleTitle.setViewPager(mBinding.get().viewpager);
        mBinding.get().tabCircleTitle.setCurrentTab(0);
        mBinding.get().refresh.setOnLoadMoreListener(refreshLayout -> {
            ((CommonFragment) (fragments.get(mBinding.get().viewpager.getCurrentItem()))).OnLoadMore(mBinding.get().refresh);
        });
        mBinding.get().refresh.setOnRefreshListener(refreshLayout -> {
            ((CommonFragment) (fragments.get(mBinding.get().viewpager.getCurrentItem()))).OnRefresh(mBinding.get().refresh);
        });
        mBinding.get().viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                mBinding.get().refresh.finishLoadMore();
                mBinding.get().refresh.finishRefresh();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    public void onVisible() {
        super.onVisible();
        if (adapter.getmObjects().size() == 0 && mViewModel.mPage == 1) {
            mViewModel.getData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    LocationClient mLocationClient;

    private void processLocation() {
        mLocationClient = new LocationClient(this.getHoldingActivity());
        RxPermissions rxPermissions = new RxPermissions(this.getHoldingActivity());
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
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
                            MyLocationListener myLocationListener = new MyLocationListener();
                            mLocationClient.registerLocationListener(myLocationListener);
                            mLocationClient.start();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            initTab();
                        } else {
                            initTab();
                        }
                    }
                });
    }

    String lat;
    String lng;

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            if (location.getCountry() != null) {
                mLocationClient.stop();
                lat = String.valueOf(location.getLatitude());
                lng = String.valueOf(location.getLongitude());
                initTab();
            } else {
                mLocationClient.stop();
                initTab();
            }
        }
    }

}
