package com.docker.module_im.init;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.NitEmptyVm;
import com.docker.module_im.R;
import com.docker.module_im.databinding.ActivityLocationOpenImBinding;

import javax.inject.Inject;

import static com.docker.module_im.location.activity.LocationExtras.ADDRESS;
import static com.docker.module_im.location.activity.LocationExtras.LATITUDE;
import static com.docker.module_im.location.activity.LocationExtras.LONGITUDE;

/*
 * 人员定位
 * */
public class ImLocationOpenActivity extends NitCommonActivity<NitEmptyVm, ActivityLocationOpenImBinding> implements BaiduMap.OnMapLoadedCallback {


    private BaiduMap mBaiduMap;

    private LocationClient mLocationClient;

    private LatLng latLng;

    private String address;


    @Override
    public NitEmptyVm getmViewModel() {
        return ViewModelProviders.of(this, factory).get(NitEmptyVm.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location_open_im;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra(LATITUDE, -100);
        double longitude = intent.getDoubleExtra(LONGITUDE, -100);
        address = intent.getStringExtra(ADDRESS);
        latLng = new LatLng(latitude, longitude);
        mToolbar.setTitle("位置信息");
        initMap();
    }

    @Override
    public void initView() {

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

        mBaiduMap = mBinding.bmapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(4).build()));
//        //定位初始化
        mLocationClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        mLocationClient.setLocOption(option);


//        MyLocationListener myLocationListener = new MyLocationListener();
//        mLocationClient.registerLocationListener(myLocationListener);
//        mLocationClient.start();
    }


    @Override
    public void onMapLoaded() {
        MapStatus ms = new MapStatus.Builder().zoom(14).target(latLng).build();
        mBinding.tvLoc.setVisibility(TextUtils.isEmpty(address) ? View.GONE : View.VISIBLE);
        mBinding.tvLoc.setText(address);
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
            mLocationClient.stop();
        }
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
        super.onDestroy();
    }


}
