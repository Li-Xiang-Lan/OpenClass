package com.zcedu.openclass.ui.activity.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.zcedu.openclass.bean.UserInfo;
import com.zcedu.openclass.callback.OnHttpCallBack;
import com.zcedu.openclass.util.AESUtils;
import com.zcedu.openclass.util.HttpAddress;
import com.zcedu.openclass.util.MD5Utils;
import com.zcedu.openclass.util.MyHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cheng on 2018/5/4.
 */

public class LoginModel implements LoginContract.ILoginModel {
    /**
     * 获取登录的token
     * @param context
     * @param info
     * @param callBack
     */
    @Override
    public void getToken(final Context context, UserInfo info, final OnHttpCallBack<String> callBack) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone",info.getPhone());
            jsonObject.put("source","APP");
            jsonObject.put("password", MD5Utils.encode(info.getPwd()));
            new MyHttpUtil().getHomeData(false, context, jsonObject, HttpAddress.LOGIN, new OnHttpCallBack<String>() {
                @Override
                public void onSuccess(String s) {
                    parseTokenJson(context,s,callBack);
                }

                @Override
                public void onFail(String msg) {
                    callBack.onFail(msg);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析token josn
     * @param context
     * @param s
     * @param callBack
     */
    private void parseTokenJson(Context context, String s, OnHttpCallBack<String> callBack) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String token = jsonObject.optString("token");
            String[] split = token.split("\\.");
            if (split.length==3){  //token长度正确
                SharedPreferences.Editor userinfo = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit();
                userinfo.putString("token", AESUtils.encrypt(context,token));
                userinfo.putString("randomKey", new JSONObject(new String(Base64.decode(split[1].getBytes(), Base64.DEFAULT))).optString("randomKey")).commit();
                //获取用户信息
                callBack.onSuccess("");
            }else { //token长度出错
                callBack.onFail("登录失败");
            }
        } catch (JSONException e) {
            callBack.onFail("登录失败");
        }
    }

    /**
     * 获取用户信息
     * @param context
     * @param callBack
     */
    @Override
    public void getUserInfo(Context context, final OnHttpCallBack<String> callBack) {
        new MyHttpUtil().getDataSame(context, HttpAddress.GET_USER_INFO, null, new OnHttpCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("qwer",s);
            }

            @Override
            public void onFail(String msg) {
                callBack.onFail(msg);
            }
        });
    }
}
