package com.docker.video.AlivcLiveRoom;
/*
 * Copyright (C) 2010-2018 Alibaba Group Holding Limited.
 */

import android.text.TextUtils;

/**
 * 时间格式化工具类
 */
public class TimeFormater {

    /**
     * 格式化毫秒数为 xx:xx:xx这样的时间格式。
     *
     * @param ms 毫秒数
     * @return 格式化后的字符串
     */
    public static String formatMs(long ms) {
        int seconds = (int) (ms / 1000);
        int finalSec = seconds % 60;
        int finalMin = seconds / 60 % 60;
        int finalHour = seconds / 3600;

        StringBuilder msBuilder = new StringBuilder("");
        if (finalHour > 9) {
            msBuilder.append(finalHour).append(":");
        } else if (finalHour > 0) {
            msBuilder.append("0").append(finalHour).append(":");
        }

        if (finalMin > 9) {
            msBuilder.append(finalMin).append(":");
        } else if (finalMin > 0) {
            msBuilder.append("0").append(finalMin).append(":");
        } else {
            msBuilder.append("00").append(":");
        }

        if (finalSec > 9) {
            msBuilder.append(finalSec);
        } else if (finalSec > 0) {
            msBuilder.append("0").append(finalSec);
        } else {
            msBuilder.append("00");
        }

        return msBuilder.toString();
    }

    /**
     * 分：秒：毫秒
     * 00:00:00-》m
     *
     * @param time
     * @return
     */
    public static double timeToSecond(String time) {
        if (TextUtils.isEmpty(time))
            return 0;
        String p[] = time.split(":");
        double fen = 0, miao = 0, hao = 0;
        if (p == null)
            return 0;
        if (p.length > 0)
            if (p[0].equals("00")) {
                fen = 0;
            } else {
                if (TextUtils.isEmpty(p[0]))
                    fen = Double.parseDouble("0") * 60;
                else
                    fen = Double.parseDouble(p[0]) * 60;
            }
        if (p.length > 1)
            if (TextUtils.isEmpty(p[1]))
                miao = Double.parseDouble("0") * 60;
            else
                miao = Double.parseDouble(p[1]);

        if (p.length > 2)
            if (TextUtils.isEmpty(p[2]))
                hao = Double.parseDouble("0") * 60;
            else
                hao = Double.parseDouble(p[2]) / 100;
        return fen + hao + miao;
    }

    public static double[] getSETime(String time) {
        String be[] = time.split("-");
        String p[] = be[0].split(":");
        double fen, miao, hao;
        if (p[0].equals("00")) {
            fen = 0;
        } else {
            fen = Double.parseDouble(p[0]) * 60;
        }
        miao = Double.parseDouble(p[1]);
        hao = Double.parseDouble(p[2]) / 100;

        String p2[] = be[1].split(":");
        double fen2, miao2, hao2;


        if (p2[0].equals("00")) {
            fen2 = 0;
        } else {
            fen2 = Double.parseDouble(p2[0]) * 60;
        }
        miao2 = Double.parseDouble(p2[1]);
        hao2 = Double.parseDouble(p2[2]) / 100;
        return new double[]{fen + hao + miao, fen2 + hao2 + miao2};
    }
}
