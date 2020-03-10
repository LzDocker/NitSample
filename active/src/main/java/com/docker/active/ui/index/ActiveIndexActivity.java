package com.docker.active.ui.index;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.docker.active.R;
import com.docker.active.databinding.ProActiveIndexActivityBinding;
import com.docker.active.vm.ActiveCommonViewModel;
import com.docker.active.vo.LinkageVo;
import com.docker.common.common.adapter.CommonpagerStateAdapter;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.location.LocationManager;
import com.docker.common.common.vo.WorldNumList;
import com.docker.common.common.widget.indector.CommonIndector;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Route(path = AppRouter.ACTIVE_INDEX)
public class ActiveIndexActivity extends NitCommonActivity<ActiveCommonViewModel, ProActiveIndexActivityBinding> {

    ArrayList<Fragment> fragments;
    ArrayList<String> titles;
    CommonNavigator commonNavigator;
    CommonIndector commonIndector;
    CommonpagerStateAdapter commonpagerStateAdapter;

    @Inject
    LocationManager locationManager;

    @Override
    public void initView() {
        mToolbar.hide();
        mBinding.linBack.setOnClickListener(v -> finish());

        // magic

        mBinding.edSerch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ActiveContainerFragment) fragments.get(mBinding.viewpager.getCurrentItem())).onSearching(mBinding.edSerch.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBinding.viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (!TextUtils.isEmpty(mBinding.edSerch.getText().toString().trim())) {
                    mBinding.edSerch.setText("");
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void initObserver() {

        mViewModel.mLinkageVos.observe(this, linkageVos -> {
            processView(linkageVos, null, null);
            processLocation();
        });


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.fetchActiveTabData();
    }


    private void processLocation() {
        locationManager.processLocation(this,
                (o) -> {
                    if (o != null) {
                        processView(null, ((BDLocation) o).getCity(), ((BDLocation) o).getCityCode());
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

    public void processView(List<LinkageVo> linkageVos, String cityname, String cityCode) {

        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        if (titles == null) {
            titles = new ArrayList<>();
        }

        if (linkageVos != null) {

            for (int i = 0; i < linkageVos.size(); i++) {
                titles.add(linkageVos.get(i).name);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("showFields", "*");
                Map<String, String> filterMap = new HashMap<>();
                filterMap.put("actType", linkageVos.get(i).linkageid);
                hashMap.put("filter", GsonUtils.toJson(filterMap));

                Map<String, String> orderbyMap = new HashMap<>();
                orderbyMap.put("is_recommend", "desc");
                orderbyMap.put("inputtime", "desc");
                hashMap.put("orderBy", GsonUtils.toJson(orderbyMap));


                fragments.add(ActiveContainerFragment.getInstance(hashMap));
            }


            titles.add(0, "精选");
            HashMap<String, String> JXhashMap = new HashMap<>();
            JXhashMap.put("showFields", "*");
            Map<String, String> filterMap = new HashMap<>();
            filterMap.put("is_recommend", "1");
            JXhashMap.put("filter", GsonUtils.toJson(filterMap));

            Map<String, String> orderbyMap = new HashMap<>();
            orderbyMap.put("is_recommend", "desc");
            orderbyMap.put("inputtime", "desc");
            JXhashMap.put("orderBy", GsonUtils.toJson(orderbyMap));
            fragments.add(0, ActiveContainerFragment.getInstance(JXhashMap));


            titles.add(1, "定位中");
            HashMap<String, String> DWhashMap = new HashMap<>();
            DWhashMap.put("showFields", "*");
            DWhashMap.put("orderBy", GsonUtils.toJson(orderbyMap));
            fragments.add(1, ActiveContainerFragment.getInstance(DWhashMap));

            if (commonpagerStateAdapter == null) {
                commonpagerStateAdapter = new CommonpagerStateAdapter(getSupportFragmentManager(), fragments);
            }
            // magic
            mBinding.viewpager.setAdapter(commonpagerStateAdapter);
            commonIndector = new CommonIndector();
            commonIndector.mTitleList = titles;
            commonNavigator = commonIndector.initMagicIndicatorScrollSpac(mBinding.viewpager, mBinding.magic, this);
        } else {

            titles.set(1, cityname);
            ((ActiveContainerFragment) fragments.get(1)).upDate(cityCode, cityname);
            commonIndector.mTitleList = titles;
            commonNavigator.notifyDataSetChanged();
        }
    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pro_active_index_activity;
    }

    @Override
    public ActiveCommonViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(ActiveCommonViewModel.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3001 && resultCode == RESULT_OK) {
            if (fragments.size() > 1) {
                WorldNumList.WorldEnty worldEnty = (WorldNumList.WorldEnty) data.getSerializableExtra("datasource");
                if (mBinding.viewpager.getCurrentItem() == 1) {
                    commonIndector.mTitleList.remove(1);
                    commonIndector.mTitleList.add(1, worldEnty.name);
                    commonNavigator.getAdapter().notifyDataSetChanged();
                }
                ((ActiveContainerFragment) (fragments.get(mBinding.viewpager.getCurrentItem()))).upDate(worldEnty.cityCode, worldEnty.name);
            }
            fragments.get(mBinding.viewpager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
        }
    }
}

