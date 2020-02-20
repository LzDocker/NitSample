package com.docker.apps.active.vo;

import java.io.Serializable;

public class ActiveSucVo implements Serializable {

    /*{"joinid":"1","evoucherNo":"111111","AuditUrl":"baidu.com",}
     * */

    public String joinid;
    public String evoucherNo;
    public String AuditUrl;
    public int signStatus; //0待审核 1待核销（可查看核销凭证）
}
