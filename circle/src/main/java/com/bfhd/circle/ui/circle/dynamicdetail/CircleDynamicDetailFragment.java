package com.bfhd.circle.ui.circle.dynamicdetail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.bfhd.circle.BR;
import com.bfhd.circle.R;
import com.bfhd.circle.base.CommentDialogFragment;
import com.bfhd.circle.base.CommonFragment;
import com.bfhd.circle.base.ViewEventResouce;
import com.bfhd.circle.databinding.CircleFragmentDetailDynamicBinding;
import com.bfhd.circle.ui.active.CircleCommentFragment;
import com.bfhd.circle.ui.circle.CircleDetailActivity;
import com.bfhd.circle.ui.circle.CircleReplayQuestionActivity;
import com.bfhd.circle.vm.CircleDynamicViewModel;
import com.bfhd.circle.vo.CommentRstVo;
import com.bfhd.circle.vo.CommentVo;
import com.bfhd.circle.vo.ServiceDataBean;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.bfhd.circle.vo.bean.StaDetailParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.dcbfhd.utilcode.utils.KeyboardUtils;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.common.common.vo.UserInfoVo;
import com.docker.core.util.LayoutManager;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/*
 * 动态 问答 类型的动态
 * */
public class CircleDynamicDetailFragment extends CommonFragment<CircleDynamicViewModel, CircleFragmentDetailDynamicBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_fragment_detail_dynamic;
    }

    private StaDetailParam mStaParam;
    private CommentDialogFragment dialogFragment;
    private String commentContent;
    private ServiceDataBean serviceDataBean;
    private CircleCommentFragment commentFragment;
    private Disposable disposable;


    public static CircleDynamicDetailFragment newInstance(StaDetailParam mStaParam) {
        CircleDynamicDetailFragment detailFragment = new CircleDynamicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStaParam", mStaParam);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }


    public static CircleDynamicDetailFragment newInstance(StaDetailParam mStaParam, ServiceDataBean serviceDataBean) {
        CircleDynamicDetailFragment detailFragment = new CircleDynamicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStaParam", mStaParam);
        bundle.putSerializable("serviceDataBean", serviceDataBean);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    protected CircleDynamicViewModel getViewModel() {

        return ViewModelProviders.of(this, factory).get(CircleDynamicViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mStaParam = (StaDetailParam) getArguments().getSerializable("mStaParam");
        serviceDataBean = (ServiceDataBean) getArguments().getSerializable("serviceDataBean");
        super.onActivityCreated(savedInstanceState);

        if (mStaParam.speical == 1) {
            mBinding.get().llSourceCoutainer.setVisibility(View.VISIBLE);
        } else {
            mBinding.get().llSourceCoutainer.setVisibility(View.GONE);
        }
        mBinding.get().setVariable(BR.viewmodel, mViewModel);
        commentFragment = CircleCommentFragment.newInstance(mStaParam);
        commentFragment.smartRefreshLayout = mBinding.get().refresh;
        FragmentUtils.add(getChildFragmentManager(), commentFragment, R.id.pro_frame_comment);
        mBinding.get().refresh.setOnLoadMoreListener(refreshLayout -> {
            commentFragment.OnLoadMore(mBinding.get().refresh);
        });

        if (mStaParam.uiType == 2) { // 问答
            mBinding.get().tvComment.setText("回答");
            mBinding.get().recycle.setLayoutManager(LayoutManager.grid(3).create(mBinding.get().recycle));
        }
//        if (mStaParam.isRecomend) {
//            StaDynaVo staDynaVo2 = new StaDynaVo();
//            staDynaVo2.UiType = 3;
//            staDynaVo2.ReqType = 1;
//            UserInfoVo userInfoVo2 = CacheUtils.getUser();
//            staDynaVo2.ReqParam.put("memberid", userInfoVo2.uid);
//            staDynaVo2.ReqParam.put("uuid", userInfoVo2.uuid);
//            FragmentUtils.add(getChildFragmentManager(), DynamicFragment.newInstance(staDynaVo2, null), R.id.pro_frame_recommend);
//            staDynaVo2.ReqParam.put("isrecommend", "1");
//            staDynaVo2.ReqParam.put("t", mStaParam.type);
//            staDynaVo2.status.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
//                @Override
//                public void onPropertyChanged(Observable sender, int propertyId) {
//                    if (!staDynaVo2.status.get()) {
//                        mBinding.get().tvRecommend.setVisibility(View.GONE);
//                    } else {
//                        mBinding.get().tvRecommend.setVisibility(View.VISIBLE);
//                    }
//                }
//            });
//        }

        mBinding.get().setItem(serviceDataBean);
        mViewModel.serviceDataBean = serviceDataBean;
        if ("project".equals(serviceDataBean.getType())) {
            if (!serviceDataBean.getMemberid().equals(CacheUtils.getUser().uid)) {
                mBinding.get().llFootCoutainer.setVisibility(View.VISIBLE);
                mBinding.get().cirlceGoodsFoot.setVisibility(View.VISIBLE); // 不是自己发布的
            }
        } else {
            mBinding.get().llFootCoutainer.setVisibility(View.VISIBLE);
            mBinding.get().circleCommonBar.setVisibility(View.VISIBLE);
        }
        UserInfoVo userInfoVo = CacheUtils.getUser();
        if (serviceDataBean.getUuid().equals(userInfoVo.uuid) && serviceDataBean.getMemberid().equals(userInfoVo.uid)) {
            mBinding.get().tvAttention.setVisibility(View.GONE);
        } else {
            mBinding.get().tvAttention.setVisibility(View.VISIBLE);
        }


        switch (mStaParam.type) {
            case "shared": // 信息共享
                mBinding.get().llSourceCoutainer.setVisibility(View.VISIBLE);
                mBinding.get().llParseCoutainer.setVisibility(View.VISIBLE);
                mBinding.get().linFromCircle.setVisibility(View.GONE);
                break;
            case "answer": // 问答
//                mBinding.get().llSourceCoutainer.setVisibility(View.GONE);
                mBinding.get().llSourceCoutainer.setVisibility(View.VISIBLE);
                mBinding.get().llParseCoutainer.setVisibility(View.VISIBLE);
                mBinding.get().linFromCircle.setVisibility(View.GONE);
                mBinding.get().rlIconTitle.setVisibility(View.GONE);
                mBinding.get().rlAnrwerFoorer.setVisibility(View.VISIBLE);
                mBinding.get().linFromCircleAnswer.setVisibility(View.VISIBLE);
                break;
            case "dynamic":
                mBinding.get().tvCity.setText("发布");
                mBinding.get().tvCityname.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void initImmersionBar() {

    }

    @Override
    protected void initView(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                getActivity().onTouchEvent(motionEvent);
                return false;
            }
        });
        dialogFragment = new CommentDialogFragment();
        dialogFragment.setDataCallback(new CommentDialogFragment.DialogFragmentDataCallback() {
            @Override
            public void setCommentText(String commentTextTemp) {
                KeyboardUtils.hideSoftInput(CircleDynamicDetailFragment.this.getHoldingActivity());
            }

            @Override
            public void sendComment(String commentTextTemp) {
                commentContent = commentTextTemp;
                KeyboardUtils.hideSoftInput(CircleDynamicDetailFragment.this.getHoldingActivity());
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
                params.put("content", commentTextTemp);
                params.put("cid", "0");
                params.put("reply_memberid", "");
                params.put("reply_uuid", "");
                params.put("reply_nickname", "");
                mViewModel.commentDynamic(params);
            }
        });


        mBinding.get().linFromCircle.setOnClickListener(v -> {
            if (serviceDataBean == null) {
                return;
            }
            CircleDetailActivity.startMe(CircleDynamicDetailFragment.this.getHoldingActivity(), new StaCirParam(serviceDataBean.getCircleid(), serviceDataBean.getUtid(), ""));
        });
    }

    @Override
    public void onVisible() {
        super.onVisible();

        /*
        * "dynamicid":"1",
    "memberid" :"",
    "uuid": "1",
        * */
        if (mViewModel.items.size() == 0) {
            UserInfoVo userInfoVo = CacheUtils.getUser();
            HashMap<String, String> param = new HashMap();
            param.put("dynamicid", mStaParam.dynamicId);
            if (!"-1".equals(userInfoVo.memberid)) {
                param.put("memberid", userInfoVo.uid);
                param.put("uuid", userInfoVo.uuid);
            }
            mViewModel.fechDynamicDetail(param);
        }
    }

    @Override
    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {
        super.OnVmEnentListner(viewEventResouce);

        switch (viewEventResouce.eventType) {
            case 204: // 大图预览
//                PictureSelector.create(this).externalPicturePreview(Integer.parseInt(viewEventResouce.message), (List<LocalMedia>) viewEventResouce.data);
                PictureSelector
                        .create(this)
                        .themeStyle(R.style.picture_default_style)
                        .openExternalPreview(Integer.parseInt(viewEventResouce.message), (List<LocalMedia>) viewEventResouce.data);
                break;
            case 210: // 显示评论对话框
                serviceDataBean = (ServiceDataBean) viewEventResouce.data;
                if (mStaParam.uiType == 2) {
                    CircleReplayQuestionActivity.startMe(this.getHoldingActivity(), serviceDataBean);
                } else {
                    dialogFragment.setText(commentContent, "发表评论..");
                    dialogFragment.show(getChildFragmentManager(), "CommentDialogFragment");
                }
                break;
            case 211:
                KeyboardUtils.hideSoftInput(CircleDynamicDetailFragment.this.getHoldingActivity());
                CommentVo commentVo = new CommentVo();
                commentVo.setCommentid(((CommentRstVo) viewEventResouce.data).commentid);
                UserInfoVo userInfoVo = CacheUtils.getUser();
                commentVo.setMemberid(userInfoVo.uid);
                commentVo.setUuid(userInfoVo.uuid);
                commentVo.setNickname(TextUtils.isEmpty(userInfoVo.nickname) ? "匿名" : userInfoVo.nickname);
                commentVo.setAvatar(userInfoVo.avatar);
                commentVo.setContent(commentContent);
                commentVo.setPraiseNum("0");
                commentVo.setInputtime(System.currentTimeMillis() + "");
                commentFragment.addComment(commentVo);
                commentContent = "";
                break;
            case 10099:
//                if (viewEventResouce.data != null) {
//                    SessionHelper.startP2PSession(CircleDynamicDetailFragment.this.getHoldingActivity(), ((ServiceDataBean) viewEventResouce.data).getUuid());
//                }
                break;
        }
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);
        this.serviceDataBean = (ServiceDataBean) obj;
        mBinding.get().setItem(serviceDataBean);
        mViewModel.serviceDataBean = serviceDataBean;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}

