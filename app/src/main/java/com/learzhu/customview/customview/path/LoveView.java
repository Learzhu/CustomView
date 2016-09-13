package com.learzhu.customview.customview.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @time 2016/9/13
 * 一个爱心的实现
 * <p/>
 * 首先要明确一个内容，就是在什么情况下需要使用贝塞尔曲线？
 * <p/>
 * 需要绘制不规则图形时？ 当然不是！目前来说，我觉得使用贝塞尔曲线主要有以下几个方面(仅个人拙见，可能存在错误，欢迎指正)
 * 序号	内容	用例
 * 1	事先不知道曲线状态，需要实时计算时	天气预报气温变化的平滑折线图
 * 2	显示状态会根据用户操作改变时	QQ小红点，仿真翻书效果
 * 3	一些比较复杂的运动状态(配合PathMeasure使用)	复杂运动状态的动画效果
 * <p/>
 * 贝塞尔曲线的主要优点是可以实时控制曲线状态，并可以通过改变控制点的状态实时让曲线进行平滑的状态变化。
 * <p/>
 * 方法一：我们最终的需要的效果是将一个圆转变成一个心形，通过分析可知，圆可以由四段三阶贝塞尔曲线组合而成
 * 方法二：心形也可以由四段的三阶的贝塞尔曲线组成
 * <p/>
 * 两者的差别仅仅在于数据点和控制点位置不同，因此只需要调整数据点和控制点的位置，就能将圆形变为心形。
 * <p/>
 * 核心难点：
 * 1.如何得到数据点和控制点的位置？
 * <p/>
 * 关于使用绘制圆形的数据点与控制点早就已经有人详细的计算好了，可以参考stackoverflow的一个回答How to create circle with Bézier curves?其中的数据只需要拿来用即可。
 * <p/>
 * 而对于心形的数据点和控制点，可以由圆形的部分数据点和控制点平移后得到，具体参数可以自己慢慢调整到一个满意的效果。
 * <p/>
 * 2.如何达到渐变效果？
 * <p/>
 * 渐变其实就是每次对数据点和控制点稍微移动一点，然后重绘界面，在短时间多次的调整数据点与控制点，使其逐渐接近目标值，通过不断的重绘界面达到一种渐变的效果。
 */
public class LoveView extends View {
    private static final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置
    private Paint mPaint;
    private int mCenterX, mCenterY;

    private PointF mCenter = new PointF(0, 0);
    private float mCircleRadius = 200;                  // 圆的半径
    private float mDifference = mCircleRadius * C;        // 圆形的控制点与数据点的差值

    private float[] mData = new float[8];               // 顺时针记录绘制圆形的四个数据点
    private float[] mCtrl = new float[16];              // 顺时针记录绘制圆形的八个控制点

    private float mDuration = 1000;                     // 变化总时长
    private float mCurrent = 0;                         // 当前已进行时长
    private float mCount = 100;                         // 将时长总共划分多少份
    private float mPiece = mDuration / mCount;            // 每一份的时长

    public LoveView(Context context) {
        this(context, null);
    }

    public LoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initData();
    }

    public LoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLoveBezier(canvas);
    }


    /**
     * 创建画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);
    }

    /**
     * 初始化数据点
     */
    private void initData() {
        // 初始化数据点
        mData[0] = 0;
        mData[1] = mCircleRadius;

        mData[2] = mCircleRadius;
        mData[3] = 0;

        mData[4] = 0;
        mData[5] = -mCircleRadius;

        mData[6] = -mCircleRadius;
        mData[7] = 0;

        // 初始化控制点
        mCtrl[0] = mData[0] + mDifference;
        mCtrl[1] = mData[1];

        mCtrl[2] = mData[2];
        mCtrl[3] = mData[3] + mDifference;

        mCtrl[4] = mData[2];
        mCtrl[5] = mData[3] - mDifference;

        mCtrl[6] = mData[4] + mDifference;
        mCtrl[7] = mData[5];

        mCtrl[8] = mData[4] - mDifference;
        mCtrl[9] = mData[5];

        mCtrl[10] = mData[6];
        mCtrl[11] = mData[7] - mDifference;

        mCtrl[12] = mData[6];
        mCtrl[13] = mData[7] + mDifference;

        mCtrl[14] = mData[0] - mDifference;
        mCtrl[15] = mData[1];
    }

    /**
     * 画爱心的曲线
     *
     * @param canvas
     */
    private void drawLoveBezier(Canvas canvas) {
        drawCoordinateSystem(canvas);       // 绘制坐标系

        canvas.translate(mCenterX, mCenterY); // 将坐标系移动到画布中央
        canvas.scale(1, -1);                 // 翻转Y轴

        /*绘制辅助线*/
        drawAuxiliaryLine(canvas);

        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path path = new Path();
        path.moveTo(mData[0], mData[1]);
        /*利用三阶贝塞尔曲线画圆*/
        path.cubicTo(mCtrl[0], mCtrl[1], mCtrl[2], mCtrl[3], mData[2], mData[3]);
        path.cubicTo(mCtrl[4], mCtrl[5], mCtrl[6], mCtrl[7], mData[4], mData[5]);
        path.cubicTo(mCtrl[8], mCtrl[9], mCtrl[10], mCtrl[11], mData[6], mData[7]);
        path.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15], mData[0], mData[1]);

        canvas.drawPath(path, mPaint);

        /*变换成爱心*/
        mCurrent += mPiece;
        if (mCurrent < mDuration) {

            mData[1] -= 120 / mCount;
            mCtrl[7] += 80 / mCount;
            mCtrl[9] += 80 / mCount;

            mCtrl[4] -= 20 / mCount;
            mCtrl[10] += 20 / mCount;

            postInvalidateDelayed((long) mPiece);
        }
    }

    // 绘制坐标系
    private void drawCoordinateSystem(Canvas canvas) {
        canvas.save();                      // 绘制做坐标系

        canvas.translate(mCenterX, mCenterY); // 将坐标系移动到画布中央
        canvas.scale(1, -1);                 // 翻转Y轴

        Paint fuzhuPaint = new Paint();
        fuzhuPaint.setColor(Color.RED);
        fuzhuPaint.setStrokeWidth(5);
        fuzhuPaint.setStyle(Paint.Style.STROKE);

        canvas.drawLine(0, -2000, 0, 2000, fuzhuPaint);
        canvas.drawLine(-2000, 0, 2000, 0, fuzhuPaint);

        canvas.restore();
    }

    // 绘制辅助线
    private void drawAuxiliaryLine(Canvas canvas) {
        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);

        for (int i = 0; i < 8; i += 2) {
            canvas.drawPoint(mData[i], mData[i + 1], mPaint);
        }

        for (int i = 0; i < 16; i += 2) {
            canvas.drawPoint(mCtrl[i], mCtrl[i + 1], mPaint);
        }

        // 绘制辅助线
        mPaint.setStrokeWidth(4);

        for (int i = 2, j = 2; i < 8; i += 2, j += 4) {
            canvas.drawLine(mData[i], mData[i + 1], mCtrl[j], mCtrl[j + 1], mPaint);
            canvas.drawLine(mData[i], mData[i + 1], mCtrl[j + 2], mCtrl[j + 3], mPaint);
        }
        canvas.drawLine(mData[0], mData[1], mCtrl[0], mCtrl[1], mPaint);
        canvas.drawLine(mData[0], mData[1], mCtrl[14], mCtrl[15], mPaint);
    }


}
