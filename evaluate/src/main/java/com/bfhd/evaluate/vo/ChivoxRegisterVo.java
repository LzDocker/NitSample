package com.bfhd.evaluate.vo;

import java.io.Serializable;

/**
 * kxf -> 2019-09-19
 **/
public class ChivoxRegisterVo implements Serializable {

    /**
     * serialNumber : xxxx-xxxx-xxxx-xxxx-xxxx
     * tips : a new deviceId
     */

    private String serialNumber;
    private String tips;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @Override
    public String toString() {
        return "ChivoxRegisterVo{" +
                "serialNumber='" + serialNumber + '\'' +
                ", tips='" + tips + '\'' +
                '}';
    }
}
