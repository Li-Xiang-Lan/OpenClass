package com.zcedu.openclass.ui.fragment.salemanage;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zcedu.openclass.R;
import com.zcedu.openclass.adapter.SaleManageAdapter;
import com.zcedu.openclass.bean.OrderDataBean;
import com.zcedu.openclass.statuslayout.BaseFragment;
import com.zcedu.openclass.statuslayout.StatusLayoutManager;
import com.zcedu.openclass.ui.activity.addorder.AddOrderActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 销售管理的fragment
 * Created by cheng on 2018/5/10.
 */

public class SaleManageFragment extends BaseFragment implements OnRefreshLoadmoreListener, View.OnClickListener {
    private RecyclerView recyclerView;
    private LinearLayout add_order_layout;
    private SmartRefreshLayout refreshLayout;

    private List<OrderDataBean> orderDataList=new ArrayList<>();
    @Override
    protected void initStatusLayout() {
        statusLayoutManager= StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.sale_fragment_content_layout)
                .emptyDataView(R.layout.empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .build();
        statusLayoutManager.showContent();
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
        refreshLayout.setOnRefreshLoadmoreListener(this);
    }

    @Override
    protected void setListener() {
        super.setListener();
        add_order_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_order_layout:  //添加订单
                startActivity(new Intent(getContext(), AddOrderActivity.class));
                break;
        }
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        for (int i = 0; i < 20; i++) {
            OrderDataBean bean=new OrderDataBean();
            orderDataList.add(bean);
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        SaleManageAdapter saleManageAdapter = new SaleManageAdapter(getContext(),orderDataList);
        recyclerView.setAdapter(saleManageAdapter);
    }

    private void findView() {
        recyclerView=findViewById(R.id.recyclerView);
        refreshLayout=findViewById(R.id.refreshLayout);
        add_order_layout=findViewById(R.id.add_order_layout);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshLayout.finishLoadmore();
    }
}
