package com.bfhd.account.vm.card;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.bfhd.account.R;
import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MyInfoDispatcherVo;
import com.bfhd.account.vo.tygs.AccountPointHeadVo;
import com.bfhd.account.vo.tygs.PointItemVo;
import com.bfhd.circle.BR;
import com.bumptech.glide.load.model.ModelLoader;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.Empty;
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

public class AccountPointRecycleViewModel extends NitCommonContainerViewModel {


    public AccountPointHeadVo accountPointHeadVo;

    @Inject
    AccountService accountService;

    @Inject
    public AccountPointRecycleViewModel() {

    }

    public ItemBinding<PointItemVo> itemBinding = ItemBinding.<PointItemVo>of(BR.item, R.layout.account_tygs_item_point);


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


    /*@Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {


        return ;
    }*/

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        PointItemVo pointItemVo = new PointItemVo(0, 0);
        pointItemVo.setTime("111");
        pointItemVo.setPoint("12");
        pointItemVo.setName("ceshidosjfi");
        List<PointItemVo> pointItemVoList = new ArrayList<>();
        pointItemVoList.add(pointItemVo);
        pointItemVoList.add(pointItemVo);
        pointItemVoList.add(pointItemVo);
        mItems.addAll(pointItemVoList);
    }

    @Override
    public void formartData(Resource resource) {
        super.formartData(resource);
//        resource.data = resource.data.list;
    }

    @Override
    public void loadCardData(BaseCardVo accountPointHeadVo) {
        fetchAccountHeaderCard((AccountPointHeadVo) accountPointHeadVo);
    }

}