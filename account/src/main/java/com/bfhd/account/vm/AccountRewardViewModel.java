package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.bfhd.account.R;
import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MyInfoDispatcherVo;
import com.bfhd.account.vo.tygs.AccountRewardHeadVo;
import com.bfhd.account.vo.tygs.FansVo;
import com.bfhd.account.vo.tygs.InvatationVo;
import com.docker.cirlev2.BR;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AccountRewardViewModel extends NitCommonContainerViewModel {


    public int flag = 1;

    public InvatationVo invatationVo;

    @Inject
    AccountService accountService;

    @Inject
    public AccountRewardViewModel() {

    }


    public void fetchAccountHeaderCard(InvatationVo invatationVo) {
        this.invatationVo = invatationVo;
        LiveData<Resource<MyInfoDispatcherVo>> responseLiveData = RequestServer(accountService.featchMineData(invatationVo.mRepParamMap));
        invatationVo.mCardVoLiveData.addSource(responseLiveData,
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
        return data;
    }

    @Override
    public Collection<? extends BaseItemModel> formatListData(Collection data) {
        return data;
    }

//    @Override
//    public void loadData() {
//
////        mEmptycommand.set(EmptyStatus.BdHiden);
////        InvatationVo invatationVo = new InvatationVo(0, 0);
////        invatationVo.setNickname("测试");
////        List<InvatationVo> invatationVoList = new ArrayList<>();
////        invatationVoList.add(invatationVo);
////        mItems.addAll(invatationVoList);
////        if (invatationVo != null) {
////            fetchAccountHeaderCard(invatationVo);
////        }
//    }


    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        LiveData<ApiResponse<BaseResponse>> apiResponseLiveData = null;

        switch (flag) {
            case 1:
                param.put("invite_status", "1");
                apiResponseLiveData = accountService.getmyinviteList(param);
                break;
            case 2:
                param.put("invite_status", "2");
                apiResponseLiveData = accountService.getmyinviteList(param);
                break;
        }
        return apiResponseLiveData;


    }

    @Override
    public void loadCardData(BaseCardVo invatationVo) {
//        fetchAccountHeaderCard((InvatationVo) invatationVo);
    }

}
