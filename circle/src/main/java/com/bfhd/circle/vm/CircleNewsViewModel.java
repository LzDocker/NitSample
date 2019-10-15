package com.bfhd.circle.vm;


import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;
import com.bfhd.circle.R;
import com.bfhd.circle.base.HivsBaseViewModel;
import com.bfhd.circle.vo.CircleNewsVo;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.repository.CommonRepository;
import java.util.ArrayList;
import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class CircleNewsViewModel extends HivsBaseViewModel {


    @Inject
    CommonRepository commonRepository;

    @Inject
    public CircleNewsViewModel() { }

    /*
     * 图片点击事件
     * */
    public static void imgclick(String imgurl, View view) {
        ToastUtils.showShort("---" + imgurl);
    }

    /*
     * 新闻observable数据
     * */
    public final ObservableList<CircleNewsVo> newsItems = new ObservableArrayList<>();


    @Override
    public void initCommand() { }

    public void getData(int types) {
        mEmptycommand.set(EmptyStatus.BdHiden);
        ArrayList<CircleNewsVo> ssList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CircleNewsVo circleNewsVo = new CircleNewsVo();
            int type = i % 2 == 0 ? 0 : 1;
            circleNewsVo.setType(type);
            circleNewsVo.setName("ooooooooooo");
            circleNewsVo.setInfo("ooooooooooo");
            ArrayList<String> arrayList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                arrayList.add("-----");
            }
            circleNewsVo.setImglist(arrayList);
            ssList.add(circleNewsVo);
        }
        newsItems.addAll(ssList);
    }
}
