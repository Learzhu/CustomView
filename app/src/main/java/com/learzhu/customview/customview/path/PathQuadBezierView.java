package com.learzhu.customview.customview.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @time 2016/9/13
 * 贝塞尔曲线的使用
 * 贝塞尔曲线奠定了计算机绘图的基础(因为它可以将任何复杂的图形用精确的数学语言进行描述)
 * <p/>
 * Examples:
 * QQ小红点拖拽效果
 * 一些炫酷的下拉刷新控件
 * 阅读软件的翻书效果
 * 一些平滑的折线图的制作
 * 很多炫酷的动画效果
 * <p/>
 * Bezier:
 * 类型	作用
 * 数据点	确定曲线的起始和结束位置
 * 控制点	确定曲线的弯曲程度
 * <p/>
 * 一阶曲线是没有控制点的，仅有两个数据点(A 和 B)，最终效果一个线段。一阶曲线其实就是前面讲解过的lineTo
 * 二阶曲线对应的方法是quadTo  二阶曲线由两个数据点(A 和 C)，一个控制点(B)来描述曲线状态
 * <p/>
 * 连接AB BC，并在AB上取点D，BC上取点E，使其满足条件：
 * <p/>
 * http://www.gcssloop.com/customview/Path_Bezier/
 * <p/>
 * 三阶曲线对应的方法是cubicTo 三阶曲线由两个数据点(A 和 D)，两个控制点(B 和 C)来描述曲线状态
 * <p/>
 * 此处为二阶贝塞尔曲线
 */
public class PathQuadBezierView extends View {

    private Paint mPaint;
    private int centerX, centerY;
    private PointF start, end, control;

    public PathQuadBezierView(Context context) {
        this(context, null);
    }

    public PathQuadBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PathQuadBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PathQuadBezierView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

        // 初始化数据点和控制点的位置
        start.x = centerX - 200;
        start.y = centerY;

        end.x = centerX + 200;
        end.y = centerY;
        control.x = centerX;
        control.y = centerY - 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawQuadBezier(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据触摸位置更新控制点，并提示重绘
        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;
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

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control = new PointF(0, 0);
    }

    /**
     * 画二阶贝塞尔曲线
     *
     * @param canvas
     */
    private void drawQuadBezier(Canvas canvas) {
        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(control.x, control.y, mPaint);

        // 绘制辅助线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint);
        canvas.drawLine(end.x, end.y, control.x, control.y, mPaint);

        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path mPath = new Path();
        mPath.moveTo(start.x, start.y);
        mPath.quadTo(control.x, control.y, end.x, end.y);
        canvas.drawPath(mPath, mPaint);
    }

}
