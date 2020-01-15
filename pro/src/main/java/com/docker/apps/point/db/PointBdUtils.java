package com.docker.apps.point.db;

import android.databinding.ObservableList;
import android.text.TextUtils;

import com.docker.apps.point.vo.PointSortItemVo;
import com.docker.common.common.binding.CommonBdUtils;

public class PointBdUtils {

    public static String getPointSortIcon(ObservableList<PointSortItemVo> Obtotals, int position) {

        if (Obtotals == null || Obtotals.size() == 0 || Obtotals.size() <= position) {
            return "";
        }
        return CommonBdUtils.getImgUrl(Obtotals.get(position).avatar);
    }

    public static boolean getPointSortIsshow(ObservableList<PointSortItemVo> Obtotals, int position) {
        if (Obtotals == null || Obtotals.size() == 0 || Obtotals.size() <= position) {
            return false;
        }
        return true;
    }

    public static String getPointSortName(ObservableList<PointSortItemVo> Obtotals, int position) {

        if (Obtotals == null || Obtotals.size() == 0 || Obtotals.size() <= position) {
            return "";
        }
        if (TextUtils.isEmpty(Obtotals.get(position).fullName)) {
            return Obtotals.get(position).fullName;
        }
        if (TextUtils.isEmpty(Obtotals.get(position).nickname)) {
            return Obtotals.get(position).nickname;
        }
        if (TextUtils.isEmpty(Obtotals.get(position).username)) {
            return Obtotals.get(position).username;
        }
        return "";
    }

    public static String getPointSortFootStr(ObservableList<PointSortItemVo> Obtotals, int position, String rankType) {

        if (Obtotals == null || Obtotals.size() == 0 || Obtotals.size() <= position) {
            return "";
        }
        String footstr = "";
        switch (rankType) {
            case "1":
                footstr = Obtotals.get(position).totalNum + "积分";
                break;
            case "2":
                footstr = "消费¥" + Obtotals.get(position).totalNum;
                break;
            case "3":
                footstr = "拓客" + Obtotals.get(position).totalNum + "人";
                break;
        }
        return footstr;
    }

    public static String getPointSortIndex(int index) {

        return String.valueOf(index + 3);
    }


    //总积分排行榜", "购买排行榜", "拓客排行榜
    public static String getPointSortInfoDesc(PointSortItemVo pointSortItemVo) {
        String str = "";
        switch (pointSortItemVo.rankType) {
            case "1":
                str = pointSortItemVo.totalNum + "积分";
                break;
            case "2":
                str = "消费¥" + pointSortItemVo.totalNum;
                break;
            case "3":
                str = "拓客" + pointSortItemVo.totalNum + "人";
                break;
        }
        return str;
    }
}
