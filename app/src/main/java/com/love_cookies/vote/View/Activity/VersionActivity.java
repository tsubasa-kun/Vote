package com.love_cookies.vote.View.Activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.love_cookies.vote.Model.Bean.ChangeLogBean;
import com.love_cookies.vote.Model.Bean.CommonAdapter;
import com.love_cookies.vote.Model.Bean.ViewHolder;
import com.love_cookies.vote.Presenter.VersionPresenter;
import com.love_cookies.vote.R;
import com.love_cookies.vote.View.Interface.IVersion;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiekun on 2016/01/11 0011.
 */
@ContentView(R.layout.activity_version)
public class VersionActivity extends BaseActivity implements IVersion {

    @ViewInject(R.id.title_text)
    TextView pageTitle;
    @ViewInject(R.id.left_btn)
    ImageView leftBtn;
    @ViewInject(R.id.version_text)
    TextView versionText;
    @ViewInject(R.id.list_view)
    ListView listView;

    ChangeLogListViewAdapter changeLogListViewAdapter;

    List<ChangeLogBean.ChangeLogEntity> changeLogDate = new ArrayList<>();

    VersionPresenter versionPresenter = new VersionPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageTitle.setText(R.string.setting_version);
        leftBtn.setImageResource(R.drawable.title_btn_back);
        leftBtn.setOnClickListener(this);
        versionText.setText(getAppVersion());
        initListView();
        versionPresenter.getChangeLog(this);
    }

    @Override
    public void initListView() {
        changeLogListViewAdapter = new ChangeLogListViewAdapter(this, changeLogDate);
        listView.setAdapter(changeLogListViewAdapter);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void initChangeLog(ChangeLogBean changeLogBean) {
        changeLogDate.addAll(changeLogBean.getChange_log());
        changeLogListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {

    }

    @Override
    public String getAppVersion() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return " V " + info.versionName;
    }

    class ChangeLogListViewAdapter extends CommonAdapter<ChangeLogBean.ChangeLogEntity> {
        public ChangeLogListViewAdapter(Context context, List<ChangeLogBean.ChangeLogEntity> datas) {
            super(context, R.layout.item_change_log_list, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, ChangeLogBean.ChangeLogEntity changeLog) {
            viewHolder.setText(R.id.version, changeLog.getVersion());
            viewHolder.setText(R.id.date, changeLog.getDate());
            viewHolder.setText(R.id.description, changeLog.getDescription());
        }
    }

}
