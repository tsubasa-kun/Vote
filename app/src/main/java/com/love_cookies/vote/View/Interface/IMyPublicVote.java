package com.love_cookies.vote.View.Interface;

import com.love_cookies.vote.Model.Bean.VoteBean;

import java.util.List;

/**
 * Created by xiekun on 2016/1/13.
 */
public interface IMyPublicVote {
    void initView();
    void getVote(int userId, int page);
    void refreshList(List<VoteBean> date);
}
