package com.zcedu.openclass.ui.activity.orderdetail;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zcedu.openclass.R;
import com.zcedu.openclass.bean.OrderDetailBean;
import com.zcedu.openclass.statuslayout.BaseActivity;
import com.zcedu.openclass.statuslayout.StatusLayoutManager;
import com.zcedu.openclass.util.stickydecoration.StickyItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单详情页面
 * Created by cheng on 2018/5/12.
 */

public class OrderDetailActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<OrderDetailBean> detailBeanList=new ArrayList<>();

    @Override
    protected void initStatusLayout() {
        statusLayoutManager= StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.order_detail_content_layout)
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
        return "订单详情";
    }

    @Override
    protected void initData() {
        super.initData();
        findView();
    }

    @Override
    protected void initView() {
        super.initView();
        //设置适配器
        setAdapter();
        //初始化数据
        getData();
    }

    private void getData() {
        OrderDetailBean bean1=new OrderDetailBean();
        bean1.setName("开课订单信息");
        bean1.setItemType(0);

    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new StickyItemDecoration());

    }

    private void findView() {
        recyclerView=findViewById(R.id.recyclerView);
    }
}
