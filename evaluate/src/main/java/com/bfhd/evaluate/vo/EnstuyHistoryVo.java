package com.bfhd.evaluate.vo;

/**
 * kxf -> 2019-10-10
 **/
public class EnstuyHistoryVo {

    /**
     * id : 1
     * inputtime : 1570625660
     * type : 1
     * detail_id : 4
     * member_id : 4
     * title : A Fish Sees
     * detail_type : 3
     * uuid : 09a36d3abd97235003f43734e5167863
     */

    private String id;
    private String inputtime;
    private String type;
    private String detail_id;
    private String member_id;
    private String title;
    private String detail_type;
    private String uuid;
    private String download_url;
private String catalog_name;

    public String getCatalog_name() {
        return catalog_name;
    }

    public void setCatalog_name(String catalog_name) {
        this.catalog_name = catalog_name;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail_type() {
        return detail_type;
    }

    public void setDetail_type(String detail_type) {
        this.detail_type = detail_type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "EnstuyHistoryVo{" +
                "id='" + id + '\'' +
                ", inputtime='" + inputtime + '\'' +
                ", type='" + type + '\'' +
                ", detail_id='" + detail_id + '\'' +
                ", member_id='" + member_id + '\'' +
                ", title='" + title + '\'' +
                ", detail_type='" + detail_type + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
