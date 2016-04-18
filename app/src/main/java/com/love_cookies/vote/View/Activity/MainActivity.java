package com.love_cookies.vote.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.love_cookies.vote.Model.Bean.CommonAdapter;
import com.love_cookies.vote.Model.Bean.ViewHolder;
import com.love_cookies.vote.Model.Bean.VoteBean;
import com.love_cookies.vote.Presenter.ActivityManager;
import com.love_cookies.vote.Presenter.MainPresenter;
import com.love_cookies.vote.Presenter.MyApplication;
import com.love_cookies.vote.R;
import com.love_cookies.vote.Utils.ToastUtils;
import com.love_cookies.vote.View.Interface.IMain;
import com.love_cookies.vote.View.Wedget.LoadAndRefreshView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiekun on 2016/01/10 0010.
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements IMain, LoadAndRefreshView.OnHeaderRefreshListener, LoadAndRefreshView.OnFooterRefreshListener {

    @ViewInject(R.id.title_text)
    TextView pageTitle;
    @ViewInject(R.id.right_btn)
    ImageView rightBtn;
    @ViewInject(R.id.create_btn)
    ImageView createBtn;
    @ViewInject(R.id.vote_list)
    ListView voteList;
    @ViewInject(R.id.load_and_refresh_view)
    LoadAndRefreshView loadAndRefreshView;

    private long exitTime;

    MainPresenter mainPresenter = new MainPresenter(this);

    List<VoteBean> voteDate = new ArrayList<>();
    VoteAdapter voteAdapter;

    int page = 1;

    private static final int CREATE_VOTE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        initView();
        getDate(MyApplication.user.getId(), page);
    }

    @Override
    public void initView() {
        pageTitle.setText(R.string.app_name);
        rightBtn.setImageResource(R.drawable.title_btn_list);
        rightBtn.setOnClickListener(this);
        createBtn.setOnClickListener(this);
        voteAdapter = new VoteAdapter(this, voteDate);
        voteList.setAdapter(voteAdapter);
        loadAndRefreshView.setOnHeaderRefreshListener(this);
        loadAndRefreshView.setOnFooterRefreshListener(this);
        voteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if(voteDate.get(position).getSubject().getUser_id() == MyApplication.user.getId()) {
                    intent = new Intent(MainActivity.this, CreatedDetailActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, VoteDetailActivity.class);
                }
                intent.putExtra("subject_id", voteDate.get(position).getSubject().getId());
                startActivity(intent);
            }
        });
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
    public void getDate(int userId, int page) {
        mainPresenter.getDate(userId, page);
    }

    @Override
    public void widgetClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.create_btn:
                intent = new Intent(this, CreateVoteActivity.class);
                startActivityForResult(intent, CREATE_VOTE);
                break;
            case R.id.right_btn:
                intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000)
            {
                ToastUtils.show(getApplicationContext(), R.string.exit_tip);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFooterRefresh(LoadAndRefreshView view) {
        getDate(MyApplication.user.getId(), ++page);
    }

    @Override
    public void onHeaderRefresh(LoadAndRefreshView view) {
        page = 1;
        getDate(MyApplication.user.getId(), page);
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
            if(voteBean.getSubject().getUser_id() == MyApplication.user.getId()) {
                viewHolder.getView(R.id.publicer_text).setVisibility(View.VISIBLE);
            } else {
                viewHolder.getView(R.id.publicer_text).setVisibility(View.GONE);
            }
            if(voteBean.getSubject().getStatus() == 0) {
                viewHolder.setText(R.id.status_text, R.string.status_on);
                viewHolder.getView(R.id.status_text).setEnabled(true);
            } else {
                viewHolder.setText(R.id.status_text, R.string.status_off);
                viewHolder.getView(R.id.status_text).setEnabled(false);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CREATE_VOTE && resultCode == RESULT_OK) {
            page = 1;
            getDate(MyApplication.user.getId(), page);
        }
    }

}
