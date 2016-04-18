package com.love_cookies.vote.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.love_cookies.vote.Model.Bean.CommonAdapter;
import com.love_cookies.vote.Model.Bean.ViewHolder;
import com.love_cookies.vote.Model.Bean.VoteBean;
import com.love_cookies.vote.Presenter.CreatedDetailPresenter;
import com.love_cookies.vote.Presenter.MyApplication;
import com.love_cookies.vote.R;
import com.love_cookies.vote.Utils.ToastUtils;
import com.love_cookies.vote.View.Interface.ICreatedDetail;
import com.love_cookies.vote.View.Wedget.XKListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_created_detail)
public class CreatedDetailActivity extends BaseActivity implements ICreatedDetail {

    @ViewInject(R.id.title_text)
    TextView pageTitle;
    @ViewInject(R.id.left_btn)
    ImageView leftBtn;
    @ViewInject(R.id.finish_btn)
    TextView finishBtn;
    @ViewInject(R.id.result_btn)
    TextView resultBtn;
    @ViewInject(R.id.subject_text)
    TextView subjectText;
    @ViewInject(R.id.content_text)
    TextView contentText;
    @ViewInject(R.id.date_and_user_text)
    TextView dateAndUserText;
    @ViewInject(R.id.max_text)
    TextView maxText;
    @ViewInject(R.id.column_list)
    XKListView columnList;

    CreatedDetailPresenter createdDetailPresenter = new CreatedDetailPresenter(this);

    List<VoteBean.Column> columnDate = new ArrayList<>();
    ColumnAdapter columnAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getVote(MyApplication.user.getId(), getIntent().getIntExtra("subject_id", 0));
    }

    @Override
    public void initView() {
        pageTitle.setText(R.string.vote_created_title);
        leftBtn.setImageResource(R.drawable.title_btn_back);
        leftBtn.setOnClickListener(this);
        finishBtn.setOnClickListener(this);
        resultBtn.setOnClickListener(this);
        columnAdapter = new ColumnAdapter(this, columnDate);
        columnList.setAdapter(columnAdapter);
        columnList.setFocusable(false);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;
            case R.id.finish_btn:
                finishVote(getIntent().getIntExtra("subject_id", 0));
                break;
            case R.id.result_btn:
                Intent intent = new Intent(this, VoteResultActivity.class);
                intent.putExtra("subject_id", getIntent().getIntExtra("subject_id", 0));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void getVote(int userId, int subjectId) {
        createdDetailPresenter.getVote(userId, subjectId);
    }

    @Override
    public void initDate(VoteBean voteBean) {
        subjectText.setText(voteBean.getSubject().getSubject());
        contentText.setText(voteBean.getSubject().getContent());
        String userformat = getResources().getString(R.string.vote_detail_user_text);
        String userTip = String.format(userformat, voteBean.getSubject().getUser_name());
        dateAndUserText.setText(voteBean.getSubject().getDate() + "  " + userTip);
        String format = getResources().getString(R.string.vote_detail_max_hint);
        String maxTip = String.format(format, voteBean.getSubject().getMax());
        maxText.setText(maxTip);
        columnDate.addAll(voteBean.getColumns());
        columnAdapter.notifyDataSetChanged();
        if(voteBean.getSubject().getStatus() == 0) {
            finishBtn.setEnabled(true);
        } else {
            finishBtn.setEnabled(false);
        }
    }

    @Override
    public void refreshBtn() {
        finishBtn.setEnabled(false);
        resultBtn.setEnabled(true);
        ToastUtils.show(this, R.string.vote_finish_tip);
    }

    @Override
    public void finishVote(int subjectId) {
        createdDetailPresenter.finishVote(subjectId);
    }

    class ColumnAdapter extends CommonAdapter<VoteBean.Column> {

        public ColumnAdapter(Context context, List<VoteBean.Column> datas) {
            super(context, R.layout.item_created_detail_column, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, VoteBean.Column column) {
            viewHolder.setText(R.id.column_text, column.getColumn());
        }

    }

}
