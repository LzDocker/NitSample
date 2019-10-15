package com.bfhd.circle.ui.active;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bfhd.circle.R;
import com.bfhd.circle.base.CommentDialogFragment;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleFragmentCommentBinding;
import com.bfhd.circle.ui.circle.CircleDynamicDetailActivity;
import com.bfhd.circle.vm.CircleCommentViewModel;
import com.bfhd.circle.vo.CommentRstVo;
import com.bfhd.circle.vo.CommentVo;
import com.bfhd.circle.vo.bean.StaDetailParam;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.refresh.SmartRefreshLayout;
import com.docker.core.util.AppExecutors;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 *  评论 fragment  通用布局
 * */
public class CircleCommentFragment extends CommonFragment<CircleCommentViewModel, CircleFragmentCommentBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    private StaDetailParam staDetailParam;
    public SmartRefreshLayout smartRefreshLayout;
    private CommentDialogFragment dialogFragment;
    private String replay;
    private CommentVo commentVo;
    private String title;
    @Inject
    AppExecutors appExecutors;
    Disposable disposable;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_comment;
    }

    public static CircleCommentFragment newInstance() {
        return new CircleCommentFragment();
    }

    public static CircleCommentFragment newInstance(StaDetailParam staDetailParam) {
        CircleCommentFragment commentFragment = new CircleCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("staDetailParam", staDetailParam);
        commentFragment.setArguments(bundle);
        return commentFragment;
    }

    public static CircleCommentFragment newInstance(StaDetailParam staDetailParam, String title) {
        CircleCommentFragment commentFragment = new CircleCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("staDetailParam", staDetailParam);
        bundle.putSerializable("title", title);
        commentFragment.setArguments(bundle);
        return commentFragment;
    }

    @Override
    protected CircleCommentViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleCommentViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView(View view) {
        dialogFragment = new CommentDialogFragment();
        dialogFragment.setDataCallback(new CommentDialogFragment.DialogFragmentDataCallback() {
            @Override
            public void setCommentText(String commentTextTemp) {
                KeyboardUtils.hideSoftInput(getActivity());
            }

            @Override
            public void sendComment(String commentTextTemp) {
                replay = commentTextTemp;
                KeyboardUtils.hideSoftInput(CircleCommentFragment.this.getHoldingActivity());

                CircleDynamicDetailActivity circleDynamicDetailActivity = (CircleDynamicDetailActivity) getHoldingActivity();
                if (circleDynamicDetailActivity.serviceDataBean != null) {
                    HashMap<String, String> params = new HashMap<>();
                    UserInfoVo userInfoVo = CacheUtils.getUser();
                    params.put("circleid", circleDynamicDetailActivity.serviceDataBean.getCircleid());
                    params.put("utid", circleDynamicDetailActivity.serviceDataBean.getUtid());
                    params.put("dynamicid", staDetailParam.dynamicId);
                    params.put("memberid", userInfoVo.uid);
                    params.put("uuid", userInfoVo.uuid);
                    params.put("push_uuid", commentVo.getUuid());
                    params.put("push_memberid", commentVo.getMemberid());
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
                    params.put("cid", commentVo.getCommentid());
                    params.put("reply_memberid", commentVo.getMemberid());
                    params.put("reply_uuid", commentVo.getUuid());
                    params.put("reply_nickname", commentVo.getNickname());
                    mViewModel.replayComment(params);
                }


            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mBinding.get().recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        staDetailParam = (StaDetailParam) getArguments().getSerializable("staDetailParam");
        title = getArguments().getString("title");
        super.onActivityCreated(savedInstanceState);
//        switch (staDetailParam.type) {
//            case "car":
//            case "time":
//            case "datetime":
//            case "product":
//                mBinding.get().circleCommentWj.setVisibility(View.VISIBLE);
//                mBinding.get().circleCommentCommon.setVisibility(View.GONE);
//                break;
//        }
        mBinding.get().circleCommentWj.setVisibility(View.VISIBLE);
        mBinding.get().circleCommentCommon.setVisibility(View.GONE);
        mViewModel.initStaParam(staDetailParam);
        mBinding.get().setViewmodel(mViewModel);
        //commentAdd
        disposable = RxBus.getDefault().toObservable(RxEvent.class).subscribe(rxEvent -> {
            if (rxEvent.getT().equals("commentAdd")) {
                CommentVo commentVo = (CommentVo) rxEvent.getR();
                commentVo.isDo = 0;
                mViewModel.commentItems.add(commentVo);
            }
            //|| rxEvent.getT().equals("login_state_change")
            if (rxEvent.getT().equals("comment_refresh")) {
                mViewModel.mPage = 1;
                mViewModel.commentItems.clear();
            }
        });
        if (!TextUtils.isEmpty(title)) {
            mBinding.get().tvTitle.setText(title);
        }
    }

    @Override
    public void OnRefresh(SmartRefreshLayout smartRefreshLayout) {
        super.OnRefresh(smartRefreshLayout);
    }

    @Override
    public void OnLoadMore(SmartRefreshLayout smartRefreshLayout) {
        super.OnLoadMore(smartRefreshLayout);
        this.smartRefreshLayout = smartRefreshLayout;
        mViewModel.getData();
    }

    @Override
    public void initImmersionBar() {

    }


    @Override
    public void onVisible() {
        super.onVisible();
        if (mViewModel.mPage == 1 && mViewModel.commentItems.size() == 0) {
            mViewModel.getData();
        }
    }

    // 新增评论
    public void addComment(CommentVo commentVo) {
        commentVo.isDo = 0;
        mViewModel.commentItems.add(commentVo);
        smartRefreshLayout.postDelayed(() -> {
            NestedScrollView nestedScrollView = (NestedScrollView) smartRefreshLayout.getChildAt(0);
            nestedScrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
        }, 500);
    }


    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 212:
                mBinding.get().empty.hide();
                if (smartRefreshLayout != null) {
                    smartRefreshLayout.finishLoadMore();
                }
                if (viewEventResouce.data == null || mViewModel.commentItems.size() < 19) {
                    if (smartRefreshLayout != null && mViewModel.mPage == 1) {
                        smartRefreshLayout.setEnableLoadMore(false);
                        smartRefreshLayout.setNoMoreData(true);
                    }
                } else {
                    if (smartRefreshLayout != null) {
                        smartRefreshLayout.setEnableLoadMore(true);
                    }
                }
                break;
            case 211:
                commentVo = (CommentVo) viewEventResouce.data;
                dialogFragment.setText(replay, "回复：" + commentVo.getNickname());
                dialogFragment.show(getChildFragmentManager(), "CommentReplayFragment");
                break;

            case 213: // 回复成功 -- 刷新列表
                KeyboardUtils.hideSoftInput(getActivity());
                UserInfoVo userInfoVo = CacheUtils.getUser();
                CommentVo.Reply reply = new CommentVo.Reply();
                reply.setCommentid(((CommentRstVo) viewEventResouce.data).commentid);
                reply.setContent(replay);
                reply.setReply_nickname(userInfoVo.nickname);
                reply.setMemberid(userInfoVo.uid);
                reply.setUuid(userInfoVo.uuid);
                reply.setNickname(userInfoVo.nickname);
                mViewModel.updataReplayData(commentVo.getCommentid(), reply);
                replay = "";
                break;
            case 214:
                break;
            case 222:
                CircleReplyListActivity.startMe(this.getHoldingActivity(), (CommentVo) viewEventResouce.data, staDetailParam, ((CircleDynamicDetailActivity) getHoldingActivity()).serviceDataBean);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
