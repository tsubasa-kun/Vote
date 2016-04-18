package com.love_cookies.vote.Presenter;

import com.love_cookies.vote.Model.Bean.ResultBean;
import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.VoteBiz;
import com.love_cookies.vote.View.Interface.IVoteResult;

/**
 * Created by xiekun on 2016/1/14 0014.
 */
public class VoteResultPresenter {

    private VoteBiz voteBiz;
    private IVoteResult iVoteResult;

    public VoteResultPresenter(IVoteResult iVoteResult) {
        voteBiz = new VoteBiz();
        this.iVoteResult = iVoteResult;
    }

    public void getVoteResult(int subjectId) {
        voteBiz.getVoteResult(subjectId, new CallBack<ResultBean>() {
            @Override
            public void getSuccess(ResultBean result) {
                iVoteResult.initChart(result);
            }

            @Override
            public void getFailed(String msg) {

            }
        });
    }

}
