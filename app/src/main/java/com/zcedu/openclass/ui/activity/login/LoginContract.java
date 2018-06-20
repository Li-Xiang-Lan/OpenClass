package com.zcedu.openclass.ui.activity.login;

import android.content.Context;

import com.zcedu.openclass.bean.UserInfo;
import com.zcedu.openclass.callback.OnHttpCallBack;

/**
 * Created by cheng on 2018/5/4.
 */

public class LoginContract {
    interface ILoginView{
        Context getcontext();
        UserInfo getUserInfo();
        void showDialog();
        void hideDialog();
        void showMsg(String msg);
        void getTokenSuccess();
        void loginSuccess();
    }

    interface ILoginPresenter{
        void getToken();
        void getUserInfo();
    }

    interface ILoginModel{
        void getToken(Context context, UserInfo info, OnHttpCallBack<String> callBack);
        void getUserInfo(Context context, OnHttpCallBack<String> callBack);
    }
}
