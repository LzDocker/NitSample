package com.bfhd.account.ui;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bfhd.account.R;
import com.bfhd.account.adapter.ContactAdapter;
import com.bfhd.account.databinding.AccountFragmentCountryNumListBinding;
import com.bfhd.account.vm.AccountViewModel;
import com.bfhd.circle.BR;
import com.bfhd.circle.base.HivsBaseFragment;
import com.bfhd.circle.base.ViewEventResouce;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.vo.WorldNumList;
import com.docker.common.common.vo.WorldNumListWj;
import com.docker.common.common.widget.recycleIndex.IndexableHeaderAdapter;
import com.docker.common.common.widget.recycleIndex.IndexableLayout;
import com.docker.core.util.adapter.SimpleCommonRecyclerAdapter;
import com.library.flowlayout.FlowLayoutManager;
import com.luck.picture.lib.permissions.RxPermissions;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 *
 */
public class AccountCountryNumListFragment extends HivsBaseFragment<AccountViewModel, AccountFragmentCountryNumListBinding> {

    public List<WorldNumList.WorldEnty> list = new ArrayList<>();
    private ContactAdapter mAdapter;
    private MyIndexAdapter gpsHeaderAdapter;
    private MyIndexAdapter mHotCityAdapter;
    private String type = "1";
    public LocationClient mLocationClient = null;
    private List<WorldNumList.WorldEnty> gpsCity = new ArrayList<>();

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.account_fragment_country_num_list;
    }

    @Override
    public AccountViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }


    public static AccountCountryNumListFragment newinstance(String type) {
        AccountCountryNumListFragment accountCountryNumListFragment = new AccountCountryNumListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        accountCountryNumListFragment.setArguments(bundle);
        return accountCountryNumListFragment;
    }


    @Override
    protected void initView(View var1) {
        type = getArguments().getString("type");
        mBinding.get().indexableLayout.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mAdapter = new ContactAdapter(this.getHoldingActivity());
        mBinding.get().indexableLayout.setAdapter(mAdapter);
        mBinding.get().indexableLayout.setOverlayStyle_Center();
        mBinding.get().indexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);
        mAdapter.setOnItemContentClickListener((View v, int originalPosition, int currentPosition, WorldNumList.WorldEnty entity) -> {
            if (originalPosition >= 0) {
                Intent intent = new Intent();
                entity.curtype = String.valueOf(Integer.parseInt(type) - 1);
                intent.putExtra("data", entity.id);
                intent.putExtra("name", entity.name);
                intent.putExtra("num", entity.global_num);
                intent.putExtra("datasource", entity);
                getHoldingActivity().setResult(Activity.RESULT_OK, intent);
                getHoldingActivity().finish();
            }
        });
        mAdapter.setOnItemTitleClickListener((v, currentPosition, indexTitle) -> {
        });
        mViewModel.fetchWordListwj(type);
        mBinding.get().empty.setOnretryListener(() -> {
            mViewModel.fetchWordListwj(type);
        });

    }

    @Override
    public void initImmersionBar() {

    }


    class MyIndexAdapter extends IndexableHeaderAdapter<List<WorldNumList.WorldEnty>> {
        private static final int TYPE = 1;

        public MyIndexAdapter(String index, String indexTitle, List<List<WorldNumList.WorldEnty>> datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return TYPE;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            return new VH(LayoutInflater.from(AccountCountryNumListFragment.this.getHoldingActivity()).inflate(R.layout.account_city_list, parent, false));
        }

        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, List<WorldNumList.WorldEnty> entity) {
            VH vh = (VH) holder;
            SimpleCommonRecyclerAdapter simpleCommonRecyclerAdapter = new SimpleCommonRecyclerAdapter(R.layout.account_city_item, BR.item);
            if (entity != null) {
                FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
                vh.recyclerView.setLayoutManager(flowLayoutManager);
                vh.recyclerView.setAdapter(simpleCommonRecyclerAdapter);
                simpleCommonRecyclerAdapter.add(entity);
                simpleCommonRecyclerAdapter.setOnItemClickListener((view, position) -> {
                    if ("-1".equals(((WorldNumList.WorldEnty) simpleCommonRecyclerAdapter.getmObjects().get(position)).id)) {
                        // 定位
                        AccountCityCoutainerFragment accountCityCoutainerFragment = (AccountCityCoutainerFragment) getParentFragment();

                        accountCityCoutainerFragment.processLocation((WorldNumList.WorldEnty) simpleCommonRecyclerAdapter.getmObjects().get(position),type);
                    } else {
                        Intent intent = new Intent();
                        WorldNumList.WorldEnty curwo = (WorldNumList.WorldEnty) simpleCommonRecyclerAdapter.getmObjects().get(position);
                        curwo.curtype = String.valueOf(Integer.parseInt(type) - 1);
                        intent.putExtra("data", curwo.id);
                        intent.putExtra("name", curwo.name);
                        intent.putExtra("num", curwo.global_num);
                        intent.putExtra("datasource", curwo);

                        getHoldingActivity().setResult(Activity.RESULT_OK, intent);
                        getHoldingActivity().finish();
                    }
                });
            }
        }

        private class VH extends RecyclerView.ViewHolder {
            private RecyclerView recyclerView;

            public VH(View itemView) {
                super(itemView);
                recyclerView = itemView.findViewById(R.id.recyclerView);
            }
        }
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 103:
                if (viewEventResouce.data == null) {
                    mBinding.get().empty.showError();
                } else {
                    mBinding.get().empty.hide();
                    mAdapter.setDatas(((WorldNumListWj) viewEventResouce.data).list);
                    AccountCountryNumListFragment.this.list = ((WorldNumListWj) viewEventResouce.data).list;
                    if ("1".equals(type) && ((WorldNumListWj) viewEventResouce.data).hot_area != null) {
//                        List<WorldNumList.WorldEnty> hotCity = new ArrayList<>();
//                        WorldNumList.WorldEnty worldEnty1 = new WorldNumList.WorldEnty();
//                        WorldNumList.WorldEnty worldEnty2 = new WorldNumList.WorldEnty();
//                        WorldNumList.WorldEnty worldEnty3 = new WorldNumList.WorldEnty();
//                        worldEnty1.name = "北京北京";
//                        worldEnty2.name = "上海北京";
//                        worldEnty3.name = "南京北京";
//                        hotCity.add(worldEnty1);
//                        hotCity.add(worldEnty2);
//                        hotCity.add(worldEnty3);
//                        hotCity.add(worldEnty3);
//                        hotCity.add(worldEnty3);
//                        hotCity.add(worldEnty3);
                        ArrayList list = new ArrayList();
                        list.add(0, ((WorldNumListWj) viewEventResouce.data).hot_area);
                        mHotCityAdapter = new MyIndexAdapter("热门", "周边热门", list);
                        mBinding.get().indexableLayout.addHeaderAdapter(mHotCityAdapter);
                        processLocation();
                    } else {
                        processLocation();
                    }
                }
                break;
        }
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
                                        Log.d("sss", "onReceiveLocation: -----------------------" + mCountryStr);
                                        WorldNumList.WorldEnty worldEnty = new WorldNumList.WorldEnty();
                                        worldEnty.name = mCountryStr;
                                        worldEnty.id = "-1";
                                        gpsCity.clear();
                                        gpsCity.add(worldEnty);
                                        ArrayList listgps = new ArrayList();
                                        listgps.add(0, gpsCity);
                                        gpsHeaderAdapter = new MyIndexAdapter("定位", "定位", listgps);
                                        mBinding.get().indexableLayout.addHeaderAdapter(gpsHeaderAdapter);
                                        mAdapter.notifyDataSetChanged();
                                        hidWaitDialog();
                                    } else {
                                        locationCount++;
                                        if (locationCount > 3) {
                                            hidWaitDialog();
                                            mLocationClient.stop();
                                        }
                                    }
                                }
                            });
                            mLocationClient.start();
                        } else {
                            ToastUtils.showShort("权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                            hidWaitDialog();
                        }
                    }
                });

    }


    @Override
    public void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        super.onDestroy();
    }
}
