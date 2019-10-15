package com.bfhd.circle.vm;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.media.MediaPlayer;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.api.CircleService;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.base.HivsNetBoundObserver;
import com.bfhd.circle.base.NetBoundCallback;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.utils.AudioPlayerUtils;
import com.bfhd.circle.vo.CommentRstVo;
import com.bfhd.circle.vo.CommentVo;
import com.bfhd.circle.vo.ServiceDataBean;
import com.bfhd.circle.vo.bean.StaDetailParam;
import com.bfhd.circle.vo.bean.StaPersionDetail;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.repository.CommonRepository;
import com.docker.core.repository.Resource;
import com.wx.goodview.GoodView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by zhangxindang on 2018/10/19.
 * <p>
 * 评论
 */

public class CircleCommentViewModel extends HivsBaseViewModel {


    @Inject
    CommonRepository commonRepository;

    @Inject
    CircleService circleService;

    private StaDetailParam staDetailParam;

    private MediaPlayer mp; // 播放语音
    public String isComment; // 评论权限

    private AudioPlayerUtils playerUtils;

    @Inject
    public CircleCommentViewModel() {

    }

    public CommentVo TopCommentVo;

    public void setTopCommon(CommentVo TopCommentVo) {
        this.TopCommentVo = TopCommentVo;
    }

    @Override
    public void initCommand() {
        mCommand.OnRefresh(() -> {
            getData();
        });
        mCommand.OnLoadMore(() -> {
            getData();
        });
        mCommand.OnRetryLoad(() -> {
            getData();
        });
    }

    public void initStaParam(StaDetailParam staDetailParam) {
        this.staDetailParam = staDetailParam;
    }

