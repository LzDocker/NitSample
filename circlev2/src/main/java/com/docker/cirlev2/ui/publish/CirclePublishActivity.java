package com.docker.cirlev2.ui.publish;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.FragmentUtils;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2SampleActivityBinding;
import com.docker.cirlev2.vm.PublishViewModel;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.param.StaCirParam;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.config.Constant;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.container.NitCommonContainerFragment;
import com.gyf.immersionbar.ImmersionBar;

import java.util.HashMap;

@Route(path = AppRouter.CIRCLE_PUBLISH_v2_INDEX)
public class CirclePublishActivity extends NitCommonActivity<SampleListViewModel, Circlev2SampleActivityBinding> {

    public static final int PUBLISH_TYPE_NEWS = 101;    // 新闻类型
    public static final int PUBLISH_TYPE_ACTIVE = 102;    // 动态类型
    public static final int PUBLISH_TYPE_QREQUESTION = 103;    // 问答类型

    public String isShowBot = "2"; // 默认显示

    public static

    /*
    *  .withInt("editType", editType)
                    .withInt("type", type)
                    .withSerializable("mStartParam", staCirParam)
    * */

    @Autowired
    int editType;

    @Autowired
    int type;

    public String getIsShowBot() {
        if (extens != null && !TextUtils.isEmpty(extens.get("isShowBot"))) {
            isShowBot = extens.get("isShowBot");
        }
        return isShowBot;
    }

    @Autowired
    String pubRoterPath;


    @Autowired
    String title;

    // 扩展字段
    public HashMap<String, String> extens = new HashMap<>();


    private StaCirParam staCirParam;

    private Fragment pubfragment;

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_sample_activity;
    }

    @Override
    public SampleListViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }


    public HashMap<String, String> getExtens() {
        return extens;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        staCirParam = (StaCirParam) getIntent().getSerializableExtra("mStartParam");
        extens = (HashMap<String, String>) getIntent().getSerializableExtra("extens");
        super.onCreate(savedInstanceState);
        String rightstr = "发布";

        if (!TextUtils.isEmpty(pubRoterPath)) {
            pubfragment = (Fragment) ARouter.getInstance().build(pubRoterPath).withBundle("bundle", getIntent().getBundleExtra("bundle")).navigation();
            if (editType == 1) {
                mToolbar.setTitle("发布" + title);
            } else {
                mToolbar.setTitle("编辑" + title);
            }
            FragmentUtils.add(getSupportFragmentManager(), pubfragment, R.id.circlev2_frame);

        } else {
            switch (type) {
                case PUBLISH_TYPE_NEWS:
                    if (editType == 1) {
                        mToolbar.setTitle("发布新闻");
                    } else {
                        mToolbar.setTitle("编辑新闻");
                    }
                    rightstr = "发布";
                    FragmentUtils.add(getSupportFragmentManager(), CirclePubNewsFragment.newInstance(), R.id.circlev2_frame);
                    break;
                case PUBLISH_TYPE_ACTIVE:

                    if (editType == 1) {
                        mToolbar.setTitle("发布动态");
                    } else {
                        mToolbar.setTitle("编辑动态");
                    }
                    rightstr = "确定";
                    FragmentUtils.add(getSupportFragmentManager(), CirclePubActiveFragment.newInstance(), R.id.circlev2_frame);
                    break;

                case PUBLISH_TYPE_QREQUESTION:
                    rightstr = "确定";
                    if (editType == 1) {
                        mToolbar.setTitle("发布问答");
                    } else {
                        mToolbar.setTitle("编辑问答");
                    }
                    FragmentUtils.add(getSupportFragmentManager(), CirclePubRequestionFragment.newInstance(), R.id.circlev2_frame);
                    break;
            }

            mToolbar.setTvRight(rightstr, v -> {
                publish();
            });
        }
    }

    @Override
    public void initView() {


    }

    @Override
    public void initObserver() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return (NitContainerCommand) () -> {
            return (PublishViewModel.class);
        };
    }

    private void publish() {

        switch (type) {
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

    /*
     * 给tab fragment 使用
     * */
    public StaCirParam getmStartParam() {
        return staCirParam;
    }

    public int getmEditType() {
        return editType;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (pubfragment != null) {
            pubfragment.onActivityResult(requestCode, resultCode, data);
        } else {
            switch (type) {
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

    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .navigationBarColor("#ffffff")
                .statusBarDarkFont(true)
                .statusBarColor(com.docker.core.R.color.colorPrimary).init();
    }
}
