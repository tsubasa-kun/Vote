package com.love_cookies.vote.Presenter;

import com.love_cookies.vote.Model.Bean.VoteBean;
import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.VoteBiz;
import com.love_cookies.vote.View.Interface.ICreatedDetail;

/**
 * Created by xiekun on 2016/1/12 0012.
 */
public class CreatedDetailPresenter {

    private VoteBiz voteBiz;
    private ICreatedDetail iCreatedDetail;

    public CreatedDetailPresenter(ICreatedDetail iCreatedDetail) {
        voteBiz = new VoteBiz();
        this.iCreatedDetail = iCreatedDetail;
    }

    public void getVote(int userId, int subjectId) {
        voteBiz.getVote(userId, subjectId, new CallBack<VoteBean>() {
            @Override
            public void getSuccess(VoteBean result) {
                iCreatedDetail.initDate(result);
            }

            @Override
            public void getFailed(String msg) {

            }
        });
    }

    public void finishVote(int subjectId) {
        voteBiz.finishVote(subjectId, new CallBack<Integer>() {
            @Override
            public void getSuccess(Integer result) {
                iCreatedDetail.refreshBtn();
            }

            @Override
            public void getFailed(String msg) {

            }
        });
    }

}
