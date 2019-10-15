package com.bfhd.circle.vo;

import java.io.Serializable;
import java.util.List;

public class TradingCommonVo implements Serializable {


    String memberid;// 	成员用户ID
    String uuid;// 	成员用户UUID
    String fullName;// 	备注
    String nickname;// 	昵称
    String avatar;// 	头像
    int role;//  	角色 0普通成员 1管理员   app 端  2 圈主
    String isCreator;//  	是否是圈主


    String groupid;// 	分组ID
    String groupName;// 	分组名称
    String listorder;// 	排序
    List<TradingCommonVo> employee;// 	成员列表
    String employeeNum;

    public boolean isExpand = true; // 是否  true关闭

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getListorder() {
        return listorder;
    }

    public void setListorder(String listorder) {
        this.listorder = listorder;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    public List<TradingCommonVo> getEmployee() {
        return employee;
    }

    public void setEmployee(List<TradingCommonVo> employee) {
        this.employee = employee;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getIsCreator() {
        return isCreator;
    }

    public void setIsCreator(String isCreator) {
        this.isCreator = isCreator;
    }


}
