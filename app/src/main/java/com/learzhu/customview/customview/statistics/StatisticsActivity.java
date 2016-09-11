package com.learzhu.customview.customview.statistics;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learzhu.customview.customview.R;

import java.util.ArrayList;

/**
 * 饼状图 统计的饼状图
 */
public class StatisticsActivity extends AppCompatActivity {

    private PieView pieView;
    private ArrayList<PieData> pieDataArrayList;
    private float value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        pieView = (PieView) findViewById(R.id.pie);
        pieView.setData(initData());
        pieView.setStartAngle(0);
    }

    public ArrayList<PieData> initData() {
        pieDataArrayList = new ArrayList<>();
        value = (float) (Math.PI / 2);
        PieData pieData1 = new PieData("A", value);
        PieData pieData2 = new PieData("B", value);
        PieData pieData3 = new PieData("C", value);
        PieData pieData4 = new PieData("D", value);
//        PieData pieData5 = new PieData("E", 5.0f);
        pieDataArrayList.add(pieData1);
        pieDataArrayList.add(pieData2);
        pieDataArrayList.add(pieData3);
        pieDataArrayList.add(pieData4);
//        pieDataArrayList.add(pieData5);
        return pieDataArrayList;
    }


}

