package com.bfhd.account.vo;

import java.io.Serializable;
import java.util.List;

public class AllLinkageVo implements Serializable {


    /**
     * data : [{"linkageid":"7","parentid":"0","name":"书企","img":"","recommend":"0","child":[{"linkageid":"1","parentid":"7","name":"出版社","img":"","recommend":"0","child":[]},{"linkageid":"2","parentid":"7","name":"出版商","img":"","recommend":"0","child":[]},{"linkageid":"3","parentid":"7","name":"代理商","img":"","recommend":"0","child":[]},{"linkageid":"4","parentid":"7","name":"经销商","img":"","recommend":"0","child":[]}]},{"linkageid":"8","parentid":"0","name":"服务商","img":"","recommend":"0","child":[{"linkageid":"10005","parentid":"8","name":"一站式出版","img":"/images/img1.png","recommend":"1","child":[]},{"linkageid":"10006","parentid":"8","name":"选题策划编写","img":"/images/img2.png","recommend":"1","child":[]},{"linkageid":"10007","parentid":"8","name":"排版","img":"/images/img3.png","recommend":"1","child":[]},{"linkageid":"10008","parentid":"8","name":"设计","img":"/images/img4.png","recommend":"1","child":[]},{"linkageid":"10009","parentid":"8","name":"印刷","img":"","recommend":"0","child":[]},{"linkageid":"10010","parentid":"8","name":"纸张","img":"","recommend":"0","child":[]},{"linkageid":"10011","parentid":"8","name":"课件制作","img":"","recommend":"0","child":[]},{"linkageid":"10012","parentid":"8","name":"物流","img":"","recommend":"0","child":[]},{"linkageid":"10013","parentid":"8","name":"其他服务","img":"","recommend":"0","child":[]}]}]
     * mtime : 1530087414
     */

    private String mtime;
    private List<DataBean> data;



    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * linkageid : 7
         * parentid : 0
         * name : 书企
         * img :
         * recommend : 0
         * child : [{"linkageid":"1","parentid":"7","name":"出版社","img":"","recommend":"0","child":[]},{"linkageid":"2","parentid":"7","name":"出版商","img":"","recommend":"0","child":[]},{"linkageid":"3","parentid":"7","name":"代理商","img":"","recommend":"0","child":[]},{"linkageid":"4","parentid":"7","name":"经销商","img":"","recommend":"0","child":[]}]
         */

        private String linkageid;
        private String parentid;
        private String name;
        private String img;
        private String recommend;
        private List<ChildBean> child;

        public String getLinkageid() {
            return linkageid;
        }

        public void setLinkageid(String linkageid) {
            this.linkageid = linkageid;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class ChildBean {
            /**
             * linkageid : 1
             * parentid : 7
             * name : 出版社
             * img :
             * recommend : 0
             * child : []
             */

            private String linkageid;
            private String parentid;
            private String name;
            private String img;
            private String recommend;
            private List<ChildTBean> child;

            public String getLinkageid() {
                return linkageid;
            }

            public void setLinkageid(String linkageid) {
                this.linkageid = linkageid;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public List<ChildTBean> getChild() {
                return child;
            }

            public void setChild(List<ChildTBean> child) {
                this.child = child;
            }
        }
    }

    public static class ChildTBean {
        /**
         * linkageid : 1
         * parentid : 7
         * name : 出版社
         * img :
         * recommend : 0
         * child : []
         */

        private String linkageid;
        private String parentid;
        private String name;
        private String img;
        private String recommend;
        private List<?> child;

        public String getLinkageid() {
            return linkageid;
        }

        public void setLinkageid(String linkageid) {
            this.linkageid = linkageid;
        }

        public String getParentid() {
            return parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public List<?> getChild() {
            return child;
        }

        public void setChild(List<?> child) {
            this.child = child;
        }
    }

}




