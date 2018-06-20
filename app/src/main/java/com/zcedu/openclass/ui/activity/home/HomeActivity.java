package com.zcedu.openclass.ui.activity.home;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zcedu.openclass.R;
import com.zcedu.openclass.adapter.ViewPageAdapter;
import com.zcedu.openclass.statuslayout.BaseActivity;
import com.zcedu.openclass.statuslayout.StatusLayoutManager;
import com.zcedu.openclass.ui.fragment.salemanage.SaleManageFragment;
import com.zcedu.openclass.view.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by cheng on 2018/5/10.
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private SlidingTabLayout tablayout;
    private ViewPager viewPager;
    private LinearLayout user_name_layout;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList=new ArrayList<>();
    private int usertype;
    private PopupWindow user_popup;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager= StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.home_content_layout)
                .emptyDataView(R.layout.empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.home_title_layout;
    }

    @Override
    protected String titleString() {
        usertype = getIntent().getIntExtra("type", 0);
        return usertype==0?"销售管理":usertype==1?"财务管理":"开课管理";
    }

    @Override
    protected void initData() {
        super.initData();
        findVIew();
    }

    @Override
    protected void initView() {
        super.initView();
        //初始化fragment
        initFragment();
        //设置适配器
        setAdapter();
    }

    @Override
    protected void setListener() {
        super.setListener();
        user_name_layout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_name_layout:
                if (null==user_popup) initUserPopup();
                user_popup.showAsDropDown(user_name_layout);
                break;
        }
    }

    /**
     * 初始化点击用户名的弹窗
     */
    private void initUserPopup() {
        View view = getLayoutInflater().inflate(R.layout.home_user_popup_layout, null);
        user_popup = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        user_popup.setBackgroundDrawable(new ColorDrawable(0000000000));
        user_popup.setFocusable(true);
        user_popup.setOutsideTouchable(true);
        user_popup.setAnimationStyle(R.style.AnimationPreview);
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        ViewPageAdapter pageAdapter=new ViewPageAdapter(getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(pageAdapter);
        tablayout.setViewPager(viewPager);
        viewPager.setCurrentItem(1);
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        for (int i = 0; i < 3; i++) {
            SaleManageFragment saleFragment = new SaleManageFragment();
//            FinanceHeadQuartersFragment saleFragment = new FinanceHeadQuartersFragment();
            fragmentList.add(saleFragment);
        }
        titleList.add("待处理");
        titleList.add("全部");
        titleList.add("已处理");
    }

    private void findVIew() {
        tablayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewPager);
        user_name_layout=findViewById(R.id.user_name_layout);
    }
}
