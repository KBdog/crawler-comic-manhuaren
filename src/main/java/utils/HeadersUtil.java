package utils;

import java.util.HashMap;
import java.util.Map;

//获取headers
public class HeadersUtil {
    private static Map<String,String> header=new HashMap<>();
    public synchronized static Map<String,String> getInstance(){
        if(header.isEmpty()){
            header.put("X-Yq-Yqci","{\"le\": \"zh\"}");
            header.put("User-Agent", "okhttp/3.11.0");
            header.put("Referer", "http://www.dm5.com/dm5api/");
            header.put("clubReferer", "http://mangaapi.manhuaren.com/");
        }
        return header;
    }
}
