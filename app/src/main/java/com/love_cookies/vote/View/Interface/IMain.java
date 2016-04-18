package com.love_cookies.vote.View.Interface;

import com.love_cookies.vote.Model.Bean.VoteBean;

import java.util.List;

/**
 * Created by xiekun on 2016/1/12 0012.
 */
public interface IMain {
    void initView();
    void refreshList(List<VoteBean> date);
    void getDate(int userId, int page);
}
