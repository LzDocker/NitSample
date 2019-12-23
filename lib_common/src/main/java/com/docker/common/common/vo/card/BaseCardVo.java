package com.docker.common.common.vo.card;

import android.databinding.Bindable;
import android.view.View;

import com.dcbfhd.utilcode.utils.AppUtils;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.utils.lv.MserialMedatorLv;
import com.docker.common.common.vm.NitCommonListVm;
import com.docker.common.common.vm.container.NitcommonCardViewModel;
import com.docker.common.common.widget.card.McardObserver;

import java.util.HashMap;

/*
 *
 * card 基类
 * */
public abstract class BaseCardVo<T> extends BaseSampleItem {

    public transient NitcommonCardViewModel mNitcommonCardViewModel;

    public String mVmPath;
    /*
     * card 请求服务端的参数
     * */
    public HashMap<String, String> mRepParamMap = new HashMap<>();

    /*
     * 服务端数据存储的lv
     * */
    public transient MserialMedatorLv<T> mCardVoLiveData = new MserialMedatorLv<>();

    public BaseCardVo(int style, int position) {
        this.style = style;
        this.position = position;
    }

    public int style = 0;

    public int position = 0;

    public int maxSupport = 1;





    public void onChangeStyleClick(BaseCardVo item, View view, NitCommonListVm viewModel) {
        if (AppUtils.isAppDebug()) {
            int style = item.style;
            if (maxSupport - 1 > style) {
                style++;
            } else {
                style--;
            }
            item.setStyle(style);
        }
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            BaseCardVo.this.onItemClick((BaseCardVo) item, view);
            onChangeStyleClick((BaseCardVo) item, view, null);
        };
    }

    public abstract void onItemClick(BaseCardVo item, View view);

    @Bindable
    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
        notifyChange();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMaxSupport() {
        return maxSupport;
    }

    public void setMaxSupport(int maxSupport) {
        this.maxSupport = maxSupport;
    }
}
