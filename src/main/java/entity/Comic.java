package entity;

public class Comic {
    private Integer comicId;
    private String comicName;

    public Comic(Integer comicId, String comicName) {
        this.comicId = comicId;
        this.comicName = comicName;
    }

    public Comic() {
    }

    public Integer getComicId() {
        return comicId;
    }

    public void setComicId(Integer comicId) {
        this.comicId = comicId;
    }

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }
}
