package com.bfhd.circle.vm;


import android.databinding.ObservableField;
import android.text.TextUtils;

import com.bfhd.circle.api.CircleService;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.vo.CircleDetailVo;
import com.bfhd.circle.vo.CircleTitlesVo;
import com.bfhd.circle.vo.CityCodeVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.docker.core.repository.CommonRepository;
import com.docker.core.repository.Resource;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class CircleTypeViewModel extends HivsBaseViewModel {

    private StaCirParam mStartParam;

    public boolean datainit = false;
    /*
     * 详情数据
     * */
    public final ObservableField<CircleDetailVo> detailVo = new ObservableField<>();

    @Inject
    CommonRepository commonRepository;

    @Inject
    CircleService circleService;

    @Inject
    public CircleTypeViewModel() {

    }

    @Override
    public void initCommand() {
        mCommand.OnRefresh(() -> {
            getData();
        });
        mCommand.OnLoadMore(() -> {
            getData();
        });
        mCommand.OnRetryLoad(() -> {
            getData();
        });
    }

    public void initCircleParam(StaCirParam mStartParam) {
        this.mStartParam = mStartParam;
    }

    public void getData() {
        if (!datainit) {
            getCircleDetail();
            getCircleClass();
        }
    }

    /*
     * 圈子详情
     * */
    public void getCircleDetail() {
        String uuid = "ebeffb827672978211b7df93f15ad844";
        String utid = "51922eb18246f7d19dcd1615f83d7af0";
        String circleid = "3172";
        String memberid = "4";
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechCircleDetail(memberid, uuid, mStartParam.getUtid(), mStartParam.getCircleid())),
                new HivsNetBoundObserver<>(new NetBoundCallback<CircleDetailVo>(this) {
                    @Override
                    public void onComplete(Resource<CircleDetailVo> resource) {
                        super.onComplete(resource);
                        detailVo.set(resource.data);
                        detailVo.notifyChange();
                        datainit = true;
                    }
                }));
    }

    /*
     * 圈子分类
     * */
    public void getCircleClass() {
        String utid = "51922eb18246f7d19dcd1615f83d7af0";
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechCircleClass(mStartParam.getCircleid(),/* mStartParam.getUtid()*/utid)), new HivsNetBoundObserver<>(new NetBoundCallback<List<CircleTitlesVo>>() {
                    @Override
                    public void onComplete(Resource<List<CircleTitlesVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(103, "", resource.data));
                    }
                }));
    }

    /*
     * 保存圈子分类
     * */
    public void saveCircleClass(HashMap<String, String> param) {
//        showDialogWait("保存中...", true);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.saveCircleClass(param)), new HivsNetBoundObserver<>(new NetBoundCallback<List<CircleTitlesVo>>() {
                    @Override
                    public void onComplete(Resource<List<CircleTitlesVo>> resource) {
                        super.onComplete(resource);
//                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(104, "", resource.data));
                    }

                    @Override
                    public void onBusinessError(Resource<List<CircleTitlesVo>> resource) {
                        super.onBusinessError(resource);
//                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<List<CircleTitlesVo>> resource) {
                        super.onNetworkError(resource);
//                        hideDialogWait();
                    }
                }));
    }

    public String img = "https://wanandroid.com/blogimgs/54f4350f-039d-48b6-a38b-0933e1405004.png";

    public void getCityCode(String lat_lng) {
        HashMap<String, String> params = new HashMap<>();
        params.put("ak", "YC98dYL7LDISmtS5lwpgSuy5cmuYth0d");
        params.put("mcode", "1A:35:91:07:2C:04:F3:BA:49:35:EB:83:61:99:BB:E7:BE:10:B3:0E;com.bfhd.tjxq");
        params.put("output", "wgs84ll");
        if (TextUtils.isEmpty(lat_lng)) {
            params.put("location", "");
        } else {
            params.put("location", lat_lng);
        }
        mResourceLiveData.addSource(
                commonRepository.SpecialFeatch(
                        circleService.fechCityCode(params)),
                new HivsNetBoundObserver<>(new NetBoundCallback<CityCodeVo>(this) {
                    @Override
                    public void onComplete(Resource<CityCodeVo> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(105, null, resource.data));
                    }
                }));
    }
}
