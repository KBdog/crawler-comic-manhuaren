import entity.Chapter;
import entity.Comic;
import entity.Pic;
import org.junit.Test;
import parser.ManhuarenParser;
import parser.impl.ManhuarenParserImpl;
import java.io.*;
import java.util.List;

public class TestUrl {
    private ManhuarenParser parser=new ManhuarenParserImpl();
    @Test
    public void TestUrl(){
//        List<Comic> comicList = parser.getComicList("异世界后宫");
//        for(Comic tmp:comicList){
//            System.out.println(tmp.getComicId()+"-"+tmp.getComicName());
//        }

//        List<Chapter> chapterList = parser.getChapterList(60442);
//        for(Chapter tmp:chapterList){
//            System.out.println(tmp.getChapterId()+"-"+tmp.getChapterName());
//        }

        List<Pic> picList = parser.getPicList(1019770);
        for(Pic tmp:picList){
            System.out.println(tmp.getPicId()+"-"+tmp.getPicUrl());
        }

    }
}
