package com.docker.message.vo;

import com.alibaba.android.arouter.launcher.ARouter;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.docker.message.R;

import timber.log.Timber;

public class SampleItemVo extends BaseSampleItem {
    @Override
    public int getItemLayout() {
        return R.layout.common_item_sample;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            Timber.e("=========itemclick============");
            switch (((SampleItemVo) item).flag) {
                case 0:
                    ARouter.getInstance().build(AppRouter.VIDEOSINGLE).navigation();
                    break;
                case 1:
                    ARouter.getInstance().build(AppRouter.VIDEOLIST).navigation();
                    break;
                case 2:
                    ARouter.getInstance().build(AppRouter.VIDEOMAIN).navigation();
                    break;
            }
        };
    }

    public String title;
    public int flag;

    public SampleItemVo(String title, int flag) {
        this.title = title;
        this.flag = flag;
        this.sampleName = title;
    }
}
