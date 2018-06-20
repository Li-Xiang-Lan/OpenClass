package com.zcedu.openclass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zcedu.openclass.R;
import com.zcedu.openclass.bean.ChoosePopupBean;

import java.util.List;

/**
 * 选择弹窗的 适配器
 * Created by cheng on 2018/5/11.
 */

public class ChoosePopuAdapter extends BaseAdapter{
    private Context context;
    private List<ChoosePopupBean> list;

    public ChoosePopuAdapter(Context context, List<ChoosePopupBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MyView myView=null;
        if (convertView==null){
            myView=new MyView();
            convertView= LayoutInflater.from(context).inflate(R.layout.popup_item_layout,viewGroup,false);
            myView.text=convertView.findViewById(R.id.text);
            convertView.setTag(myView);
        }else
            myView= (MyView) convertView.getTag();
        myView.text.setText(list.get(i).getName());
        return convertView;
    }

    private class MyView{
        private TextView text;
    }
}
