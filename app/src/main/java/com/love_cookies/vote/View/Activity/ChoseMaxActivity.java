package com.love_cookies.vote.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.love_cookies.vote.R;
import com.love_cookies.vote.Utils.ToastUtils;
import com.love_cookies.vote.View.Interface.IChoseMax;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_chose_max)
public class ChoseMaxActivity extends BaseActivity implements IChoseMax {

    @ViewInject(R.id.number_picker)
    NumberPicker numberPicker;
    @ViewInject(R.id.chose_btn)
    TextView choseBtn;
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
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(999);
        numberPicker.setValue(1);
        choseBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.chose_btn:
                doChose();
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
        setResult(code, intent);
        finish();
        this.overridePendingTransition(R.anim.alpha_out, 0);
    }

    @Override
    public void doChose() {
        if(numberPicker.getValue() <= 0) {
            ToastUtils.show(this, R.string.create_max_error);
        } else {
            Intent intent = new Intent();
            intent.putExtra("max", numberPicker.getValue());
            tFinish(RESULT_OK, intent);
        }
    }
}
