package com.zcedu.openclass.statuslayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.zcedu.openclass.R;


/**
 * Activity基类
 * Created by cheng on 2017/12/27.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected StatusLayoutManager statusLayoutManager;

    private InputMethodManager imm;
    protected ImmersionBar mImmersionBar;
    private LinearLayout title_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //初始化状态布局
        initStatusLayout();
        //添加状态布局
        LinearLayout content_layout = findViewById(R.id.content_layout);
        content_layout.addView(statusLayoutManager.getRootLayout());
        //绑定标题布局
        title_layout = findViewById(R.id.title_layout);
        if (titleLayoutId()==0) title_layout.setVisibility(View.GONE);
        else {
            //初始化沉浸式
            if (isImmersionBarEnabled())
                initImmersionBar();
            title_layout.addView(LayoutInflater.from(this).inflate(titleLayoutId(), null));
            //监听标题和返回键点击事件
            titleBackClick();
        }
        //初始化数据
        initData();
        //view与数据绑定
        initView();
        //设置监听
        setListener();
    }

    /**
     * 监听标题和返回键点击事件
     */
    private void titleBackClick() {
        try {
            LinearLayout back_layout = findViewById(R.id.back_layout);
            TextView title_text = findViewById(R.id.title_text);
            title_text.setText(titleString());
            title_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickTitle();
                }
            });

            back_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickBack();
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("请在标题布局中写入隐藏的标题控件和返回控件");
        }
    }

    protected abstract void initStatusLayout();

    protected abstract int titleLayoutId();

    protected abstract String titleString();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.imm = null;
        if (mImmersionBar != null) mImmersionBar.destroy();  //在BaseActivity里销毁
        OkGo.getInstance().cancelAll();
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        mImmersionBar.titleBar(title_layout).init();
    }

    protected void initData() {
    }

    protected void initView() {
    }

    protected void setListener() {
    }

    protected void clickTitle() {
    }

    protected void clickBack() {
        finish();
    }

    /**
     * 是否可以使用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }
}
