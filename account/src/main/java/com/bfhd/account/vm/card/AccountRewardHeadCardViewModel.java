package com.bfhd.account.vm.card;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MyInfoDispatcherVo;
import com.bfhd.account.vo.card.AccountHeadCardVo;
import com.bfhd.account.vo.tygs.AccountRewardHeadVo;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.Collection;

import javax.inject.Inject;

public class AccountRewardHeadCardViewModel extends NitcommonCardViewModel {


    public AccountRewardHeadVo accountRewardHeadVo;

    @Inject
    AccountService accountService;

    @Inject
    public AccountRewardHeadCardViewModel() {

    }

    public void fetchAccountHeaderCard(AccountRewardHeadVo accountRewardHeadVo) {
        this.accountRewardHeadVo = accountRewardHeadVo;
        LiveData<Resource<MyInfoDispatcherVo>> responseLiveData = RequestServer(accountService.featchMineData(accountRewardHeadVo.mRepParamMap));
        accountRewardHeadVo.mCardVoLiveData.addSource(responseLiveData,
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
//        if (accountRewardHeadVo != null) {
//            fetchAccountHeaderCard(accountRewardHeadVo);
//        }
    }

    @Override
    public void loadCardData(BaseCardVo accountRewardHeadVo) {
//        fetchAccountHeaderCard((AccountRewardHeadVo) accountRewardHeadVo);
    }

}
