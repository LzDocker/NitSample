package com.docker.common.common.ui.location;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.docker.common.R;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.vm.NitEmptyViewModel;
import com.docker.common.databinding.CommonActivityMapLocationBinding;

//@Route(path = AppRouter.COMMON_LOCATION_ACTIVITY)
public class BaiduMapActivity extends NitCommonActivity<NitEmptyViewModel, CommonActivityMapLocationBinding> implements OnGetGeoCoderResultListener {
    private LocationClient locationClient;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    LatLng latLng;
    GeoCoder geoCoder;
    MylocationListener myListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaiduMapView();
    }

    @Override
    public void initView() {
        mToolbar.setTitle("选择位置");

        findViewById(R.id.btn_location).setOnClickListener(v -> {
            if (!locationClient.isStarted()) {
                locationClient.start();
            }
            locationClient.requestLocation();
        });

    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {

    }


    public class MylocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || mMapView == null) {
                return;
            }
            latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
            mBaiduMap.animateMapStatus(u);
            geoCoder.reverseGeoCode(new ReverseGeoCodeOption()
                    .location(latLng));
            locationClient.stop();
            mBinding.tvAddress.setVisibility(View.VISIBLE);

        }
    }


    private void initBaiduMapView() {

        geoCoder = GeoCoder.newInstance();
        locationClient = new LocationClient(this.getApplicationContext());
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.disableCache(true);
        locationClientOption.setCoorType("bd09ll");
        locationClientOption.setAddrType("all");
        locationClientOption.setPriority(LocationClientOption.NetWorkFirst);
        locationClient.setLocOption(locationClientOption);
        myListener = new MylocationListener();
        locationClient.registerLocationListener(myListener);
        locationClient.start();
        locationClient.requestLocation();

        mMapView = findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 初始化搜索模块，注册事件监听
        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(this);
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            /**
             * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
             * @param status 地图状态改变开始时的地图状态
             */
            public void onMapStatusChangeStart(MapStatus status) {
                mBaiduMap.clear();
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
                mBaiduMap.clear();
            }

            /**
             * 地图状态变化中
             * @param status 当前地图状态
             */
            public void onMapStatusChange(MapStatus status) {
            }

            /**
             * 地图状态改变结束
             * @param status 地图状态改变结束后的地图状态
             */
            public void onMapStatusChangeFinish(MapStatus status) {
                LatLng ll = status.target;
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
            }
        });


    }


    @Override
    public void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mMapView != null) {
            mMapView.onResume();
        }
        super.onResume();
    }

    @Override
    public void finish() {
        if (locationClient != null && locationClient.isStarted()) {
            if (myListener != null) {
                locationClient.unRegisterLocationListener(myListener);
            }
            locationClient.stop();
            locationClient = null;
            mMapView = null;
        }
        super.finish();
    }


    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (geoCodeResult == null
                || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            // 没有检测到结果

        }
        // todo
        final MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(geoCodeResult.getLocation());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mBaiduMap.animateMapStatus(u);
            }
        }, 100);
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (locationClient != null) {
            latLng = result.getLocation();
            if (result == null
                    || result.error != SearchResult.ERRORNO.NO_ERROR) {
            }
            String address = result.getAddress();

            if (result.getAddressDetail() != null) {
                address = result.getAddressDetail().street + result.getAddressDetail().streetNumber;
            }
            final MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(result.getLocation());
            new Handler().postDelayed(() -> mBaiduMap.animateMapStatus(u), 100);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.common_activity_map_location;
    }

    @Override
    public NitEmptyViewModel getmViewModel() {
        return ViewModelProviders.of(this).get(NitEmptyViewModel.class);
    }


}
