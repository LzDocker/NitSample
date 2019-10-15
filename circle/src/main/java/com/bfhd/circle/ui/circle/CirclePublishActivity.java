package com.bfhd.circle.ui.circle;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseActivity;
import com.bfhd.circle.databinding.CircleActivityCircleCreateBinding;
import com.bfhd.circle.ui.circle.circlepublish.CirclePubActiveFragment;
import com.bfhd.circle.ui.circle.circlepublish.CirclePubNewsFragment;
import com.bfhd.circle.ui.circle.circlepublish.CirclePubRequestionFragment;
import com.bfhd.circle.vm.CircleViewModel;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.common.common.router.AppRouter;
import javax.inject.Inject;

/*
 * 发布 编辑
 * */
@Route(path = AppRouter.CIRCLE_CREATE)
public class CirclePublishActivity extends HivsBaseActivity<CircleViewModel, CircleActivityCircleCreateBinding> {


    public static final int PUBLISH_TYPE_NEWS = 101;    // 新闻类型
    public static final int PUBLISH_TYPE_ACTIVE = 102;    // 动态类型
    public static final int PUBLISH_TYPE_QREQUESTION = 103;    // 问答类型

    private int mPubLishType = PUBLISH_TYPE_NEWS;

    public StaCirParam mStartParam; // 详情发布点过来的 或者 编辑动态点过来的

    private int mEditType = 1; // 默认发布


    /*
     * type  发布的类型
     *
     * mStartParam 包含圈子信息的数据
     *
     *editType 1 发布  2 编辑
     *
     * */
    public static void startMe(Context context, int type, StaCirParam mStartParam, int editType) {
        Intent intent = new Intent(context, CirclePublishActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mStartParam", mStartParam);
        bundle.putSerializable("TYPE", type);
        bundle.putSerializable("editType", editType);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.circle_activity_circle_create;
    }


    @Override
    public CircleViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(CircleViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mPubLishType = (int) getIntent().getExtras().get("TYPE");
        mStartParam = (StaCirParam) getIntent().getSerializableExtra("mStartParam");
        mEditType = getIntent().getIntExtra("editType", 1);
        super.onCreate(savedInstanceState);
        mBinding.setViewmodel(mViewModel);
    }

    @Override
    public void initView() {
        String rightstr = "发布";
        switch (mPubLishType) {

            case PUBLISH_TYPE_NEWS:
                if (mEditType == 1) {
                    mToolbar.setTitle("发布新闻");
                } else {
                    mToolbar.setTitle("编辑新闻");
                }
                rightstr = "发布";
                FragmentUtils.add(getSupportFragmentManager(), CirclePubNewsFragment.newInstance(), R.id.circle_publish_frame);
                break;
            case PUBLISH_TYPE_ACTIVE:

                if (mEditType == 1) {
                    mToolbar.setTitle("发布动态");
                } else {
                    mToolbar.setTitle("编辑动态");
                }
                rightstr = "确定";
                FragmentUtils.add(getSupportFragmentManager(), CirclePubActiveFragment.newInstance(), R.id.circle_publish_frame);
                break;

            case PUBLISH_TYPE_QREQUESTION:
                rightstr = "确定";
                if (mEditType == 1) {
                    mToolbar.setTitle("发布问答");
                } else {
                    mToolbar.setTitle("编辑问答");
                }
                FragmentUtils.add(getSupportFragmentManager(), CirclePubRequestionFragment.newInstance(), R.id.circle_publish_frame);
                break;
        }
        mToolbar.setTvRight(rightstr, v -> {
            publish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (mPubLishType) {
            case PUBLISH_TYPE_NEWS:
                FragmentUtils.findFragment(getSupportFragmentManager(), CirclePubNewsFragment.class).onActivityResult(requestCode, resultCode, data);
                break;
            case PUBLISH_TYPE_ACTIVE:
                FragmentUtils.findFragment(getSupportFragmentManager(), CirclePubActiveFragment.class).onActivityResult(requestCode, resultCode, data);

                break;
            case PUBLISH_TYPE_QREQUESTION:
                FragmentUtils.findFragment(getSupportFragmentManager(), CirclePubRequestionFragment.class).onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    /*
     * 给tab fragment 使用
     * */
    public StaCirParam getmStartParam() {
        return mStartParam;
    }

    public int getmEditType() {
        return mEditType;
    }


    // 发布
    private void publish() {

        switch (mPubLishType) {
            case PUBLISH_TYPE_NEWS:
                publishNews();
                break;
            case PUBLISH_TYPE_ACTIVE:
                publishActive();
                break;
            case PUBLISH_TYPE_QREQUESTION:
                publishRequestion();
                break;
        }

    }

    // 新闻
    private void publishNews() {
        ((CirclePubNewsFragment) FragmentUtils.findFragment(getSupportFragmentManager(), CirclePubNewsFragment.class)).publish();
    }

    // 动态
    private void publishActive() {
        ((CirclePubActiveFragment) FragmentUtils.findFragment(getSupportFragmentManager(), CirclePubActiveFragment.class)).publish();
    }

    // 问答
    private void publishRequestion() {
        ((CirclePubRequestionFragment) FragmentUtils.findFragment(getSupportFragmentManager(), CirclePubRequestionFragment.class)).publish();
    }

}
