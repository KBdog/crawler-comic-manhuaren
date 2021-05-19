package parser;

import entity.Chapter;
import entity.Comic;
import entity.Pic;

import java.util.List;

//解析器接口
public interface ManhuarenParser {
    //获取漫画集合
    List<Comic>getComicList(String keyword);
    //获取章节集合
    List<Chapter>getChapterList(Integer comicId);
    //获取图片集合
    List<Pic>getPicList(Integer chapterId);
}
