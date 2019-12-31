package com.docker.cirlev2.ui.detail.home.base;

import android.arch.lifecycle.MediatorLiveData;
import android.text.TextUtils;
import android.view.View;

import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.BR;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.vo.entity.CircleDetailVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.entity.MemberGroupingVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.NitCommonVm;
import com.docker.common.common.vo.ShareBean;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public abstract class AbsCircleDetailIndexViewModel extends NitCommonVm {


    @Inject
    CircleApiService circleApiService;

    private String utid, circleid;

    /*
     * 圈子详情信息
     * */
    public final MediatorLiveData<CircleDetailVo> mCircleDetailLv = new MediatorLiveData<>();

    /*
     * 圈子栏目
     * */
    public final MediatorLiveData<List<CircleTitlesVo>> mCircleClassLv = new MediatorLiveData();

    /*
    * 分享数据
    * */
    public final MediatorLiveData<ShareBean> mShareLv = new MediatorLiveData();

    /*
    * 加入圈子
    * */
    public final MediatorLiveData<String> mJoninLv = new MediatorLiveData();


    @Override
    public void initCommand() {
        super.initCommand();
        mCommand.OnRetryLoad(() -> {
            mEmptycommand.set(EmptyStatus.BdLoading);
            if (!TextUtils.isEmpty(utid) && !TextUtils.isEmpty(circleid)) {
                FetchCircleDetail(utid, circleid);
                if (mCircleDetailLv.getValue() == null) {
                    FetchCircleClass();
                }
            }
        });
    }


    public void FetchCircleDetail(String utid, String circleid) {
        this.circleid = circleid;
        this.utid = utid;
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> param = new HashMap();
        param.put("memberid", "3");
        param.put("uuid", "3c29a4eed44db285468df3443790e64a");
        param.put("utid", utid);
        param.put("circleid", circleid);
        mCircleDetailLv.addSource(RequestServer(circleApiService.fechCircleDetail(param)),
                new NitNetBoundObserver<>(new NitBoundCallback<CircleDetailVo>() {
                    @Override
                    public void onNetworkError(Resource<CircleDetailVo> resource) {
                        super.onNetworkError(resource);
                        mEmptycommand.set(EmptyStatus.BdError);
                    }

                    @Override
                    public void onComplete(Resource<CircleDetailVo> resource) {
                        super.onComplete(resource);
                        mCircleDetailLv.setValue(resource.data);
                        mEmptycommand.set(EmptyStatus.BdHiden);
                    }
                }));
    }

    public void FetchCircleClass() {
        mCircleClassLv.addSource(RequestServer(circleApiService.fechCircleClass(circleid, utid)),
                new NitNetBoundObserver<>(new NitBoundCallback<List<CircleTitlesVo>>() {
                    @Override
                    public void onNetworkError(Resource<List<CircleTitlesVo>> resource) {
                        super.onNetworkError(resource);
                        mEmptycommand.set(EmptyStatus.BdError);
                    }

                    @Override
                    public void onComplete(Resource<List<CircleTitlesVo>> resource) {
                        super.onComplete(resource);
                        mCircleClassLv.setValue(resource.data);
                        mEmptycommand.set(EmptyStatus.BdHiden);
                    }
                }));
    }


    public void FetchShareData(HashMap<String, String> params) {
        mShareLv.addSource(RequestServer(circleApiService.share(params)),
                new NitNetBoundObserver<>(new NitBoundCallback<ShareBean>() {
                    @Override
                    public void onNetworkError(Resource<ShareBean> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络错误请重试");
                    }

                    @Override
                    public void onComplete(Resource<ShareBean> resource) {
                        super.onComplete(resource);
                        mShareLv.setValue(resource.data);
                        mEmptycommand.set(EmptyStatus.BdHiden);
                    }
                }));
    }

    public void joinCircle(View view) {
        if (mCircleDetailLv.getValue().getRole() > 0) {
            ToastUtils.showShort("圈主不能退出圈子");
            return;
        }
        if ("1".equals(mCircleDetailLv.getValue().getIsJoin())) {
            quitCirlce();
        } else {
            joinCircle();
        }
    }


    public void quitCirlce() {
        showDialogWait("退出中..", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        Map<String, String> parms = new HashMap<>();
        parms.put("circleid", circleid);
        parms.put("utid", utid);
        parms.put("memberid", userInfoVo.uid);
        parms.put("uuid", userInfoVo.uuid);
        mJoninLv.addSource(RequestServer(circleApiService.quitCircle(parms)),
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
                        mJoninLv.setValue(resource.data);
                        hideDialogWait();
                        ToastUtils.showShort("退出成功！");
                        mCircleDetailLv.getValue().setIsJoin("0");
                        mCircleDetailLv.getValue().notifyPropertyChanged(BR.isJoin);
                        mCircleDetailLv.getValue().setEmployeeNum(String.valueOf(Integer.parseInt(mCircleDetailLv.getValue().getEmployeeNum()) - 1));
                        mCircleDetailLv.getValue().notifyPropertyChanged(BR.employeeNum);
                        RxBus.getDefault().post(new RxEvent<>("refresh_circle_myjoin", "111")); // 刷新我的圈子
                    }
                }));
    }

    public void joinCircle() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("utid", utid);
        if (TextUtils.isEmpty(userInfoVo.nickname)) {
            params.put("fullName", "匿名");
        } else {
            params.put("fullName", userInfoVo.nickname);
        }
        params.put("circleid", circleid);
        showDialogWait("加入中...", false);
        mJoninLv.addSource(RequestServer(circleApiService.joinCircle(params)),
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
                        mJoninLv.setValue(resource.data);
                        hideDialogWait();
                        ToastUtils.showShort("加入成功！");
                        mCircleDetailLv.getValue().setIsJoin("1");
                        mCircleDetailLv.getValue().notifyPropertyChanged(BR.isJoin);
                        mCircleDetailLv.getValue().setEmployeeNum(String.valueOf(Integer.parseInt(mCircleDetailLv.getValue().getEmployeeNum()) + 1));
                        mCircleDetailLv.getValue().notifyPropertyChanged(BR.employeeNum);
                        RxBus.getDefault().post(new RxEvent<>("refresh_circle_myjoin", "111")); // 刷新我的圈子
                    }
                }));
    }


    public void invite(HashMap<String, String> param) {
//        showDialogWait("请稍后...", false);
        mShareLv.addSource(RequestServer(circleApiService.invite(param)),
                new NitNetBoundObserver<>(new NitBoundCallback<ShareBean>() {
                    @Override
                    public void onNetworkError(Resource<ShareBean> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络错误请重试");
                    }

                    @Override
                    public void onComplete(Resource<ShareBean> resource) {
                        super.onComplete(resource);
                        mShareLv.setValue(resource.data);
                    }
                }));
    }


}
