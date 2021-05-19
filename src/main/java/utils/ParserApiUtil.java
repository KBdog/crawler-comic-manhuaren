package utils;



import okhttp3.HttpUrl;
import okhttp3.Request;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

//解析url工具类
public class ParserApiUtil {
    //每页大小
    private int pageSize=20;
    //漫画人域名
    private String baseUrl = "http://mangaapi.manhuaren.com";
    //密钥
    private String secretKey="4e0a48e1c0b54041bce9c8f0e036124d";

    //签名算法
    private String generateGSNHash(HttpUrl url) {
        String s=secretKey+"GET";
        //请求参数
        Set<String> parameterNames = url.queryParameterNames();
        //排序
        ArrayList<String> setList = new ArrayList<>(parameterNames);
        Collections.sort(setList);
        //遍历
        for(String tmp:setList){
            if(tmp!="gsn"){
                s+=tmp;
                s+=urlEncode(url.queryParameterValues(tmp).get(0));
            }
        }
        s+=secretKey;
        return hashString("md5", s);
    }
    //url编码
    private String urlEncode(String str) {
        String result=null;
        try {
            result=URLEncoder.encode(str,"utf-8")
                    .replace("+", "%20")
                    .replace("%7E", "~")
                    .replace("*", "%2A");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            return result;
        }
    }
    //计算hash值
    private String hashString(String type,String input){
        final String hashChar="0123456789abcdef";
        MessageDigest md= null;
        try {
            md = MessageDigest.getInstance(type);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(input.getBytes());
        byte[] bytes = md.digest();
        StringBuilder result=new StringBuilder();
        for(byte tmp:bytes){
            result.append(hashChar.charAt((tmp>>4) & 0x0f));
            result.append(hashChar.charAt(tmp & 0x0f));
        }
        return result.toString();
    }

    //获取url
    private Request getUrl(HttpUrl url) {
        //时间格式
        SimpleDateFormat timePattern = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");
        //当前发起请求时间
        String now= timePattern.format(new Date());
        //未签名的url
        HttpUrl.Builder realUrl = url.newBuilder()
                .setQueryParameter("gsm", "md5")
                .setQueryParameter("gft", "json")
                .setQueryParameter("gts", now)
                .setQueryParameter("gak", "android_manhuaren2")
                .setQueryParameter("gat", "")
                .setQueryParameter("gaui", "191909801")
                .setQueryParameter("gui", "191909801")
                .setQueryParameter("gut", "0");
        //签名的url
        return new Request.Builder()
                .url(realUrl.setQueryParameter("gsn", generateGSNHash(realUrl.build())).build())
                .build();
    }



    //搜索漫画
    public Request searchManga(int page,String keyword) {
        //构建url
        HttpUrl.Builder httpBuilder=HttpUrl.parse(baseUrl).newBuilder();
        httpBuilder.addQueryParameter("start", (pageSize * (page - 1))+"");
        httpBuilder.addQueryParameter("limit",pageSize+"");
        if(keyword!=null&&keyword!=""){
            httpBuilder.addQueryParameter("keywords",keyword);
            httpBuilder.addPathSegments("v1/search/getSearchManga");
        }
        return getUrl(httpBuilder.build());
    }

    //搜索章节
    public Request searchChapter(int mangaId)  {
        HttpUrl.Builder builder=HttpUrl.parse(baseUrl).newBuilder();
        builder.addQueryParameter("mangaId",mangaId+"");
        builder.addPathSegments("v1/manga/getDetail");
        return getUrl(builder.build());
    }

    //搜索图片
    public Request searchPic(int chapterId){
        HttpUrl.Builder builder=HttpUrl.parse(baseUrl).newBuilder();
        builder.addQueryParameter("netType", "4")
                .addQueryParameter("loadreal", "1")
                .addQueryParameter("imageQuality", "2");
        builder.addQueryParameter("mangaSectionId",chapterId+"");
        builder.addPathSegments("v1/manga/getRead");
        return getUrl(builder.build());
    }

}
