package entity;

public class Chapter {
    private Integer chapterId;
    private String chapterName;

    public Chapter() {
    }

    public Chapter(Integer chapterId, String chapterName) {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