    public void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("dynamicid", staDetailParam.dynamicId);
        params.put("page", String.valueOf(mPage));
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.fechCommentList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<CommentVo>>() {
                    @Override
                    public void onComplete(Resource<List<CommentVo>> resource) {
                        super.onComplete(resource);
                        commentItems.addAll(resource.data);
                        mVmEventSouce.setValue(new ViewEventResouce(212, "", resource.data));
                        mPage++;
                    }
                }));
    }

    // 评论列表数据源
    public final ObservableList<CommentVo> commentItems = new ObservableArrayList<>();
    // 评论item 样式
    public final ItemBinding<CommentVo> commentBinding = ItemBinding.<CommentVo>of(BR.item, R.layout.circle_item_comment) // 单一view 有点击事件
            .bindExtra(BR.viewmodel, this);


    // 嵌套rv的数据源
    public final ObservableList<CommentVo.Reply> getResouceData(CommentVo commentVo) {
        ObservableList<CommentVo.Reply> observableList = new ObservableArrayList<>();
        if (commentVo != null && commentVo.getReply() != null && commentVo.getReply().size() > 0) {
            if (commentVo.getReply().size() > 2) {
                observableList.addAll(commentVo.getReply().subList(0, 2));
            } else {
                observableList.addAll(commentVo.getReply());
            }
        }
        return observableList;
    }


    // 点击头像
    public void ItemAvaterClick(CommentVo commentVo, View v) {
        if (commentVo != null) {
            StaPersionDetail staPersionDetail = new StaPersionDetail();
            staPersionDetail.name = commentVo.nickname;
            staPersionDetail.uuid = commentVo.getUuid();
            staPersionDetail.uid = commentVo.getMemberid();
            ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", staPersionDetail).navigation();
        }
    }

    // 点击头像
    public void ItemAvaterClick(CommentVo.Reply commentVo, View v) {
        if (commentVo != null) {
            StaPersionDetail staPersionDetail = new StaPersionDetail();
            staPersionDetail.name = commentVo.nickname;
            staPersionDetail.uuid = commentVo.getUuid();
            staPersionDetail.uid = commentVo.getMemberid();
            ARouter.getInstance().build(AppRouter.CIRCLE_persion_detail).withSerializable("mStartParam", staPersionDetail).navigation();
        }
    }


    // 嵌套rv的itembinding
    public final ItemBinding<CommentVo.Reply> itemBinding = ItemBinding.<CommentVo.Reply>of(BR.item, R.layout.circle_item_replay) // 单一view 有点击事件
            .bindExtra(BR.viewmodel, this);


    // 条目点击事件  回复
    public void ItemCommentClick(CommentVo commentVo, View view) {
        if ("-1".equals(CacheUtils.getUser().memberid)) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
            return;
        }
        mVmEventSouce.setValue(new ViewEventResouce(211, "", commentVo));
    }


    public void ItemReplayClick(CommentVo commentVo, View view) {
        if ("-1".equals(CacheUtils.getUser().memberid)) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
            return;
        }
        mVmEventSouce.setValue(new ViewEventResouce(222, "", commentVo));
    }

    // 回复评论
    public void replayComment(HashMap<String, String> params) {
        if ("-1".equals(CacheUtils.getUser().memberid)) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
            return;
        }
        showDialogWait("发送中...", false);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.commentDynamic(params)), new HivsNetBoundObserver<>(new NetBoundCallback<CommentRstVo>() {
                    @Override
                    public void onComplete(Resource<CommentRstVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();

                        if (resource.data != null) {
                            mVmEventSouce.setValue(new ViewEventResouce(213, "", resource.data));
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


//    //删除评论
//    public void DelCommentClick(CommentVo commentVo, View view) {
//        //delComment
//        UserInfoVo userInfoVo = CacheUtils.getUser();
//        showDialogWait("删除中...", false);
//        HashMap<String, String> params = new HashMap<>();
//        params.put("dynamicid", staDetailParam.dynamicId);
//        params.put("cid", commentVo.getCommentid());
//        params.put("commentid", commentVo.getCommentid());
//        params.put("memberid", userInfoVo.uid);
//        params.put("uuid", userInfoVo.uuid);
//        mResourceLiveData.addSource(
//                commonRepository.noneChache(
//                        circleService.delComment(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
//                    @Override
//                    public void onComplete(Resource<String> resource) {
//                        super.onComplete(resource);
//                        hideDialogWait();
//                        mVmEventSouce.setValue(new ViewEventResouce(214, "", commentVo));
//                        commentItems.remove(commentVo);
//                        if (commentItems.size() < 20) {
//                            mPage = 1;
//                            mVmEventSouce.setValue(new ViewEventResouce(212, "", ""));
//                        }
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
//                        hideDialogWait();
//                    }
//                }));
//    }


    //删除评论
    public void DelCommentClick(CommentVo commentVo, View view) {
        //delComment
        UserInfoVo userInfoVo = CacheUtils.getUser();
        showDialogWait("删除中...", false);
        HashMap<String, String> params = new HashMap<>();
        params.put("dynamicid", staDetailParam.dynamicId);
        if (TopCommentVo != null) {
            params.put("cid", TopCommentVo.getCommentid());
        } else {
            params.put("cid", commentVo.getCommentid());
        }
        params.put("commentid", commentVo.getCommentid());
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.delComment(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(214, "", commentVo));
                        commentItems.remove(commentVo);
                        if (commentItems.size() < 20) {
                            mPage = 1;
                            mVmEventSouce.setValue(new ViewEventResouce(212, "", ""));
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    // 刷新回复列表
    public void updataReplayData(String cid, CommentVo.Reply reply) {
        for (int i = 0; i < commentItems.size(); i++) {
            if (commentItems.get(i).getCommentid().equals(cid)) {
                commentItems.get(i).getReply().add(reply);
                commentItems.get(i).notifyChange();
                break;
            }
        }
    }


    // 点赞
    public void PariseCommentClick(CommentVo commentVo, View view) {
        if ("-1".equals(CacheUtils.getUser().memberid)) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("commentid", commentVo.getCommentid());
        params.put("status", commentVo.getIsDo() == 1 ? "0" : "1");
        showDialogWait("点赞中...", false);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.pariseComment(params)), new HivsNetBoundObserver<>(new NetBoundCallback<String>() {
                    @Override
                    public void onComplete(Resource<String> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        int parisenum = Integer.parseInt(commentVo.praiseNum);
                        if (commentVo.getIsDo() == 1) {
                            commentVo.setIsDo(0);
                            if (parisenum > 0) {
                                parisenum--;
                            }
                        } else {
                            commentVo.setIsDo(1);
                            parisenum++;
                            showDzView(view);
                        }
                        commentVo.setPraiseNum(String.valueOf(parisenum));
                        if (parisenum > 99) {
                            commentVo.setPraiseNum("99+");
                        }
                        commentVo.notifyPropertyChanged(BR.isDo);
                        commentVo.notifyPropertyChanged(BR.praiseNum);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    // 评论中语音点击
    public void AudioCommentClick(String audiourl, View view) {
        if (playerUtils == null) {
            playerUtils = new AudioPlayerUtils();
        }
        playerUtils.AudioDetailClick(audiourl, view);
    }


    private void showDzView(View view) {
        GoodView goodView = new GoodView(view.getContext());
        goodView.setDistance(100);
        goodView.setImage(R.mipmap.circle_icon_dz2);
        GoodView goodView1 = new GoodView(view.getContext());
        goodView1.setImage(R.mipmap.circle_icon_dz2);
        GoodView goodView2 = new GoodView(view.getContext());
        goodView2.setImage(R.mipmap.circle_icon_dz2);
        goodView.show(view.findViewById(R.id.iv_dz));
        goodView1.show(view.findViewById(R.id.iv_dz));
        goodView2.show(view.findViewById(R.id.iv_dz));
    }

    public String img = "https://wanandroid.com/blogimgs/54f4350f-039d-48b6-a38b-0933e1405004.png";

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destory() {
        if (playerUtils != null) {
            playerUtils.ondestory();
        }
    }


    public void replyList(String commentid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("commentid", commentid);
        params.put("page", String.valueOf(mPage));
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.replyList(params)), new HivsNetBoundObserver<>(new NetBoundCallback<List<CommentVo>>() {
                    @Override
                    public void onComplete(Resource<List<CommentVo>> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        mVmEventSouce.setValue(new ViewEventResouce(223, "", resource.data));
                        mPage++;
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }


    // 评论
    public void commentDynamic(HashMap<String, String> params) {
        if ("-1".equals(CacheUtils.getUser().memberid)) {
            ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).withBoolean("isFoceLogin", true).navigation();
            return;
        }
        showDialogWait("发送中...", false);
        mResourceLiveData.addSource(
                commonRepository.noneChache(
                        circleService.commentDynamic(params)), new HivsNetBoundObserver<>(new NetBoundCallback<CommentRstVo>() {
                    @Override
                    public void onComplete(Resource<CommentRstVo> resource) {
                        super.onComplete(resource);
                        hideDialogWait();
                        if (resource.data != null) {
                            mVmEventSouce.setValue(new ViewEventResouce(230, "", resource.data));
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        hideDialogWait();
                    }
                }));
    }

}
