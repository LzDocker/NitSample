package com.bfhd.account.vm.card;

import android.util.Log;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MyInfoDispatcherVo;
import com.bfhd.account.vo.index.AccountIndexItemVo;
import com.bfhd.account.vo.index.setting.AccountSettingCardVo;
import com.bfhd.account.vo.module.mine.AccountHeadCardVo;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.widget.card.mark.CardMark;
import com.docker.core.repository.Resource;

import java.util.ArrayList;

import javax.inject.Inject;

public class AccountCardViewModel extends NitcommonCardViewModel implements CardMark.AccountCardMark {


    public ArrayList<AccountHeadCardVo> accountHeadCardVos = new ArrayList<>();
    public ArrayList<AccountIndexItemVo> accountIndexItemVos = new ArrayList<>();
    public ArrayList<AccountSettingCardVo> accountSettingCardVos = new ArrayList<>();


    @Inject
    AccountService accountService;

    @Inject
    public AccountCardViewModel() {
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


    public void fetchAccountHeaderCard(AccountHeadCardVo accountHeadCardVo) {
        Log.d("sss", "fetchAccountHeaderCard: ===============1111s========");
        accountHeadCardVo.mCardVoLiveData.addSource(RequestServer(accountService.featchMineData(accountHeadCardVo.mRepParamMap)),
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
    }

    @Override
    public void onOuterVmRefresh(NitCommonListVm outerVm) {
        super.onOuterVmRefresh(outerVm);

        for (int i = 0; i < accountHeadCardVos.size(); i++) {
            outerVm.addCardVo(accountHeadCardVos.get(i), accountHeadCardVos.get(i).position);
            fetchAccountHeaderCard(accountHeadCardVos.get(i));
        }
        for (int i = 0; i < accountIndexItemVos.size(); i++) {
            outerVm.addCardVo(accountIndexItemVos.get(i), accountIndexItemVos.get(i).position);
        }

        for (int i = 0; i < accountSettingCardVos.size(); i++) {
            outerVm.addCardVo(accountSettingCardVos.get(i), accountSettingCardVos.get(i).position);
        }
    }


    // 只刷新不添加
    public void onJustRefresh() {
        for (int i = 0; i < accountHeadCardVos.size(); i++) {
            fetchAccountHeaderCard(accountHeadCardVos.get(i));
        }
    }


}
