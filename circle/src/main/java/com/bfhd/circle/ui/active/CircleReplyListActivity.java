package com.bfhd.circle.ui.active;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.bfhd.circle.R;
import com.bfhd.circle.BR;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.base.adapter.HivsAbsSampleAdapter;
import com.bfhd.circle.databinding.CircleActivityCircleReplyListBinding;
import com.bfhd.circle.databinding.CircleItemReplyBinding;
import com.bfhd.circle.vm.CircleCommentViewModel;
import com.bfhd.circle.vo.CommentVo;
import com.bfhd.circle.vo.ServiceDataBean;
import com.bfhd.circle.vo.bean.StaDetailParam;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnLoadMoreListener;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;

import java.util.Collection;
import java.util.HashMap;

import javax.inject.Inject;

/*
 *  回复列表
 * */
public class CircleReplyListActivity extends HivsBaseActivity<CircleCommentViewModel, CircleActivityCircleReplyListBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    CommentVo commentVo;
    HivsAbsSampleAdapter adapter;
    StaDetailParam staDetailParam;
    private String replay;
    private ServiceDataBean serviceDataBean;

    public static void startMe(Context context, CommentVo commentVo, StaDetailParam staDetailParam, ServiceDataBean serviceDataBean) {
        Intent intent = new Intent(context, CircleReplyListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("commentVo", commentVo);
        bundle.putSerializable("staDetailParam", staDetailParam);
        bundle.putSerializable("serviceDataBean", serviceDataBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_reply_list;
    }

    @Override
    public CircleCommentViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleCommentViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentVo = (CommentVo) getIntent().getSerializableExtra("commentVo");
        staDetailParam = (StaDetailParam) getIntent().getSerializableExtra("staDetailParam");
        serviceDataBean = (ServiceDataBean) getIntent().getSerializableExtra("serviceDataBean");
        mBinding.setViewmodel(mViewModel);
        mViewModel.initStaParam(staDetailParam);
        mViewModel.setTopCommon(commentVo);
        mBinding.setItem(commentVo);
        mViewModel.replyList(commentVo.getCommentid());
//        mToolbar.setTitle(commentVo.getReplyNum() + "条回复");

    }

    @Override
    public void initView() {
        mToolbar.setTitle("回复详情");

        mBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.mPage = 1;
                mViewModel.replyList(commentVo.getCommentid());
            }
        });
        mBinding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.replyList(commentVo.getCommentid());
            }
        });
        adapter = new HivsAbsSampleAdapter(R.layout.circle_item_reply, BR.item) {
            @Override
            public void onRealviewBind(ViewHolder holder, int position) {
                CircleItemReplyBinding itemReplyBinding = (CircleItemReplyBinding) holder.getBinding();
                itemReplyBinding.setViewmodel(mViewModel);
            }
        };
        mBinding.recycle.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recycle.setAdapter(adapter);
//        adapter.setOnItemClickListener(new SimpleCommonRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//            }
//        });

        mBinding.btnCommit.setOnClickListener(v -> {
            replay = mBinding.editInput.getText().toString().trim();
            if (TextUtils.isEmpty(replay)) {
                return;
            }
            KeyboardUtils.hideSoftInput(CircleReplyListActivity.this);
            HashMap<String, String> params = new HashMap<>();
            UserInfoVo userInfoVo = CacheUtils.getUser();
            params.put("circleid", serviceDataBean.getCircleid());
            params.put("utid", serviceDataBean.getUtid());
            params.put("dynamicid", staDetailParam.dynamicId);
            params.put("memberid", userInfoVo.uid);
            params.put("uuid", userInfoVo.uuid);
            params.put("push_uuid", commentVo.getUuid());
            params.put("push_memberid", commentVo.getMemberid());
            if (TextUtils.isEmpty(userInfoVo.avatar)) {
                userInfoVo.avatar = "/var/upload/image/section/logo3.png";
            }
            params.put("avatar", userInfoVo.avatar);
            if (TextUtils.isEmpty(userInfoVo.nickname)) {
                params.put("nickname", "匿名");
            } else {
                params.put("nickname", userInfoVo.nickname);
            }
            params.put("content", replay);
            params.put("cid", commentVo.getCommentid());
            params.put("reply_memberid", commentVo.getMemberid());
            params.put("reply_uuid", commentVo.getUuid());
            params.put("reply_nickname", commentVo.getNickname());
            mViewModel.replayComment(params);
        });
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);
        switch (viewEventResouce.eventType) {
            case 223:

                mBinding.refresh.finishLoadMore();
                mBinding.refresh.finishRefresh();
                if (viewEventResouce.data != null) {
                    if (mViewModel.mPage == 1) {
                        adapter.getmObjects().clear();
                        adapter.getmObjects().add(0, commentVo);
                    }
                    adapter.getmObjects().addAll((Collection) viewEventResouce.data);
                    adapter.notifyDataSetChanged();
                }
                break;
            case 211:
                mBinding.editInput.setHint("回复：" + commentVo.getNickname());
                KeyboardUtils.showSoftInput(this);
                break;
            case 213:
                replay = "";
                mViewModel.mPage = 1;
                mViewModel.replyList(commentVo.getCommentid());
                RxBus.getDefault().post(new RxEvent<>("comment_refresh", ""));
                break;
            case 214:
                for (int i = 0; i < adapter.getmObjects().size(); i++) {
                    if (((CommentVo) adapter.getmObjects().get(i)).getCommentid().equals(((CommentVo) viewEventResouce.data).getCommentid())) {
                        adapter.getmObjects().remove(((CommentVo) adapter.getmObjects().get(i)));
                        adapter.notifyDataSetChanged();
                        RxBus.getDefault().post(new RxEvent<>("comment_refresh", ""));
                        break;
                    }
                }
                break;
        }
    }
}
