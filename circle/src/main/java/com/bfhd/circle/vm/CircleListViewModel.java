package com.bfhd.circle.vm;


import android.text.TextUtils;

import com.bfhd.circle.api.CircleService;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.vo.CircleListVo;
import com.bfhd.circle.vo.bean.StaCircleListParam;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.repository.CommonRepository;
import com.docker.core.repository.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class CircleListViewModel extends HivsBaseViewModel {


    @Inject
    CommonRepository commonRepository;

    @Inject
    CircleService circleService;

    private StaCircleListParam mStaParam;

    @Inject
    public CircleListViewModel() {

    }

    @Override
    public void initCommand() {
        mCommand.OnRefresh(() -> {
            mPage = 1;
            if (mStaParam.ReqType == 0) {
                getData();
            } else {
                getCircleList(mStaParam.paramMap);
            }
        });
        mCommand.OnLoadMore(() -> {
            if (mStaParam.ReqType == 0) {
                getData();
            } else {
                getCircleList(mStaParam.paramMap);
            }
        });
        mCommand.OnRetryLoad(() -> {
            if (mStaParam.ReqType == 0) {
                getData();
            } else {
                getCircleList(mStaParam.paramMap);
            }
        });
    }

    public void setStaParam(StaCircleListParam mStaParam) {
        this.mStaParam = mStaParam;
    }

    public void getData() {
        getCircleList();
    }

    // 加入的圈子
    public void getCircleList() {
        UserInfoVo userInfoVo = CacheUtils.getUser();
        HashMap<String, String> params = new HashMap<>();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(commonRepository.noneChache(circleService.fechJoinCircle(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<CircleListVo>>(this) {
            @Override
            public void onComplete(Resource<List<CircleListVo>> resource) {
                super.onComplete(resource);
                mVmEventSouce.setValue(new ViewEventResouce(103, "", resource.data));
                mPage++;
                mCompleteCommand.set(true);
                mCompleteCommand.notifyChange();
            }

            @Override
            public void onNetworkError(Resource<List<CircleListVo>> resource) {
                super.onNetworkError(resource);
                mVmEventSouce.setValue(new ViewEventResouce(104, "", null));
            }
        }));
    }


    public void getCircleList(Map<String, String> params) {

        UserInfoVo userInfoVo = CacheUtils.getUser();
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        params.put("page", String.valueOf(mPage));
        mResourceLiveData.addSource(commonRepository.noneChache(circleService.fechCircleList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<CircleListVo>>(this) {
            @Override
            public void onComplete(Resource<List<CircleListVo>> resource) {
                super.onComplete(resource);
                mVmEventSouce.setValue(new ViewEventResouce(103, "", resource.data));
                mPage++;
                mCompleteCommand.set(true);
                mCompleteCommand.notifyChange();
            }

            @Override
            public void onNetworkError(Resource<List<CircleListVo>> resource) {
                super.onNetworkError(resource);
                mVmEventSouce.setValue(new ViewEventResouce(104, "", null));
            }
        }));
    }

    // 加入圈子
    public void joinCircle(String circleid, String utid, int position) {
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
        mResourceLiveData.addSource(commonRepository.noneChache(circleService.joinCircle(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
            @Override
            public void onComplete(Resource<String> resource) {
                super.onComplete(resource);
                hideDialogWait();
                mVmEventSouce.setValue(new ViewEventResouce(105, "", position));
                ToastUtils.showShort("加入成功！");
            }

            @Override
            public void onComplete() {
                super.onComplete();
                hideDialogWait();
            }

            @Override
            public void onNetworkError(Resource<String> resource) {
                super.onNetworkError(resource);
                ToastUtils.showShort("网络问题，加入失败请重试！");
            }
        }));
    }


    public String img = "https://wanandroid.com/blogimgs/54f4350f-039d-48b6-a38b-0933e1405004.png";

}
