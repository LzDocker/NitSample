package com.bfhd.evaluate.vo;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.evaluate.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.router.AppRouter;

import timber.log.Timber;

/**
 * kxf -> 2019-09-29
 **/
public class RadioLessonVo extends BaseSampleItem {

    /**
     * name : 8 Vegan Foods Rich in Iron
     * id : 1
     * lession_dubbing_id : 1
     * num : 0
     * : 0
     */

    private String name;
    private String id;
    private String lession_dubbing_id;
    private String num;
    private String is_read;
    private int get_price;

    public int getGet_price() {
        return get_price;
    }

    public void setGet_price(int get_price) {
        this.get_price = get_price;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLession_dubbing_id() {
        return lession_dubbing_id;
    }

    public void setLession_dubbing_id(String lession_dubbing_id) {
        this.lession_dubbing_id = lession_dubbing_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    @Override
    public int getItemLayout() {
        return R.layout.item_radio_lesson;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            Timber.e("sss=========OnItemClickListener===========");
            ARouter.getInstance().build(AppRouter.EVALUATE_DETAIL).withString("id",id).navigation();
        };
    }
}
