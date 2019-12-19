package com.docker.cirlev2.vm;

import android.arch.lifecycle.MediatorLiveData;

import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class CircleEditTabViewModel extends NitCommonContainerViewModel {


    public final MediatorLiveData<List<CircleTitlesVo>> mCircleTitleLv = new MediatorLiveData<>();
    public final MediatorLiveData<List<CircleTitlesVo>> mModCircleTitleLv = new MediatorLiveData<>();

    @Inject
    public CircleEditTabViewModel() {

    }

    @Inject
    CircleApiService circleApiService;


    @Override
    public void loadData() {

    }

    public void getCircleClass(String circleid, String utid) {
        mCircleTitleLv.addSource(RequestServer(circleApiService.fechCircleClass(circleid, utid)), new NitNetBoundObserver<>(new NitBoundCallback<List<CircleTitlesVo>>() {
            @Override
            public void onComplete(Resource<List<CircleTitlesVo>> resource) {
                super.onComplete(resource);
                mCircleTitleLv.setValue(resource.data);
            }
        }));
    }

    /*
     * 保存圈子分类
     * */
    public void saveCircleClass(HashMap<String, String> param) {
        showDialogWait("保存中...", true);
        mModCircleTitleLv.addSource(
                RequestServer(
                        circleApiService.saveCircleClass(param)), new NitNetBoundObserver<>(new NitBoundCallback<List<CircleTitlesVo>>() {
                    @Override
                    public void onComplete(Resource<List<CircleTitlesVo>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mModCircleTitleLv.setValue(resource.data);
                    }

                    @Override
                    public void onBusinessError(Resource<List<CircleTitlesVo>> resource) {
                        super.onBusinessError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("保存失败，请重试");
                    }

                    @Override
                    public void onNetworkError(Resource<List<CircleTitlesVo>> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("保存失败，请重试");
                    }
                }));
    }
}
