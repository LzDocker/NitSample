package com.bfhd.circle.vm;


import com.bfhd.circle.api.CircleService;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.vo.CircleListVo;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.repository.CommonRepository;
import com.docker.core.repository.Resource;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class CircleFrameViewModel extends HivsBaseViewModel {


    @Inject
    CommonRepository commonRepository;

    @Inject
    CircleService circleService;

    @Inject
    public CircleFrameViewModel() {
    }

    @Override
    public void initCommand() {

    }

    public void getData() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String,String> params = new HashMap<>();
        params.put("memberid",userInfoVo.uid);
        params.put("uuid",userInfoVo.uuid);
        mResourceLiveData.addSource(commonRepository.noneChache(circleService.fechJoinCircle(params)),
                new HivsNetBoundObserver<>(new NetBoundCallback<List<CircleListVo>>() {
                    @Override
                    public void onComplete(Resource<List<CircleListVo>> resource) {
                        super.onComplete(resource);
                        this.onComplete();
                        mVmEventSouce.setValue(new ViewEventResouce(103, "", (List<CircleListVo>) resource.data));
                    }
                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mVmEventSouce.setValue(new ViewEventResouce(103, "", null));
                    }
                }));

    }


    public String img = "https://wanandroid.com/blogimgs/54f4350f-039d-48b6-a38b-0933e1405004.png";

}
