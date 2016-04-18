package com.love_cookies.vote.View.Interface;

import com.love_cookies.vote.Model.Bean.ResultBean;

/**
 * Created by xiekun on 2016/1/14 0014.
 */
public interface IVoteResult {
    void initView();
    void getVoteResult(int subjectId);
    void initChart(ResultBean resultBean);
}
