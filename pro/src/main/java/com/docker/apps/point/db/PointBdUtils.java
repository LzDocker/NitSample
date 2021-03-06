package com.docker.apps.point.db;

import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.text.TextUtils;

import com.docker.apps.point.vo.PointSortItemVo;
import com.docker.cirlev2.util.BdUtils;
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
        if (!TextUtils.isEmpty(Obtotals.get(position).fullName)) {
            return Obtotals.get(position).fullName;
        }
        if (!TextUtils.isEmpty(Obtotals.get(position).nickname)) {
            return Obtotals.get(position).nickname;
        }
        if (!TextUtils.isEmpty(Obtotals.get(position).username)) {
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
            case "integral":
                footstr = Obtotals.get(position).totalNum + "积分";
                break;
            case "buy":
                footstr = "消费¥" + Obtotals.get(position).totalNum;
                break;
            case "invite":
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
            case "integral":
                str = pointSortItemVo.totalNum + "积分";
                break;
            case "buy":
                str = "消费¥" + pointSortItemVo.totalNum;
                break;
            case "invite":
                str = "拓客" + pointSortItemVo.totalNum + "人";
                break;
        }
        return str;
    }


    public static String getMyRankName(PointSortItemVo pointSortItemVo) {
        if (pointSortItemVo == null) {
            return "";
        }
        if (!TextUtils.isEmpty(pointSortItemVo.fullName)) {
            return pointSortItemVo.fullName;
        }
        if (!TextUtils.isEmpty(pointSortItemVo.nickname)) {
            return pointSortItemVo.nickname;
        }
        if (!TextUtils.isEmpty(pointSortItemVo.username)) {
            return pointSortItemVo.username;
        }
        return "";
    }


    public static String getPointSortMyFootStr(ObservableField<PointSortItemVo> Obtotals, String rankType) {

        if (Obtotals == null || Obtotals.get() == null) {
            return "";
        }
        String footstr = "";
        switch (rankType) {
            case "integral":
                footstr = Obtotals.get().totalNum + "积分";
                break;
            case "buy":
                footstr = "消费¥" + Obtotals.get().totalNum;
                break;
            case "invite":
                footstr = "拓客" + Obtotals.get().totalNum + "人";
                break;
        }
        return footstr;
    }

    public static String getPointSortMyIcon(ObservableField<PointSortItemVo> Obtotals) {
        if (Obtotals == null || Obtotals.get() == null) {
            return "";
        }
        return BdUtils.getImgUrl(Obtotals.get().avatar);
    }
}
