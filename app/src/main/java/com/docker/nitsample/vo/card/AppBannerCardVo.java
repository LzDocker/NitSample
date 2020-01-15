package com.docker.nitsample.vo.card;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.circle.vo.bean.StaDetailParam;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.router.AppRouter;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;
import com.docker.nitsample.vo.BannerEntityVo;
import java.util.ArrayList;
public class AppBannerCardVo extends BaseCardVo {

    public ObservableList<BannerEntityVo> observableList = new ObservableArrayList<>();
    /*
     * banner 点击事件
     * */
    public ReplyCommandParam replyCommandParam = (ReplyCommandParam) o -> {
        if ("".equals(((BannerEntityVo) o).dynamicid)) {
            ARouter.getInstance().build(AppRouter.COMMONH5)
                    .withString("title", ((BannerEntityVo) o).title)
                    .withString("weburl", ((BannerEntityVo) o).http).navigation();
        } else {
            StaDetailParam staDetailParam = new StaDetailParam();
            staDetailParam.dynamicId = (((BannerEntityVo) o)).dynamicid;
            ARouter.getInstance().build(AppRouter.CIRCLE_DETAIL).withSerializable("mStaparam", staDetailParam).navigation();
        }
    };

    public AppBannerCardVo(int style, int position) {
        super(style, position);
        maxSupport = 1;
        mVmPath = "com.docker.nitsample.vm.card.AppBannerCardViewModel";
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.app_banner_card;
    }

    public void setBannerList(ArrayList<BannerEntityVo> bannerEntityVos) {
        observableList.clear();
        observableList.addAll(bannerEntityVos);
    }
}
