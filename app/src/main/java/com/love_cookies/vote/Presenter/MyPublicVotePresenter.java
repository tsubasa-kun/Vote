package com.love_cookies.vote.Presenter;

import com.love_cookies.vote.Model.Bean.VoteBean;
import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.VoteBiz;
import com.love_cookies.vote.View.Interface.IMyPublicVote;

import java.util.List;

/**
 * Created by xiekun on 2016/1/13.
 */
public class MyPublicVotePresenter {

    private VoteBiz voteBiz;
    private IMyPublicVote iMyPublicVote;

    public MyPublicVotePresenter(IMyPublicVote iMyPublicVote) {
        voteBiz = new VoteBiz();
        this.iMyPublicVote = iMyPublicVote;
    }

    public void getVote(int userId, int page) {
        voteBiz.getPublicVote(userId, page - 1, new CallBack<List<VoteBean>>() {
            @Override
            public void getSuccess(List<VoteBean> result) {
                iMyPublicVote.refreshList(result);
            }

            @Override
            public void getFailed(String msg) {

            }
        });
    }

}
