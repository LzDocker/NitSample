package com.docker.cirlev2.ui.pro;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.docker.cirlev2.R;
import com.docker.cirlev2.databinding.Circlev2ProInfoActivityBinding;
import com.docker.cirlev2.databinding.Circlev2ProManagerActivityBinding;
import com.docker.cirlev2.ui.CircleInfoActivity;
import com.docker.cirlev2.ui.detail.CircleDetailIndexActivity;
import com.docker.cirlev2.vm.SampleListViewModel;
import com.docker.cirlev2.vo.card.ProRecycleCardVo;
import com.docker.cirlev2.vo.entity.CircleDetailVo;
import com.docker.common.common.binding.CommonBdUtils;
import com.docker.common.common.command.NitDelegetCommand;
import com.docker.common.common.model.CommonListOptions;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.ui.base.NitCommonActivity;
import com.docker.common.common.ui.base.NitCommonFragment;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.widget.card.NitBaseProviderCard;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/*
 * 圈子应用介绍
 * */
@Route(path = AppRouter.CIRCLE_DETAIL_v2_PRO_INFO)
public class CircleProInfoActivity extends NitCommonActivity<SampleListViewModel, Circlev2ProInfoActivityBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.circlev2_pro_info_activity;
    }

    @Override
    public SampleListViewModel getmViewModel() {
        return ViewModelProviders.of(this, factory).get(SampleListViewModel.class);
    }


    @Override
    public void initView() {
        mToolbar.setTitle("应用介绍");
        processBanner();
    }

    @Override
    public void initObserver() {

    }

    private void processBanner(/*CircleDetailVo circleDetailVo*/) {
        ArrayList<String> imglist = new ArrayList<>();
//        if (circleDetailVo.getSurfaceImg().contains(",")) {
//            String[] img = circleDetailVo.getSurfaceImg().split(",");
//            for (int i = 0; i < img.length; i++) {
//                if (!TextUtils.isEmpty(img[i])) {
//                    imglist.add(CommonBdUtils.getCompleteImageUrl(img[i]));
//                }
//            }
//        } else {
//            imglist.add(CommonBdUtils.getCompleteImageUrl(circleDetailVo.getSurfaceImg()));
//        }
        imglist.clear();
        imglist.add(CommonBdUtils.getCompleteImageUrl("/static/var/upload/img20191029/upload/image/1572354265340_536x451.png"));
        imglist.add(CommonBdUtils.getCompleteImageUrl("/static/var/upload/img20191029/upload/image/1572354265340_536x451.png"));
        imglist.add(CommonBdUtils.getCompleteImageUrl("/static/var/upload/img20191029/upload/image/1572354265340_536x451.png"));
        startBanner(imglist);
    }

    private void startBanner(List<String> imglist) {
        mBinding.banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                RequestOptions options = new RequestOptions();
                options.transforms(new CenterCrop());
                Glide.with(CircleProInfoActivity.this)
                        .load(CommonBdUtils.getCompleteImageUrl((String) path))
                        .apply(options)
                        .into(imageView);
            }
        });
        mBinding.banner.update(imglist);
        mBinding.banner.setDelayTime(3000);
        mBinding.banner.setOnBannerListener(position -> {
        });
        mBinding.banner.start();
    }


    @Override
    public void initRouter() {
        ARouter.getInstance().inject(this);
    }

}
