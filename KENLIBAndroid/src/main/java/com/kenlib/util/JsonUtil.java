package com.kenlib.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

/**
 * json 解析
 */
public class JsonUtil {

    public JsonUtil(Context context) {
        // TODO Auto-generated constructor stub

    }

    /**
     * 解析KI多重嵌套json数组
     * @param jsonString
     * @return 结构如{"fields":[["newid","adminname"]],"data":[["595","系统用户"],[
     * "596","无锡藕塘托老院"],["596","无锡藕塘托老院"]]}
     */
    public static List<Map<String, String>> psJsonDataList(String jsonString) {

        if (jsonString == null || "".equals(jsonString.trim())) {
            return null;
        }
        //解码
        jsonString = Base64Coder.decodeString(jsonString);
        //Utility.showLogTest("jsonString=" + jsonString);

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {

            JSONObject jsonObject3 = new JSONObject(jsonString);

            JSONArray jsonArray = jsonObject3.has("fields") ? jsonObject3
                    .getJSONArray("fields") : null;
            JSONArray sjjsonArray = jsonObject3.has("data") ? jsonObject3
                    .getJSONArray("data") : null;

            if (jsonArray == null || sjjsonArray == null) {
                Util.showLogDebug("fields||data=null");
                return null;
            }

            ArrayList<String> zdarrayList = new ArrayList<String>();

            JSONArray zdJsonArray = (JSONArray) jsonArray.get(0);
            for (int j = 0; j < zdJsonArray.length(); j++) {
                // System.out.println("zdarrayList=" + zdJsonArray.get(j));
                zdarrayList.add(zdJsonArray.get(j).toString());
            }
            for (int j = 0; j < sjjsonArray.length(); j++) {
                // System.out.println("sjjsonArray=" + sjjsonArray.get(j));

                JSONArray jsonArray112 = (JSONArray) sjjsonArray.get(j);
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < jsonArray112.length(); i++) {

                    map.put(zdarrayList.get(i), "null".equals(jsonArray112.get(
                            i).toString()) ? "" : jsonArray112.get(i)
                            .toString());// 过滤null

                }
                list.add(map);

            }

        } catch (JSONException e) {
            Util.showLogDebug("psJsonDataList--JSONException" + e.toString());
            return null;
        }
        return list;

    }

    /**
     * 解析格式{"code":0,"ppzongshu":"132","ppdangqian":"132",
     * "list":[{"tel":"13015680758"
     * ,"pswd":null,"nicheng":"麦子爱2010","sex":"女","juzhudi":"女"},
     * {"tel":"13015680758"
     * ,"pswd":null,"nicheng":"麦子爱2010","sex":"女","juzhudi":"女"}]}
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, String>> psJsonDataList1(String jsonString) {

        if (jsonString == null || "".equals(jsonString.trim())) {
            return null;
        }
        // 解码
        jsonString = Base64Coder.decodeString(jsonString);
        //Utility.showLogTest("jsonString=" + jsonString);

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {

            JSONObject jsonObject3 = new JSONObject(jsonString);

            JSONArray jsonArray = jsonObject3.has("list") ? jsonObject3
                    .getJSONArray("list") : null;

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Map<String, String> map = new HashMap<String, String>();
                map.put("tel", jsonObject.getString("tel"));
                map.put("nicheng",
                        "null".equals(jsonObject.getString("nicheng")) ? ""
                                : jsonObject.getString("nicheng"));
                map.put("sex", jsonObject.getString("sex"));
                map.put("juzhudi", jsonObject.getString("juzhudi"));
                map.put("zhiye", jsonObject.getString("zhiye"));
                map.put("yueshouru",
                        "null".equals(jsonObject.getString("yueshouru")) ? ""
                                : jsonObject.getString("yueshouru"));
                map.put("zhaopian", jsonObject.getString("zhaopian"));
                map.put("nianling", jsonObject.getString("nianling"));
                map.put("isshimingrenzheng",
                        jsonObject.getString("isshimingrenzheng"));
                map.put("ppd",
                        jsonObject.getString("ppd"));

                list.add(map);

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }
        return list;

    }

}
