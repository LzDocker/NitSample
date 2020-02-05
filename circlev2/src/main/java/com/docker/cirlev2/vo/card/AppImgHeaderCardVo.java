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

public class AppImgHeaderCardVo extends BaseCardVo<String> {


    public ObservableField<String> imgurl = new ObservableField<>();
    public ObservableField<String> logourl = new ObservableField<>();

    public AppImgHeaderCardVo(int style, int position) {
        super(style, position);
        maxSupport = 1;
//        imgurl.set("http://taijistar.oss-cn-beijing.aliyuncs.com/static/var/upload/img20191029/upload/image/1572354265340_536x451.png");
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.circlev2_img_card;
    }


}
