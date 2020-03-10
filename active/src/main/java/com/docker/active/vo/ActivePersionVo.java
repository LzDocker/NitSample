package com.docker.active.vo;

import com.docker.active.R;
import com.docker.common.common.model.BaseSampleItem;
import com.docker.common.common.model.OnItemClickListener;

public class ActivePersionVo extends BaseSampleItem {


    @Override
    public int getItemLayout() {
        return R.layout.pro_active_item_persion;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return (item, view) -> {
            if (view.getId() == R.id.rl_content) { // 个人详情
//                ARouter.getInstance().build(AppRouter.ACTIVE_MANAGER_PERSION_DETAIL).navigation();
            }
            if (view.getId() == R.id.tv_verfi) { // 核销
               //ACTIVE_MANAGE_VERFIC
            }
        };
    }


    /*
    * {
            "joinid": "2",
            "fullName": "",
            "wxavatar": "",
            "wxnickname": "",
            "mobile": "18525645522",
            "inputtime": "1581496897",
            "money": "0",
            "payStatus": "0",
            "status": "0"
        }*/

    public String joinid;
    public String fullName;
    public String wxavatar;
    public String wxnickname;
    public String mobile;
    public String inputtime;
    public String money;
    public String payStatus;
    public int status;

}
