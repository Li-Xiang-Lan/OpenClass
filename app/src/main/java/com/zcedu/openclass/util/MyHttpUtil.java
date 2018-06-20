package com.zcedu.openclass.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zcedu.openclass.callback.OnHttpCallBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * http访问的封装类
 * Created by cheng on 2018/2/27.
 */

public class MyHttpUtil {
    private Context context;
    private boolean byUser;

    /**
     * 访问地址和权限地址一致时 调用此方法获取数据
     * @param url
     * @param content
     * @param callBack
     */
    public void getDataSame(Context context,String url, final JSONObject content, final OnHttpCallBack<String> callBack) {
        this.context=context;
        //判断网络
        if (!NetWorkUtil.isNetworkAvailable(context)){
            callBack.onFail("网络出错");
            return;
        }
        //判断是否使用了代理
        if (Util.isWifiProxy(context)){
            callBack.onFail("访问出错");
            return;
        }
        if (null==content){  //传的内容是空  用get获取信息
            OkGo.<String>get(HttpAddress.URL+url)
                    .headers("Authorization","Bearer "+Util.replaceBlank(Util.getToken(context)))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            parseDataJson(response.body(),callBack);
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Log.e("qwer",response.code()+"..."+response.message());
                            callBack.onFail("访问出错");
                        }
                    });

        }else {  //传过来的数据内容不是空  获取签名验证
            String data = getFinalData(content,Util.getRandomKey(context));
            OkGo.<String>post(HttpAddress.URL+url)
                    .headers("Authorization","Bearer "+Util.replaceBlank(Util.getToken(context)))
                    .upJson(data)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            parseDataJson(response.body(),callBack);
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Log.e("qwer",response.code()+"..."+response.message());
                            callBack.onFail("访问出错");
                        }
                    });
        }
    }

    /**
     * 访问地址和权限地址不一致时 调用此方法获取数据
     * @param authorityUrl 验证权限的url
     * @param visitUrl 要访问的url
     * @param content
     * @param byUser 是否是用户手动交互  是的话 返回的错误信息会不一样
     */
    public void getDataNotSame(final boolean byUser, Context context, final String authorityUrl, final String visitUrl, final JSONObject content, final OnHttpCallBack<String> callBack) {
        this.context=context;
        this.byUser=byUser;
        //判断网络
        if (!NetWorkUtil.isNetworkAvailable(context)){
            callBack.onFail(byUser?"网络异常，请检查网络！":"网络出错");
            return;
        }
        //判断是否使用了代理
        if (Util.isWifiProxy(context)){
            callBack.onFail(byUser?"访问服务器出错":"访问出错");
            return;
        }
        if (null==content){  //传的内容是空  用get获取信息
            OkGo.<String>get(HttpAddress.URL+visitUrl)
                    .headers("Authorization","Bearer "+Util.replaceBlank(Util.getToken(context)))
                    .headers("Permission",Util.replaceBlank(AESUtils.encrypt(context,authorityUrl)))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            parseDataJson(response.body(),callBack);
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Log.e("qwer",response.code()+"..."+response.message());
                            callBack.onFail(byUser?"访问服务器出错":"访问出错");
                        }
                    });

        }else {  //传过来的数据内容不是空  获取签名验证
            String data = getFinalData(content,Util.getRandomKey(context));
            OkGo.<String>post(HttpAddress.URL+visitUrl)
                    .headers("Authorization","Bearer "+Util.replaceBlank(Util.getToken(context)))
                    .headers("Permission",Util.replaceBlank(AESUtils.encrypt(context,authorityUrl)))
                    .upJson(data)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            parseDataJson(response.body(),callBack);
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Log.e("qwer",response.code()+"..."+response.message());
                            callBack.onFail(byUser?"访问服务器出错":"访问出错");
                        }
                    });
        }
    }


    /**
     * 解析获取的数据
     * @param body
     * @param callBack
     */
    private void parseDataJson(String body, OnHttpCallBack<String> callBack) {
        try {
            String data = AESUtils.decrypt(context, new JSONObject(body).optString("data"));
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.optInt("code");
            if (code==1001)  //获取成功
                callBack.onSuccess(jsonObject.optString("data"));
            else if (code==1005){  //token失效 重新登录
                //重新登录
                Util.loginAgain(context,"登录失效，请重新登录");
            }
            else callBack.onFail(byUser?"访问服务器"+jsonObject.optString("msg"):jsonObject.optString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取最终加密后的json数据
     * @param content
     * @return
     */
    public String getFinalData(JSONObject content,String randomKey){
        try {
            //md5加密数据获取签名
            String sign;
            if (TextUtils.isEmpty(randomKey)) sign=MD5Utils.encode(content.toString().replace("\\","") + Util.getMd5Key(context));
            else sign=MD5Utils.encode(content.toString().replace("\\","") + Util.getMd5Key(context)+randomKey);
            //封装数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("datas",content);
            jsonObject.put("sign",sign);
            JSONObject object = new JSONObject();
            object.put("data",AESUtils.encrypt(context,jsonObject.toString()));
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取首页数据的接口访问封装类
     * @param context
     * @param content
     * @param url  访问地址 例如 system/sms/send
     * @param byUser 是否是 用户主动交互
     */
    public void getHomeData(final boolean byUser, Context context, JSONObject content, String url, final OnHttpCallBack<String> callBack){
        this.context=context;
        this.byUser=byUser;
        //判断网络
        if (!NetWorkUtil.isNetworkAvailable(context)){
            callBack.onFail(byUser?"网络异常，请检查网络！":"网络出错");
            return;
        }
        //判断是否使用了代理
        if (Util.isWifiProxy(context)){
            callBack.onFail(byUser?"访问服务器出错":"访问出错");
            return;
        }
        //访问
        if (null==content){  //get请求
            OkGo.<String>get(HttpAddress.URL+url)
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            parseHomeDataJson(response.body(),callBack);
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            callBack.onFail(byUser?"访问服务器出错":"访问出错");
                            Log.e("qwer",response.code()+".."+response.message());
                        }
                    });
        }else {  //post请求
            //获取签名数据
            String data=getFinalData(content,"");
            OkGo.<String>post(HttpAddress.URL+url)
                    .upJson(data)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            parseHomeDataJson(response.body(),callBack);
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Log.e("qwer",response.code()+"..."+response.message());
                            callBack.onFail(byUser?"访问服务器出错":"访问出错");
                        }
                    });
        }
    }

    /**
     * 解析首页数据json
     * @param body
     * @param callBack
     */
    private void parseHomeDataJson(String body, OnHttpCallBack<String> callBack) {
        try {
            String data = AESUtils.decrypt(context,new JSONObject(body).optString("data"));
            JSONObject jsonObject = new JSONObject(data);
            if (TextUtils.isEmpty(data)){  //解密数据走异常
                callBack.onFail(byUser?"访问服务器出错":"访问出错");
                return;
            }
            if (jsonObject.optInt("code")==1001) //获取数据成功
                callBack.onSuccess(jsonObject.optString("data"));
            else //获取数据失败
            callBack.onFail(byUser?"访问服务器"+jsonObject.optString("msg"):jsonObject.optString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取验证码
     * @param context
     * @param phone
     * @param smsType
     * @param url
     * @param callBack
     */
    public void getSmsCode(Context context, String phone, String smsType, String url, final OnHttpCallBack<String> callBack){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone",phone);
            jsonObject.put("smsType",smsType);
            jsonObject.put("dataSource","APP");
            getHomeData(true,context,jsonObject, url, new OnHttpCallBack<String>() {
                @Override
                public void onSuccess(String s) {
                    callBack.onSuccess(s);
                }

                @Override
                public void onFail(String msg) {
                    callBack.onSuccess(msg);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
