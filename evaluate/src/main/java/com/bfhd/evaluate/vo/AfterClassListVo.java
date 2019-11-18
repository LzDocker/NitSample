package com.bfhd.evaluate.vo;

/**
 * kxf -> 2019-09-29
 **/
public class AfterClassListVo {


    /**
     * id : 5
     * inputtime : 1569829177
     * type : 1
     * fileurl : /var/upload/file/2019/09/2019093007402693666.doc
     * tags :
     * fileurl_name : 测试doc
     * fileurl_size : 9216
     * fileurl_ext : doc
     * status : 0
     * layweruid : 0
     * uuid :
     * filedesc : <p>人不带能感受到豁免</p>
     * introduction : <p><span>人不带能感受到豁免</span><br /></p>
     * detail : <p><span>人不带能感受到豁免</span><br /></p>
     */
    public boolean isCheck = false;
    public boolean isShow = false;


    private String id;
    private String inputtime;
    private String type;
    private String fileurl;
    private String tags;
    private String fileurl_name;
    private String fileurl_size;
    private String fileurl_ext;
    private String status;
    private String layweruid;
    private String uuid;
    private String filedesc;
    private String introduction;
    private String detail;
    private String detail_type;

    public String getDetail_type() {
        return detail_type;
    }

    public void setDetail_type(String detail_type) {
        this.detail_type = detail_type;
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

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFileurl_name() {
        return fileurl_name;
    }

    public void setFileurl_name(String fileurl_name) {
        this.fileurl_name = fileurl_name;
    }

    public String getFileurl_size() {
        return fileurl_size;
    }

    public void setFileurl_size(String fileurl_size) {
        this.fileurl_size = fileurl_size;
    }

    public String getFileurl_ext() {
        return fileurl_ext;
    }

    public void setFileurl_ext(String fileurl_ext) {
        this.fileurl_ext = fileurl_ext;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLayweruid() {
        return layweruid;
    }

    public void setLayweruid(String layweruid) {
        this.layweruid = layweruid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFiledesc() {
        return filedesc;
    }

    public void setFiledesc(String filedesc) {
        this.filedesc = filedesc;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
