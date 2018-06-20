package com.zcedu.openclass.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zcedu.openclass.R;
import com.zcedu.openclass.bean.OrderDataBean;

import java.util.List;

/**
 * 销售管理 列表适配器
 * Created by cheng on 2018/5/10.
 */

public class SaleManageAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<OrderDataBean> list;

    public SaleManageAdapter(Context context, List<OrderDataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(context).inflate(R.layout.sale_manage_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class MyView extends RecyclerView.ViewHolder {

        public MyView(@NonNull View itemView) {
            super(itemView);
        }
    }
}
