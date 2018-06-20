package com.zcedu.openclass.util;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.zcedu.openclass.R;


/**
 * 加载弹窗
 * Created by cheng on 2018/3/1.
 */

public class LoadDialog {
    public Dialog loadDialog(Context context,String str){
        Dialog progressDialog = new Dialog(context, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.load_dialog);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg =progressDialog.findViewById(R.id.id_tv_loadingmsg);
        if (TextUtils.isEmpty(str)) msg.setText("正在加载中");
        else msg.setText(str);
        return progressDialog;
    }
}
