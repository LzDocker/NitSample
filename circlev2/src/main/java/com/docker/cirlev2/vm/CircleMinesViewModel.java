package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;
import android.text.TextUtils;

import com.dcbfhd.utilcode.utils.CollectionUtils;
import com.dcbfhd.utilcode.utils.MapUtils;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.CircleListVo;
import com.docker.cirlev2.vo.vo.SampleItemVo;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

public class CircleMinesViewModel extends NitCommonContainerViewModel {


    @Inject
    public CircleMinesViewModel() {

    }

    @Inject
    CircleApiService circleApiService;

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        LiveData<ApiResponse<BaseResponse>> serverfun = null;
        switch (mCommonListReq.falg) {
            case 101:
            case 103:
                serverfun = circleApiService.fechJoinCircle(param);
                break;
            case 102:
                serverfun = circleApiService.fechCircleList(param);
                break;
            case 105:
                if (param.containsKey("keyword")) {
                    serverfun = circleApiService.fechCircleList(param);
                } else {
                    serverfun = null;
                }
                break;
        }
        return serverfun;
    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        switch (mCommonListReq.falg) {
            case 101:
                CircleListVo circleCraeteVo = new CircleListVo();
                circleCraeteVo.circleName = "创建圈子";
                data.add(circleCraeteVo);
                break;
            case 103:
                for (int i = 0; i < data.size(); i++) {
                    ((ArrayList<CircleListVo>) data).get(i).style = 1;
                }
                break;
        }
        return data;
    }


    public void joinCircle(String circleid, String utid, int index) {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("utid", utid);
        if (TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("fullName", "匿名");
        } else {
            params.put("fullName", userInfoVo.nickname);
        }
        params.put("circleid", circleid);
        mContainerLiveData.addSource(RequestServer(circleApiService.joinCircle(params)), new NitNetBoundObserver<>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                mItems.remove(index);
            }
        }));
    }
}
