package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MyInfoDispatcherVo;
import com.bfhd.account.vo.index.setting.SettingItemVo;
import com.bfhd.account.vo.tygs.PointItemVo;
import com.bfhd.account.vo.tygs.PointVo;
import com.dcbfhd.utilcode.utils.GsonUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
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

public class AccountPointViewModel extends NitCommonContainerViewModel {

    @Inject
    AccountService accountService;

    @Inject
    public AccountPointViewModel() {
    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        PointVo pointVo = new PointVo(0, 0);
        pointVo.setPoint("113");
        PointItemVo pointItemVo = new PointItemVo(0, 0);
        pointItemVo.setName("圣诞节快乐就发生");
        pointItemVo.setPoint("20");
        pointItemVo.setTime("2019-12-02");
        List<PointItemVo> pointItemVoList = new ArrayList<>();
        pointItemVoList.add(pointItemVo);
        pointItemVoList.add(pointItemVo);
        pointItemVoList.add(pointItemVo);
        pointItemVoList.add(pointItemVo);
        pointItemVoList.add(pointItemVo);
        pointItemVoList.add(pointItemVo);
        mItems.addAll(pointItemVoList);
    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }

    public final MediatorLiveData<MyInfoDispatcherVo> myInfoDispatcherVoMediatorLiveData = new MediatorLiveData<>();

    public void fetchPointTotal() {

        HashMap<String, String> mRepParamMap = new HashMap<>();
        if (CacheUtils.getUser() != null) {
            HashMap<String, String> postArrMap = new HashMap<>();
            postArrMap.put("uuid", CacheUtils.getUser().uuid);
            mRepParamMap.put("postArray", GsonUtils.toJson(postArrMap));
            HashMap<String, Object> disArrMap = new HashMap<>();
            String[] userarr = new String[]{"all"};
            disArrMap.put("member", userarr);
            String[] exarr = new String[]{"dynamicNum", "dzNum", "plNum"};
            disArrMap.put("extData", exarr);
            mRepParamMap.put("dispatcherArray", GsonUtils.toJson(disArrMap));
        }
        LiveData<Resource<MyInfoDispatcherVo>> responseLiveData = RequestServer(accountService.featchMineData(mRepParamMap));
        myInfoDispatcherVoMediatorLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<MyInfoDispatcherVo>() {
                    @Override
                    public void onComplete(Resource<MyInfoDispatcherVo> resource) {
                        super.onComplete(resource);
                        myInfoDispatcherVoMediatorLiveData.setValue(resource.data);
                    }
                    @Override
                    public void onNetworkError(Resource<MyInfoDispatcherVo> resource) {
                        super.onNetworkError(resource);
                        myInfoDispatcherVoMediatorLiveData.setValue(null);
                    }
                }));
    }


}
