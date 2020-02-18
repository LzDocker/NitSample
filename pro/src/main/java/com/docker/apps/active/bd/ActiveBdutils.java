package com.docker.apps.active.bd;

import com.docker.apps.active.vo.ActiveVo;

public class ActiveBdutils {


    public static String getActiveStr(ActiveVo activeVo) {
        String str = "";
        if (activeVo == null) {
            return str;
        }
        if (activeVo.status == -1) {
            str = "已结束";
        }
        if (activeVo.status == 1) {
            str = "立即报名";
        }
        if (activeVo.signStatus == 1) {
            str = "已报名";
        }
        if (activeVo.enrollNum.equals(activeVo.limitNum)) {
            str = "报名人数已满";
        }
        return str;
    }
}
