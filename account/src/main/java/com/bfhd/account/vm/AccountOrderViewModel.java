package com.bfhd.account.vm;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MyInfoDispatcherVo;
import com.bfhd.account.vo.tygs.InvatationVo;
import com.bfhd.account.vo.tygs.OrderVo;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class AccountOrderViewModel extends NitCommonContainerViewModel {


    public OrderVo orderVo;

    @Inject
    AccountService accountService;

    @Inject
    public AccountOrderViewModel() {

    }


    public void fetchAccountHeaderCard(OrderVo orderVo) {
        this.orderVo = orderVo;
        LiveData<Resource<MyInfoDispatcherVo>> responseLiveData = RequestServer(accountService.featchMineData(orderVo.mRepParamMap));
        orderVo.mCardVoLiveData.addSource(responseLiveData,
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

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
        OrderVo orderVo = new OrderVo(0, 0);
        orderVo.setShop_name("商品名称");
        List<OrderVo> invatationVoList = new ArrayList<>();
        invatationVoList.add(orderVo);
        invatationVoList.add(orderVo);
        invatationVoList.add(orderVo);
        invatationVoList.add(orderVo);
        mItems.addAll(invatationVoList);
//        if (invatationVo != null) {
//            fetchAccountHeaderCard(invatationVo);
//        }
    }

    @Override
    public void loadCardData(BaseCardVo invatationVo) {
//        fetchAccountHeaderCard((InvatationVo) invatationVo);
    }

}
