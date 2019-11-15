package com.docker.cirlev2.vm;

import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.CircleDetailVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.vo.CircleCreateVo;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class CircleDetailIndexViewModel extends NitCommonVm {


    @Inject
    CircleApiService circleApiService;

    private String utid, circleid;

    /*
     * 圈子详情信息
     * */
    public final MediatorLiveData<CircleDetailVo> mCircleDetailLv = new MediatorLiveData<>();

    /*
     * 圈子栏目
     * */
    public final MediatorLiveData<List<CircleTitlesVo>> mCircleClassLv = new MediatorLiveData();


    @Inject
    public CircleDetailIndexViewModel() {

    }

    @Override
    public void initCommand() {
        super.initCommand();
        mCommand.OnRetryLoad(() -> {
            mEmptycommand.set(EmptyStatus.BdLoading);
            if (!TextUtils.isEmpty(utid) && !TextUtils.isEmpty(circleid)) {
                FetchCircleDetail(utid, circleid);
                if (mCircleDetailLv.getValue() == null) {
                    FetchCircleClass();
                }
            }
        });
    }


    public void FetchCircleDetail(String utid, String circleid) {
        this.circleid = circleid;
        this.utid = utid;
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> param = new HashMap();
        param.put("memberid", "3");
        param.put("uuid", "3c29a4eed44db285468df3443790e64a");
        param.put("utid", utid);
        param.put("circleid", circleid);
        mCircleDetailLv.addSource(RequestServer(circleApiService.fechCircleDetail(param)),
                new NitNetBoundObserver<>(new NitBoundCallback<CircleDetailVo>() {
                    @Override
                    public void onNetworkError(Resource<CircleDetailVo> resource) {
                        super.onNetworkError(resource);
                        mEmptycommand.set(EmptyStatus.BdError);
                    }

                    @Override
                    public void onComplete(Resource<CircleDetailVo> resource) {
                        super.onComplete(resource);
                        mCircleDetailLv.setValue(resource.data);
                        mEmptycommand.set(EmptyStatus.BdHiden);
                    }
                }));
    }

    public void FetchCircleClass() {
        mCircleClassLv.addSource(RequestServer(circleApiService.fechCircleClass(circleid, utid)),
                new NitNetBoundObserver<>(new NitBoundCallback<List<CircleTitlesVo>>() {
                    @Override
                    public void onNetworkError(Resource<List<CircleTitlesVo>> resource) {
                        super.onNetworkError(resource);
                        mEmptycommand.set(EmptyStatus.BdError);
                    }

                    @Override
                    public void onComplete(Resource<List<CircleTitlesVo>> resource) {
                        super.onComplete(resource);
                        mCircleClassLv.setValue(resource.data);
                        mEmptycommand.set(EmptyStatus.BdHiden);
                    }
                }));
    }
}
