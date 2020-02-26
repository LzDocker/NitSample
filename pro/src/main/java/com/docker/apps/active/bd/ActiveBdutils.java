package com.docker.apps.active.bd;

import android.text.TextUtils;

import com.docker.apps.R;
import com.docker.apps.active.vo.ActiveVo;
import com.docker.common.common.utils.cache.CacheUtils;

public class ActiveBdutils {




    public static boolean getPointShow(ActiveVo activeVo) {
        if (activeVo == null) {
            return false;
        }
        if (TextUtils.isEmpty(activeVo.point)) {
            return false;
        } else {
            return true;
        }
    }




    public static boolean isShowBot(ActiveVo activeVo) {
        if (activeVo == null || CacheUtils.getUser() == null) {
            return false;
        }

        if (activeVo.uuid.equals(CacheUtils.getUser().uuid)) {
            return false;
        } else {
            return true;
        }
    }

}