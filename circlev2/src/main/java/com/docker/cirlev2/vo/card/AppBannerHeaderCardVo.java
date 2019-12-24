package com.docker.cirlev2.vo.card;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.docker.cirlev2.R;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.vo.card.BaseCardVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppBannerHeaderCardVo extends BaseCardVo<String> {


    /*
     * banner 点击事件
     * */
    public ReplyCommandParam replyCommandParam = new ReplyCommandParam() {
        @Override
        public void exectue(Object o) {
            Log.d("sss", "exectue: ===========popopo==========" + o);
        }
    };

    public ObservableField<List<BannerVo>> bannerVos = new ObservableField<>();

    public AppBannerHeaderCardVo(int style, int position) {
        super(style, position);
        maxSupport = 1;
        ArrayList<BannerVo> arrayList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            arrayList.add(new BannerVo());
        }
        bannerVos.set(arrayList);
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.circlev2_banner_card;
    }

    public static class BannerVo implements Serializable {
        public String img = "http://taijistar.oss-cn-beijing.aliyuncs.com/static/var/upload/img20191029/upload/image/1572354265340_536x451.png";
        public String url;
        public String type;
    }

}
