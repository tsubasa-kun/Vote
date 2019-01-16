package com.love_cookies.vote.Model.Biz;

import android.content.ContentValues;
import android.database.Cursor;

import com.love_cookies.vote.Model.Bean.ResultBean;
import com.love_cookies.vote.Model.Bean.VoteBean;
import com.love_cookies.vote.Model.Biz.Interface.CallBack;
import com.love_cookies.vote.Model.Biz.Interface.IVoteBiz;
import com.love_cookies.vote.Presenter.MyApplication;
import com.love_cookies.vote.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiekun on 2016/1/12 0012.
 */
public class VoteBiz implements IVoteBiz {

    @Override
    public void publicVote(String subject, String content, List<String> column, String date, int max, int userId, String userName, CallBack<Integer> callBack) {
        try {
            ContentValues subjectValues = new ContentValues();
            subjectValues.put("user_id", userId);
            subjectValues.put("user_name", userName);
            subjectValues.put("subject", subject);
            subjectValues.put("content", content);
            subjectValues.put("date", date);
            subjectValues.put("max", max);
            subjectValues.put("status", 0);
            MyApplication.db.insert("vote_subject", null, subjectValues);

            String sql_getId = "select LAST_INSERT_ROWID() ";
            Cursor cursor = MyApplication.db.rawQuery(sql_getId, null);
            cursor.moveToFirst();
            int subject_id = cursor.getInt(0);
            cursor.close();

            for(String cl : column) {
                ContentValues columnValues = new ContentValues();
                columnValues.put("subject_id", subject_id);
                columnValues.put("column", cl);
                columnValues.put("count", 0);
                MyApplication.db.insert("vote_column", null, columnValues);
            }

            callBack.getSuccess(0);
        } catch (Exception ex) {
            callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.error));
        }
    }

    @Override
    public void getVote(int userId, int subjectId, CallBack<VoteBean> callBack) {
        try {
            VoteBean voteBean = new VoteBean();
            VoteBean.Subject subject = null;
            VoteBean.Column column;
            List<VoteBean.Column> columns = new ArrayList<>();

            String sql_sel = "SELECT * FROM vote_subject LEFT OUTER JOIN vote_column ON vote_column.subject_id = vote_subject.subject_id WHERE vote_subject.subject_id = ?";
            Cursor cursor = MyApplication.db.rawQuery(sql_sel, new String[]{subjectId + ""});
            while (cursor.moveToNext()){
                subject = voteBean.new Subject();
                subject.setId(cursor.getInt(cursor.getColumnIndex("subject_id")));
                subject.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                subject.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                subject.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
                subject.setContent(cursor.getString(cursor.getColumnIndex("content")));
                subject.setDate(cursor.getString(cursor.getColumnIndex("date")));
                subject.setMax(cursor.getInt(cursor.getColumnIndex("max")));
                subject.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

                column = voteBean.new Column();
                column.setId(cursor.getInt(cursor.getColumnIndex("column_id")));
                column.setColumn(cursor.getString(cursor.getColumnIndex("column")));
                column.setCount(cursor.getInt(cursor.getColumnIndex("count")));
                columns.add(column);
            }
            voteBean.setSubject(subject);
            voteBean.setColumns(columns);

            String sql_vote = "SELECT count(*) FROM user_vote WHERE user_id = ? AND subject_id = ?";
            cursor = MyApplication.db.rawQuery(sql_vote, new String[]{userId + "", subjectId + ""});
            cursor.moveToFirst();
            if(cursor.getInt(0) != 0) {
                voteBean.setVote(true);
            } else {
                voteBean.setVote(false);
            }
            cursor.close();

            callBack.getSuccess(voteBean);
        } catch (Exception ex) {
            callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.error));
        }
    }

    @Override
    public void getSimpleVote(int userId, int page, CallBack<List<VoteBean>> callBack) {
        try {
            List<VoteBean> result = new ArrayList<>();
            VoteBean voteBean;
            VoteBean.Subject subject;

            String sql = "SELECT * FROM vote_subject ORDER BY status, date DESC LIMIT 10 OFFSET " + (page * 10);
            Cursor cursor = MyApplication.db.rawQuery(sql, null);
            while (cursor.moveToNext()){
                voteBean = new VoteBean();
                subject = voteBean.new Subject();
                subject.setId(cursor.getInt(cursor.getColumnIndex("subject_id")));
                subject.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                subject.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                subject.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
                subject.setContent(cursor.getString(cursor.getColumnIndex("content")));
                subject.setDate(cursor.getString(cursor.getColumnIndex("date")));
                subject.setMax(cursor.getInt(cursor.getColumnIndex("max")));
                subject.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                voteBean.setSubject(subject);
                result.add(voteBean);
            }
            cursor.close();

            callBack.getSuccess(result);
        } catch (Exception ex) {
            callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.error));
        }
    }

    @Override
    public void getPublicVote(int userId, int page, CallBack<List<VoteBean>> callBack) {
        try {
            List<VoteBean> result = new ArrayList<>();
            VoteBean voteBean;
            VoteBean.Subject subject;

            String sql = "SELECT * FROM vote_subject WHERE user_id = ? ORDER BY status, date DESC LIMIT 10 OFFSET " + (page * 10);
            Cursor cursor = MyApplication.db.rawQuery(sql, new String[]{userId + ""});
            while (cursor.moveToNext()){
                voteBean = new VoteBean();
                subject = voteBean.new Subject();
                subject.setId(cursor.getInt(cursor.getColumnIndex("subject_id")));
                subject.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                subject.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                subject.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
                subject.setContent(cursor.getString(cursor.getColumnIndex("content")));
                subject.setDate(cursor.getString(cursor.getColumnIndex("date")));
                subject.setMax(cursor.getInt(cursor.getColumnIndex("max")));
                subject.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                voteBean.setSubject(subject);
                result.add(voteBean);
            }
            cursor.close();

            callBack.getSuccess(result);
        } catch (Exception ex) {
            callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.error));
        }
    }

    @Override
    public void getJoinVote(int userId, int page, CallBack<List<VoteBean>> callBack) {
        try {
            List<VoteBean> result = new ArrayList<>();
            VoteBean voteBean;
            VoteBean.Subject subject;

            String sql = "SELECT * FROM vote_subject WHERE subject_id IN (SELECT subject_id FROM user_vote WHERE user_id = ?) ORDER BY status, date DESC LIMIT 10 OFFSET " + (page * 10);
            Cursor cursor = MyApplication.db.rawQuery(sql, new String[]{userId + ""});
            while (cursor.moveToNext()) {
                voteBean = new VoteBean();
                subject = voteBean.new Subject();
                subject.setId(cursor.getInt(cursor.getColumnIndex("subject_id")));
                subject.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                subject.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                subject.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
                subject.setContent(cursor.getString(cursor.getColumnIndex("content")));
                subject.setDate(cursor.getString(cursor.getColumnIndex("date")));
                subject.setMax(cursor.getInt(cursor.getColumnIndex("max")));
                subject.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                voteBean.setSubject(subject);
                result.add(voteBean);
            }
            cursor.close();

            callBack.getSuccess(result);
        } catch (Exception ex) {
            callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.error));
        }
    }

    @Override
    public void doVote(int userId, int subjectId, List<Integer> columnIds, CallBack<Integer> callBack) {
        try {
            String sql_up = "UPDATE vote_column SET count = (SELECT count+1 FROM vote_column WHERE column_id = ?) WHERE column_id = ?;";
            for(int id : columnIds) {
                MyApplication.db.execSQL(sql_up, new String[]{id + "", id + ""});
            }

            ContentValues values = new ContentValues();
            values.put("user_id", userId);
            values.put("subject_id", subjectId);
            MyApplication.db.insert("user_vote", null, values);

            callBack.getSuccess(0);
        } catch (Exception ex) {
            callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.error));
        }
    }

    @Override
    public void finishVote(int subjectId, CallBack<Integer> callBack) {
        try {
            ContentValues values = new ContentValues();
            values.put("status", 1);
            MyApplication.db.update("vote_subject", values, "subject_id = ?", new String[]{subjectId + ""});

            callBack.getSuccess(0);
        } catch (Exception ex) {
            callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.error));
        }
    }

    @Override
    public void getVoteResult(int subjectId, CallBack<ResultBean> callBack) {
        try {
            ResultBean resultBean = new ResultBean();
            ResultBean.Subject subject = null;
            ResultBean.Column column;
            List<ResultBean.Column> columns = new ArrayList<>();
            int total = 0;

            String sql = "SELECT * FROM vote_subject LEFT OUTER JOIN vote_column ON vote_column.subject_id = vote_subject.subject_id WHERE vote_subject.subject_id = ?";
            Cursor cursor = MyApplication.db.rawQuery(sql, new String[]{subjectId + ""});
            while (cursor.moveToNext()) {
                subject = resultBean.new Subject();
                subject.setId(cursor.getInt(cursor.getColumnIndex("subject_id")));
                subject.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                subject.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                subject.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
                subject.setContent(cursor.getString(cursor.getColumnIndex("content")));
                subject.setDate(cursor.getString(cursor.getColumnIndex("date")));
                subject.setMax(cursor.getInt(cursor.getColumnIndex("max")));
                subject.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

                column = resultBean.new Column();
                column.setId(cursor.getInt(cursor.getColumnIndex("column_id")));
                column.setColumn(cursor.getString(cursor.getColumnIndex("column")));
                column.setCount(cursor.getInt(cursor.getColumnIndex("count")));
                columns.add(column);
            }
            resultBean.setSubject(subject);
            resultBean.setColumns(columns);

            for(ResultBean.Column col : columns) {
                total += col.getCount();
            }
            resultBean.setTotal(total);

            cursor.close();

            callBack.getSuccess(resultBean);
        } catch (Exception ex) {
            callBack.getFailed(MyApplication.getInstance().getResources().getString(R.string.error));
        }
    }

}
