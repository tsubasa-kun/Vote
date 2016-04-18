package com.love_cookies.vote.View.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.love_cookies.vote.Model.Bean.ResultBean;
import com.love_cookies.vote.Presenter.VoteResultPresenter;
import com.love_cookies.vote.R;
import com.love_cookies.vote.View.Interface.IVoteResult;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_vote_result)
public class VoteResultActivity extends BaseActivity implements IVoteResult{

    @ViewInject(R.id.title_text)
    TextView pageTitle;
    @ViewInject(R.id.left_btn)
    ImageView btnLeft;
    @ViewInject(R.id.result_chart)
    HorizontalBarChart barChart;

    VoteResultPresenter voteResultPresenter = new VoteResultPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getVoteResult(getIntent().getIntExtra("subject_id", 0));
    }

    @Override
    public void initView() {
        pageTitle.setText(R.string.vote_result_title);
        btnLeft.setImageResource(R.drawable.title_btn_back);
        btnLeft.setOnClickListener(this);
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
    public void getVoteResult(int subjectId) {
        voteResultPresenter.getVoteResult(subjectId);
    }

    @Override
    public void initChart(ResultBean resultBean) {
        int count = resultBean.getColumns().size();
        float range = resultBean.getTotal();

        //x的数据
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xValues.add(resultBean.getColumns().get(i).getColumn());
        }

        //y的数据
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        for (int i = 0; i < count; i++) {
            float value = (float) resultBean.getColumns().get(i).getCount();
            yValues.add(new BarEntry(value, i));
        }

        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, resultBean.getSubject().getSubject());
        barDataSet.setColor(getResources().getColor(R.color.theme));
        barDataSet.setBarShadowColor(Color.TRANSPARENT);
        barDataSet.setValueTextSize(10);
        barDataSet.setValueTextColor(getResources().getColor(R.color.theme));

        ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
        barDataSets.add(barDataSet);

        BarData barData = new BarData(xValues, barDataSets);

        barChart.setDrawBorders(false);//是否在折线图上添加边框
        barChart.setDescription("");//数据描述
        barChart.setNoDataTextDescription(getResources().getString(R.string.empty));//如果没有数据的时候，会显示这个
        barChart.setDrawGridBackground(false);//是否显示表格颜色
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF);//表格的的颜色，在这里是给颜色设置一个透明度
        barChart.setTouchEnabled(true);//设置是否可以触摸
        barChart.setDragEnabled(true);//是否可以拖拽
        barChart.setScaleEnabled(true);//是否可以缩放
        barChart.setPinchZoom(false);//
        barChart.setDrawBarShadow(true);
        barChart.setData(barData);//设置数据

        Legend mLegend = barChart.getLegend();//设置比例图标示
        mLegend.setForm(Legend.LegendForm.CIRCLE);//样式
        mLegend.setFormSize(14f);//字体
        mLegend.setTextColor(Color.BLACK);//颜色

        barChart.animateY(1500);//执行动画，y轴
    }

}
