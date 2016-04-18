package com.love_cookies.vote.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.love_cookies.vote.Model.Bean.CommonAdapter;
import com.love_cookies.vote.Model.Bean.ViewHolder;
import com.love_cookies.vote.Model.Bean.VoteBean;
import com.love_cookies.vote.Presenter.MyApplication;
import com.love_cookies.vote.Presenter.MyJoinVotePresenter;
import com.love_cookies.vote.R;
import com.love_cookies.vote.View.Interface.IMyJoinVote;
import com.love_cookies.vote.View.Wedget.LoadAndRefreshView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_my_join_vote)
public class MyJoinVoteActivity extends BaseActivity implements IMyJoinVote, LoadAndRefreshView.OnHeaderRefreshListener, LoadAndRefreshView.OnFooterRefreshListener{

    @ViewInject(R.id.title_text)
    TextView pageTitle;
    @ViewInject(R.id.left_btn)
    ImageView leftBtn;
    @ViewInject(R.id.vote_list)
    ListView voteList;
    @ViewInject(R.id.load_and_refresh_view)
    LoadAndRefreshView loadAndRefreshView;

    MyJoinVotePresenter myJoinVotePresenter = new MyJoinVotePresenter(this);

    List<VoteBean> voteDate = new ArrayList<>();
    VoteAdapter voteAdapter;

    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getVote(MyApplication.user.getId(), page);
    }

    @Override
    public void initView() {
        pageTitle.setText(R.string.home_my_join);
        leftBtn.setImageResource(R.drawable.title_btn_back);
        leftBtn.setOnClickListener(this);
        voteAdapter = new VoteAdapter(this, voteDate);
        voteList.setAdapter(voteAdapter);
        loadAndRefreshView.setOnHeaderRefreshListener(this);
        loadAndRefreshView.setOnFooterRefreshListener(this);
        voteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyJoinVoteActivity.this, VoteDetailActivity.class);
                intent.putExtra("subject_id", voteDate.get(position).getSubject().getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void getVote(int userId, int page) {
        myJoinVotePresenter.getVote(userId, page);
    }

    @Override
    public void refreshList(List<VoteBean> date) {
        if(page == 1) {
            voteDate.clear();
        }
        voteDate.addAll(date);
        voteAdapter.notifyDataSetChanged();
        loadAndRefreshView.setResultSize(date.size());
        loadAndRefreshView.onHeaderRefreshComplete();
        loadAndRefreshView.onFooterRefreshComplete();
    }

    @Override
    public void onFooterRefresh(LoadAndRefreshView view) {
        getVote(MyApplication.user.getId(), ++page);
    }

    @Override
    public void onHeaderRefresh(LoadAndRefreshView view) {
        page = 1;
        getVote(MyApplication.user.getId(), page);
    }

    class VoteAdapter extends CommonAdapter<VoteBean> {

        public VoteAdapter(Context context, List<VoteBean> datas) {
            super(context, R.layout.item_main_vote, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, VoteBean voteBean) {
            viewHolder.setText(R.id.subject_text, voteBean.getSubject().getSubject());
            viewHolder.setText(R.id.content_text, voteBean.getSubject().getContent());
            viewHolder.setText(R.id.date_text, voteBean.getSubject().getDate());
            if(voteBean.getSubject().getStatus() == 0) {
                viewHolder.setText(R.id.status_text, R.string.status_on);
                viewHolder.getView(R.id.status_text).setEnabled(true);
            } else {
                viewHolder.setText(R.id.status_text, R.string.status_off);
                viewHolder.getView(R.id.status_text).setEnabled(false);
            }
        }
    }

}
