package com.zcedu.openclass.callback;

/**
 * http请求返回成功或者失败接口
 * Created by cheng on 2018/1/9.
 */

public interface OnHttpCallBack<T> {
    void onSuccess(T t);
    void onFail(String msg);
}
