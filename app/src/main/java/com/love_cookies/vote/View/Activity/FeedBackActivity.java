package com.love_cookies.vote.View.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.love_cookies.vote.Presenter.FeedBackPresenter;
import com.love_cookies.vote.Presenter.MyApplication;
import com.love_cookies.vote.R;
import com.love_cookies.vote.Utils.ToastUtils;
import com.love_cookies.vote.View.Interface.IFeedBack;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_feed_back)
public class FeedBackActivity extends BaseActivity implements IFeedBack {

    @ViewInject(R.id.title_text)
    TextView pageTitle;
    @ViewInject(R.id.left_btn)
    ImageView leftBtn;
    @ViewInject(R.id.feedback_et)
    EditText feedbackET;
    @ViewInject(R.id.submit_btn)
    TextView submitBtn;

    FeedBackPresenter feedBackPresenter = new FeedBackPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void initView() {
        pageTitle.setText(R.string.home_feedback);
        leftBtn.setImageResource(R.drawable.title_btn_back);
        leftBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;
            case R.id.submit_btn:
                doFeedBack();
                break;
            default:
                break;
        }
    }

    @Override
    public void doFeedBack() {
        if(TextUtils.isEmpty(feedbackET.getText().toString())) {
            ToastUtils.show(this, R.string.create_content_hint);
        } else {
            feedBackPresenter.doFeedBack(MyApplication.user.getId(), MyApplication.user.getNickname(), feedbackET.getText().toString());
        }
    }

    @Override
    public void feedBackSuccess() {
        finish();
        ToastUtils.show(this, R.string.feedback_success);
    }

}
