package com.bfhd.evaluate.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;
import com.bfhd.circle.base.adapter.CommonpagerAdapter;
import com.bfhd.evaluate.R;
import com.bfhd.evaluate.api.EnStudyService;
import com.bfhd.evaluate.databinding.ActivityLessonAudioBinding;
import com.bfhd.evaluate.vm.EnStudyViewModel;
import com.bfhd.evaluate.vo.RadioMenuVo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chivox.AIEngine;
import com.chivox.AIEngineHelper;
import com.chivox.AIRecorder;
import com.chivox.ChivoxUtils;
import com.dcbfhd.utilcode.utils.LogUtils;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.command.NitContainerCommand;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

///评测------新概念英语课程列表
@Route(path = AppRouter.EVALUATE_DEMO)
public class AudioLessonActivity extends NitCommonActivity<EnStudyViewModel, ActivityLessonAudioBinding> {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lesson_audio;
    }

    @Override
    public EnStudyViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(EnStudyViewModel.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        isOverrideContentView = true;
        super.onCreate(savedInstanceState);
        mViewModel.getLessonBookList("44", "37c0fc402ba039c78b07065bfd4d114c");
        initChivox();

    }

    @Override
    public void initView() {

    }

    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }


    @Override
    public void initObserver() {
        mViewModel.mContainerLiveData.observe(this, object -> {
            List<RadioMenuVo> radioMenuVos = (List<RadioMenuVo>) object;
            if (radioMenuVos == null || radioMenuVos.isEmpty())
                return;
            List<Fragment> fragments = new ArrayList<>();
            String[] names = new String[radioMenuVos.size()];
            for (int i = 0; i < radioMenuVos.size(); i++) {
                RadioMenuVo radioMenuVo = radioMenuVos.get(i);
                fragments.add((Fragment) ARouter.getInstance().build(AppRouter.EVALUATE_LESSON_LIST).withString("id", radioMenuVo.getId()).navigation());
                names[i] = radioMenuVo.getName();
                if (i == 0) {
                    nowRadioMenu = radioMenuVo;
                    mBinding.lessonAudioName.setText(String.format("%s%s", nowRadioMenu.getBook_name(), nowRadioMenu.getName()));
                    mBinding.lessonAudioZj.setText(String.format("共%s节", nowRadioMenu.getNum()));
                    String bck = EnStudyService.OSS_URL + radioMenuVo.getBack_ground();
                    String thumb = EnStudyService.OSS_URL + radioMenuVo.getThumb();
                    showLinerBackground(bck, thumb);
                }
            }
            mBinding.lessonAudioViewpager.setAdapter(new CommonpagerAdapter(getSupportFragmentManager(), fragments, names));
            mBinding.lessonAudioTab.setViewPager(mBinding.lessonAudioViewpager);
            mBinding.lessonAudioTab.setCurrentTab(0);
            mBinding.lessonAudioViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                }

                @Override
                public void onPageSelected(int i) {
                    nowRadioMenu = radioMenuVos.get(i);
                    mBinding.lessonAudioName.setText(String.format("%s%s", nowRadioMenu.getBook_name(), nowRadioMenu.getName()));
                    mBinding.lessonAudioZj.setText(String.format("共%s节", nowRadioMenu.getNum()));
                    String bck = EnStudyService.OSS_URL + nowRadioMenu.getBack_ground();
                    String thumb = EnStudyService.OSS_URL + nowRadioMenu.getThumb();
                    showLinerBackground(bck, thumb);
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });

        });
    }


    @Override
    public NitContainerCommand providerNitContainerCommand(int flag) {
        return (NitContainerCommand) () -> (EnStudyViewModel.class);
    }

    private RadioMenuVo nowRadioMenu;


    private void showLinerBackground(String... u) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                mBinding.lessonAudioRelative.setBackground(resource);
            }
        };
        Glide.with(this).load(u[0]).apply(options).into(simpleTarget);
        Glide.with(this).load(u[1]).apply(options).into(mBinding.lessonAudioSrc);

    }


    private void initChivox() {
        mViewModel.showDialogWait("正在初始化语音引擎", false);
        Observable.create((e) -> {
            if (TextUtils.isEmpty(ChivoxUtils.serialNumber)) {
                byte buf[] = new byte[1024];
                AIEngine.aiengine_get_device_id(buf, getApplicationContext());
                ChivoxUtils.deviceId = new String(buf).trim();
                String sig = String.format("{\"appKey\":\"%s\",\"secretKey\":\"%s\",\"userId\":\"%s\"}", ChivoxUtils.appKey, ChivoxUtils.secretKey, "userid");
                JSONObject sig_json = JSONObject.parseObject(sig);
                byte cfg_b[] = Arrays.copyOf(sig_json.toString().getBytes(), 1024);
                int ret = AIEngine.aiengine_opt(0, 6, cfg_b, 1024);
                String serialNumberInfo = "";
                if (ret > 0) {
                    serialNumberInfo = new String(cfg_b, 0, ret);
                } else {
                    serialNumberInfo = new String(cfg_b);
                }
                LogUtils.e("serialNumberInfo: " + serialNumberInfo);
                if (!serialNumberInfo.contains("serialNumber")) {
                    e.onError(new Throwable(serialNumberInfo));
                } else {
                    JSONObject serialJson = JSONObject.parseObject(serialNumberInfo);
                    ChivoxUtils.serialNumber = serialJson.getString("serialNumber");
                }
            }
            if (ChivoxUtils.recorderApp == null) {
                String resourcePath = AIEngineHelper.extractResourceOnce(AudioLessonActivity.this, "aiengine.resource.zip", true);
                String provisionPath = AIEngineHelper.extractResourceOnce(AudioLessonActivity.this, "aiengine.provision", false);
                String path = AIEngineHelper.getFilesDir(AudioLessonActivity.this).getPath();
                String cfg = String.format("{\"prof\":{\"enable\":1, \"output\":\"" + path + "/log.log\"}, \"appKey\": \"%s\", \"secretKey\": \"%s\", \"provision\": \"%s\", \"native\": {\"en.pred.exam\":{\"res\": \"%s\"},\"en.sent.score\":{\"res\": \"%s\"},\"en.word.score\":{\"res\": \"%s\"}}}",
                        ChivoxUtils.appKey, ChivoxUtils.secretKey,
                        provisionPath,
                        new File(resourcePath, "exam/bin/eng.pred.aux.P3.V4.12").getAbsolutePath(),
                        new File(resourcePath, "eval/bin/eng.snt.g4.P3.N1.0.2").getAbsolutePath(),
                        new File(resourcePath, "eval/bin/eng.wrd.g4.P3.N1.0.2").getAbsolutePath());
                ChivoxUtils.engine = AIEngine.aiengine_new(cfg, AudioLessonActivity.this);
                ChivoxUtils.recorderApp = new AIRecorder();
                e.onNext("");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mViewModel.hideDialogWait();
                        ToastUtils.showShort("错误信息:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mViewModel.hideDialogWait();
                        ToastUtils.showShort("初始化成功:");

                    }
                });


    }

}
