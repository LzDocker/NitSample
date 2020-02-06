package com.docker.common.common.vo;

import java.io.Serializable;

public class WxOrderVo implements Serializable {


    /*"appid": "wx3daafb92083e7708",
        "noncestr": "U3S5C2F53x0vJG79z3Ho5KZ3dlHuqX3x",
        "partnerid": "1384939602",
        "prepayid": "wx2016090909494724f48587740897835929",
        "timestamp": "1473385787",
        "sign": "E0C19C3577CD7D9E7196DF81A18A52D8"
    * */

    public String appid;
    public String noncestr;
    public String partnerid;
    public String prepayid;
    public String timestamp;
    public String sign;

}
