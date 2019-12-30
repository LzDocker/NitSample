package com.bfhd.account.vo.index.setting;

import android.view.View;
import com.bfhd.account.R;
import com.bfhd.account.vo.MyInfoVo;
import com.docker.common.common.vo.card.BaseCardVo;

public class SettingPersonInfoVo extends BaseCardVo<MyInfoVo> {

    public SettingPersonInfoVo(int style, int position) {
        super(style, position);
        maxSupport = 3;
        mVmPath = "com.bfhd.account.vm.AccountPersonInfoViewModel";
    }

    private  String fullName;
    private  String sex;
    private  String mobile;
    private  String age;
    private  String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public int getItemLayout() {
        return R.layout.account_fragment_personinfo_item;
    }


    @Override
    public void onItemClick(BaseCardVo item, View view) {

    }
}
