package properties;

import entity.Chapter;
import entity.Comic;
import parser.ManhuarenParser;
import us.codecraft.webmagic.Spider;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

//全局属性
public class RunProperties {
    public static Spider spider=null;
    public static String crawlerDirectory="D:/Comic/manhuaren/";
    public static Comic comic=null;
    public static List<Chapter> chapterList=null;
    public static volatile int flag=0;
    public static int chapterList_size=0;
    public static ManhuarenParser parser=null;
    //代理
    public static Proxy proxy=new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",1080));

}
