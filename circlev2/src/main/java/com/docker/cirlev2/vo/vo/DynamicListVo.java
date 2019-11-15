package com.docker.cirlev2.vo.vo;

import com.docker.cirlev2.R;
import com.docker.common.common.model.OnItemClickListener;
import com.docker.common.common.vo.ServiceDataBean;

public class DynamicListVo extends ServiceDataBean {

    @Override
    public int getItemLayout() {

        int layoutid;
        switch (this.getType()) {
            case "persion":
//                layoutid = R.layout.circlev2_item_dynamic_persion;
                break;
            case "car":
            case "datetime":
            case "product":
            case "goods":
                layoutid = R.layout.circlev2_item_dynamic_goods;
                break;
//            case "project":
//                layoutid = R.layout.circle_item_dynamic_project;
//                break;
//            case "house":
//                layoutid = R.layout.circle_item_dynamic_hourse;
//                break;
//            case "answer":
//                layoutid = R.layout.circle_item_dynamic_answer;
//                break;
//            case "dynamic":
//                layoutid = R.layout.circle_item_dynamic_dynamic;
//                break;
//            case "news":
//                layoutid = R.layout.circle_item_dynamic_news;
//                break;
//            case "event":
//                layoutid = R.layout.circle_item_dynamic_event;
//                break;
//            case "shared":
//                layoutid = R.layout.circle_item_dynamic_share;
//                break;
//            case "alarm":
//                // todo
//                layoutid = R.layout.circle_item_dynamic_dangrous;
//                break;
//            case "report":
//                layoutid = R.layout.circle_item_dynamic_report;
//                break;
//            case "sentiment":
//                layoutid = R.layout.circle_item_dynamic_sentiment;
//                break;
//            case "emergency": // 应急管理
//            case "management": // 管理要求
//                layoutid = R.layout.circle_item_dynamic_file_manager;
//                break;
//            default:
//                layoutid = R.layout.circle_item_dynamic_answer;
//                break;
        }


        return R.layout.circlev2_item_dynamic_goods;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return null;
    }


}
