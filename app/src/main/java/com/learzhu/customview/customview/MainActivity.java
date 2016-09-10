package com.learzhu.customview.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.learzhu.customview.customview.statistics.StatisticsActivity;
import com.learzhu.customview.customview.view.SketchView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button toDrawListViewBtn, toStatisticsBtn;
    private TextView toDragLayoutActivityTv;
    private Intent intent;
    private SketchView sketchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    public void initViews() {
        sketchView = (SketchView) findViewById(R.id.sketch_view);
        toDrawListViewBtn = (Button) findViewById(R.id.to_drawview_list);
        toStatisticsBtn = (Button) findViewById(R.id.statistics);
        toStatisticsBtn.setOnClickListener(this);
        toDragLayoutActivityTv = (TextView) findViewById(R.id.draglayout);
        toDragLayoutActivityTv.setOnClickListener(this);
        sketchView.startAnimation();
        toDrawListViewBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_drawview_list:
                intent = new Intent(this, DrawViewListActivity.class);
                break;
            case R.id.draglayout:
                intent = new Intent(this, DragLayoutActivity.class);
                break;
            case R.id.statistics:
                intent = new Intent(this, StatisticsActivity.class);
                break;
        }
        startActivity(intent);
    }
}
