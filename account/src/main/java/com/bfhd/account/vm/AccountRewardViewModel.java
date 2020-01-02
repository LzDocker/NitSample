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
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AccountRewardViewModel extends NitCommonContainerViewModel {


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

    @Override
    public void loadData() {

        mEmptycommand.set(EmptyStatus.BdHiden);
        InvatationVo invatationVo = new InvatationVo(0, 0);
        invatationVo.setNickname("测试");
        List<InvatationVo> invatationVoList = new ArrayList<>();
        invatationVoList.add(invatationVo);
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
