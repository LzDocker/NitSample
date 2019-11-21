package com.bfhd.evaluate.vo;

/**
 * kxf -> 2019-09-25
 **/
public class DownloadFileVo {
    private String name;
    private String url;
    private int type;//0 点读/1配音 ，2文件 3，音频，4视频
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DownloadFileVo() {
    }

    public DownloadFileVo(String name, String url, int type, long time) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.time = time;
    }

    @Override
    public String toString() {
        return "DownloadInfoVo{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", time=" + time +
                '}';
    }
}
