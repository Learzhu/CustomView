package com.learzhu.customview.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.learzhu.customview.customview.shapedetail.CircleActivity;
import com.learzhu.customview.customview.shapedetail.OvalActivity;
import com.learzhu.customview.customview.shapedetail.PathActivity;
import com.learzhu.customview.customview.shapedetail.RectActivity;
import com.learzhu.customview.customview.shapedetail.SectorActivity;
import com.learzhu.customview.customview.shapedetail.ThriangleActivity;
import com.learzhu.customview.customview.shapedetail.TvIvActivity;
import com.learzhu.customview.customview.viewdetail.CustomTextViewActivity;

public class DrawViewListActivity extends AppCompatActivity {

    private ListView listView;
    //item数据源集合
    private String[] names = {"矩形", "圆形", "三角形", "扇形", "椭圆", "曲线", "文字和图片", "自定义TextView"};

    private ArrayAdapter<String> adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_view);
        initView();
    }

    private void initView() {
        //实例化listview
        listView = (ListView) findViewById(R.id.listview);
        //实例化数据源
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        //listview设置adapter
        listView.setAdapter(adapter);
        //listview设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //判断点击了第几个
                if (id == 0) {
                    //矩形
                    startActivity(new Intent(DrawViewListActivity.this, RectActivity.class));
                } else if (id == 1) {
                    //圆形
                    startActivity(new Intent(DrawViewListActivity.this, CircleActivity.class));
                } else if (id == 2) {
                    //三角形
                    startActivity(new Intent(DrawViewListActivity.this, ThriangleActivity.class));
                } else if (id == 3) {
                    //扇形
                    startActivity(new Intent(DrawViewListActivity.this, SectorActivity.class));
                } else if (id == 4) {
                    //椭圆
                    startActivity(new Intent(DrawViewListActivity.this, OvalActivity.class));
                } else if (id == 5) {
                    //曲线
                    startActivity(new Intent(DrawViewListActivity.this, PathActivity.class));
                } else if (id == 6) {
                    //曲线
                    startActivity(new Intent(DrawViewListActivity.this, TvIvActivity.class));
                } else if (id == 7) {
                    //自定义的TextView
                    startActivity(new Intent(DrawViewListActivity.this, CustomTextViewActivity.class));
                }
            }
        });
    }
}
