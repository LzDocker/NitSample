package com.bfhd.account.vm.card;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MyInfoDispatcherVo;
import com.bfhd.account.vo.tygs.AccountPointHeadVo;
import com.bfhd.account.vo.tygs.AccountRewardHeadVo;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.Collection;

import javax.inject.Inject;

public class AccountPointHeadCardViewModel extends NitcommonCardViewModel {


    public AccountPointHeadVo accountPointHeadVo;

    @Inject
    AccountService accountService;

    @Inject
    public AccountPointHeadCardViewModel() {

    }

    public void fetchAccountHeaderCard(AccountPointHeadVo accountPointHeadVo) {
        this.accountPointHeadVo = accountPointHeadVo;
        LiveData<Resource<MyInfoDispatcherVo>> responseLiveData = RequestServer(accountService.featchMineData(accountPointHeadVo.mRepParamMap));
        accountPointHeadVo.mCardVoLiveData.addSource(responseLiveData,
                new NitNetBoundObserver<>(new NitBoundCallback<MyInfoDispatcherVo>() {
                    @Override
                    public void onComplete(Resource<MyInfoDispatcherVo> resource) {
                        super.onComplete(resource);

                    }

                    @Override
                    public void onNetworkError(Resource<MyInfoDispatcherVo> resource) {
                        super.onNetworkError(resource);

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
        if (accountPointHeadVo != null) {
            fetchAccountHeaderCard(accountPointHeadVo);
        }
    }

    @Override
    public void loadCardData(BaseCardVo accountPointHeadVo) {
        fetchAccountHeaderCard((AccountPointHeadVo) accountPointHeadVo);
    }

}
