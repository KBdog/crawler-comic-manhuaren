package entity;

public class Pic {
    //页数
    private Integer picId;
    //每页url
    private String picUrl;

    public Pic() {
    }

    public Pic(Integer picId, String picUrl) {
        this.picId = picId;
        this.picUrl = picUrl;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
