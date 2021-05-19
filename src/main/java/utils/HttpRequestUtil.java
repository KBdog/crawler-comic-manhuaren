package utils;

import com.alibaba.fastjson.JSONObject;
import properties.RunProperties;

import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.*;
import java.util.Map;

//发送http请求工具
public class HttpRequestUtil {
    //获取请求结果json
    public static JSONObject sendRequest(String url){
        URL urlAddress=null;
        HttpURLConnection connection=null;
        InputStreamReader isr=null;
        JSONObject jsonObject=null;
        try {
            urlAddress=new URL(url);
            connection=(HttpURLConnection) urlAddress.openConnection(RunProperties.proxy);
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            //添加请求头
            Map<String, String> headers = HeadersUtil.getInstance();
            for(Map.Entry<String,String>header:headers.entrySet()){
                connection.setRequestProperty(header.getKey(),header.getValue());
            }
            if(connection.getResponseCode()==200){
                isr=new InputStreamReader(connection.getInputStream());
                StringBuffer stringBuffer = IOStringBufferUtil.getStringBuffer(isr);
                jsonObject=JSONObject.parseObject(stringBuffer.toString());
            }
            return jsonObject;
        } catch (IOException e) {
            //重新发送请求
            return sendRequest(url);
        }finally {
            try {
                if(isr!=null){
                    isr.close();
                }
                if(connection!=null){
                    connection.disconnect();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
