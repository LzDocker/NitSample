package com.docker.nitsample.vm;

import com.bfhd.circle.api.CircleService;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.ServiceDataBean;

import java.util.List;

import javax.inject.Inject;

/**
 * kxf -> 2019-12-16
 **/
public class SearchViewModel extends NitCommonContainerViewModel {
    @Inject
    CircleService circleService;

    @Inject
    public SearchViewModel() {
    }

    public void getInfoData() {

        LiveData<ApiResponse<BaseResponse<List<ServiceDataBean>>>> servicefun = null;
        mStaDy.ReqParam.put("page", String.valueOf(mPage));
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        mStaDy.ReqParam.put("memberid", userInfoVo.uid);
//        mStaDy.ReqParam.put("uuid", userInfoVo.uuid);

//        if (mStaDy.ReqParam.containsKey("memberid") && "-1".equals(mStaDy.ReqParam.get("memberid"))) {
//            mStaDy.ReqParam.put("memberid", userInfoVo.uid);
//        }
//        if (mStaDy.ReqParam.containsKey("uuid") && "-1".equals(mStaDy.ReqParam.get("uuid"))) {
//            mStaDy.ReqParam.put("uuid", userInfoVo.uuid);
//        }

        if (mStaDy.ReqType == 1) { // 固定url
            servicefun = circleService.fechCircleInfoList(mStaDy.ReqParam);
        } else {
            servicefun = circleService.fechCircleInfoList(mStaDy.ApiUrl, mStaDy.ReqParam);
        }
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        servicefun), new HivsNetBoundObserver<>(new NetBoundCallback<List<ServiceDataBean>>() {
                    @Override
                    public void onLoading() {
                        super.onLoading();
                        isLoadState = true;
                        if (mPage == 1) {
                            if (mIsfirstLoad) {
                                mEmptycommand.set(EmptyStatus.BdLoading);
                                setLoadControl(false);
                            } else {
                                setLoadControl(true);
                            }
                        }
                    }

                    @Override
                    public void onComplete(Resource<List<ServiceDataBean>> resource) {
                        super.onComplete(resource);
                        isLoadState = false;
                        mIsfirstLoad = false;
                        if (mPage == 1) {
                            items.clear();
                        }
                        if (resource.data != null && resource.data.size() > 0) {
                            mVmEventSouce.setValue(new ViewEventResouce(201, "", resource.data));
                            mEmptycommand.set(EmptyStatus.BdHiden);
                            items.addAll(resource.data);
                            if (isAddAttenPersion) { //todo
                                // 关注
                                if (mPage == 1) {
                                    getUserVoList();
                                    ServiceDataBean serverPersion = new ServiceDataBean();
                                    serverPersion.setType("persion");
                                    items.add(1, serverPersion);
                                }
                                // 关注
                            }
                            mPage++;
                            setLoadControl(true);
                        } else {
                            if (items.size() == 0) { // 暂无数据
                                mEmptycommand.set(EmptyStatus.BdEmpty);
                                setLoadControl(false);
                            }
                            mVmEventSouce.setValue(new ViewEventResouce(202, "", null));
                        }
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        isLoadState = false;
                        mVmEventSouce.setValue(new ViewEventResouce(202, "", null));
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                    }

                    @Override
                    public void onBusinessError(Resource<List<ServiceDataBean>> resource) {
                        super.onBusinessError(resource);
                        setLoadControl(false);
                        mVmEventSouce.setValue(new ViewEventResouce(202, "", null));
                    }

                    @Override
                    public void onNetworkError(Resource<List<ServiceDataBean>> resource) {
//                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络问题，请重试");
                        mCompleteCommand.set(true);
                        mCompleteCommand.notifyChange();
                        setLoadControl(false);
                        if (items.size() == 0) {
                            mEmptycommand.set(EmptyStatus.BdError);
                        }
                        mVmEventSouce.setValue(new ViewEventResouce(203, "", null));
                    }
                }));
    }

    //  获取热门搜索关键词
    public void fetchHotSearch() {
        mContainerLiveData.addSource(
                RequestServer(
                        circleService.hotwords("2")), new HivsNetBoundObserver<>(new NetBoundCallback<List<RstServerVo>>(this) {
                    @Override
                    public void onComplete(Resource<List<RstServerVo>> resource) {
                        super.onComplete(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(1003, "", resource.data));
                    }

                    @Override
                    public void onNetworkError(Resource<List<RstServerVo>> resource) {
                        super.onNetworkError(resource);
                        mVmEventSouce.setValue(new ViewEventResouce(1003, "", null));
                    }
                }));
    }
}
