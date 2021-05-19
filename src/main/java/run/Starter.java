package run;

import crawler.CrawlerComicPipeline;
import crawler.CrawlerComicProcessor;
import entity.Comic;
import parser.impl.ManhuarenParserImpl;
import properties.RunProperties;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.Scanner;

//启动器
public class Starter {
    public static void main(String[] args) {
        //监控程序结束线程
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if(RunProperties.spider!=null){
                    System.out.println("漫画已下载完毕,程序结束！");
                }else {
                    System.out.println("程序结束！");
                }
            }
        }));
        //开始
        RunProperties.parser=new ManhuarenParserImpl();
        Scanner in=new Scanner(System.in);
        System.out.print("请输入漫画存放目录(若不设置默认为"+RunProperties.crawlerDirectory+"):");
        String directory=in.nextLine();
        if(directory!=null&&!directory.equals("")){
            RunProperties.crawlerDirectory=directory;
        }
        System.out.println("存放目录:"+RunProperties.crawlerDirectory);
        System.out.println("---------------------------------------");
        System.out.print("请输入漫画关键字进行搜索:");
        String keyword=in.nextLine();
        List<Comic> comicList=null;
        if(keyword!=null&&!keyword.equals("")){
            System.out.println("关键字:"+keyword);
            System.out.println("关键字长度:"+keyword.length());
            comicList=RunProperties.parser.getComicList(keyword);
        }
        //如果为空则重试十次
        if(comicList==null){
            for(int i=0;i<10;i++){
                comicList=RunProperties.parser.getComicList(keyword);
                if(comicList!=null){
                    break;
                }
            }
        }
        System.out.println("---------------------------------------");
        if(comicList!=null){
            int i=1;
            for(Comic comic:comicList){
                if(comic!=null){
                    System.out.println(i+"-"+comic.getComicName());
                    i++;
                }
            }
            System.out.println("---------------------------------------");
            System.out.print("请输入您要爬的漫画序号:");
            RunProperties.comic=comicList.get(Integer.parseInt(in.nextLine())-1);
            System.out.println("漫画名:"+RunProperties.comic.getComicName());
            //根据漫画信息查询章节信息
            RunProperties.chapterList = RunProperties.parser.getChapterList(RunProperties.comic.getComicId());
            //如果为空重试十次
            if(RunProperties.chapterList==null||RunProperties.chapterList.size()==0){
                for(int j=0;j<10;j++){
                    RunProperties.chapterList = RunProperties.parser.getChapterList(RunProperties.comic.getComicId());
                    if(RunProperties.chapterList!=null){
                        break;
                    }
                }
            }
            RunProperties.chapterList_size=RunProperties.chapterList.size();
            System.out.println("总共:"+RunProperties.chapterList_size+"话");
            System.out.println("---------------------------------------");
            //开始爬虫
            RunProperties.spider= Spider.create(new CrawlerComicProcessor());
            RunProperties.spider.addPipeline(new CrawlerComicPipeline())
                    .addUrl("https://www.baidu.com")
                    .thread(5)
                    .start();
        }else {
            System.out.println("漫画不存在!");
        }
        in.close();
    }
}
