package com.docker.nitsample.vm.card;

import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.nitsample.vo.card.AppBannerCardVo;

import java.util.ArrayList;

import javax.inject.Inject;

public class AppCardViewModel extends NitcommonCardViewModel {

    public ArrayList<AppBannerCardVo> appBannerCardVos = new ArrayList<>();


    @Inject
    public AppCardViewModel() {
    }

    @Override
    public void loadData() {
    }


//    public void fetchAccountHeaderCard(AccountHeadCardVo accountHeadCardVo) {
//        Log.d("sss", "fetchAccountHeaderCard: ===============1111s========");
//        accountHeadCardVo.mCardVoLiveData.addSource(RequestServer(accountService.featchMineData(accountHeadCardVo.mRepParamMap)),
//                new HivsNetBoundObserver<>(new NetBoundCallback<MyInfoDispatcherVo>() {
//                    @Override
//                    public void onComplete(Resource<MyInfoDispatcherVo> resource) {
//                        super.onComplete(resource);
//                        accountHeadCardVo.setMyinfo(resource.data.member);
//                        if (resource.data.extData != null) {
//                            accountHeadCardVo.myinfo.setCircleNum(resource.data.extData.dynamicNum);
//                            accountHeadCardVo.myinfo.setCommentNum(resource.data.extData.plNum);
//                            accountHeadCardVo.myinfo.setLikeNum(resource.data.extData.dzNum);
//                        }
//                    }
//                }));
//    }

//    @Override
//    public void onOuterVmRefresh(NitCommonListVm outerVm) {
//        super.onOuterVmRefresh(outerVm);
//
//        for (int i = 0; i < appBannerCardVos.size(); i++) {
//            outerVm.addCardVo(appBannerCardVos.get(i), appBannerCardVos.get(i).position);
////            fetchAccountHeaderCard(accountHeadCardVos.get(i));
//        }
//
//    }


    // 只刷新不添加
    public void onJustRefresh() {
//        for (int i = 0; i < accountHeadCardVos.size(); i++) {
//            fetchAccountHeaderCard(accountHeadCardVos.get(i));
//        }
    }


}
