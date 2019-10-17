package com.docker.nitsample.vo;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.vo.bean.StaCirParam;
import com.docker.common.common.model.BaseItemModel;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;
import com.docker.nitsample.R;

import timber.log.Timber;

public class SampleItemVo implements BaseItemModel {
    @Override
    public int getItemLayout() {
        return R.layout.item_sample;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            Timber.e("=========itemclick============");

            switch (((SampleItemVo) item).flag) {
                case 0:
                    ARouter.getInstance().build(AppRouter.ACCOUNT_LOGIN).navigation();
                    break;
                case 1:
                    ARouter.getInstance().build(AppRouter.VIDEOMAIN).navigation();
                    break;
                case 2:
                    ARouter.getInstance().build(AppRouter.ACCOUNT_COUNTRY).navigation();
                    break;
                case 3:
                    StaCirParam staCirParam = new StaCirParam();
                    staCirParam.setCircleid("67");
                    staCirParam.setUtid("482e8892629068523502b63df24bc526");
                    ARouter.getInstance().build(AppRouter.CIRCLEHOME).withSerializable("mStartParam", staCirParam).navigation();
                    break;
                case 4:
                    ARouter.getInstance().build(AppRouter.AUDIO_DEMO).navigation();
                    break;
            }
        };
    }

    public String title;

    public int flag;

    public SampleItemVo(String title, int flag) {
        this.title = title;
        this.flag = flag;
    }
}
