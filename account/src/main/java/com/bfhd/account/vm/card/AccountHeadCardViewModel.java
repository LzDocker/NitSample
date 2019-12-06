package com.bfhd.account.vm.card;

import com.bfhd.account.api.AccountService;
import com.bfhd.account.vo.MyInfoVo;
import com.bfhd.account.vo.module.mine.AccountHeadCardVo;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.repository.Resource;

import java.util.Collection;

import javax.inject.Inject;

import timber.log.Timber;

public class AccountHeadCardViewModel extends NitcommonCardViewModel {


    public AccountHeadCardVo accountHeadCardVo = new AccountHeadCardVo(1, 0);

    @Inject
    AccountService accountService;

    @Inject
    public AccountHeadCardViewModel() {

    }

    public void fetchMyInfo() {

        UserInfoVo userInfoVo = CacheUtils.getUser();
        mServerLiveData.addSource(RequestServer(accountService.featchMineData(userInfoVo.uid, userInfoVo.uuid)),
                new HivsNetBoundObserver<>(new NetBoundCallback<MyInfoVo>() {
                    @Override
                    public void onComplete(Resource<MyInfoVo> resource) {
                        super.onComplete(resource);
                        Timber.e("========" + resource.data.getFullName());
                        accountHeadCardVo.setMyinfo(resource.data);
                    }
                }));
    }

    @Override
    public void initCommand() {
        fetchMyInfo();
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
        fetchMyInfo();
    }

    @Override
    public void loadCardData(CommonListOptions commonListOptions) {
        fetchMyInfo();
    }


}
