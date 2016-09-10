package com.learzhu.customview.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/10 16:17
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  16:17
 * @updateDes ${TODO}
 * <p/>
 * .Canvas的常用操作速查表
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
 * Matrix(矩阵)	getMatrix, setMatrix, concat	实际画布的位移，缩放等操作的都是图像矩阵Matrix，只不过Matrix比较难以理解和使用，故封装了一些常用的方法。
 */
public class CustomerCanvas extends View {
    // 1.创建一个画笔
    private Paint mPaint = new Paint();

    // 2.初始化画笔
    private void initPaint() {
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
    }

    public CustomerCanvas(Context context) {
        super(context);
    }

    // 3.在构造函数中初始化
    public CustomerCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomerCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomerCanvas(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPoint(canvas);
    }

    /**
     * 画一个点
     * 可以绘制一个点，也可以绘制一组点
     *
     * @param canvas
     */
    public void drawPoint(Canvas canvas) {
        canvas.drawPoint(200, 200, mPaint);     //在坐标(200,200)位置绘制一个点
        canvas.drawPoints(new float[]{          //绘制一组点，坐标位置由float数组指定
                500, 500,
                500, 600,
                500, 700
        }, mPaint);
    }

    /**
     * 画直线
     * 绘制直线需要两个点，初始点和结束点，同样绘制直线也可以绘制一条或者绘制一组：
     *
     * @param canvas
     */
    public void drawLine(Canvas canvas) {
        canvas.drawLine(300, 300, 500, 600, mPaint);    // 在坐标(300,300)(500,600)之间绘制一条直线
        canvas.drawLines(new float[]{               // 绘制一组线 每四数字(两个点的坐标)确定一条线
                100, 200, 200, 200,
                100, 300, 200, 300
        }, mPaint);
    }

    /**
     * 绘制矩形
     * 确定确定一个矩形最少需要四个数据，就是对角线的两个点的坐标值，这里一般采用左上角和右下角的两个点的坐标。
     * <p/>
     * 关于绘制矩形，Canvas提供了三种重载方法，第一种就是提供四个数值(矩形左上角和右下角两个点的坐标)来确定一个矩形进行绘制。
     * 其余两种是先将矩形封装为Rect或RectF(实际上仍然是用两个坐标点来确定的矩形)，然后传递给Canvas绘制
     * <p/>
     * Rect是int(整形)的，而RectF是float(单精度浮点型)的。除了精度不同，两种提供的方法也稍微存在差别
     *
     * @param canvas
     */
    public void drawRect(Canvas canvas) {
        /*第一种*/
        canvas.drawRect(100, 100, 800, 400, mPaint);
        // 第二种
        Rect rect = new Rect(100, 100, 800, 400);
        canvas.drawRect(rect, mPaint);
        // 第三种
        RectF rectF = new RectF(100, 100, 800, 400);
        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 绘制圆角矩形
     *
     * @param canvas 这里圆角矩形的角实际上不是一个正圆的圆弧，而是椭圆的圆弧  rx,ry，这里的两个参数实际上是椭圆的两个半径
     */
    public void drawRoundRect(Canvas canvas) {
        // 第一种
        RectF rectF = new RectF(100, 100, 800, 400);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF, mPaint);

        // 绘制圆角矩形  实际上在rx为宽度的一半，ry为高度的一半时，刚好是一个椭圆，通过上面我们分析的原理推算一下就能得到，而当rx大于宽度的一半，
        // ry大于高度的一半时，实际上是无法计算出圆弧的，所以drawRoundRect对大于该数值的参数进行了限制(修正)，凡是大于一半的参数均按照一半来处理。
        mPaint.setColor(Color.BLUE);
        canvas.drawRoundRect(rectF, 700, 400, mPaint);
//        canvas.drawRoundRect(rectF, 30, 30, mPaint);

        // 第二种  API>=21
        canvas.drawRoundRect(100, 100, 800, 400, 30, 30, mPaint);
    }

    /**
     * 绘制椭圆
     * 一般使用第一种
     * 绘制椭圆实际上就是绘制一个矩形的内切图形
     * 如果你传递进来的是一个长宽相等的矩形(即正方形)，那么绘制出来的实际上就是一个圆
     *
     * @param canvas
     */
    public void drawOval(Canvas canvas) {
        // 第一种
        RectF rectF = new RectF(100, 100, 800, 400);
        canvas.drawOval(rectF, mPaint);

        // 第二种
        canvas.drawOval(100, 100, 800, 400, mPaint);
    }

    /**
     * 绘制圆形
     *
     * @param canvas
     */
    public void drawCircle(Canvas canvas) {
        // 绘制一个圆心坐标在(500,500)，半径为400 的圆。
        canvas.drawCircle(500, 500, 400, mPaint);
    }

    /**
     * 绘制圆弧
     *
     * @param canvas // 第一种
     *               public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint){}
     *               <p/>
     *               // 第二种
     *               public void drawArc(float left, float top, float right, float bottom, float startAngle,
     *               float sweepAngle, boolean useCenter, @NonNull Paint paint) {}
     *               <p/>
     *               startAngle  // 开始角度
     *               sweepAngle  // 扫过角度
     *               useCenter   // 是否使用中心
     *               <p/>
     *               两者的区别就是是否使用了中心点
     */
    public void drawArc(Canvas canvas) {
        RectF rectF = new RectF(100, 100, 800, 400);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF, mPaint);

        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF, 0, 90, false, mPaint);

        //-------------------------------------

        RectF rectF2 = new RectF(100, 600, 800, 900);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF2, mPaint);

        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF2, 0, 90, true, mPaint);
    }

    /**
     * 正圆的扇形
     *
     * @param canvas
     */
    public void drawArcCircle(Canvas canvas) {
        RectF rectF = new RectF(100, 100, 200, 200);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF, mPaint);

        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF, 0, 90, false, mPaint);

        //-------------------------------------

        RectF rectF2 = new RectF(100, 100, 200, 200);
        // 绘制背景矩形
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectF2, mPaint);

        // 绘制圆弧
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF2, 0, 90, true, mPaint);
    }

    /**
     * 绘制的基本形状由Canvas确定，但绘制出来的颜色,具体效果则由Paint确定
     *
     * @param canvas
     */
    public void drawPaint(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(40);     //为了实验效果明显，特地设置描边宽度非常大

        // 描边
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(200, 200, 100, mPaint);

        // 填充
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(200, 500, 100, mPaint);

        // 描边加填充
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(200, 800, 100, mPaint);
    }

}
