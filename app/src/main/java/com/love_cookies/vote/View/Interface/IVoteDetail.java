package com.love_cookies.vote.View.Interface;

import com.love_cookies.vote.Model.Bean.VoteBean;

/**
 * Created by xiekun on 2016/1/12 0012.
 */
public interface IVoteDetail {
    void initView();
    void getVote(int userId, int subjectId);
    void initDate(VoteBean voteBean);
    void doVote(int userId, int subjectId);
    void refreshBtn();
}
