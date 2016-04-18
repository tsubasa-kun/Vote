package com.love_cookies.vote.Presenter;

import com.love_cookies.vote.Model.Bean.VoteBean;
import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.VoteBiz;
import com.love_cookies.vote.View.Interface.IVoteDetail;

import java.util.List;

/**
 * Created by xiekun on 2016/1/13 0013.
 */
public class VoteDetailPresenter {

    private VoteBiz voteBiz;
    private IVoteDetail iVoteDetail;

    public VoteDetailPresenter(IVoteDetail iVoteDetail) {
        voteBiz = new VoteBiz();
        this.iVoteDetail = iVoteDetail;
    }

    public void getVote(int userId, int subjectId) {
        voteBiz.getVote(userId, subjectId, new CallBack<VoteBean>() {
            @Override
            public void getSuccess(VoteBean result) {
                iVoteDetail.initDate(result);
            }

            @Override
            public void getFailed(String msg) {

            }
        });
    }

    public void doVote(int userId, int subjectId, List<Integer> columnIds) {
        voteBiz.doVote(userId, subjectId, columnIds, new CallBack<Integer>() {
            @Override
            public void getSuccess(Integer result) {
                iVoteDetail.refreshBtn();
            }

            @Override
            public void getFailed(String msg) {

            }
        });
    }

}
