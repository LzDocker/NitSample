package com.docker.cirlev2.vm;

import android.arch.lifecycle.MediatorLiveData;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.util.AudioPlayerUtils;
import com.docker.cirlev2.util.BdUtils;
import com.docker.cirlev2.vo.entity.CommentVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.BR;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;

import java.io.Serializable;
import java.util.HashMap;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class CircleDynamicDetailViewModel extends CircleDynamicListViewModel {

    public final MediatorLiveData<ServiceDataBean> mDynamicDetailLv = new MediatorLiveData<>();
    public final MediatorLiveData<String> mDynamicDelLv = new MediatorLiveData<>();

    private AudioPlayerUtils playerUtils;
    @Inject
    CircleApiService circleApiService;

    @Inject
    public CircleDynamicDetailViewModel() {

    }

    // 动态详情
    public MediatorLiveData<ServiceDataBean> fechDynamicDetail(HashMap<String, String> param) {
        mDynamicDetailLv.addSource(RequestServer(circleApiService.fechDynamicDetail(param)), new NitNetBoundObserver<ServiceDataBean>(new NitBoundCallback<ServiceDataBean>() {
            @Override
            public void onComplete(Resource<ServiceDataBean> resource) {
                super.onComplete(resource);
                mDynamicDetailLv.setValue(resource.data);
            }

            @Override
            public void onNetworkError(Resource resource) {
                super.onNetworkError(resource);
                mDynamicDetailLv.setValue(null);
            }

            @Override
            public void onBusinessError(Resource resource) {
                super.onBusinessError(resource);
                mDynamicDetailLv.setValue(null);
            }
        }));
        return mDynamicDetailLv;
    }

    public void dynamicDel(String circleid, String dynamicid, String utid) {
        HashMap<String, String> params = new HashMap<>();
        UserInfoVo userInfoVo = CacheUtils.getUser();
        params.put("circleid", circleid);
        params.put("dynamicid", dynamicid);
        params.put("utid", utid);
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mDynamicDelLv.addSource(
                RequestServer(circleApiService.dynamicDel(params)), new NitNetBoundObserver<>(new NitBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mDynamicDelLv.setValue(resource.data);
                        ToastUtils.showShort("删除成功");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("网络原因请重试");
                    }
                }));
    }


    // 详情rv的
    public final ItemBinding<ServiceDataBean.ResourceBean> itemDetailBinding = ItemBinding.<ServiceDataBean.ResourceBean>of(BR.item, R.layout.circlev2_item_dynamic_detail_img) // 单一view 有点击事件
            .bindExtra(BR.viewmodel, this);

    public void AudioDetailClick(String audioUrl, View view) {

        Log.d("sss", "AudioDetailClick: ===========AudioDetailClick=======");
        if (playerUtils == null) {
            playerUtils = new AudioPlayerUtils();
        }
        playerUtils.AudioDetailClick(BdUtils.getAudioUrl(audioUrl), view);
    }


    /**
     * 评论 查看更多的点击事件
     */
    public void moreCommentClick(ServiceDataBean item, View view) {
        if (item == null) {
            return;
        }
        ARouter.getInstance().build(AppRouter.CIRCLE_more_comment_v2_reply)
                .withSerializable("serviceDataBean", item)
                .navigation();
    }




    public void circleBlackList(String memberid) {
        showDialogWait("拉黑中...", false);
        UserInfoVo userInfoVo = CacheUtils.getUser();
        mServerLiveData.addSource(
                RequestServer(
                        circleApiService.pullBlack(userInfoVo.memberid, memberid)), new NitNetBoundObserver(new NitBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("拉黑成功");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("拉黑失败请重试...");
                    }
                }));
    }

    // 举报个人
    public void circlePersionReport(String memberid, String content) {
        showDialogWait("举报中...", false);
        mServerLiveData.addSource(
                RequestServer(
                        circleApiService.CircleReport(memberid, content)), new NitNetBoundObserver(new NitBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        ToastUtils.showShort("举报成功");
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }

                    @Override
                    public void onNetworkError(Resource<String> resource) {
                        super.onNetworkError(resource);
                        ToastUtils.showShort("举报失败请重试...");
                    }
                }));
    }


}
