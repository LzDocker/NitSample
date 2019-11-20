package com.docker.module_im.login;

import com.docker.common.common.utils.cache.CacheUtils;
import com.docker.module_im.DemoCache;
import com.docker.module_im.redpacket.NIMRedPacketClient;
import com.netease.nim.uikit.api.NimUIKit;

/**
 * 注销帮助类
 * Created by huangjun on 2015/10/8.
 */
public class LogoutHelper {
    public static void logout() {
        // 清理缓存&注销监听&清除状态
        NimUIKit.logout();
        DemoCache.clear();
        CacheUtils.clearUser();
        NIMRedPacketClient.clear();
    }
}
