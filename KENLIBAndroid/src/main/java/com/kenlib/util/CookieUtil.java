package com.kenlib.util;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cookie相关的操作实用程序
 */
public class CookieUtil {


    public static final String COOKIE_BW_ETERNITY = "bweternity";
    public static final String COOKIE_BW_DOMAIN = ".bookwalker.jp";

    /**
     * Cookie的预约语
     */
    private static final List<String> RESERVED_WORDS = Collections.unmodifiableList
            (new ArrayList<String>() {{
                add("Max-Age");
                add("path");
                add("domain");
                add("expires");
            }});

    /**
     * 取得对象URL的Cookies
     */
    public static HashMap<String, String> getCookies(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(url);
        HashMap<String, String> cookieMap = new HashMap<>();
        if (!TextUtils.isEmpty(cookies)) {
            for (String cookie : cookies.split("; ")) {
                String[] kv = cookie.split("=");
                if (checkReservedWord(kv[0])) continue;
                cookieMap.put(kv[0], kv[1]);
            }
        }
        return cookieMap;
    }

    public static String getPortalCookies(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        return cookieManager.getCookie(url);
    }

    public static HashMap<String, String> parseCookies(String cookies) {
        HashMap<String, String> cookieMap = new HashMap<>();
        if (!TextUtils.isEmpty(cookies)) {
            for (String cookie : cookies.split("; ")) {
                String[] kv = cookie.split("=");
                if ("st_kp_ap_login_account".equals(kv[0])) {
                } else {
                    continue;
                }
                cookieMap.put(kv[0], kv[1]);
            }
        }
        return cookieMap;
    }

    /**
     * 在Cookies中设置用value映射的散列
     */
    public static void setCookies(HashMap<String, String> map, CookieManager cookieManager, Context context) {
        if (null != map && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (null == cookieManager) {
                    cookieManager = CookieManager.getInstance();
                    CookieSyncManager.createInstance(context);
                }
                if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                    String cookieSet = entry.getKey() + "=" + entry.getValue() + "; domain=" + COOKIE_BW_DOMAIN + ";";
                    cookieManager.setCookie(COOKIE_BW_DOMAIN, cookieSet);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        cookieManager.flush();
                    }
                    CookieSyncManager.getInstance().sync();
                }
            }
        }
    }

    /**
     * removeAllCookies
     */
    public static void removeAllCookies(CookieManager cookieManager) {
        if (null == cookieManager) {
            cookieManager = CookieManager.getInstance();
        }
        if (isOverLollipop()) {
            cookieManager.removeSessionCookies(null);
            cookieManager.removeAllCookies(null);
        } else {
            cookieManager.removeSessionCookie();
            cookieManager.removeAllCookie();
        }
    }

    /**
     * removeAllCookie
     *
     * @param context
     */
    public static void removeAllCookie(Context context, String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        // Cookiesの初期化と持ち越し処理
        CookieSyncManager.createInstance(context);
        String cookies = cookieManager.getCookie(url + "/");
        if (!TextUtils.isEmpty(cookies)) {
            // 持ち越す値を抽出
            HashMap<String, String> cookiesMap = new HashMap<>();
            String[] cookieSet = cookies.split("; ");
            for (String cookie : cookieSet) {
                String[] keyVal = cookie.split("=");
                if (2 == keyVal.length) {
                    // v4.2.3現在、持ち越す値は"bweternity"と"{st|cm}_kp_ap_login_account"
                    if (keyVal[0].equals(COOKIE_BW_ETERNITY)) {
                        cookiesMap.put(keyVal[0], keyVal[1]);
                    }
                }
            }
            // Cookiesを初期化
            removeAllCookies(cookieManager);
            // 持ち越す値を再設定
            setCookies(cookiesMap, cookieManager, null);
        }
    }

    /**
     * <p>OS返回Lolipop（5.0）或以上</p>
     * ※Cookie Manager在Lolipop前后会改变推荐方法，请注意各规格
     */
    private static boolean isOverLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Cookie的预约语检查
     */
    public static boolean checkReservedWord(String s) {
        return RESERVED_WORDS.contains(s);
    }

}
