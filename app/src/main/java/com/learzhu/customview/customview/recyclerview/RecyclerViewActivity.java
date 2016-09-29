package com.learzhu.customview.customview.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.learzhu.customview.customview.R;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * recyclerView的使用过程
 * mRecyclerView = findView(R.id.id_recyclerview);
 * //设置布局管理器
 * mRecyclerView.setLayoutManager(layout);
 * //设置adapter
 * mRecyclerView.setAdapter(adapter)
 * //设置Item增加、移除动画
 * mRecyclerView.setItemAnimator(new DefaultItemAnimator());
 * //添加分割线
 * mRecyclerView.addItemDecoration(new DividerItemDecoration(
 * getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
 */
public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    //    private RecyclerViewWithHeadAdapter mAdapter;
    private RecyclerViewWithHeadAdapter1 mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        setRecyclerView();
    }

    private void setRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        /*以下是不同的布局管理器*/
//        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new GridItemDecoration(this, true));

//        mAdapter = new RecyclerViewWithHeadAdapter();
        mAdapter = new RecyclerViewWithHeadAdapter1();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addDatas(generateData());
        setHeader(mRecyclerView);

//        mAdapter.setOnItemClickListener(new RecyclerViewWithHeadAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, Object data) {
//                Toast.makeText(RecyclerViewActivity.this, position + "," + data, Toast.LENGTH_SHORT).show();
//            }
//
////            @Override
////            public void onItemClick(int position, Objects data) {
////                Toast.makeText(RecyclerViewActivity.this, position + "," + data, Toast.LENGTH_SHORT).show();
////            }
//        });

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Toast.makeText(RecyclerViewActivity.this, position + "," + data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setHeader(RecyclerView recyclerView) {
        View header = LayoutInflater.from(this).inflate(R.layout.item_recyclerview_header, recyclerView, false);
        mAdapter.setHeaderView(header);
    }

    /**
     * 设置数据
     *
     * @return
     */
    private ArrayList<String> generateData() {
        /*不一样的方式*/
        ArrayList<String> data = new ArrayList<String>() {
            {
                for (int i = 0; i < 100; i++) {
                    add("DATA " + i);
                }
            }
        };
//        ArrayList<String> data = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            data.add("DATA " + i);
//        }
        return data;
    }
}
