package com.bfhd.account.vm;

import android.arch.lifecycle.MediatorLiveData;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MoneyBoxVov2;
import com.bfhd.account.vo.TxmemberVo;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.circle.base.ViewEventResouce;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.HashMap;

import javax.inject.Inject;

public class MoneyBoxCommonViewModel extends NitCommonContainerViewModel {


    @Inject
    AccountService accountService;


    @Inject
    public MoneyBoxCommonViewModel() {

    }

    public final MediatorLiveData<TxmemberVo> mTxvoLv = new MediatorLiveData<>();


    public void getMemberWithdraw() {
        //uuid
        HashMap<String, String> param = new HashMap<>();
        param.put("uuid", CacheUtils.getUser().uuid);
        mTxvoLv.addSource(RequestServer(accountService.getMemberWithdraw(param)), new NitNetBoundObserver<TxmemberVo>(new NitBoundCallback<TxmemberVo>() {
            @Override
            public void onComplete(Resource<TxmemberVo> resource) {
                super.onComplete(resource);
                mTxvoLv.setValue(resource.data);
            }
        }));
    }
    //getMemberWithdraw


    public final MediatorLiveData<MoneyBoxVov2> mMoneyBoxLv = new MediatorLiveData<>();

    /**
     * 我的钱包
     */
    public void moneyBox() {
        showDialogWait("加载中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mMoneyBoxLv.addSource(RequestServer(accountService.moneyBoxv2(params)), new NitNetBoundObserver<MoneyBoxVov2>(new NitBoundCallback<MoneyBoxVov2>() {
            @Override
            public void onComplete(Resource<MoneyBoxVov2> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mMoneyBoxLv.setValue(resource.data);
            }

            @Override
            public void onNetworkError(Resource<MoneyBoxVov2> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                ToastUtils.showShort("网络问题，请重试");
            }

            @Override
            public void onBusinessError(Resource<MoneyBoxVov2> resource) {
                super.onComplete();
                hideDialogWait();
                ToastUtils.showShort("获取金额失败");
            }
        }));
    }


    public final MediatorLiveData<String> mTxMoneyLv = new MediatorLiveData<>();

    /**
     * 提现
     */
    public void txMoney(HashMap<String, String> params) {
        showDialogWait("加载中", false);
        mTxMoneyLv.addSource(RequestServer(accountService.txMoney(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mTxMoneyLv.setValue(resource.data);
                RxBus.getDefault().post(new RxEvent<>("refresh_tx", ""));
                ToastUtils.showShort("提现成功");
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                hideDialogWait();
                ToastUtils.showShort("网络问题，请重试");
            }

            @Override
            public void onBusinessError(Resource<String> resource) {
                super.onComplete();
                hideDialogWait();
                ToastUtils.showShort("提现失败");
            }
        }));
    }
}
