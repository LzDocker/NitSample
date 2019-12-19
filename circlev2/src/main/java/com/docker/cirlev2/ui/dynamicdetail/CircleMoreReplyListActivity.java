package com.docker.cirlev2.ui.dynamicdetail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleMoreReplyListBinding;
import com.docker.cirlev2.databinding.Circlev2ActivityCircleReplyListBinding;
import com.docker.cirlev2.vm.CircleCommentListViewModel;
import com.docker.cirlev2.vo.entity.CommentVo;
import com.docker.cirlev2.vo.entity.ServiceDataBean;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.docker.common.common.widget.refresh.api.RefreshLayout;
import com.docker.common.common.widget.refresh.listener.OnLoadMoreListener;
import com.docker.common.common.widget.refresh.listener.OnRefreshListener;

import java.util.HashMap;

import javax.inject.Inject;

/*
 *  评论列表
 * */
@Route(path = AppRouter.CIRCLE_more_comment_v2_reply)
public class CircleMoreReplyListActivity extends NitCommonActivity<CircleCommentListViewModel, Circlev2ActivityCircleMoreReplyListBinding> {

    private String replay;
    private ServiceDataBean serviceDataBean;

    public static void startMe(Context context,  ServiceDataBean serviceDataBean) {
        Intent intent = new Intent(context, CircleMoreReplyListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("serviceDataBean", serviceDataBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_activity_circle_more_reply_list;
    }

    @Override
    public CircleCommentListViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleCommentListViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceDataBean = (ServiceDataBean) getIntent().getSerializableExtra("serviceDataBean");
        mBinding.setViewmodel(mViewModel);
//        CommonListOptions commonListReq = new CommonListOptions();
//        commonListReq.ReqParam.put("dynamicid", serviceDataBean.getDynamicid());
//        commonListReq.ReqParam.put("page", "1");
//        commonListReq.externs.put("serverdata", serviceDataBean);
//        mViewModel.initParam(commonListReq);
//        mViewModel.loadData();


        CommonListOptions commonListOptions = new CommonListOptions();
        commonListOptions.refreshState = Constant.KEY_REFRESH_OWNER;
        commonListOptions.isActParent = true;
        commonListOptions.RvUi = Constant.KEY_RVUI_LINER;
        commonListOptions.falg = 1002;
        commonListOptions.ReqParam.put("dynamicid", serviceDataBean.getDynamicid());
        NitBaseProviderCard.providerCoutainerForFrame(getSupportFragmentManager(),R.id.frame_comment,commonListOptions);
//        FragmentUtils.add(CircleMoreReplyListActivity.this.getSupportFragmentManager(), DynamicBotContentFragment.getInstance(serviceDataBean), R.id.frame_comment);


    }

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        NitDelegetCommand nitDelegetCommand = new NitDelegetCommand() {
            @Override
            public Class providerOuterVm() {
                return CircleCommentListViewModel.class;
            }

            @Override
            public void next(NitCommonListVm commonListVm, NitCommonFragment nitCommonFragment) {

            }
        };
        return nitDelegetCommand;
    }

    @Override
    public void initView() {
        mToolbar.setTitle("评论列表");


        mBinding.btnCommit.setOnClickListener(v -> {
            replay = mBinding.editInput.getText().toString().trim();
            if (TextUtils.isEmpty(replay)) {
                return;
            }
            KeyboardUtils.hideSoftInput(CircleMoreReplyListActivity.this);
            HashMap<String, String> params = new HashMap<>();
            UserInfoVo userInfoVo = CacheUtils.getUser();
            params.put("circleid", serviceDataBean.getCircleid());
            params.put("utid", serviceDataBean.getUtid());
            params.put("dynamicid", serviceDataBean.getDynamicid());
            params.put("memberid", userInfoVo.uid);
            params.put("uuid", userInfoVo.uuid);
//            params.put("push_uuid", commentVo.getUuid());
//            params.put("push_memberid", commentVo.getMemberid());
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
//            params.put("cid", commentVo.getCommentid());
//            params.put("reply_memberid", commentVo.getMemberid());
//            params.put("reply_uuid", commentVo.getUuid());
//            params.put("reply_nickname", commentVo.getNickname());
//            mViewModel.replayComment(commentVo, params);
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
}
