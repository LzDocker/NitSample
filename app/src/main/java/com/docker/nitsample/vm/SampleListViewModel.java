package com.docker.nitsample.vm;

import android.arch.lifecycle.LiveData;

import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.common.common.widget.empty.EmptyStatus;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;
import com.docker.nitsample.vo.SampleItemVo;

import java.util.HashMap;
import java.util.Random;

import javax.inject.Inject;

public class SampleListViewModel extends NitCommonContainerViewModel {


    @Inject
    public SampleListViewModel() {

    }

    @Override
    public void loadData() {
        mEmptycommand.set(EmptyStatus.BdHiden);


        /*
         * todo widget 通用控件
         *      tool   通用工具
         *      account (登录/注册/重置密码/第三方登录)
         *      message (im/系统通知/评论/点赞/关注/收藏)
         *      mine (card/我购买的/我卖出的/我收藏的/我关注的)
         *
         *
         * */

        SampleItemVo video_edit = new SampleItemVo("video_edit", -4);
        mItems.add(video_edit);

        SampleItemVo stick_sample = new SampleItemVo("stick", -3);
        mItems.add(stick_sample);

        SampleItemVo dev_sample = new SampleItemVo("deving", -2);
        mItems.add(dev_sample);

        SampleItemVo spa_sample = new SampleItemVo("spa", -1);
        mItems.add(spa_sample);

        SampleItemVo account_sample = new SampleItemVo("登录注册", 0);
        mItems.add(account_sample);

        SampleItemVo video_sample = new SampleItemVo("视频模块", 1);
        mItems.add(video_sample);

        SampleItemVo address_sample = new SampleItemVo("城市选择", 2);
        mItems.add(address_sample);

        SampleItemVo circle_sample = new SampleItemVo("圈子", 3);
        mItems.add(circle_sample);

        SampleItemVo audio_sample = new SampleItemVo("音频", 4);
        mItems.add(audio_sample);

        SampleItemVo xPopup_sample = new SampleItemVo("XPopup", 5);
        mItems.add(xPopup_sample);

        SampleItemVo mine_sample = new SampleItemVo("个人中心", 6);
        mItems.add(mine_sample);

        SampleItemVo message_sample = new SampleItemVo("消息中心", 7);
        mItems.add(message_sample);

        SampleItemVo loc_sample = new SampleItemVo("定位", 8);
        mItems.add(loc_sample);

        SampleItemVo im_sample = new SampleItemVo("im", 9);
        mItems.add(im_sample);

        SampleItemVo pub_sample = new SampleItemVo("发布", 10);
        mItems.add(pub_sample);

        SampleItemVo version_sample = new SampleItemVo("版本更新", 11);
        mItems.add(version_sample);

        SampleItemVo don_sample = new SampleItemVo("文件下载/上传", 12);
        mItems.add(don_sample);

        SampleItemVo rx_sample = new SampleItemVo("RX", 13);
        mItems.add(rx_sample);

        SampleItemVo pic_sample = new SampleItemVo("图片选择", 14);
        mItems.add(pic_sample);

        SampleItemVo oss_sample = new SampleItemVo("oss上传", 15);
        mItems.add(oss_sample);

        SampleItemVo locfile_sample = new SampleItemVo("本地文件管理", 16);
        mItems.add(locfile_sample);

        SampleItemVo perr_sample = new SampleItemVo("权限管理", 17);
        mItems.add(perr_sample);

        SampleItemVo train_sample = new SampleItemVo("培训考试模块", 18);
        mItems.add(train_sample);

        SampleItemVo search_sample = new SampleItemVo("搜索", 19);
        mItems.add(search_sample);

        SampleItemVo banner_sample = new SampleItemVo("banner", 20);
        mItems.add(banner_sample);

        SampleItemVo picker_sample = new SampleItemVo("选择picker", 21);
        mItems.add(picker_sample);

        SampleItemVo map_sample = new SampleItemVo("地图", 22);
        mItems.add(map_sample);

        SampleItemVo pdf_sample = new SampleItemVo("pdf..文件预览", 23);
        mItems.add(pdf_sample);

        SampleItemVo staff_sample = new SampleItemVo("成员管理", 24);
        mItems.add(staff_sample);

        SampleItemVo addre_sample = new SampleItemVo("地址管理", 25);
        mItems.add(addre_sample);

        SampleItemVo share_sample = new SampleItemVo("分享", 26);
        mItems.add(share_sample);

        SampleItemVo pay_sample = new SampleItemVo("支付", 27);
        mItems.add(pay_sample);

        SampleItemVo h5_sample = new SampleItemVo("万能h5", 28);
        mItems.add(h5_sample);

        SampleItemVo order_sample = new SampleItemVo("订单", 29);
        mItems.add(order_sample);

        SampleItemVo money_sample = new SampleItemVo("钱包", 30);
        mItems.add(money_sample);

        SampleItemVo evaluate_sample = new SampleItemVo("评测", 31);
        mItems.add(evaluate_sample);

        SampleItemVo livedate_sample = new SampleItemVo("Livedata", 32);
        mItems.add(livedate_sample);
    }

    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        return null;
    }
}
