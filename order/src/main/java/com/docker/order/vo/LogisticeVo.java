package com.docker.order.vo;

import android.databinding.BaseObservable;

import java.util.List;

public class LogisticeVo extends BaseObservable {


    /**
     * message : ok
     * nu : 4300597014418
     * ischeck : 1
     * condition : F00
     * com : yunda
     * status : 200
     * state : 3
     * data : [{"time":"2019-08-28 13:43:08","ftime":"2019-08-28 13:43:08","context":"[浙江宁波鄞州区潘火公司钢材市场分部]【宁波市】快件已被 已签收 签收。如有问题请电联业务员：梁改轩【13148827911】。相逢是缘,如果您对我的服务感到满意,给个五星好不好？【请在评价小件员处给予五星好评】","location":"浙江宁波鄞州区潘火公司钢材市场分部"}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private String state;
    private String imgurl;
    private List<DataBean> data;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2019-08-28 13:43:08
         * ftime : 2019-08-28 13:43:08
         * context : [浙江宁波鄞州区潘火公司钢材市场分部]【宁波市】快件已被 已签收 签收。如有问题请电联业务员：梁改轩【13148827911】。相逢是缘,如果您对我的服务感到满意,给个五星好不好？【请在评价小件员处给予五星好评】
         * location : 浙江宁波鄞州区潘火公司钢材市场分部
         */

        private String time;
        private String ftime;
        private String context;
        private String location;
        private String ischeck;

        public String getIscheck() {
            return ischeck;
        }

        public void setIscheck(String ischeck) {
            this.ischeck = ischeck;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
