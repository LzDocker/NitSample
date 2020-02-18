package com.docker.apps.active.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableList;
import android.view.View;

import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.apps.active.api.ActiveService;
import com.docker.apps.active.vo.ActiveServerDataBean;
import com.docker.apps.active.vo.ActiveSucVo;
import com.docker.apps.active.vo.ActiveVo;
import com.docker.apps.active.vo.ActiveWraperVo;
import com.docker.apps.active.vo.LinkageVo;
import com.docker.apps.active.vo.PersionVo;
import com.docker.apps.active.vo.card.ActiveManagerDetailVo;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.PublishRstVo;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class ActiveCommonViewModel extends NitCommonContainerViewModel {


    public int apiType = 0;

    @Inject
    public ActiveCommonViewModel() {

    }


    @Inject
    ActiveService activeService;

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {

        LiveData<ApiResponse<BaseResponse>> serverFun = null;
        switch (apiType) {
            case 0:
                serverFun = activeService.fetchLinkType(param);
                break;
            case 1:
                serverFun = activeService.fetchActiveList(param);
                break;
            case 2:
                serverFun = activeService.fechCircleInfoList(param);
                break;
        }
        return serverFun;
    }

    @Override
    public void formartData(Resource resource) {
        super.formartData(resource);
        switch (apiType) {
            case 1:
                resource.data = ((ActiveWraperVo) resource.data).indexList;
                break;
            case 2:
                ArrayList<ActiveVo> activeVos = new ArrayList<>();
                if ((List<ActiveServerDataBean>) resource.data == null || ((List<ActiveServerDataBean>) resource.data).size() == 0) {
                    resource.data = resource.data;
                } else {
                    for (int i = 0; i < ((List<ActiveServerDataBean>) resource.data).size(); i++) {
                        activeVos.add(((List<ActiveServerDataBean>) resource.data).get(i).extData);
                    }
                    resource.data = activeVos;
                }
                break;
        }
    }

    public void onTypeSelectClick(LinkageVo item, View view) {
        for (int i = 0; i < mItems.size(); i++) {
            if (!((LinkageVo) mItems.get(i)).linkageid.equals(item.linkageid)) {
                ((LinkageVo) mItems.get(i)).setChecked(false);
            } else {
                ((LinkageVo) mItems.get(i)).setChecked(!((LinkageVo) mItems.get(i)).isChecked());
            }
        }
    }


    /*
    *  commonListOptions.ReqParam.put("keyid", "3568");
        commonListOptions.ReqParam.put("parentid", "0");
    * */

    public final MediatorLiveData<List<LinkageVo>> mLinkageVos = new MediatorLiveData<>();

    public void fetchActiveTabData() {
        //activeService.fetchLinkType(param)
        HashMap<String, String> param = new HashMap<>();
        param.put("keyid", "3568");
        param.put("parentid", "0");
        mLinkageVos.addSource(RequestServer(activeService.fetchLinkType(param)), new NitNetBoundObserver<List<LinkageVo>>(new NitBoundCallback<List<LinkageVo>>() {
            @Override
            public void onComplete(Resource<List<LinkageVo>> resource) {
                super.onComplete(resource);
                mLinkageVos.setValue(resource.data);
            }
        }));
    }


    // 报名
    public final MediatorLiveData<ActiveSucVo> mSignSuccLv = new MediatorLiveData<>();

    public void activeJoin(HashMap<String, String> parm) {
        mSignSuccLv.addSource(RequestServer(activeService.activeJoin(parm)), new NitNetBoundObserver<ActiveSucVo>(new NitBoundCallback<ActiveSucVo>() {
            @Override
            public void onComplete(Resource<ActiveSucVo> resource) {
                super.onComplete(resource);
                mSignSuccLv.setValue(resource.data);
            }
        }));
    }


    public final MediatorLiveData<ActiveManagerDetailVo> voMediatorLiveData = new MediatorLiveData<>();

    public void getActiveManagerDv(HashMap<String, String> param) {
        voMediatorLiveData.addSource(RequestServer(activeService.activitymanage(param)), new NitNetBoundObserver<ActiveManagerDetailVo>(new NitBoundCallback<ActiveManagerDetailVo>() {
            @Override
            public void onComplete(Resource<ActiveManagerDetailVo> resource) {
                super.onComplete(resource);
                voMediatorLiveData.setValue(resource.data);
            }
        }));

    }


    public final MediatorLiveData<String> mDoactiveLv = new MediatorLiveData<>();

    public void updateStatus(ActiveManagerDetailVo activeManagerDetailVo) {
        if ("-1".equals(activeManagerDetailVo.status)) {
            ToastUtils.showShort("该活动已下架");
            return;
        }
        HashMap<String, String> param = new HashMap<>();
        param.put("activityid", activeManagerDetailVo.dataid);
        param.put("uuid", CacheUtils.getUser().uuid);
        param.put("status", "0"); // 0 下架  1 上架
//        if(activeManagerDetailVo.status){
//
//        }
        mDoactiveLv.addSource(RequestServer(activeService.updateStatus(param)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                mDoactiveLv.setValue("1");
            }
        }));
    }

    public void delActive(ActiveManagerDetailVo activeManagerDetailVo) {
        HashMap<String, String> param = new HashMap<>();
        param.put("activityid", activeManagerDetailVo.dataid);
        param.put("uuid", CacheUtils.getUser().uuid);
        mDoactiveLv.addSource(RequestServer(activeService.delete(param)), new NitNetBoundObserver<String>(new NitBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                mDoactiveLv.setValue("2");
            }
        }));
    }


    public final MediatorLiveData<ActiveVo> mActiveDetailLv = new MediatorLiveData<>();

    public void fetchActiveVo(HashMap<String, String> parm) {
        mActiveDetailLv.addSource(RequestServer(activeService.fetchActivedetail(parm)), new NitNetBoundObserver<ActiveVo>(new NitBoundCallback<ActiveVo>() {
            @Override
            public void onComplete(Resource<ActiveVo> resource) {
                super.onComplete(resource);
                mActiveDetailLv.setValue(resource.data);
            }
        }));
    }

    @Inject
    CircleApiService circleApiService;

    public final MediatorLiveData<String> mDynamicPublishLV = new MediatorLiveData<>();

    public void publishActive(HashMap<String, String> paramMap) {
        showDialogWait("发布中...", false);
        mDynamicPublishLV.addSource(RequestServer(circleApiService.publishactive(paramMap)),
                new NitNetBoundObserver(new NitBoundCallback() {
                    @Override
                    public void onComplete(Resource resource) {
                        super.onComplete(resource);
                        if (resource != null && resource.data != null) {
                            mDynamicPublishLV.setValue(((PublishRstVo) resource.data).activityid + "-" + "1");
                        } else {
                            mDynamicPublishLV.setValue(resource.message);
                        }
                        hideDialogWait();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));

    }
}
