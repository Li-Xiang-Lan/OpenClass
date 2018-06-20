package com.zcedu.openclass.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zcedu.openclass.R;
import com.zcedu.openclass.adapter.ChoosePopuAdapter;
import com.zcedu.openclass.bean.ChoosePopupBean;
import com.zcedu.openclass.callback.SureBackListener;

import java.util.List;

/**
 * 选择的 popup弹窗
 * Created by cheng on 2018/5/11.
 */

public class ChoosePopup {
    /**
     * 初始化popup
     * @param context
     * @param list 数据集合
     * @param width popup宽度
     * @param listener 选中监听
     */
    public PopupWindow initChoosePopup(Context context, final List<ChoosePopupBean> list, int width, final SureBackListener<ChoosePopupBean> listener){
        View view = LayoutInflater.from(context).inflate(R.layout.popup_layout, null);
        ListView listView=view.findViewById(R.id.listView);
        ChoosePopuAdapter adapter=new ChoosePopuAdapter(context,list);
        listView.setAdapter(adapter);
        final PopupWindow popupWindow = new PopupWindow(view,width, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0000000000));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.AnimationPreview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (popupWindow.isShowing()) popupWindow.dismiss();
                if (null!=listener) listener.sure(list.get(position));
            }
        });
        return popupWindow;
    }
}
