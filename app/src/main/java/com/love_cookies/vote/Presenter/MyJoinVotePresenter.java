package com.love_cookies.vote.Presenter;

import com.love_cookies.vote.Model.Bean.VoteBean;
import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.VoteBiz;
import com.love_cookies.vote.View.Interface.IMyJoinVote;

import java.util.List;

/**
 * Created by xiekun on 2016/1/13.
 */
public class MyJoinVotePresenter {

    private VoteBiz voteBiz;
    private IMyJoinVote iMyJoinVote;

    public MyJoinVotePresenter(IMyJoinVote iMyJoinVote) {
        voteBiz = new VoteBiz();
        this.iMyJoinVote = iMyJoinVote;
    }

    public void getVote(int userId, int page) {
        voteBiz.getJoinVote(userId, page - 1, new CallBack<List<VoteBean>>() {
            @Override
            public void getSuccess(List<VoteBean> result) {
                iMyJoinVote.refreshList(result);
            }

            @Override
            public void getFailed(String msg) {

            }
        });
    }

}
