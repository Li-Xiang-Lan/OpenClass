package com.zcedu.openclass.util;

import android.content.Context;

/**
 * AES密钥  MD5key C++保存部分
 * Created by cheng on 2018/3/2.
 */

public class KeyUtil {
    static {
        System.loadLibrary("project");
    }
    /**
     * 获取AES  MD5 部分key  前四个是AES  后4个是MD5
     * @param context
     * @return
     */
    public native String getKey(Context context);
}
