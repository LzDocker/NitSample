package com.bfhd.circle.ui.active;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleActivityCircleCommentListBinding;
import com.bfhd.circle.vm.CircleCommentViewModel;
import com.bfhd.circle.vo.CommentRstVo;
import com.bfhd.circle.vo.CommentVo;
import com.bfhd.circle.vo.ServiceDataBean;
import com.bfhd.circle.vo.bean.StaDetailParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.utils.rxbus.RxBus;
import com.docker.common.common.utils.rxbus.RxEvent;
import com.docker.common.common.vo.UserInfoVo;
import java.util.HashMap;
import javax.inject.Inject;

/*
 *  评论列表
 * */
@Route(path = AppRouter.COMMENTLIST)
public class CircleCommentListActivity extends HivsBaseActivity<CircleCommentViewModel, CircleActivityCircleCommentListBinding> {

    @Inject
    ViewModelProvider.Factory factory;
    StaDetailParam staDetailParam;
    ServiceDataBean serviceDataBean;
    private String CommentContent;

    public static void startMe(Context context, StaDetailParam staDetailParam, ServiceDataBean serviceDataBean) {
        Intent intent = new Intent(context, CircleCommentListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("staDetailParam", staDetailParam);
        bundle.putSerializable("serviceDataBean", serviceDataBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_comment_list;
    }

    @Override
    public CircleCommentViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleCommentViewModel.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staDetailParam = (StaDetailParam) getIntent().getSerializableExtra("staDetailParam");
        serviceDataBean = (ServiceDataBean) getIntent().getSerializableExtra("serviceDataBean");
        FragmentUtils.add(getSupportFragmentManager(), CircleCommentFragment.newInstance(staDetailParam), R.id.frame);
    }

    @Override
    public void initView() {
        mToolbar.setTitle("评论详情");
        mBinding.btnCommit.setOnClickListener(v -> {
            if (serviceDataBean == null) {
                return;
            }
            if (TextUtils.isEmpty(mBinding.editInput.getText().toString().trim())) {
                return;
            }
            CommentContent = mBinding.editInput.getText().toString().trim();
            KeyboardUtils.hideSoftInput(this);
            HashMap<String, String> params = new HashMap<>();
            UserInfoVo userInfoVo = CacheUtils.getUser();
            params.put("circleid", serviceDataBean.getCircleid());
            params.put("utid", serviceDataBean.getUtid());
            params.put("dynamicid", serviceDataBean.getDynamicid());
            params.put("push_uuid", serviceDataBean.getUuid());
            params.put("push_memberid", serviceDataBean.getMemberid());
            params.put("memberid", userInfoVo.uid);
            params.put("uuid", userInfoVo.uuid);
            if (TextUtils.isEmpty(userInfoVo.avatar)) {
                userInfoVo.avatar = "/var/upload/image/section/logo3.png";
            }
            params.put("avatar", userInfoVo.avatar);
            if (TextUtils.isEmpty(userInfoVo.nickname)) {
                params.put("nickname", "匿名");
            } else {
                params.put("nickname", userInfoVo.nickname);
            }
            params.put("content", CommentContent);
            params.put("cid", "0");
            params.put("reply_memberid", "");
            params.put("reply_uuid", "");
            params.put("reply_nickname", "");
            mViewModel.commentDynamic(params);

        });

    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);

        if (viewEventResouce.eventType == 230) {
            hideSoftKeyBoard();
            if (viewEventResouce.data != null) {
                CommentVo commentVo = new CommentVo();
                commentVo.setCommentid(((CommentRstVo) viewEventResouce.data).commentid);
                UserInfoVo userInfoVo = CacheUtils.getUser();
                commentVo.setMemberid(userInfoVo.uid);
                commentVo.setUuid(userInfoVo.uuid);
                commentVo.setNickname(TextUtils.isEmpty(userInfoVo.nickname) ? "匿名" : userInfoVo.nickname);
                commentVo.setAvatar(userInfoVo.avatar);
                commentVo.setContent(CommentContent);
                commentVo.setPraiseNum("0");
                commentVo.isDo = 0;
                commentVo.setInputtime(System.currentTimeMillis() + "");
                RxBus.getDefault().post(new RxEvent<>("commentAdd", commentVo));
                CommentContent = "";
                mBinding.editInput.setText("");
            }
        }
    }
}
