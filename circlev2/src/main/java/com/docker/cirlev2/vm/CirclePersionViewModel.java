package com.docker.cirlev2.vm;

import android.arch.lifecycle.MediatorLiveData;

import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.CircleCountpageVo;
import com.docker.cirlev2.vo.entity.CircleGroupPerssionVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.entity.MemberSettingsVo;
import com.docker.cirlev2.vo.entity.TradingCommonVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.vo.vo.CircleCreateVo;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class CirclePersionViewModel extends NitCommonVm {


    @Inject
    public CirclePersionViewModel() {

    }


    @Inject
    CircleApiService circleApiService;

    public final MediatorLiveData<List<TradingCommonVo>> mInnerTradingLV = new MediatorLiveData<>();
    public final MediatorLiveData<List<TradingCommonVo>> mOuterTradingLV = new MediatorLiveData<>();
    public final MediatorLiveData<MemberSettingsVo> mMemberSettingsLV = new MediatorLiveData<>();
    public final MediatorLiveData<String> mMemberSettingUpdateLV = new MediatorLiveData<>();
    public final MediatorLiveData<String> mMemberQuitLV = new MediatorLiveData<>();
    public final MediatorLiveData<CircleCountpageVo> mCircleCountpageVoLV = new MediatorLiveData<>();
    public final MediatorLiveData<List<CircleTitlesVo>> mCircleClass = new MediatorLiveData<>();
    public final MediatorLiveData<String> mFocusLv = new MediatorLiveData<>();
    public final MediatorLiveData<String> mReportLv = new MediatorLiveData<>();
    public final MediatorLiveData<String> mBlackLv = new MediatorLiveData<>();
    public final MediatorLiveData<String> mCircleGroupPerssionUpdateLv = new MediatorLiveData<>();
    public final MediatorLiveData<CircleGroupPerssionVo> mCircleGroupPerssionVoLv = new MediatorLiveData<>();

    public void getInnerPersonList(StaCirParam mStartParam) {
        HashMap<String, String> parammap = new HashMap<>();
        parammap.put("circleid", mStartParam.getCircleid());
        parammap.put("utid", mStartParam.getUtid());
        mInnerTradingLV.addSource(RequestServer(circleApiService.fechInnerPersonList(parammap)),
                new NitNetBoundObserver<>(new NitBoundCallback<List<TradingCommonVo>>() {
                    @Override
                    public void onNetworkError(Resource<List<TradingCommonVo>> resource) {
                        super.onNetworkError(resource);
                    }

                    @Override
                    public void onComplete(Resource<List<TradingCommonVo>> resource) {
                        super.onComplete(resource);
                        mInnerTradingLV.setValue(resource.data);
                    }
                }));
    }


    public void getOuterPersonList(StaCirParam mStartParam) {

        HashMap<String, String> parammap = new HashMap<>();
        parammap.put("circleid", mStartParam.getCircleid());
        parammap.put("utid", mStartParam.getUtid());
        mOuterTradingLV.addSource(RequestServer(circleApiService.fechInnerPersonList(parammap)),
                new NitNetBoundObserver<>(new NitBoundCallback<List<TradingCommonVo>>() {
                    @Override
                    public void onNetworkError(Resource<List<TradingCommonVo>> resource) {
                        super.onNetworkError(resource);
                    }

                    @Override
                    public void onComplete(Resource<List<TradingCommonVo>> resource) {
                        super.onComplete(resource);
                        mOuterTradingLV.setValue(resource.data);
                    }
                }));
    }


    /*
     *
     * 设置成员
     * */
    public void getSettingMember(StaCirParam mStartParam, String employeeid, String employeeuuid) {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        Map<String, String> parms = new HashMap<>();
        parms.put("circleid", mStartParam.getCircleid());
        parms.put("utid", mStartParam.getUtid());
        parms.put("memberid", userInfoVo.uid);
        parms.put("uuid", userInfoVo.uuid);
        parms.put("employeeid", employeeid);
        parms.put("employeeuuid", employeeuuid);

        mMemberSettingsLV.addSource(RequestServer(circleApiService.fechSettingMember(parms)),
                new NitNetBoundObserver<>(new NitBoundCallback<MemberSettingsVo>() {
                    @Override
                    public void onNetworkError(Resource<MemberSettingsVo> resource) {
                        super.onNetworkError(resource);
                    }

                    @Override
                    public void onComplete(Resource<MemberSettingsVo> resource) {
                        super.onComplete(resource);
                        mMemberSettingsLV.setValue(resource.data);
                    }
                }));
    }

    public void updateSettingMember(StaCirParam mStartParam,
                                    String employeeid,
                                    String employeeuuid,
                                    String fullName,
                                    HashMap<String, String> parms) {
        showDialogWait("修改中", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        parms.put("circleid", mStartParam.getCircleid());
        parms.put("employeeid", employeeid);
        parms.put("employeeuuid", employeeuuid);
        parms.put("fullName", fullName);
        parms.put("memberid", userInfoVo.uid);
        parms.put("utid", mStartParam.getUtid());
        parms.put("uuid", userInfoVo.uuid);
        mMemberSettingUpdateLV.addSource(RequestServer(circleApiService.updateSettingMember(parms)),
                new NitNetBoundObserver<>(new NitBoundCallback<String>() {
                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("网络问题，修改失败！请重试");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("修改成功！");
                        mMemberSettingUpdateLV.setValue(resource.data);
                    }
                }));
    }

    public void quitCircle(StaCirParam mStartParam, String employeeid, String employeeuuid) {
        showDialogWait("删除中..", false);
        Map<String, String> parms = new HashMap<>();
        parms.put("circleid", mStartParam.getCircleid());
        parms.put("utid", mStartParam.getUtid());
        parms.put("memberid", employeeid);
        parms.put("uuid", employeeuuid);
        mMemberQuitLV.addSource(RequestServer(circleApiService.updateSettingMember(parms)),
                new NitNetBoundObserver<>(new NitBoundCallback<String>() {
                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("网络问题,请重试");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("删除成功！");
                        mMemberQuitLV.setValue(resource.data);
                        mMemberSettingUpdateLV.setValue(resource.data);
                    }
                }));
    }


    public void circlePersionDetail(String memberid2, String uuid2) {
        HashMap<String, String> params = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("memberid2", memberid2);
        params.put("uuid2", uuid2);

        mCircleCountpageVoLV.addSource(RequestServer(circleApiService.circlePersionDetail(params)),
                new NitNetBoundObserver<>(new NitBoundCallback<CircleCountpageVo>() {
                    @Override
                    public void onNetworkError(Resource<CircleCountpageVo> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                    }

                    @Override
                    public void onComplete(Resource<CircleCountpageVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mCircleCountpageVoLV.setValue(resource.data);
                    }
                }));
    }

    /*
     * 圈子分类
     * */
    public void getCircleClass(String circleid, String utid) {
        HashMap<String, String> params = new HashMap();
        params.put("utid", utid);
        params.put("circleid", circleid);
        mCircleClass.addSource(RequestServer(circleApiService.circlePersionDetail(params)),
                new NitNetBoundObserver<>(new NitBoundCallback<List<CircleTitlesVo>>() {
                    @Override
                    public void onNetworkError(Resource<List<CircleTitlesVo>> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                    }

                    @Override
                    public void onComplete(Resource<List<CircleTitlesVo>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mCircleClass.setValue(resource.data);
                    }
                }));
    }

    public void focus(String uid, String uuid, CircleCountpageVo circleCountpageVo) {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid2", uid);
        params.put("uuid2", uuid);
        params.put("uuid", userInfoVo.uuid);
        params.put("memberid", userInfoVo.uid);
        params.put("nickname", userInfoVo.nickname);
        if (circleCountpageVo.isFocus == 1) {
            params.put("status", "0");
        } else {
            params.put("status", "1");
        }
        showDialogWait("请稍后...", false);

        mFocusLv.addSource(RequestServer(circleApiService.circlePersionDetail(params)),
                new NitNetBoundObserver<>(new NitBoundCallback<String>() {
                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("网络错误，请重试");
                    }

                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        if (circleCountpageVo.isFocus == 1) {
                            circleCountpageVo.setIsFocus(0);
                        } else {
                            circleCountpageVo.setIsFocus(1);
                        }
                        circleCountpageVo.notifyPropertyChanged(BR.isFocus);
                        RxBus.getDefault().post(new RxEvent<>("refresh_focus", circleCountpageVo.getIsFocus())); // 刷新我关注
                    }
                }));
    }


    // 举报个人
    public void circlePersionReport(String memberid, String content) {
        showDialogWait("举报中...", false);
        mReportLv.addSource(RequestServer(circleApiService.CircleReport(memberid, content)),
                new NitNetBoundObserver<>(new NitBoundCallback<String>() {
                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("网络错误，请重试");
                    }

                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("举报成功");
                    }
                }));
    }

    public void circleBlackList(String memberid) {

        showDialogWait("拉黑中...", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        mBlackLv.addSource(RequestServer(circleApiService.pullBlack(userInfoVo.uid, memberid)),
                new NitNetBoundObserver<>(new NitBoundCallback<String>() {
                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("拉黑失败请重试...");
                    }

                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("拉黑成功");
                    }
                }));
    }

    /*
     * 圈子分组列表
     * */
    public void getCircleGrouop(StaCirParam mStartParam) {

        mCircleGroupPerssionVoLv.addSource(RequestServer(circleApiService.fechCircleGroup(mStartParam.getUtid(), mStartParam.getCircleid())),
                new NitNetBoundObserver<>(new NitBoundCallback<CircleGroupPerssionVo>() {
                    @Override
                    public void onNetworkError(Resource<CircleGroupPerssionVo> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                    }

                    @Override
                    public void onComplete(Resource<CircleGroupPerssionVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mCircleGroupPerssionVoLv.setValue(resource.data);
                    }
                }));

    }

    /*
     * 分组权限变更
     * */

    public void updateCircleGrouopPerssion(StaCirParam mStartParam, String key, String val, String groupid) {
        showDialogWait("修改中", false);
        Map<String, String> parms = new HashMap<>();
        parms.put("circleid", mStartParam.getCircleid());
        parms.put("utid", mStartParam.getUtid());
        parms.put("key", key);
        parms.put("val", val);
        parms.put("groupid", groupid);
        mCircleGroupPerssionUpdateLv.addSource(RequestServer(circleApiService.fechCircleGroup(mStartParam.getUtid(), mStartParam.getCircleid())),
                new NitNetBoundObserver<>(new NitBoundCallback<String>() {
                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        hideDialogWait();
                        ToastUtils.showShort("网络问题，修改失败！请重试");
                    }

                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("修改成功！");
                    }
                }));
    }


}
