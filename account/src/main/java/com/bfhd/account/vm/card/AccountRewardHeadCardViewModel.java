package com.bfhd.account.vm.card;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MoneyBoxVov2;
import com.bfhd.account.vo.MyInfoDispatcherVo;
import com.bfhd.account.vo.card.AccountHeadCardVo;
import com.bfhd.account.vo.tygs.AccountRewardHeadVo;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.circle.base.ViewEventResouce;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

public class AccountRewardHeadCardViewModel extends NitcommonCardViewModel {


    public AccountRewardHeadVo accountRewardHeadVo;

    @Inject
    AccountService accountService;

    @Inject
    public AccountRewardHeadCardViewModel() {

    }

    public void fetchMoneyBox(AccountRewardHeadVo accountRewardHeadVo) {
        this.accountRewardHeadVo = accountRewardHeadVo;
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        accountRewardHeadVo.mCardVoLiveData.addSource(RequestServer(
                accountService.moneyBoxv2(params)), new NitNetBoundObserver<>(new NitBoundCallback<MoneyBoxVov2>() {
            @Override
            public void onComplete(Resource<MoneyBoxVov2> resource) {
                super.onComplete(resource);
                hideDialogWait();
                accountRewardHeadVo.mCardVoLiveData.setValue(resource.data);
                accountRewardHeadVo.setMoneyBoxVov2(resource.data);
            }

            @Override
            public void onNetworkError(Resource<MoneyBoxVov2> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                ToastUtils.showShort("获取金额失败");
            }

            @Override
            public void onBusinessError(Resource<MoneyBoxVov2> resource) {
                super.onComplete();
                hideDialogWait();
                ToastUtils.showShort("获取金额失败");
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
        if (accountRewardHeadVo != null) {
            fetchMoneyBox(accountRewardHeadVo);
        }
    }

    @Override
    public void loadCardData(BaseCardVo accountRewardHeadVo) {
        fetchMoneyBox((AccountRewardHeadVo) accountRewardHeadVo);
    }

}
