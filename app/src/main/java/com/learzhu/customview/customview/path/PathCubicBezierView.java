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
 * 三阶曲线对应的方法是cubicTo 三阶曲线由两个数据点(A 和 D)，两个控制点(B 和 C)来描述曲线状态
 * <p/>
 * 此处为三阶贝塞尔曲线
 * <p/>
 * <p/>
 * 三阶曲线相比于二阶曲线可以制作更加复杂的形状，但是对于高阶的曲线，用低阶的曲线组合也可达到相同的效果，就是传说中的降阶。因此我们对贝塞尔曲线的封装方法一般最高只到三阶曲线。
 * 降阶与升阶
 * 类型	释义	变化
 * 降阶	在保持曲线形状与方向不变的情况下，减少控制点数量，即降低曲线阶数	方法变得简单，数据点变多，控制点可能减少，灵活性变弱
 * 升阶	在保持曲线形状与方向不变的情况下，增加控制点数量，即升高曲线阶数	方法更加复杂，数据点不变，控制点增加，灵活性变强
 */
public class PathCubicBezierView extends View {

    private Paint mPaint;
    private int centerX, centerY;
    private boolean mode = true;
    private PointF start, end, control1, control2;

    public PathCubicBezierView(Context context) {
        this(context, null);
    }

    public PathCubicBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PathCubicBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PathCubicBezierView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setMode(boolean mode) {
        this.mode = mode;
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
        control1.x = centerX - 200;
        control1.y = centerY - 100;
        control2.x = centerX + 200;
        control2.y = centerY - 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCubicBezier(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float mX = event.getX();
        if (mX < centerX) {
            setMode(true);
        } else {
            setMode(false);
        }
        // 根据触摸位置更新控制点，并提示重绘
        if (mode) {
            control1.x = event.getX();
            control1.y = event.getY();
        } else {
            control2.x = event.getX();
            control2.y = event.getY();
        }
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
        control1 = new PointF(0, 0);
        control2 = new PointF(0, 0);
    }

    /**
     * 画三阶贝塞尔曲线
     *
     * @param canvas
     */
    private void drawCubicBezier(Canvas canvas) {
        //drawCoordinateSystem(canvas);
        // 绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(control1.x, control1.y, mPaint);
        canvas.drawPoint(control2.x, control2.y, mPaint);

        // 绘制辅助线
        mPaint.setStrokeWidth(4);
        /*start---->contral1*/
        canvas.drawLine(start.x, start.y, control1.x, control1.y, mPaint);
        /*control1--->control2*/
        canvas.drawLine(control1.x, control1.y, control2.x, control2.y, mPaint);
        /*control2--->end*/
        canvas.drawLine(control2.x, control2.y, end.x, end.y, mPaint);

        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path mPath = new Path();
        mPath.moveTo(start.x, start.y);
        /*画三阶贝塞尔*/
        mPath.cubicTo(control1.x, control1.y, control2.x, control2.y, end.x, end.y);
        canvas.drawPath(mPath, mPaint);
    }

}
