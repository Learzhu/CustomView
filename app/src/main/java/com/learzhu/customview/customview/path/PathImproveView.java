package com.learzhu.customview.customview.path;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.view.PagerTabStrip;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/13 13:22
 * Path的高级总结
 */
public class PathImproveView extends View {

    private Paint mPaint;
    private int mWidth, mHeight;

    public PathImproveView(Context context) {
        this(context, null);
    }

    public PathImproveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PathImproveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PathImproveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawrLineTo(canvas);
//        drawPathPaint(canvas);
//        drawPathBooleanTaiji(canvas);
//        drawPathBoolean(canvas);
        drawPathComputeBounds(canvas);
    }

    /**
     * 创建画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    /**
     * rXxx方法的坐标使用的是相对位置(基于当前点的位移)，而之前方法的坐标是绝对位置(基于当前坐标系的坐标)。
     *
     * @param canvas
     */
    private void drawrLineTo(Canvas canvas) {
        Path mPath = new Path();
        mPath.moveTo(100, 100);
        mPath.rLineTo(100, 200);
    }

    /**
     * Paint有三种样式，“描边” “填充” 以及 “描边加填充”
     * Paint设置为后两种样式时不同的填充模式对图形渲染效果的影响
     * <p/>
     * ****对于填充的内外的判断
     * <p/>
     * 方法	判定条件	解释
     * 奇偶规则	奇数表示在图形内，偶数表示在图形外	从任意位置p作一条射线，
     * 若与该射线相交的图形边的数目为奇数，则p是图形内部点，否则是外部点。
     * 非零环绕数规则	若环绕数为0表示在图形外，非零表示在图形内
     * 首先使图形的边变为矢量。将环绕数初始化为零。再从任意位置p作一条射线。当从p点沿射线方向移动时，
     * 对在每个方向上穿过射线的边计数，每当图形的边从右到左穿过射线时，环绕数加1，从左到右时，环绕数减1。处理完图形的所有相关边之后，
     * 若环绕数为非零，则p为内部点，否则，p是外部点。
     * <p/>
     * 通常，这两种方法的判断结果是相同的，但也存在两种方法判断结果不同的情况  回字形的空心图
     *
     * @param canvas
     */
    private void drawPaint(Canvas canvas) {

    }

    /**
     * Android中的填充模式
     * Android中的填充模式有四种，是封装在Path中的一个枚举。
     * <p/>
     * 模式	简介
     * EVEN_ODD	奇偶规则
     * INVERSE_EVEN_ODD	反奇偶规则
     * WINDING	非零环绕数规则
     * INVERSE_WINDING	反非零环绕数规则
     * <p/>
     * <p/>
     * Android与填充模式相关的方法
     * 这些都是Path中的方法。
     * 方法	作用
     * setFillType	设置填充规则
     * getFillType	获取当前填充规则
     * isInverseFillType	判断是否是反向(INVERSE)规则
     * toggleInverseFillType	切换填充规则(即原有规则与反向规则之间相互切换)
     *
     * @param canvas
     */
    private void drawPathPaint(Canvas canvas) {
        /*设置画布模式为填充*/
        mPaint.setStyle(Paint.Style.FILL);
        /*移动画布(坐标系)*/
        canvas.translate(mWidth / 2, mHeight / 2);
        Path mPath = new Path();
        /*设置Path填充模式为 奇偶规则  不能同时使用 同时使用没有画东西了*/
        mPath.setFillType(Path.FillType.EVEN_ODD);
        /*反奇偶规则*/
        mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        /*给Path中添加一个矩形*/
        /*给Path添加图形时顺时针与逆时针的作用 1.方便记录外 2.作为非零环绕数规则的判断依据。*/
        mPath.addRect(-200, -200, 200, 200, Path.Direction.CW);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 方向对于画图的影响
     *
     * @param canvas
     */
    private void drawPathPaintRect(Canvas canvas) {
        /*设置画笔模式为填充*/
        mPaint.setStyle(Paint.Style.FILL);
        // 移动画布(坐系)
        canvas.translate(mWidth / 2, mHeight / 2);
        Path mPath = new Path();
        // 添加小正方形 (通过这两行代码来控制小正方形边的方向,从而演示不同的效果)
        mPath.addRect(-200, -200, 200, 200, Path.Direction.CCW);
        // 添加大正方形
        mPath.addRect(-400, -400, 400, 400, Path.Direction.CW);
        /*设置Path填充模式为非零环绕规则*/
        mPath.setFillType(Path.FillType.WINDING);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 布尔操作是两个Path之间的运算，主要作用是用一些简单的图形通过一些规则合成一些相对比较复杂，或难以直接得到的图形。
     * 布尔操作与我们中学所学的集合操作非常像，只要知道集合操作中等交集，并集，差集等操作，那么理解布尔操作也是很容易的。
     * <p/>
     * 太极中的阴阳鱼，如果用贝塞尔曲线制作的话，可能需要六段贝塞尔曲线才行，而在这里我们可以用四个Path通过布尔运算得到，而且会相对来说更容易理解一点。
     * <p/>
     * Path的布尔运算有五种逻辑，如下:
     * <p/>
     * 逻辑名称	类比	说明	示意图
     * DIFFERENCE	差集	Path1中减去Path2后剩下的部分
     * REVERSE_DIFFERENCE	差集	Path2中减去Path1后剩下的部分
     * INTERSECT	交集	Path1与Path2相交的部分
     * UNION	并集	包含全部Path1和Path2
     * XOR	异或	包含Path1与Path2但不包括两者相交的部分
     * <p/>
     * <p/>
     * 在Path中的布尔运算有两个方法
     * <p/>
     * boolean op (Path path, Path.Op op)
     * boolean op (Path path1, Path path2, Path.Op op)
     *
     * @param canvas
     */
    private void drawPathBooleanTaiji(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);
        Path mPath1 = new Path();
        Path mPath2 = new Path();
        Path mPath3 = new Path();
        Path mPath4 = new Path();

        /*一个顺时针的圆形*/
        mPath1.addCircle(0, 0, 200, Path.Direction.CW);
        mPath2.addRect(0, -200, 200, 200, Path.Direction.CW);
        mPath3.addCircle(0, -100, 100, Path.Direction.CW);
        mPath4.addCircle(0, 100, 100, Path.Direction.CCW);

        /*Path1中减去Path2后剩下的部分*/
        /*对 path1 和 path2 执行布尔运算，运算方式由第二个参数指定，运算结果存入到path1中。*/
        mPath1.op(mPath2, Path.Op.DIFFERENCE);
        /*包含全部Path1和Path2*/
        mPath1.op(mPath3, Path.Op.UNION);
        mPath1.op(mPath4, Path.Op.DIFFERENCE);

        // 对 path1 和 path2 执行布尔运算，运算方式由第三个参数指定，运算结果存入到path3中。
        mPath3.op(mPath1, mPath2, Path.Op.DIFFERENCE);
        canvas.drawPath(mPath1, mPaint);
    }

    /**
     * 计算后的图形
     *
     * @param canvas
     */
    private void drawPathBoolean(Canvas canvas) {
        int x = 80;
        int r = 100;
        canvas.translate(250, 0);
        Path path1 = new Path();
        Path path2 = new Path();
        Path pathOpResult = new Path();

        path1.addCircle(-x, 0, r, Path.Direction.CW);
        path2.addCircle(x, 0, r, Path.Direction.CW);

        pathOpResult.op(path1, path2, Path.Op.DIFFERENCE);
        canvas.translate(0, 200);
        canvas.drawText("DIFFERENCE", 240, 0, mPaint);
        canvas.drawPath(pathOpResult, mPaint);

        pathOpResult.op(path1, path2, Path.Op.REVERSE_DIFFERENCE);
        canvas.translate(0, 300);
        canvas.drawText("REVERSE_DIFFERENCE", 240, 0, mPaint);
        canvas.drawPath(pathOpResult, mPaint);

        pathOpResult.op(path1, path2, Path.Op.INTERSECT);
        canvas.translate(0, 300);
        canvas.drawText("INTERSECT", 240, 0, mPaint);
        canvas.drawPath(pathOpResult, mPaint);

        pathOpResult.op(path1, path2, Path.Op.UNION);
        canvas.translate(0, 300);
        canvas.drawText("UNION", 240, 0, mPaint);
        canvas.drawPath(pathOpResult, mPaint);

        pathOpResult.op(path1, path2, Path.Op.XOR);
        canvas.translate(0, 300);
        canvas.drawText("XOR", 240, 0, mPaint);
        canvas.drawPath(pathOpResult, mPaint);
    }

    /**
     * 计算边界
     * <p/>
     * 这个方法主要作用是计算Path所占用的空间以及所在位置,方法如下：
     * <p/>
     * void computeBounds (RectF bounds, boolean exact)
     * 它有两个参数：
     * <p/>
     * 参数	作用
     * bounds	测量结果会放入这个矩形
     * exact	是否精确测量，目前这一个参数作用已经废弃，一般写true即可。
     *
     * @param canvas
     */
    private void drawPathComputeBounds(Canvas canvas) {
        // 移动canvas,mViewWidth与mViewHeight在 onSizeChanged 方法中获得
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect1 = new RectF();              // 存放测量结果的矩形

        Path path = new Path();                 // 创建Path并添加一些内容
        path.lineTo(100, -50);
        path.lineTo(100, 50);
        path.close();
        path.addCircle(-100, 0, 100, Path.Direction.CW);

        path.computeBounds(rect1, true);         // 测量Path
        canvas.drawPath(path, mPaint);    // 绘制Path

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rect1, mPaint);   // 绘制边界
    }

    /**
     * 重置路径
     * <p/>
     * 重置Path有两个方法，分别是reset和rewind，两者区别主要有一下两点：
     * <p/>
     * 方法	是否保留FillType设置	是否保留原有数据结构
     * reset	是	否
     * rewind	否	是
     * 这个两个方法应该何时选择呢？
     * <p/>
     * 选择权重: FillType > 数据结构
     * <p/>
     * 因为“FillType”影响的是显示效果，而“数据结构”影响的是重建速度。
     *
     * @param canvas
     */
    private void drawPathReset(Canvas canvas) {

    }
}
