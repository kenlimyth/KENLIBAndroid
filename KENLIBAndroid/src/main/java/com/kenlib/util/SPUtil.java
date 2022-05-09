package com.kenlib.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences
 * 简单数据持久化XML格式保存，一种以Key-Value的键值对形式保存数据的方式
 */
public class SPUtil {
    static SharedPreferences pre = null;

    /**
     * 获取SharedPreferences，用于读取数据
     *
     * @param context
     * @return
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        if (pre == null) {
            // 获取到SharedPreferences，并且权限为本程序读写。
            pre = context.getSharedPreferences("ken",
                    android.content.Context.MODE_PRIVATE);
        }
        return pre;
    }

    /**
     * 获取SharedPreferences.Editor
     * 用于修改保存数据
     * 编辑后不要忘记提交 editor.commit();//提交修改
     *
     * @param context
     * @return
     */
    public static SharedPreferences.Editor getEdit(Context context) {
        // 获取到SharedPreferences，并且权限为本程序读写。
        if (pre == null) {
            getSharedPreferences(context);
        }

        SharedPreferences.Editor editor = pre.edit();
        return editor;
    }

}
