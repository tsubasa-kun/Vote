package com.love_cookies.vote.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.love_cookies.vote.Model.Bean.CommonAdapter;
import com.love_cookies.vote.Model.Bean.ViewHolder;
import com.love_cookies.vote.Model.Bean.VoteBean;
import com.love_cookies.vote.Presenter.MyApplication;
import com.love_cookies.vote.Presenter.VoteDetailPresenter;
import com.love_cookies.vote.R;
import com.love_cookies.vote.Utils.ToastUtils;
import com.love_cookies.vote.View.Interface.IVoteDetail;
import com.love_cookies.vote.View.Wedget.XKListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_vote_detail)
public class VoteDetailActivity extends BaseActivity implements IVoteDetail {

    @ViewInject(R.id.title_text)
    TextView pageTitle;
    @ViewInject(R.id.left_btn)
    ImageView leftBtn;
    @ViewInject(R.id.vote_btn)
    TextView voteBtn;
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

    List<VoteBean.Column> columnDate = new ArrayList<>();
    ColumnAdapter columnAdapter;

    VoteDetailPresenter voteDetailPresenter = new VoteDetailPresenter(this);
    private int maxNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getVote(MyApplication.user.getId(), getIntent().getIntExtra("subject_id", 0));
    }

    @Override
    public void initView() {
        pageTitle.setText(R.string.vote_detail_title);
        leftBtn.setImageResource(R.drawable.title_btn_back);
        leftBtn.setOnClickListener(this);
        voteBtn.setOnClickListener(this);
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
            case R.id.vote_btn:
                doVote(MyApplication.user.getId(), getIntent().getIntExtra("subject_id", 0));
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
        voteDetailPresenter.getVote(userId, subjectId);
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
            if(voteBean.isVote()) {
                voteBtn.setEnabled(false);
                voteBtn.setText(R.string.vote_detail_voted_text);
            } else {
                voteBtn.setEnabled(true);
                voteBtn.setText(R.string.vote_detail_vote_btn_text);
            }
        } else {
            voteBtn.setEnabled(false);
            voteBtn.setText(R.string.status_off);
        }
        maxNum = voteBean.getSubject().getMax();
    }

    @Override
    public void doVote(int userId, int subjectId) {
        List<Integer> columnIds = new ArrayList<>();
        for(VoteBean.Column column : columnDate) {
            if(column.isSelect()) {
                columnIds.add(column.getId());
            }
        }
        if(columnIds.size() > 0 && columnIds.size() <= maxNum) {
            voteDetailPresenter.doVote(userId, subjectId, columnIds);
        } else if(columnIds.size() <= 0){
            ToastUtils.show(this, R.string.vote_detail_column_hint);
        } else if(columnIds.size() > maxNum) {
            String format = getResources().getString(R.string.vote_detail_max_hint);
            String maxTip = String.format(format, maxNum);
            ToastUtils.show(this, maxTip);
        }
    }

    @Override
    public void refreshBtn() {
        voteBtn.setEnabled(false);
        voteBtn.setText(R.string.vote_detail_voted_text);
        ToastUtils.show(this, R.string.vote_detail_vote_success);
    }

    class ColumnAdapter extends CommonAdapter<VoteBean.Column> {

        public ColumnAdapter(Context context, List<VoteBean.Column> datas) {
            super(context, R.layout.item_vote_detail_column, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, final VoteBean.Column column) {
            viewHolder.setText(R.id.column_text, column.getColumn());
            LikeButton likeButton = viewHolder.getView(R.id.heart_btn);
            if(column.isSelect()) {
                likeButton.setLiked(true);
            } else {
                likeButton.setLiked(false);
            }
            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    column.setSelect(true);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    column.setSelect(false);
                }
            });
        }

    }

}
