package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MyInfoVo;
import com.bfhd.account.vo.index.AccountHeadVo;
import com.bfhd.account.vo.index.AccountIndexItemVo;
import com.bfhd.account.vo.index.setting.AccountHeadVoStyleCard;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.Resource;

import java.util.HashMap;

import javax.inject.Inject;

import timber.log.Timber;

//public class AccountIndexListViewModel extends NitSampleListViewModel {
public class AccountIndexListViewModel extends NitCommonContainerViewModel {

    public final MediatorLiveData<MyInfoVo> infoVoMutableLiveData = new MediatorLiveData<>();

    private AccountHeadVoStyleCard accountHeadVo;
    @Inject
    AccountService accountService;

    @Inject
    public AccountIndexListViewModel() {
    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        accountHeadVo = new AccountHeadVoStyleCard();
        mItems.add(accountHeadVo);
        AccountIndexItemVo accountIndexItemVo = new AccountIndexItemVo();
        mItems.add(accountIndexItemVo);
        fetchMyInfo();
    }

    public void fetchMyInfo() {
        UserInfoVo userInfoVo = CacheUtils.getUser();

        infoVoMutableLiveData.addSource(RequestServer(accountService.featchMineData(userInfoVo.uid, userInfoVo.uuid)),
                new HivsNetBoundObserver<>(new NetBoundCallback<MyInfoVo>() {
                    @Override
                    public void onComplete(Resource<MyInfoVo> resource) {
                        super.onComplete(resource);
                        Timber.e("========" + resource.data.getFullName());
                        accountHeadVo.setMyinfo(resource.data);
                    }
                }));
        mContainerLiveData.addSource(RequestServer(accountService.featchMineData(userInfoVo.uid, userInfoVo.uuid)),
                new HivsNetBoundObserver<>(new NetBoundCallback<MyInfoVo>() {
                    @Override
                    public void onComplete(Resource<MyInfoVo> resource) {
                        super.onComplete(resource);
                        Timber.e("========" + resource.data.getFullName());
                        accountHeadVo.setMyinfo(resource.data);
                    }
                }));
    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }


}
