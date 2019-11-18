package com.docker.module_im.init;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.NitEmptyVm;
import com.docker.module_im.R;
import com.docker.module_im.databinding.ActivityLocationImBinding;
import com.docker.module_im.location.activity.LocationExtras;
import com.netease.nim.uikit.api.model.location.LocationProvider;

import javax.inject.Inject;

/*
 * 人员定位
 * */
public class ImLocationActivity extends NitCommonActivity<NitEmptyVm, ActivityLocationImBinding> implements BaiduMap.OnMapLoadedCallback {



    private BaiduMap mBaiduMap;

    private LocationClient mLocationClient;

    private boolean isFirst = true;

    private GeoCoder geoCoder;

    private LatLng latLng;

    private String address;

    private static LocationProvider.Callback callback;


    public static void start(Context context, LocationProvider.Callback callback) {
        ImLocationActivity.callback = callback;
        context.startActivity(new Intent(context, ImLocationActivity.class));
    }

    @Override
    public NitEmptyVm getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyVm.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location_im;
    }


    @Override
    public void initView() {
        mToolbar.setTitle("定位");
        initMap();
    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return null;
    }

    private void initMap() {

        mBinding.myLocation.setOnClickListener(v -> {
            isFirst = false;
            mLocationClient.start();
        });
        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(listener);

        mBaiduMap = mBinding.bmapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(4).build()));
//        //定位初始化
        mLocationClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        mLocationClient.start();
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                mToolbar.setTvRight("", null);
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                LatLng target = mapStatus.target;
//                isFirst = true;
//                mLocationClient.start();

                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(mapStatus.target));
            }
        });
    }


    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
                mToolbar.setTvRight("", null);
                address = "";
                return;
            } else {
                //详细地址
                address = reverseGeoCodeResult.getAddress();
                mBinding.tvLoc.setText(address);
                mToolbar.setTvRight("确定", v -> {
                    sure();
                });
            }
        }
    };


    @Override
    public void onMapLoaded() {
        MapStatus ms = new MapStatus.Builder().zoom(14).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mBinding.bmapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            MapStatus ms = new MapStatus.Builder().zoom(14).target(new LatLng(location.getLatitude(), location.getLongitude())).build();
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
            mBinding.tvLoc.setText(location.getAddress().address);
            address = location.getAddress().address;
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mToolbar.setTvRight("确定", v -> {
                sure();
            });
            isFirst = false;
            mLocationClient.stop();
        }
    }


    private void sendLocation() {
        Intent intent = new Intent();
        intent.putExtra(LocationExtras.LATITUDE, latLng.latitude);
        intent.putExtra(LocationExtras.LONGITUDE, latLng.longitude);
        address = TextUtils.isEmpty(address) ? getString(R.string.location_address_unkown) : address;
        intent.putExtra(LocationExtras.ADDRESS, address);
        intent.putExtra(LocationExtras.ZOOM_LEVEL, 14);
        intent.putExtra(LocationExtras.IMG_URL, getStaticMapUrl());
        if (callback != null) {
            callback.onSuccess(latLng.longitude, latLng.latitude, address);
        }
    }

    private String getStaticMapUrl() {
        StringBuilder urlBuilder = new StringBuilder(LocationExtras.STATIC_MAP_URL_1);
        urlBuilder.append(latLng.latitude);
        urlBuilder.append(",");
        urlBuilder.append(latLng.longitude);
        urlBuilder.append(LocationExtras.STATIC_MAP_URL_2);
        return urlBuilder.toString();
    }


    private void sure() {
        sendLocation();
        finish();
    }

    @Override
    protected void onResume() {
        mBinding.bmapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mBinding.bmapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mBinding.bmapView.onDestroy();
        if (geoCoder != null) {
            geoCoder.destroy();
        }
        super.onDestroy();
    }


}
