package com.love_cookies.vote.View.Interface;

/**
 * Created by xiekun on 2016/1/12 0012.
 */
public interface ICreateVote {
    void initView();
    void addColumn();
    void choseMax();
    void reColumn(int index);
    void refreshList();
    void doPublic();
    void publicSuccess();
    void publicFailed(String msg);
}
