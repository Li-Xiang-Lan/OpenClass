package com.zcedu.openclass.ui.activity.addorder;

import com.zcedu.openclass.R;
import com.zcedu.openclass.statuslayout.BaseActivity;
import com.zcedu.openclass.statuslayout.StatusLayoutManager;

/**
 * 添加订单页面
 * Created by cheng on 2018/5/11.
 */

public class AddOrderActivity extends BaseActivity {
    @Override
    protected void initStatusLayout() {
        statusLayoutManager= StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.add_order_content_layout)
                .emptyDataView(R.layout.empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "添加订单";
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();  //解决软键盘与底部输入框冲突问题
    }
}
