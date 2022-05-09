package com.kenlib.http.retrofitrxjava;

import com.kenlib.util.ApplicationUtil;
import com.kenlib.util.SPUtil;

/**
 * HTTP配置类
 */
public class HttpConfig {

    // baseurl
//    public static String BASE_URL = "http://47.101.173.37:8034";
    public static String BASE_URL = "http://";
    public static String DEFAULT_IP = "47.101.173.37:8034";

    // http缓存目录
    public static String DIR_CACHE_FILE = "tmp";

    // 连接超时时间，单位秒
    public static int HTTP_TIME_OUT_TIME = 5;

    public static String CHECK_API = "/api/ServerInfo.ashx";

    public static String getBaseUrl(){
        return  BASE_URL+ SPUtil.getSharedPreferences(ApplicationUtil.getContext()).getString("ip",HttpConfig.DEFAULT_IP);
    }

}


