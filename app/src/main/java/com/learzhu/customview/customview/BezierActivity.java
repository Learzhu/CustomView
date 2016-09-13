package com.learzhu.customview.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.learzhu.customview.customview.path.PathCubicBezierView;
import com.learzhu.customview.customview.path.PathQuadBezierView;

/**
 * 在制作这个实例之前，首先要明确一个内容，就是在什么情况下需要使用贝塞尔曲线？
 * <p/>
 * 需要绘制不规则图形时？ 当然不是！目前来说，我觉得使用贝塞尔曲线主要有以下几个方面(仅个人拙见，可能存在错误，欢迎指正)
 * 序号	内容	用例
 * 1	事先不知道曲线状态，需要实时计算时	天气预报气温变化的平滑折线图
 * 2	显示状态会根据用户操作改变时	QQ小红点，仿真翻书效果
 * 3	一些比较复杂的运动状态(配合PathMeasure使用)	复杂运动状态的动画效果
 * <p/>
 * 至于只需要一个静态的曲线图形的情况，用图片岂不是更好，大量的计算会很不划算。
 * <p/>
 * 如果是显示SVG矢量图的话，已经有相关的解析工具了(内部依旧运用的有贝塞尔曲线)，不需要手动计算。
 * <p/>
 * 贝塞尔曲线的主要优点是可以实时控制曲线状态，并可以通过改变控制点的状态实时让曲线进行平滑的状态变化。
 */
public class BezierActivity extends AppCompatActivity implements View.OnClickListener {

    private Button quadBezierBtn, cubicBezierBtn;
    /*二阶贝塞尔曲线*/
    private PathQuadBezierView mPathQuadBezierView;
    /*三阶贝塞尔曲线*/
    private PathCubicBezierView mPathCubicBezierView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        initViews();
    }

    private void initViews() {
        /*二阶贝塞尔*/
        quadBezierBtn = (Button) findViewById(R.id.show_quad_bezier);
        quadBezierBtn.setOnClickListener(this);
        mPathQuadBezierView = (PathQuadBezierView) findViewById(R.id.quad_bezier);
        /*三阶贝塞尔*/
        cubicBezierBtn = (Button) findViewById(R.id.show_cubic_bezier);
        cubicBezierBtn.setOnClickListener(this);
        mPathCubicBezierView = (PathCubicBezierView) findViewById(R.id.cubic_bezier);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_quad_bezier:
                mPathQuadBezierView.setVisibility(View.VISIBLE);
                quadBezierBtn.setVisibility(View.GONE);
                break;
            case R.id.show_cubic_bezier:
                mPathCubicBezierView.setVisibility(View.VISIBLE);
                cubicBezierBtn.setVisibility(View.GONE);
                mPathCubicBezierView.setMode(true);
                break;
        }
    }
}
