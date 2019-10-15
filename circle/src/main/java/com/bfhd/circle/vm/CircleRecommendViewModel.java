package com.bfhd.circle.vm;


import com.bfhd.circle.base.HivsBaseViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.repository.CommonRepository;
import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 *
 * 推荐相关列表
 */

public class CircleRecommendViewModel extends HivsBaseViewModel {


    @Inject
    CommonRepository commonRepository;


    @Inject
    public CircleRecommendViewModel() {

    }

    @Override
    public void initCommand() {
//        mEmptycommand.set(EmptyStatus.BdError);
        mEnableRefresh.set(true);
        mEnableLoadmore.set(true);

        mCommand.OnRefresh(() -> {
            getData();
        });
        mCommand.OnLoadMore(() -> {
            mEmptycommand.set(EmptyStatus.BdLoading);
        });
        mCommand.OnRetryLoad(() -> {
            getData();
        });
    }

    private void getData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
    }



    public String img = "https://wanandroid.com/blogimgs/54f4350f-039d-48b6-a38b-0933e1405004.png";

}
