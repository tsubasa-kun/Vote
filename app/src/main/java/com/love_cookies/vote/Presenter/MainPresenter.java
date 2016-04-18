package com.love_cookies.vote.Presenter;

import com.love_cookies.vote.Model.Bean.VoteBean;
import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.VoteBiz;
import com.love_cookies.vote.View.Interface.IMain;

import java.util.List;

/**
 * Created by xiekun on 2016/1/12 0012.
 */
public class MainPresenter {

    private VoteBiz voteBiz;
    private IMain iMain;

    public MainPresenter(IMain iMain) {
        voteBiz = new VoteBiz();
        this.iMain = iMain;
    }

    public void getDate(int userId, int page) {
        voteBiz.getSimpleVote(userId, page - 1, new CallBack<List<VoteBean>>() {
            @Override
            public void getSuccess(List<VoteBean> result) {
                iMain.refreshList(result);
            }

            @Override
            public void getFailed(String msg) {

            }
        });
    }

}
