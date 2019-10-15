package com.bfhd.circle.vm;


import com.bfhd.circle.api.CircleService;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.vo.TradingCommonVo;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.docker.core.repository.CommonRepository;
import com.docker.core.repository.Resource;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class CirclePersonViewModel extends HivsBaseViewModel {

    @Inject
    CommonRepository commonRepository;

    @Inject
    CircleService circleService;

    @Inject
    public CirclePersonViewModel() {

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

    public void getData() {

    }

    public void getInnerPersonList(StaCirParam mStartParam) {
        HashMap<String, String> parammap = new HashMap<>();
        parammap.put("circleid", mStartParam.getCircleid());
        parammap.put("utid", mStartParam.getUtid());
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechInnerPersonList(parammap)),
                new HivsNetBoundObserver<>(new NetBoundCallback<List<TradingCommonVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<TradingCommonVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(103, null, resource.data));
                    }
                }));
    }

    public void getOuterPersonList(StaCirParam mStartParam) {
        HashMap<String, String> parammap = new HashMap<>();
        parammap.put("circleid", mStartParam.getCircleid());
        parammap.put("utid", mStartParam.getUtid());
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechOuterPersonList(parammap)),
                new HivsNetBoundObserver<>(new NetBoundCallback<List<TradingCommonVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<TradingCommonVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(104, null, resource.data));
                    }
                }));
    }


}
