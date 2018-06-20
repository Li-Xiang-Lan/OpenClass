package com.zcedu.openclass.ui.activity.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcedu.openclass.R;
import com.zcedu.openclass.bean.UserInfo;
import com.zcedu.openclass.statuslayout.BaseActivity;
import com.zcedu.openclass.statuslayout.StatusLayoutManager;
import com.zcedu.openclass.ui.activity.home.HomeActivity;
import com.zcedu.openclass.util.LoadDialog;
import com.zcedu.openclass.util.LoadLocalImageUtil;
import com.zcedu.openclass.util.Util;

/**
 * 登录页面
 * Created by cheng on 2018/5/3.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.ILoginView {
    private ImageView login_bg_img,delete_input_img,show_hide_pwd_img;
    private EditText user_phone_edit,user_pwd_edit;
    private TextView login_text;
    private boolean showPwd=false; //默认不显示密码
    private Dialog loginDialog;
    private LoginPresenter loginPresenter;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager=StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.login_content_layout)
                .build();
    }

    @Override
    protected int titleLayoutId() {
        return 0;
    }

    @Override
    protected String titleString() {
        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findView();
    }

    @Override
    protected void initView() {
        super.initView();
        //初始化p
        loginPresenter = new LoginPresenter(this);
        //加载背景图
        LoadLocalImageUtil.getInstance().displayFromDrawable(R.drawable.login_bg,login_bg_img);
    }

    @Override
    protected void setListener() {
        super.setListener();
        login_text.setOnClickListener(this);
        delete_input_img.setOnClickListener(this);
        show_hide_pwd_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_text:  //登录
//                login();
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.delete_input_img:  //删除已输入账号
                user_phone_edit.setText("");
                break;
            case R.id.show_hide_pwd_img:  //显示或隐藏密码
                show_hide_pwd_img.setImageResource(showPwd?R.drawable.sign_in_invisible:R.drawable.sign_in_visible);
                user_pwd_edit.setTransformationMethod(showPwd? PasswordTransformationMethod.getInstance(): HideReturnsTransformationMethod.getInstance());
                user_pwd_edit.setSelection(user_pwd_edit.getText().toString().length());
                showPwd = !showPwd;
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        String phone = user_phone_edit.getText().toString();
        String pwd = user_pwd_edit.getText().toString();
        if (phone.length()!=11&&!phone.startsWith("1")) Util.t(this,"账号输入有误！");
        else if (pwd.length()<6) Util.t(this,"密码最少6位");
        else {
            loginPresenter.getToken();
        }
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public UserInfo getUserInfo() {
        UserInfo info = new UserInfo();
        info.setPhone(user_phone_edit.getText().toString());
        info.setPwd(user_pwd_edit.getText().toString());
        return info;
    }

    @Override
    public void showDialog() {
        if (null==loginDialog) loginDialog = new LoadDialog().loadDialog(this, "登录中");
        loginDialog.show();
    }

    @Override
    public void hideDialog() {
        Util.closeDialog(loginDialog);
    }

    @Override
    public void showMsg(String msg) {
        Util.t(this,msg);
    }

    @Override
    public void getTokenSuccess() {  //获取登录的token成功
        loginPresenter.getUserInfo();
    }

    @Override
    public void loginSuccess() {

    }

    private void findView() {
        login_bg_img=findViewById(R.id.login_bg_img);
        delete_input_img=findViewById(R.id.delete_input_img);
        show_hide_pwd_img=findViewById(R.id.show_hide_pwd_img);
        user_phone_edit=findViewById(R.id.user_phone_edit);
        user_pwd_edit=findViewById(R.id.user_pwd_edit);
        login_text=findViewById(R.id.login_text);
    }
}
