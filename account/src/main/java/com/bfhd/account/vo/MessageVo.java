package com.bfhd.account.vo;

import java.io.Serializable;

public class MessageVo implements Serializable {

    public MessageVo(int type, int iconRes, String title) {
        this.type = type;
        this.iconRes = iconRes;
        this.title = title;
    }

    public int type;
    public int iconRes;
    public String title;
    public MeaasgeInfoVo meaasgeInfoVo;


    /*
    * "id":"206",
"uid":"4",
"title":"关注",
"type":"1",
"params":{
"act":"userFocus",
"memberid":"16",
"uuid":"8f5bca109e7e512c3bfc14d152544209"
},
"content":"Fighji关注了你！",
"isread":"0",
"addtime":"1557812639",
"mid":"206",
"notReadMsgNum":"4"
    * */
    public class MeaasgeInfoVo implements Serializable {

        public String id;
        public String uid;
        public String title;
        public String type;
        public String content;
        public String isread;
        public String addtime;
        public String mid;
        public String notReadMsgNum;
        public ParamsVo params;

    }

    public class ParamsVo implements Serializable {
        public String act;
        public String memberid;
        public String uuid;
        public String utid;
        public String circleid;
        public String dynamicid;
        public String commentid;

    }

}
