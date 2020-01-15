package com.docker.apps.intvite.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class InviteDataVo implements Serializable {


    public ArrayList<String> ad;
    public String inviteCode;
    public String shareUrl;
    public String shareImg;
    public Reward myReward;


    public class Reward implements Serializable {

        public String pointsReward;
        public String incomeReward;
        public String inviteNum;
    }

}
