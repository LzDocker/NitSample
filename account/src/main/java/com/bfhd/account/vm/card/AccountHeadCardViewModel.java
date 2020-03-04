package com.bfhd.account.vm.card;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MyInfoDispatcherVo;
import com.bfhd.account.vo.card.AccountHeadCardVo;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.Collection;

import javax.inject.Inject;

public class AccountHeadCardViewModel extends NitcommonCardViewModel {


    public AccountHeadCardVo accountHeadCardVo;

    @Inject
    AccountService accountService;

    @Inject
    public AccountHeadCardViewModel() {

    }

    public void fetchAccountHeaderCard(AccountHeadCardVo accountHeadCardVo) {
        this.accountHeadCardVo = accountHeadCardVo;
        LiveData<Resource<MyInfoDispatcherVo>> responseLiveData = RequestServer(accountService.featchMineData(accountHeadCardVo.mRepParamMap));
        accountHeadCardVo.mCardVoLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<MyInfoDispatcherVo>() {
                    @Override
                    public void onComplete(Resource<MyInfoDispatcherVo> resource) {
                        super.onComplete(resource);
                        accountHeadCardVo.mCardVoLiveData.setValue(resource.data.member);
                        accountHeadCardVo.mCardVoLiveData.removeSource(responseLiveData);
                        accountHeadCardVo.setMyinfo(resource.data.member);
                        if (resource.data.extData != null) {
                            accountHeadCardVo.myinfo.setCircleNum(resource.data.extData.dynamicNum);
                            accountHeadCardVo.myinfo.setInviteNum(resource.data.extData.inviteNum);
                            accountHeadCardVo.myinfo.setNotReadMsgNum(resource.data.extData.notReadMsgNum);
//                            accountHeadCardVo.myinfo.setCommentNum(resource.data.extData.plNum);
//                            accountHeadCardVo.myinfo.setLikeNum(resource.data.extData.dzNum);
                        }
                    }

                    @Override
                    public void onNetworkError(Resource<MyInfoDispatcherVo> resource) {
//                        super.onNetworkError(resource);
                        accountHeadCardVo.mCardVoLiveData.setValue(null);
                    }

                    @Override
                    public void onBusinessError(Resource<MyInfoDispatcherVo> resource) {
//                        super.onBusinessError(resource);
                    }
                }));
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
        if (accountHeadCardVo != null) {
            fetchAccountHeaderCard(accountHeadCardVo);
        }
    }

    @Override
    public void loadCardData(BaseCardVo accountHeadCardVo) {
        fetchAccountHeaderCard((AccountHeadCardVo) accountHeadCardVo);
    }


}
