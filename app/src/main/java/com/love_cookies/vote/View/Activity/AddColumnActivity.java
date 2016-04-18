package com.love_cookies.vote.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.love_cookies.vote.R;
import com.love_cookies.vote.Utils.ToastUtils;
import com.love_cookies.vote.View.Interface.IAddColumn;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_add_column)
public class AddColumnActivity extends BaseActivity implements IAddColumn {

    @ViewInject(R.id.column_et)
    EditText columnET;
    @ViewInject(R.id.add_btn)
    TextView addBtn;
    @ViewInject(R.id.cancel_btn)
    TextView cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        initView();
    }

    @Override
    public void initView() {
        addBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                doAdd();
                break;
            case R.id.cancel_btn:
                tFinish(RESULT_CANCELED, null);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            tFinish(RESULT_CANCELED, null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void tFinish(int code, Intent intent) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(columnET.getWindowToken(), 0);
        setResult(code, intent);
        finish();
        this.overridePendingTransition(R.anim.alpha_out, 0);
    }

    @Override
    public void doAdd() {
        if(TextUtils.isEmpty(columnET.getText().toString())) {
            ToastUtils.show(this, R.string.create_column_hint);
        } else {
            Intent intent = new Intent();
            intent.putExtra("column", columnET.getText().toString());
            tFinish(RESULT_OK, intent);
        }
    }

}
