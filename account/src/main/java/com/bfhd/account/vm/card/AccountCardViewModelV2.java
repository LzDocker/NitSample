package com.bfhd.account.vm.card;

import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MyInfoDispatcherVo;
import com.bfhd.account.vo.MyInfoVo;
import com.bfhd.account.vo.index.AccountIndexItemVo;
import com.bfhd.account.vo.module.mine.AccountHeadCardVo;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.core.repository.Resource;

import java.util.HashMap;

import javax.inject.Inject;

public class AccountCardViewModelV2 extends NitcommonCardViewModel {


    public AccountHeadCardVo accountHeadCardVo = new AccountHeadCardVo(0, 0);
    public HashMap<String, String> AccountHeaderCardParamMap;
    public final MediatorLiveData<MyInfoVo> mAccountHeadCardData = new MediatorLiveData<MyInfoVo>();


    public AccountIndexItemVo accountMenuCardVo = new AccountIndexItemVo(0, 0);
    public final MediatorLiveData<String> mAccountMenuCardData = new MediatorLiveData<String>();


    @Inject
    AccountService accountService;

    @Inject
    public AccountCardViewModelV2() {
    }

    @Override
    public void loadData() {
    }

//    public MediatorLiveData<MyInfoVo> fetchAccountHeaderCard(HashMap<String, String> reqParamMap) {
//        this.AccountHeaderCardParamMap = reqParamMap;
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        mAccountHeadCardData.addSource(RequestServer(accountService.featchMineData(userInfoVo.uid, userInfoVo.uuid)),
//                new HivsNetBoundObserver<>(new NetBoundCallback<MyInfoVo>() {
//                    @Override
//                    public void onComplete(Resource<MyInfoVo> resource) {
//                        super.onComplete(resource);
//                        Timber.e("========" + resource.data.getFullName());
//                        accountHeadCardVo.setMyinfo(resource.data);
//                    }
//                }));
//        return mAccountHeadCardData;
//    }


    public MediatorLiveData<MyInfoVo> fetchAccountHeaderCard(HashMap<String, String> reqParamMap) {
        this.AccountHeaderCardParamMap = reqParamMap;
        Log.d("sss", "fetchAccountHeaderCard: ===============1111s========");
        final MediatorLiveData<MyInfoVo> mAccountHeadCardData = new MediatorLiveData<MyInfoVo>();

        mAccountHeadCardData.addSource(RequestServer(accountService.featchMineData(reqParamMap)),
                new HivsNetBoundObserver<>(new NetBoundCallback<MyInfoDispatcherVo>() {
                    @Override
                    public void onComplete(Resource<MyInfoDispatcherVo> resource) {
                        super.onComplete(resource);
                        accountHeadCardVo.setMyinfo(resource.data.member);
                        if (resource.data.extData != null) {
                            accountHeadCardVo.myinfo.setCircleNum(resource.data.extData.dynamicNum);
                            accountHeadCardVo.myinfo.setCommentNum(resource.data.extData.plNum);
                            accountHeadCardVo.myinfo.setLikeNum(resource.data.extData.dzNum);
                        }
                    }
                }));
        return mAccountHeadCardData;
    }

    @Override
    public void onOuterVmRefresh(NitCommonListVm outerVm) {
        super.onOuterVmRefresh(outerVm);
        if (mAccountHeadCardData.hasObservers()) {
            outerVm.addCardVo(accountHeadCardVo, accountHeadCardVo.position);
            fetchAccountHeaderCard(AccountHeaderCardParamMap);
        }
        if (mAccountMenuCardData.hasActiveObservers()) {
            outerVm.addCardVo(accountMenuCardVo, accountMenuCardVo.position);
        }
    }


    // 只刷新不添加
    public void onJustRefresh() {
        if (mAccountHeadCardData.hasObservers()) {
            fetchAccountHeaderCard(AccountHeaderCardParamMap);
        }
    }


}
