package com.docker.cirlev2.vm;

import android.text.TextUtils;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.vo.CircleCreateVo;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import javax.inject.Inject;

import timber.log.Timber;

public class CircleCreateViewModel extends NitCommonContainerViewModel {


    @Inject
    public CircleCreateViewModel() {

    }

    @Inject
    CircleApiService circleApiService;

    @Override
    public void loadData() {
        if (TextUtils.isEmpty(mCommonListReq.ReqParam.get("circleid"))) {
            mEmptycommand.set(EmptyStatus.BdHiden);
            CircleCreateVo circleCreateVo = new CircleCreateVo(Integer.parseInt(mCommonListReq.ReqParam.get("flag")));
            mItems.add(circleCreateVo);
        } else {
            mContainerLiveData.addSource(RequestServer(circleApiService.fechCircleDetailVo(mCommonListReq.ReqParam.get("utid"), mCommonListReq.ReqParam.get("circleid"))),
                    new NitNetBoundObserver<>(new NitBoundCallback<CircleCreateVo>() {
                        @Override
                        public void onComplete(Resource<CircleCreateVo> resource) {
                            super.onComplete(resource);
                            Timber.e("========");
                            if (resource.data != null) {
                                resource.data.flag = Integer.parseInt(mCommonListReq.ReqParam.get("flag"));
                                mItems.add(resource.data);
                                mEmptycommand.set(EmptyStatus.BdHiden);
                            } else {
                                mEmptycommand.set(EmptyStatus.BdError);
                            }
                        }
                        @Override
                        public void onNetworkError(Resource<CircleCreateVo> resource) {
                            super.onNetworkError(resource);
                            mEmptycommand.set(EmptyStatus.BdError);
                        }
                    }));
        }
    }
}
