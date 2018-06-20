package com.zcedu.openclass.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.zcedu.openclass.BuildConfig;
import com.zcedu.openclass.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cheng on 2018/5/4.
 */

public class Util {

    public static void t(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_LONG).show();
    }

    /**
     * 获取是否使用了代理
     *
     * @return
     */
    public static boolean isWifiProxy(Context context) {
        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            proxyAddress = android.net.Proxy.getHost(context);
            proxyPort = android.net.Proxy.getPort(context);
        }
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }

    /**
     * 获取randomKey
     *
     * @param context
     * @return
     */
    public static String getRandomKey(Context context) {
        return context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("randomKey", "");
    }

    /**
     * 获取token
     *
     * @param context
     * @return
     */
    public static String getToken(Context context) {
        return context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("token", "");
    }

    public static int getUserId(Context context){
        String id = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).getString("id", "");
        return Integer.parseInt(AESUtils.decrypt(context,id));
    }

    /**
     * 获取AES key
     *
     * @param context
     * @return
     */
    public static String getAesKey(Context context) {
        try {
            return context.getResources().getString(R.string.k1) + new KeyUtil().getKey(context).substring(0, 4) + BuildConfig.appKeyPre.substring(0, 4) + "qisl";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取md5 key
     *
     * @param context
     * @return
     */
    public static String getMd5Key(Context context) {
        try {
            return new KeyUtil().getKey(context).substring(4, 8) + BuildConfig.appKeyPre.substring(4, 7) + "$#RW" + context.getResources().getString(R.string.k2);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取AES 模 iv
     *
     * @param context
     * @return
     */
    public static String getIV(Context context) {
        try {
            return "Ksit" + BuildConfig.appKeyPre.substring(7, 11) + context.getResources().getString(R.string.k3) + new KeyUtil().getKey(context).substring(8, 12);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 去掉字符串的特殊字符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 重新登录弹窗
     *
     * @param context
     */
    public static void loginAgain(final Context context, String s) {
        //清除用户信息
        context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit().clear().commit();
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setMessage(s)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .show();
    }

    /**
     * 获取签名信息
     *
     * @param context
     * @return
     */
    public static String getSignInfo(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            return sign.toCharsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void closeDialog(Dialog dialog){
        if (null!=dialog&&dialog.isShowing()) dialog.dismiss();
    }

}
