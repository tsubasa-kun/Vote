package com.love_cookies.vote.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.love_cookies.vote.Model.Bean.CommonAdapter;
import com.love_cookies.vote.Model.Bean.ViewHolder;
import com.love_cookies.vote.Presenter.CreateVotePresenter;
import com.love_cookies.vote.Presenter.MyApplication;
import com.love_cookies.vote.R;
import com.love_cookies.vote.Utils.ToastUtils;
import com.love_cookies.vote.View.Interface.ICreateVote;
import com.love_cookies.vote.View.Wedget.XKListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_create_vote)
public class CreateVoteActivity extends BaseActivity implements ICreateVote {

    @ViewInject(R.id.title_text)
    TextView pageTitle;
    @ViewInject(R.id.right_btn)
    ImageView rightBtn;
    @ViewInject(R.id.left_btn)
    ImageView leftBtn;
    @ViewInject(R.id.add_column_btn)
    TextView addColumnBtn;
    @ViewInject(R.id.column_list)
    XKListView columnList;
    @ViewInject(R.id.subject_et)
    EditText subjectET;
    @ViewInject(R.id.content_et)
    EditText contentET;
    @ViewInject(R.id.max_tv)
    TextView maxTV;

    private static final int CHOSE_MAX = 0;
    private static final int ADD_COLUMN = 1;

    List<String> columnDate = new ArrayList<>();

    ColumnAdapter columnAdapter;

    CreateVotePresenter createVotePresenter = new CreateVotePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void initView() {
        pageTitle.setText(R.string.create_title);
        leftBtn.setImageResource(R.drawable.title_btn_back);
        leftBtn.setOnClickListener(this);
        rightBtn.setImageResource(R.drawable.title_btn_public);
        rightBtn.setOnClickListener(this);
        addColumnBtn.setOnClickListener(this);
        columnAdapter = new ColumnAdapter(this, columnDate);
        columnList.setAdapter(columnAdapter);
        columnList.setFocusable(false);
        maxTV.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;
            case R.id.right_btn:
                doPublic();
                break;
            case R.id.add_column_btn:
                addColumn();
                break;
            case R.id.max_tv:
                choseMax();
                break;
            default:
                break;
        }
    }

    @Override
    public void addColumn() {
        Intent intent = new Intent(this, AddColumnActivity.class);
        startActivityForResult(intent, ADD_COLUMN);
    }

    @Override
    public void choseMax() {
        Intent intent = new Intent(CreateVoteActivity.this, ChoseMaxActivity.class);
        startActivityForResult(intent, CHOSE_MAX);
    }

    @Override
    public void reColumn(int index) {
        columnDate.remove(index);
        refreshList();
    }

    @Override
    public void refreshList() {
        columnAdapter.notifyDataSetChanged();
    }

    @Override
    public void doPublic() {
        if(TextUtils.isEmpty(subjectET.getText().toString())) {
            ToastUtils.show(this, R.string.create_subject_hint);
        } else if(TextUtils.isEmpty(contentET.getText().toString())) {
            ToastUtils.show(this, R.string.create_content_hint);
        } else if(TextUtils.isEmpty(maxTV.getText().toString())) {
            ToastUtils.show(this, R.string.create_max_hint);
        } else if(Integer.parseInt(maxTV.getText().toString()) <= 0) {
            ToastUtils.show(this, R.string.create_max_error);
        } else if(columnDate.size() <= 0) {
            ToastUtils.show(this, R.string.create_column_error);
        } else {
            createVotePresenter.doPublic(
                    subjectET.getText().toString(),
                    contentET.getText().toString(),
                    columnDate,
                    Integer.parseInt(maxTV.getText().toString()),
                    MyApplication.user.getId(),
                    MyApplication.user.getNickname()
            );
        }
    }

    @Override
    public void publicSuccess() {
        setResult(RESULT_OK);
        finish();
        ToastUtils.show(this, R.string.create_success);
    }

    @Override
    public void publicFailed(String msg) {
        ToastUtils.show(this, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_COLUMN && resultCode == RESULT_OK) {
            columnDate.add(data.getStringExtra("column"));
            refreshList();
        }
        if(requestCode == CHOSE_MAX && resultCode == RESULT_OK) {
            maxTV.setText(data.getIntExtra("max", 1) + "");
        }
    }

    class ColumnAdapter extends CommonAdapter<String> {

        public ColumnAdapter(Context context, List<String> datas) {
            super(context, R.layout.item_create_column, datas);
        }

        @Override
        public void convert(final ViewHolder viewHolder, String s) {
            viewHolder.setText(R.id.column_text, s);
            viewHolder.getView(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reColumn(viewHolder.getPosition());
                }
            });
        }
    }

}
