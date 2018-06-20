package com.zcedu.openclass.ui.fragment.financeheadquarters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zcedu.openclass.R;
import com.zcedu.openclass.bean.ChoosePopupBean;
import com.zcedu.openclass.callback.SureBackListener;
import com.zcedu.openclass.statuslayout.BaseFragment;
import com.zcedu.openclass.statuslayout.StatusLayoutManager;
import com.zcedu.openclass.util.ChoosePopup;

import java.util.ArrayList;
import java.util.List;

/**
 * 财务管理 总部开课管理 fragment
 * Created by cheng on 2018/5/11.
 */

public class FinanceHeadQuartersFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private TextView choose_school_text,choose_teacher_text;
    private PopupWindow chooseSchoolPopup,chooseTeacherPopup;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager= StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.finance_head_quarters_fragment_content_layout)
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
    protected void setListener() {
        super.setListener();
        choose_school_text.setOnClickListener(this);
        choose_teacher_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.choose_school_text:  //选择学校
                chooseSchool();
                break;
            case R.id.choose_teacher_text: //选择老师
                chooseTeacher();
                break;
        }
    }

    /**
     * 选择老师
     */
    private void chooseTeacher() {
        if (null==chooseTeacherPopup){
            List<ChoosePopupBean> list=new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                ChoosePopupBean bean=new ChoosePopupBean();
                bean.setName("老师"+i);
                list.add(bean);
            }
            chooseTeacherPopup = new ChoosePopup().initChoosePopup(getContext(), list, choose_teacher_text.getWidth(), new SureBackListener<ChoosePopupBean>() {
                @Override
                public void sure(ChoosePopupBean choosePopupBean) {
                    choose_teacher_text.setText(choosePopupBean.getName());
                }
            });
        }
        chooseTeacherPopup.showAsDropDown(choose_teacher_text);
    }

    /**
     * 选择学校
     */
    private void chooseSchool() {
        if (null==chooseSchoolPopup){
            List<ChoosePopupBean> list=new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                ChoosePopupBean bean=new ChoosePopupBean();
                bean.setName("学校"+i);
                list.add(bean);
            }
            chooseSchoolPopup = new ChoosePopup().initChoosePopup(getContext(), list, choose_school_text.getWidth(), new SureBackListener<ChoosePopupBean>() {
                @Override
                public void sure(ChoosePopupBean choosePopupBean) {
                    choose_school_text.setText(choosePopupBean.getName());
                }
            });
        }
        chooseSchoolPopup.showAsDropDown(choose_school_text);
    }

    private void findView() {
        choose_school_text=findViewById(R.id.choose_school_text);
        choose_teacher_text=findViewById(R.id.choose_teacher_text);
    }
}
