package com.zcedu.openclass.ui.activity.login;

import com.zcedu.openclass.callback.OnHttpCallBack;

/**
 * Created by cheng on 2018/5/4.
 */

public class LoginPresenter implements LoginContract.ILoginPresenter {
    private LoginContract.ILoginModel loginModel;
    private LoginContract.ILoginView loginView;

    public LoginPresenter(LoginContract.ILoginView loginView) {
        this.loginView = loginView;
        loginModel=new LoginModel();
    }

    /**
     * 获取登录的token
     */
    @Override
    public void getToken() {
        loginView.showDialog();
        loginModel.getToken(loginView.getcontext(), loginView.getUserInfo(), new OnHttpCallBack<String>() {
            @Override
            public void onSuccess(String s) {
                loginView.getTokenSuccess();
            }

            @Override
            public void onFail(String msg) {
                loginView.hideDialog();
                loginView.showMsg(msg);
            }
        });
    }

    /**
     * 获取用户信息
     */
    @Override
    public void getUserInfo() {
        loginModel.getUserInfo(loginView.getcontext(), new OnHttpCallBack<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFail(String msg) {
                loginView.hideDialog();
                loginView.showMsg(msg);
            }
        });
    }
}
