package com.docker.cirlev2.ui.detail.index.type;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.docker.cirlev2.R;
import com.docker.cirlev2.ui.CircleInfoActivity;
import com.docker.cirlev2.ui.detail.CircleInviteActivity;
import com.docker.cirlev2.ui.detail.index.temp.defaults.NitDefaultCircleFragment;
import com.docker.cirlev2.ui.persion.CirclePersonListActivity;
import com.docker.cirlev2.vo.entity.CircleDetailVo;
import com.docker.cirlev2.vo.entity.CircleTitlesVo;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.cirlev2.vo.pro.AppVo;
import com.docker.common.common.adapter.NitAbsSampleAdapter;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.core.widget.BottomSheetDialog;

import java.util.HashMap;
import java.util.List;

public class CircleDetailFragment_tyz extends NitDefaultCircleFragment {

    @Override
    public NitDelegetCommand providerNitDelegetCommand(int flag) {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.get().title.setText(circleConfig.extens.get("title"));
    }

    @Override
    protected void initView(View var1) {
        super.initView(var1);
        mBinding.get().ivShare.setVisibility(View.GONE);
        mBinding.get().circlev2Edit.setVisibility(View.GONE);

    }

    @Override
    public void onCircleDetailFetched(CircleDetailVo circleDetailVo) {
        super.onCircleDetailFetched(circleDetailVo);
//        mBinding.get().circlev2IvPublish.setVisibility(View.VISIBLE);
//        if (CacheUtils.getUser() != null) {
//            if ("1".equals(circleDetailVo.getRole())) {
//                mBinding.get().circlev2IvPublish.setVisibility(View.VISIBLE);
//            } else {
//                mBinding.get().circlev2IvPublish.setVisibility(View.GONE);
//            }
//        } else {
//            mBinding.get().circlev2IvPublish.setVisibility(View.GONE);
//        }
    }


    @Override
    public void processPushSHow(CircleDetailVo circleDetailVo) {
        super.processPushSHow(circleDetailVo);

        if (CacheUtils.getUser() != null) {
            if ("1".equals(circleDetailVo.getRole())) {
                mBinding.get().circlev2IvPublish.setVisibility(View.VISIBLE);
            } else {
                mBinding.get().circlev2IvPublish.setVisibility(View.GONE);
            }
        } else {
            mBinding.get().circlev2IvPublish.setVisibility(View.GONE);
        }

    }

    @Override
    public void initAppBar() {
        mBinding.get().ivBack.setImageResource(com.docker.core.R.mipmap.ic_back);
        mBinding.get().ivMenuImg.setImageResource(R.mipmap.gray_menu);
        mBinding.get().ivMenuImg.setBackgroundDrawable(null);
        mBinding.get().ivBack.setBackgroundDrawable(null);
        mBinding.get().frameHeader.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshIng() {
        super.onRefreshIng();
    }

    @Override
    public void initRefresh() {
        mBinding.get().refresh.setEnableLoadMore(false);
        mBinding.get().refresh.setEnableRefresh(false);

    }

    @Override
    public int processRefreshState() {
        return Constant.KEY_REFRESH_OWNER;
    }

    @Override
    public void onCircleTabFetched(List<CircleTitlesVo> list) {
        super.onCircleTabFetched(list);
    }

    @Override
    public void onCircleMenuClick() {
//        super.onCircleMenuClick();
        onShareClick();

//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
//        bottomSheetDialog.setDataCallback(new String[]{"成员管理", "编辑分类"}, position -> {
//            bottomSheetDialog.dismiss();
//            switch (position) {
//                case 0: //成员管理
//                    if (mViewModel.mCircleDetailLv.getValue() != null /*&& mViewModel.detailVo.get().getRole()> 0*/) {
//                        StaCirParam mStartParam = new StaCirParam();
//                        mStartParam.setCircleid(circleid);
//                        mStartParam.setUtid(utid);
//                        mStartParam.type = Integer.parseInt(mViewModel.mCircleDetailLv.getValue().getType());
//                        CirclePersonListActivity.startMe(this.getHoldingActivity(), mStartParam, mViewModel.mCircleDetailLv.getValue());
//                    }
//                    break;
//                case 1: //编辑分类
//                    onLevel1EditClick();
//                    break;
//            }
//        });
//        bottomSheetDialog.show(this.getHoldingActivity());
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();

    }

    @Override
    public void processPro(NitAbsSampleAdapter mAdapter) {
        super.processPro(mAdapter);
        mAdapter.remove(1);
    }

    @Override
    public void processPubRouterNext(Postcard postcard) {
//        super.processPubRouterNext(postcard);
        HashMap<String, String> param = new HashMap<>();
        param.put("isShowBot", "1");
        postcard.withSerializable("extens", param).navigation();
    }
}
