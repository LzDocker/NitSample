package com.docker.nitsample.vm.card;

import android.arch.lifecycle.LiveData;
import android.text.TextUtils;
import android.util.Log;

import com.bfhd.circle.BR;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.card.PersonInfoHeadCardVo;
import com.docker.cirlev2.vo.card.PersonInfoHeadVo;
import com.docker.cirlev2.vo.entity.CircleListNomalVo;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.docker.nitsample.vo.card.AppRecycleHorizontalCardVo2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class CircleRecomendListCardVm extends NitcommonCardViewModel {

    public int mApitype = 0;

    public AppRecycleHorizontalCardVo2 appRecycleHorizontalCardVo2;

    @Inject
    CircleApiService circleApiService;

    @Inject
    public CircleRecomendListCardVm() {

    }


    public void process() {
        Log.d("sss", "process: ===============================");
    }


    @Override
    public BaseItemModel formatData(BaseItemModel data) {
        return null;
    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return null;
    }

    @Override
    public void loadData() {
        if (appRecycleHorizontalCardVo2 != null) {
            loadCardData(appRecycleHorizontalCardVo2);
        }
    }

    @Override
    public void loadCardData(BaseCardVo accountHeadCardVo) {
        this.appRecycleHorizontalCardVo2 = (AppRecycleHorizontalCardVo2) accountHeadCardVo;
        LiveData<Resource<List<CircleListNomalVo>>> responseLiveData = RequestServer(circleApiService.fechCircleList(appRecycleHorizontalCardVo2.mRepParamMap));
        appRecycleHorizontalCardVo2.mCardVoLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<List<CircleListNomalVo>>() {
                    @Override
                    public void onComplete(Resource<List<CircleListNomalVo>> resource) {
                        super.onComplete(resource);
                        appRecycleHorizontalCardVo2.mCardVoLiveData.setValue(resource.data);
                        appRecycleHorizontalCardVo2.mCardVoLiveData.removeSource(responseLiveData);
//                        ArrayList<CircleListNomalVo> unjoinlist = null;
//                        for (int i = 0; i < resource.data.size(); i++) {
//                            unjoinlist = new ArrayList<>();
//                            if (!"1".equals(resource.data.get(i).getIsJoin())) {
//                                unjoinlist.add(resource.data.get(i));
//                            }
//                        }
                        appRecycleHorizontalCardVo2.setDatasource(resource.data);
                    }

                    @Override
                    public void onNetworkError(Resource<List<CircleListNomalVo>> resource) {
                        super.onNetworkError(resource);
                        appRecycleHorizontalCardVo2.mCardVoLiveData.setValue(null);
                    }
                }));
    }


    public void joinCircle(CircleListNomalVo circleListNomalVo) {

        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("utid", circleListNomalVo.getUtid());
        if (TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("fullName", "匿名");
        } else {
            params.put("fullName", userInfoVo.nickname);
        }
        params.put("circleid", circleListNomalVo.circleid);
        appRecycleHorizontalCardVo2.mCardVoLiveData.addSource(RequestServer(circleApiService.joinCircle(params)), new NitNetBoundObserver<String>(new NitBoundCallback() {
            @Override
            public void onComplete(Resource resource) {
                super.onComplete(resource);
                ToastUtils.showShort("加入成功");
                appRecycleHorizontalCardVo2.InnerResource.remove(circleListNomalVo);
            }

            @Override
            public void onBusinessError(Resource resource) {
                super.onBusinessError(resource);
                ToastUtils.showShort("加入失败请重试");
            }

            @Override
            public void onNetworkError(Resource resource) {
                super.onNetworkError(resource);
                ToastUtils.showShort("加入失败请重试");
            }
        }));
    }

}
