package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleReplyListBinding;
import com.docker.cirlev2.databinding.Circlev2ItemReplyBinding;
import com.docker.cirlev2.vm.CircleCommentListViewModel;
import com.docker.cirlev2.vo.entity.CommentVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.cirlev2.vo.param.StaDetailParam;
import com.docker.common.BR;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.utils.DisplayUtil;
import com.docker.common.common.utils.SoftKeyBroadManager;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnLoadMoreListener;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/*
 *  回复列表
 * */
@Route(path = AppRouter.CIRCLE_comment_v2_reply)
public class CircleReplyListActivity extends NitCommonActivity<CircleCommentListViewModel, Circlev2ActivityCircleReplyListBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    CommentVo commentVo;
    //    NitAbsSampleAdapter adapter;
    private String replay;
    private ServiceDataBean serviceDataBean;
    private RelativeLayout relativeLayout;

    public static void startMe(Context context, CommentVo commentVo, ServiceDataBean serviceDataBean) {
        Intent intent = new Intent(context, CircleReplyListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("commentVo", commentVo);
        bundle.putSerializable("serviceDataBean", serviceDataBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_circle_reply_list;
    }

    @Override
    public CircleCommentListViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleCommentListViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoftKeyBroadManager softKeyBroadManager = new SoftKeyBroadManager(mBinding.root);
        softKeyBroadManager.addSoftKeyboardStateListener(softKeyboardStateListener);
        commentVo = (CommentVo) getIntent().getSerializableExtra("commentVo");
        serviceDataBean = (ServiceDataBean) getIntent().getSerializableExtra("serviceDataBean");
        mBinding.setViewmodel(mViewModel);
        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.externs.put("serverdata", serviceDataBean);
        commonListOptions.ReqParam.put("commentid", commentVo.getCommentid());
        mViewModel.initParam(commonListOptions);
        mViewModel.setTopCommon(commentVo);
        mBinding.setItem(commentVo);
        mViewModel.loadData();


//        mToolbar.setTitle(commentVo.getReplyNum() + "条回复");

//        mViewModel.replatLv.observe(this, new Observer<List<CommentVo>>() {
//            @Override
//            public void onChanged(@Nullable List<CommentVo> commentVos) {
//                if (mViewModel.mPage == 1) {
//                    adapter.getmObjects().clear();
//                    adapter.getmObjects().add(0, commentVo);
//                }
//                adapter.getmObjects().addAll(commentVos);
//                adapter.notifyDataSetChanged();
//            }
//        });

    }

    @Override
    public void initView() {
        mToolbar.setTitle("回复详情");
        mBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.mPage = 1;
                mViewModel.loadData();
            }
        });
        mBinding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.loadData();
            }
        });
//        adapter = new NitAbsSampleAdapter(R.layout.circlev2_item_reply, BR.item) {
//            @Override
//            public void onRealviewBind(ViewHolder holder, int position) {
//                Circlev2ItemReplyBinding itemReplyBinding = (Circlev2ItemReplyBinding) holder.getBinding();
//                itemReplyBinding.setViewmodel(mViewModel);
//            }
//        };
        mBinding.recycle.setLayoutManager(new LinearLayoutManager(this));
//        mBinding.recycle.setAdapter(adapter);
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
            params.put("dynamicid", serviceDataBean.getDynamicid());
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
            mViewModel.replayComment(commentVo, params);
        });
    }

    @Override
    public void initObserver() {
        mViewModel.mServerLiveData.observe(this, o -> {
        });
    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    SoftKeyBroadManager.SoftKeyboardStateListener softKeyboardStateListener = new SoftKeyBroadManager.SoftKeyboardStateListener() {

        @Override
        public void onSoftKeyboardOpened(int keyboardHeightInPx) {
            mBinding.rvBottom.requestLayout();
        }

        @Override
        public void onSoftKeyboardClosed() {
            mBinding.rvBottom.requestLayout();
        }
    };
}
