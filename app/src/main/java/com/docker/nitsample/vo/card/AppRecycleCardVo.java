package com.docker.nitsample.vo.card;

import android.databinding.ObservableField;
import android.view.View;

import com.docker.cirlev2.BR;
import com.docker.common.common.vo.card.BaseCardVo;
import com.docker.nitsample.R;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class AppRecycleCardVo extends BaseCardVo<String> {

    public AppRecycleCardVo(int style, int position) {
        super(style, position);
        maxSupport = 1;
    }

    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }

    @Override
    public int getItemLayout() {
        return R.layout.app_recycle_card;
    }


    public transient ItemBinding<String> itemImgBinding = ItemBinding.<String>of(BR.item,
            R.layout.app_card_img_inner); // 单一view 有点击事件;
//


    public ItemBinding<String> getItemImgBinding() {
        if (itemImgBinding == null) {
            itemImgBinding = ItemBinding.<String>of(BR.item,
                    R.layout.app_card_img_inner);
        }
        return itemImgBinding;
    }

    private ObservableField<List<String>> InnerResource = new ObservableField<>();

    public void setInnerResource(ObservableField<List<String>> innerResource) {
        InnerResource = innerResource;
    }

    public ObservableField<List<String>> getInnerResource() {
        ArrayList<String> arrayList = new ArrayList();
        for (int i = 0; i < 9; i++) {
            arrayList.add(i + "===");
        }
        InnerResource.set(arrayList);
        return InnerResource;
    }


}
