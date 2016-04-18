package com.love_cookies.vote.Model.Biz.Interface;

import com.love_cookies.vote.Model.Bean.ResultBean;
import com.love_cookies.vote.Model.Bean.VoteBean;

import java.util.List;

/**
 * Created by xiekun on 2016/1/12 0012.
 */
public interface IVoteBiz {
    void publicVote(String subject, String content, List<String> column, String date, int max, int userId, String userName, CallBack<Integer> callBack);
    void getVote(int userId, int subjectId, CallBack<VoteBean> callBack);
    void getSimpleVote(int userId, int page, CallBack<List<VoteBean>> callBack);
    void getPublicVote(int userId, int page, CallBack<List<VoteBean>> callBack);
    void getJoinVote(int userId, int page, CallBack<List<VoteBean>> callBack);
    void doVote(int userId, int subjectId, List<Integer> columnIds, CallBack<Integer> callBack);
    void finishVote(int subjectId, CallBack<Integer> callBack);
    void getVoteResult(int subjectId, CallBack<ResultBean> callBack);
}
