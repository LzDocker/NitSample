package com.docker.cirlev2.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ActivityUtils;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.api.CircleApiService;
import com.docker.cirlev2.ui.comment.CommentDialogFragment;
import com.docker.cirlev2.ui.dynamicdetail.CircleDynamicDetailActivity;
import com.docker.cirlev2.util.AudioPlayerUtils;
import com.docker.cirlev2.util.BdUtils;
import com.docker.cirlev2.vo.entity.CommentRstVo;
import com.docker.cirlev2.vo.entity.CommentVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.param.StaPersionDetail;
import com.docker.common.BR;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.core.repository.NitBoundCallback;
import com.docker.core.repository.NitNetBoundObserver;
import com.docker.core.repository.Resource;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.wx.goodview.GoodView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class CircleCommentListViewModel extends NitCommonContainerViewModel {


    @Inject
    CircleApiService circleApiService;

    private AudioPlayerUtils playerUtils;

    @Inject
    public CircleCommentListViewModel() {

    }

    public CommentVo TopCommentVo;

    public void setTopCommon(CommentVo TopCommentVo) {
        this.TopCommentVo = TopCommentVo;
    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {

        if (TopCommentVo == null) {
            return circleApiService.fechCommentList(param);
        } else {
            return circleApiService.replyList(param);
        }
    }

    @Override
    public void setLoadControl(boolean enable) {
        super.setLoadControl(enable);
        mEmptycommand.set(EmptyStatus.BdHiden);
    }


    // 嵌套rv的itembinding
    public final ItemBinding<CommentVo.Reply> itemReplayBinding = ItemBinding.<CommentVo.Reply>of(BR.item, R.layout.circlev2_item_replay) // 单一view 有点击事件
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

    private CommentDialogFragment dialogFragment;

    // 条目点击事件  回复
    public void ItemCommentClick(CommentVo commentVo, View view) {
        if (dialogFragment == null) {
            dialogFragment = new CommentDialogFragment();
        }
        ServiceDataBean serviceDataBean = (ServiceDataBean) mCommonListReq.externs.get("serverdata");
        if (TopCommentVo != null) {
            dialogFragment.setText("", "回复：" + TopCommentVo.getNickname());
        } else {
            dialogFragment.setText("", "回复：" + commentVo.getNickname());
        }
        dialogFragment.setDataCallback(new CommentDialogFragment.DialogFragmentDataCallback() {
            @Override
            public void setCommentText(String commentTextTemp) {
                KeyboardUtils.hideSoftInput(ActivityUtils.getTopActivity());
            }

            @Override
            public void sendComment(String commentTextTemp) {
                KeyboardUtils.hideSoftInput(ActivityUtils.getTopActivity());
                if (serviceDataBean != null) {
                    HashMap<String, String> params = new HashMap<>();
                    UserInfoVo userInfoVo = CacheUtils.getUser();
                    params.put("circleid", serviceDataBean.getCircleid());
                    params.put("utid", serviceDataBean.getUtid());
                    params.put("dynamicid", serviceDataBean.getDynamicid());
                    params.put("memberid", userInfoVo.uid);
                    params.put("uuid", userInfoVo.uuid);
                    if (TopCommentVo == null) {
                        params.put("push_uuid", commentVo.getUuid());
                        params.put("push_memberid", commentVo.getMemberid());
                        params.put("cid", commentVo.getCommentid());
                        params.put("reply_memberid", commentVo.getMemberid());
                        params.put("reply_uuid", commentVo.getUuid());
                        params.put("reply_nickname", commentVo.getNickname());
                    } else {
                        params.put("push_uuid", TopCommentVo.getUuid());
                        params.put("push_memberid", TopCommentVo.getMemberid());
                        params.put("cid", TopCommentVo.getCommentid());
                        params.put("reply_memberid", TopCommentVo.getMemberid());
                        params.put("reply_uuid", TopCommentVo.getUuid());
                        params.put("reply_nickname", TopCommentVo.getNickname());
                    }

                    if (TextUtils.isEmpty(userInfoVo.avatar)) {
                        userInfoVo.avatar = "";
                    }
                    params.put("avatar", userInfoVo.avatar);
                    if (TextUtils.isEmpty(userInfoVo.nickname)) {
                        params.put("nickname", "匿名");
                    } else {
                        params.put("nickname", userInfoVo.nickname);
                    }
                    params.put("content", commentTextTemp);
                    if (TopCommentVo != null) {
                        replayComment(TopCommentVo, params);
                    } else {
                        replayComment(commentVo, params);
                    }

                }
            }
        });
        dialogFragment.show(((FragmentActivity) ActivityUtils.getTopActivity()).getSupportFragmentManager(), "null");
    }


    // 回复评论
    public void replayComment(CommentVo commentVo, HashMap<String, String> params) {
        showDialogWait("发送中...", false);
        mServerLiveData.addSource(RequestServer(circleApiService.commentDynamic(params)), new NitNetBoundObserver(new NitBoundCallback() {
            @Override
            public void onComplete(Resource resource) {
                super.onComplete(resource);
                hideDialogWait();
                KeyboardUtils.hideSoftInput(ActivityUtils.getTopActivity());
                UserInfoVo userInfoVo = CacheUtils.getUser();
                CommentVo.Reply reply = new CommentVo.Reply();
                reply.setCommentid(((CommentRstVo) resource.data).commentid);
                reply.setContent(params.get("content"));
                reply.setReply_nickname(userInfoVo.nickname);
                reply.setMemberid(userInfoVo.uid);
                reply.setUuid(userInfoVo.uuid);
                reply.setNickname(userInfoVo.nickname);
                commentVo.getReply().add(reply);
                commentVo.notifyChange();
            }

            @Override
            public void onComplete() {
                super.onComplete();
                hideDialogWait();
            }
        }));
    }

    // 点击头像
    public void ItemAvaterClick(CommentVo commentVo, View v) {
        if (commentVo != null) {
            StaPersionDetail staPersionDetail = new StaPersionDetail();
            staPersionDetail.name = commentVo.nickname;
            staPersionDetail.uuid = commentVo.getUuid();
            staPersionDetail.uid = commentVo.getMemberid();
            ARouter.getInstance().build(AppRouter.CIRCLE_persion_v2_detail).withSerializable("mStartParam", staPersionDetail).navigation();
        }
    }

    //删除评论
    public void DelCommentClick(CommentVo commentVo, View view) {
        //delComment
        ServiceDataBean serviceDataBean = (ServiceDataBean) mCommonListReq.externs.get("serverdata");
        UserInfoVo userInfoVo = CacheUtils.getUser();
        showDialogWait("删除中...", false);
        HashMap<String, String> params = new HashMap<>();
        params.put("dynamicid", serviceDataBean.getDynamicid());
        if (TopCommentVo != null) {
            params.put("cid", TopCommentVo.getCommentid());
        } else {
            params.put("cid", commentVo.getCommentid());
        }
        params.put("commentid", commentVo.getCommentid());
        params.put("memberid", userInfoVo.uid);
        params.put("uuid", userInfoVo.uuid);

        mServerLiveData.addSource(RequestServer(circleApiService.delComment(params)), new NitNetBoundObserver(new NitBoundCallback() {
            @Override
            public void onComplete(Resource resource) {
                super.onComplete(resource);
                hideDialogWait();
                if (replatLv.getValue() != null) {
                    replatLv.getValue().remove(commentVo);
                    RxBus.getDefault().post(new RxEvent<>("comment_refresh", ""));
                } else {
                    mItems.remove(commentVo);
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                hideDialogWait();
            }
        }));
    }


    // 点赞
    public void PariseCommentClick(CommentVo commentVo, View view) {
        HashMap<String, String> params = new HashMap<>();
        params.put("commentid", commentVo.getCommentid());
        params.put("status", commentVo.getIsDo() == 1 ? "0" : "1");
        showDialogWait("点赞中...", false);

        mServerLiveData.addSource(RequestServer(circleApiService.pariseComment(params)), new NitNetBoundObserver(new NitBoundCallback() {
            @Override
            public void onComplete(Resource resource) {
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

    // 评论中语音点击
    public void AudioCommentClick(String audiourl, View view) {
        if (playerUtils == null) {
            playerUtils = new AudioPlayerUtils();
        }
        playerUtils.AudioDetailClick(audiourl, view);
    }


    public void ItemReplayClick(CommentVo commentVo, View view) {

        Log.d("sss", "ItemReplayClick: ==================");
        ARouter.getInstance().build(AppRouter.CIRCLE_comment_v2_reply).withSerializable("commentVo", commentVo).withSerializable("serviceDataBean", (Serializable) mCommonListReq.externs.get("serverdata")).navigation();

//        CircleReplyListActivity.startMe(this.getHoldingActivity(), (CommentVo) viewEventResouce.data, staDetailParam, ((CircleDynamicDetailActivity) getHoldingActivity()).serviceDataBean);
    }

    public final MediatorLiveData<List<CommentVo>> replatLv = new MediatorLiveData<>();

}
