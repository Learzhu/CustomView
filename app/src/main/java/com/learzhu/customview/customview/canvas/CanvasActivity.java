package com.learzhu.customview.customview.canvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.camera2.TotalCaptureResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.learzhu.customview.customview.R;
import com.learzhu.customview.customview.path.PathImproveView;

/**
 * Canvas详解
 * 一.Canvas的常用操作速查表
 * 操作类型	相关API	备注
 * 绘制颜色	drawColor, drawRGB, drawARGB	使用单一颜色填充整个画布
 * 绘制基本形状	drawPoint, drawPoints, drawLine, drawLines, drawRect, drawRoundRect, drawOval, drawCircle, drawArc	依次为 点、线、矩形、圆角矩形、椭圆、圆、圆弧
 * 绘制图片	drawBitmap, drawPicture	绘制位图和图片
 * 绘制文本	drawText, drawPosText, drawTextOnPath	依次为 绘制文字、绘制文字时指定每个文字位置、根据路径绘制文字
 * 绘制路径	drawPath	绘制路径，绘制贝塞尔曲线时也需要用到该函数
 * 顶点操作	drawVertices, drawBitmapMesh	通过对顶点操作可以使图像形变，drawVertices直接对画布作用、 drawBitmapMesh只对绘制的Bitmap作用
 * 画布剪裁	clipPath, clipRect	设置画布的显示区域
 * 画布快照	save, restore, saveLayerXxx, restoreToCount, getSaveCount	依次为 保存当前状态、 回滚到上一次保存的状态、 保存图层状态、 回滚到指定状态、 获取保存次数
 * 画布变换	translate, scale, rotate, skew	依次为 位移、缩放、 旋转、错切
 * Matrix(矩阵)	getMatrix, setMatrix, concat	实际上画布的位移，缩放等操作的都是图像矩阵Matrix， 只不过Matrix比较难以理解和使用，故封装了一些常用的方法。
 * <p/>
 * ****所有的画布操作都只影响后续的绘制，对之前已经绘制过的内容没有影响
 * <p/>
 * <p/>
 * Example:从坐标原点为起点，绘制一个长度为20dp，与水平线夹角为30度的线段怎么做？
 * 1.先使用三角函数计算出线段结束点的坐标，然后调用drawLine即可
 * 2.先绘制一个长度为20dp的水平线，然后将这条水平线旋转30度
 * 合理的使用画布操作可以帮助你用更容易理解的方式创作你想要的效果
 */
public class CanvasActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout relativeLayout;

    private LinearLayout buttonsLl;
    private Button toCaveViewBtn, toCavePictureViewBtn, toCheckViewBtn, toTaijiViewBtn, toPathImproveViewBtn;
    private CanvasView mCanvasView;
    private CanvasPictureView mCanvasPictureView;
    private CheckView mCheckView;
    private TaiJiBaguaView mTaiJiBaguaView;
    private PathImproveView mPathImproveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
//        showViewByCode();
        initViews();
    }

    /**
     * 初始化views控件
     */
    private void initViews() {
        buttonsLl = (LinearLayout) findViewById(R.id.buttons);
        /*CaveView */
        toCaveViewBtn = (Button) findViewById(R.id.to_cave_view);
        toCaveViewBtn.setOnClickListener(this);
        mCanvasView = (CanvasView) findViewById(R.id.canvas_view);
        /*CanvasPictureView*/
        toCavePictureViewBtn = (Button) findViewById(R.id.to_cave_picture_view);
        toCavePictureViewBtn.setOnClickListener(this);
        mCanvasPictureView = (CanvasPictureView) findViewById(R.id.canvas_picture_view);
        /*CheckView*/
        toCheckViewBtn = (Button) findViewById(R.id.to_check_view);
        toCheckViewBtn.setOnClickListener(this);
        mCheckView = (CheckView) findViewById(R.id.checkview);
        /*TaiJiBaguaView*/
        toTaijiViewBtn = (Button) findViewById(R.id.to_taiji_view);
        toTaijiViewBtn.setOnClickListener(this);
        mTaiJiBaguaView = (TaiJiBaguaView) findViewById(R.id.taiji_view);
        /*PathImproveView*/
        toPathImproveViewBtn = (Button) findViewById(R.id.to_path_improve_view);
        toPathImproveViewBtn.setOnClickListener(this);
        mPathImproveView = (PathImproveView) findViewById(R.id.path_improve_view);
    }

    /**
     * 打钩的图
     */
    private void check() {
        mCheckView.setAnimDuration(100);
        mCheckView.check();
    }

    /**
     * 通过代码显示自定义的View
     */
    private void showViewByCode() {
    /*代码添加View   注意如果在XML里面有设置还是会显示此处的图形 因为源码里面有清空的PhoneWindow.setContentView()*/
        relativeLayout = (RelativeLayout) findViewById(R.id.root);
        CanvasView canvasView = new CanvasView(this);
        canvasView.setMinimumHeight(400);
        canvasView.setMinimumWidth(400);
        canvasView.invalidate();
        relativeLayout.addView(canvasView);
    }

    @Override
    public void onClick(View v) {
        buttonsLl.setVisibility(View.GONE);
        switch (v.getId()) {
            case R.id.to_cave_view:
                mCanvasView.setVisibility(View.VISIBLE);
                break;
            case R.id.to_cave_picture_view:
                mCanvasPictureView.setVisibility(View.VISIBLE);
                break;
            case R.id.to_check_view:
                mCheckView.setVisibility(View.VISIBLE);
                check();
                break;
            case R.id.to_taiji_view:
                mTaiJiBaguaView.setVisibility(View.VISIBLE);
                break;
            case R.id.to_path_improve_view:
                mPathImproveView.setVisibility(View.VISIBLE);
                break;
        }
    }
}
