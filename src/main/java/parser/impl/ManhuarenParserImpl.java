package parser.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import entity.Chapter;
import entity.Comic;
import entity.Pic;
import okhttp3.Request;
import parser.ManhuarenParser;
import utils.HttpRequestUtil;
import utils.ParserApiUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ManhuarenParserImpl implements ManhuarenParser {
    private ParserApiUtil apiUtil=new ParserApiUtil();
    @Override
    public List<Comic> getComicList(String keyword) {
        List<Comic>comicList=null;
        Request comics = apiUtil.searchManga(1, keyword);
        JSONObject jsonObject = HttpRequestUtil.sendRequest(comics.url().toString());
        if(jsonObject!=null){
            comicList=new ArrayList<>();
            JSONArray jsonArray=jsonObject.getJSONObject("response").getJSONArray("result");
            for(Object singleComic:jsonArray){
                JSONObject tmp=(JSONObject)singleComic;
                Comic tmpComic=new Comic(tmp.getInteger("mangaId"),tmp.getString("mangaName"));
                comicList.add(tmpComic);
            }
        }
        return comicList;
    }

    @Override
    public List<Chapter> getChapterList(Integer comicId) {
        List<Chapter>chapterList=null;
        Request chapters = apiUtil.searchChapter(comicId);
        JSONObject jsonObject=HttpRequestUtil.sendRequest(chapters.url().toString());
        if(jsonObject!=null){
            chapterList=new ArrayList<>();
            JSONArray mangaEpisode = jsonObject.getJSONObject("response").getJSONArray("mangaEpisode");
            JSONArray mangaWords = jsonObject.getJSONObject("response").getJSONArray("mangaWords");
            JSONArray mangaRolls = jsonObject.getJSONObject("response").getJSONArray("mangaRolls");
            if(mangaEpisode.size()>0){
                for(Object singleChapter:mangaEpisode){
                    JSONObject tmp=(JSONObject)singleChapter;
                    Chapter tmpChapter=new Chapter(tmp.getInteger("sectionId"),tmp.getString("sectionName"));
                    chapterList.add(tmpChapter);
                }
            }
            if(mangaWords.size()>0){
                for(Object singleChapter:mangaWords){
                    JSONObject tmp=(JSONObject)singleChapter;
                    Chapter tmpChapter=new Chapter(tmp.getInteger("sectionId"),tmp.getString("sectionName"));
                    chapterList.add(tmpChapter);
                }
            }
            if(mangaRolls.size()>0){
                for(Object singleChapter:mangaRolls){
                    JSONObject tmp=(JSONObject)singleChapter;
                    Chapter tmpChapter=new Chapter(tmp.getInteger("sectionId"),tmp.getString("sectionName"));
                    chapterList.add(tmpChapter);
                }
            }
            //排序
            Collections.sort(chapterList, new Comparator<Chapter>() {
                @Override
                public int compare(Chapter o1, Chapter o2) {
                    return -1;
                }
            });
        }
        return chapterList;
    }

    @Override
    public List<Pic> getPicList(Integer chapterId) {
        List<Pic>picList=null;
        Request pics = apiUtil.searchPic(chapterId);
        JSONObject jsonObject = HttpRequestUtil.sendRequest(pics.url().toString());
        if(jsonObject!=null){
            picList=new ArrayList<>();
            String host = jsonObject.getJSONObject("response").getJSONArray("hostList").get(0).toString();
            String query = jsonObject.getJSONObject("response").getString("query");
            JSONArray picArray = jsonObject.getJSONObject("response").getJSONArray("mangaSectionImages");
            for(int i=0;i<picArray.size();i++){
                Pic tmpPic=new Pic(i,host+picArray.get(i).toString()+query);
                picList.add(tmpPic);
            }
        }
        return picList;
    }
}
