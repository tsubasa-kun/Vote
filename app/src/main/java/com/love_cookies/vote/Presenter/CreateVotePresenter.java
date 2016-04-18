package com.love_cookies.vote.Presenter;

import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.VoteBiz;
import com.love_cookies.vote.View.Interface.ICreateVote;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by xiekun on 2016/1/12 0012.
 */
public class CreateVotePresenter {

    private VoteBiz voteBiz;
    private ICreateVote iCreateVote;

    public CreateVotePresenter(ICreateVote iCreateVote) {
        voteBiz = new VoteBiz();
        this.iCreateVote = iCreateVote;
    }

    public void doPublic(String subject, String content, List<String> column, int max, int userId, String userName) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String date = formatter.format(curDate);

        voteBiz.publicVote(subject, content, column, date, max, userId, userName, new CallBack<Integer>() {
            @Override
            public void getSuccess(Integer result) {
                iCreateVote.publicSuccess();
            }

            @Override
            public void getFailed(String msg) {
                iCreateVote.publicFailed(msg);
            }
        });
    }

}
