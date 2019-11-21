package com.bfhd.evaluate.vo;

import java.io.Serializable;
import java.util.List;

/**
 * kxf -> 2019-09-27
 **/
public class LessonDetailVo implements Serializable {

    /**
     * id : 1
     * inputtime : 1569043450
     * book_id : 1
     * catalog_id : 1
     * content : You know that sleep is vital to your physical and mental health. But, how can you tell whether you’re truly sleeping well? Especially if you work shifts, your sleep probably does not look exactly like other peoples’ sleep. It can be hard to measure your sleep patterns against those of the people around you.    On average, adults should optimally receive between seven and nine hours of sleep each night, but those needs vary individually. For example, some people feel best with eight consecutive hours of sleep, while others do well with six to seven hours at night and daytime napping. Some people feel okay when their sleep schedule changes, while others feel very affected by a new schedule or even one night of insufficient sleep.
     * radio :
     * name : What is Healthy Sleep? 6 Sleep tips to better sleep
     * sort_content : [{"order":"1q","en":"ffddfdf","text":null,"img":"/var/upload/image/2019/09/2019092502023221002_458x285.png","start_time":"00:01:11","end_time":"00:01:22"},{"order":"qqq","en":"4534","text":null,"img":"/var/upload/image/2019/09/2019092502035816201_458x285.png","start_time":"00:01:22","end_time":"00:01:59"},{"order":"qw","en":"123123123","text":null,"img":"/var/upload/image/2019/09/2019092502040487088_1280x734.png","start_time":"00:02:00","end_time":"00:02:29"},{"order":"去23","en":"123123","text":null,"img":"/var/upload/image/2019/09/2019092502040916064_750x424.png","start_time":"00:03:56","end_time":"00:03:59"}]
     * read_num :
     */

    private String id;
    private String inputtime;
    private String book_id;
    private String catalog_id;
    private String content;
    private String radio;
    private String name;
    private String read_num;
    private String notes;
    private String download_url;

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    private List<SortContentBean> sort_content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getCatalog_id() {
        return catalog_id;
    }

    public void setCatalog_id(String catalog_id) {
        this.catalog_id = catalog_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public List<SortContentBean> getSort_content() {
        return sort_content;
    }

    public void setSort_content(List<SortContentBean> sort_content) {
        this.sort_content = sort_content;
    }

    public static class SortContentBean implements Serializable {
        /**
         * order : 1q
         * en : ffddfdf
         * text : null
         * img : /var/upload/image/2019/09/2019092502023221002_458x285.png
         * start_time : 00:01:11
         * end_time : 00:01:22
         */

        private String order;
        private String en;
        private String text;
        private String img;
        private String start_time;
        private String end_time;
        private boolean isChoose = false;
        private boolean isCH = false;

        public boolean isChoose() {
            return isChoose;
        }

        public void setChoose(boolean choose) {
            isChoose = choose;
        }

        public boolean isCH() {
            return isCH;
        }

        public void setCH(boolean CH) {
            isCH = CH;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        @Override
        public String toString() {
            return "SortContentBean{" +
                    "order='" + order + '\'' +
                    ", en='" + en + '\'' +
                    ", text='" + text + '\'' +
                    ", img='" + img + '\'' +
                    ", start_time='" + start_time + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", isChoose=" + isChoose +
                    ", isCH=" + isCH +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LessonDetailVo{" +
                "id='" + id + '\'' +
                ", inputtime='" + inputtime + '\'' +
                ", book_id='" + book_id + '\'' +
                ", catalog_id='" + catalog_id + '\'' +
                ", content='" + content + '\'' +
                ", radio='" + radio + '\'' +
                ", name='" + name + '\'' +
                ", read_num='" + read_num + '\'' +
                ", sort_content=" + sort_content +
                '}';
    }
}
