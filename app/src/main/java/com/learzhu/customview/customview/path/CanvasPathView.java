package com.learzhu.customview.customview.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/12 13:22
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  13:22
 * @updateDes ${TODO}
 * <p/>
 * 一.Path常用方法表
 * 为了兼容性(偷懒) 本表格中去除了部分API21(即安卓版本5.0)以上才添加的方法。
 * 作用	相关方法	备注
 * 移动起点	moveTo	移动下一次操作的起点位置
 * 设置终点	setLastPoint	重置当前path中最后一个点位置，如果在绘制之前调用，效果和moveTo相同
 * 连接直线	lineTo	添加上一个点到当前点之间的直线到Path
 * 闭合路径	close	连接第一个点连接到最后一个点，形成一个闭合区域
 * 添加内容	addRect, addRoundRect, addOval, addCircle, addPath, addArc, arcTo	添加(矩形， 圆角矩形， 椭圆， 圆， 路径， 圆弧) 到当前Path (注意addArc和arcTo的区别)
 * 是否为空	isEmpty	判断Path是否为空
 * 是否为矩形	isRect	判断path是否是一个矩形
 * 替换路径	set	用新的路径替换到当前路径所有内容
 * 偏移路径	offset	对当前路径之前的操作进行偏移(不会影响之后的操作)
 * 贝塞尔曲线	quadTo, cubicTo	分别为二次和三次贝塞尔曲线的方法
 * rXxx方法	rMoveTo, rLineTo, rQuadTo, rCubicTo	不带r的方法是基于原点的坐标系(偏移量)， rXxx方法是基于当前点坐标系(偏移量)
 * 填充模式	setFillType, getFillType, isInverseFillType, toggleInverseFillType	设置,获取,判断和切换填充模式
 * 提示方法	incReserve	提示Path还有多少个点等待加入(这个方法貌似会让Path优化存储结构)
 * 布尔操作(API19)	op	对两个Path进行布尔运算(即取交集、并集等操作)
 * 计算边界	computeBounds	计算Path的边界
 * 重置路径	reset, rewind	清除Path中的内容
 * reset不保留内部数据结构，但会保留FillType.
 * rewind会保留内部的数据结构，但不保留FillType
 * 矩阵操作	transform	矩阵变换
 * <p/>
 * Path是封装了由直线和曲线(二次，三次贝塞尔曲线)构成的几何路径。你能用Canvas中的drawPath来把这条路径画出来(同样支持Paint的不同绘制模式)，也可以用于剪裁画布和根据路径绘制文字。
 * 我们有时会用Path来描述一个图像的轮廓，所以也会称为轮廓线(轮廓线仅是Path的一种使用方法，两者并不等价)
 */
public class CanvasPathView extends View {

    private Paint mPaint;
    private int mWidth, mHeight;

    public CanvasPathView(Context context) {
        this(context, null);
    }

    public CanvasPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CanvasPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CanvasPathView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        drawLine(canvas);
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
     * moveTo、 setLastPoint、 lineTo 和 close
     * public void lineTo (float x, float y) Path可以用来描述一个图像的轮廓，
     * 图像的轮廓通常都是用一条线构成的，所以这里的某个点就是上次操作结束的点，如果没有进行过操作则默认点为坐标原点
     * <p/>
     * // moveTo
     * public void moveTo (float x, float y)
     * <p/>
     * // setLastPoint
     * public void setLastPoint (float dx, float dy)
     * <p/>
     * 方法名	简介	                   是否影响之前的操作	是否影响之后操作
     * moveTo	移动下一次操作的起点位置	        否	          是
     * setLastPoint	设置之前操作的最后一个点位置	是	          是
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        // 移动坐标系到屏幕中心(宽高数据在onSizeChanged中获取)
        canvas.translate(mWidth / 2, mHeight / 2);
        // 创建Path
        Path mPath = new Path();
        // lineTo
        mPath.lineTo(200, 200);
        /*moveTo  只是改变后面的变化*/
//        mPath.moveTo(200, 100);
        /*setLastPoint改变之前的  setLastPoint是重置上一次操作的最后一个点*/
        mPath.setLastPoint(200, 100);
        /*close方法用于连接当前最后一个点和最初的一个点(如果两个点不重合的话)，最终形成一个封闭的图形。*/
        /* close的作用是封闭路径，与当前最后一个点和第一个点并不等价。如果连接了最后一个点和第一个点仍然无法形成封闭图形，则close什么 也不做。*/
        mPath.close();
        mPath.lineTo(200, 0);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 画基本图形
     * <p/>
     * // 第一类(基本形状)
     * <p/>
     * // 圆形
     * public void addCircle (float x, float y, float radius, Path.Direction dir)
     * // 椭圆
     * public void addOval (RectF oval, Path.Direction dir)
     * // 矩形
     * public void addRect (float left, float top, float right, float bottom, Path.Direction dir)
     * public void addRect (RectF rect, Path.Direction dir)
     * // 圆角矩形
     * public void addRoundRect (RectF rect, float[] radii, Path.Direction dir)
     * public void addRoundRect (RectF rect, float rx, float ry, Path.Direction dir)
     * <p/>
     * <p/>
     * Path.Direction dir
     * 类型	解释	翻译
     * CW	clockwise	顺时针
     * CCW	counter-clockwise	逆时针
     * 序号	作用
     * 1	在添加图形时确定闭合顺序(各个点的记录顺序)
     * 2	对图形的渲染结果有影响(是判断图形渲染的重要条件)
     * <p/>
     * 顺时针和逆时针就是用来确定记录这些点的顺序的
     * ***参数中点的顺序很重要！
     *
     * @param canvas
     */
    private void drawBasicGraphic1(Canvas canvas) {
        // 移动坐标系到屏幕中心
        canvas.translate(mWidth / 2, mHeight / 2);
        Path mPath = new Path();
        /*设置顺时针画一个矩形*/
        mPath.addRect(-200, -200, 200, 200, Path.Direction.CW);
        /*逆时针画一个矩形*/
        mPath.addRect(-200, -200, 200, 200, Path.Direction.CCW);
        /*重置最后一个点的位置  用于检测两种不同的方向的区别*/
        mPath.setLastPoint(-300, 300);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * // 第二类(Path) 将两个Path合并成为一个
     * 二个方法比第一个方法多出来的两个参数是将src进行了位移之后再添加进当前path中
     * // path
     * public void addPath (Path src)
     * public void addPath (Path src, float dx, float dy)
     * public void addPath (Path src, Matrix matrix)
     *
     * @param canvas
     */
    private void drawBasicGraphic2(Canvas canvas) {
        /*移动坐标系到屏幕中心*/
        canvas.translate(mWidth / 2, mHeight / 2);
        /*注意 翻转y坐标轴*/
        canvas.scale(1, -1);
        Path mPath = new Path();
        Path src = new Path();
        /*绘制矩形*/
        mPath.addRect(-200, -200, 200, 200, Path.Direction.CW);
        /*绘制圆*/
        src.addCircle(0, 0, 100, Path.Direction.CW);
        /*合并路径*/
        src.addPath(src, 0, 200);
        /*绘制合并后的路径*/
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 第三类(addArc与arcTo)
     * // 第三类(addArc与arcTo)
     * <p/>
     * // addArc
     * public void addArc (RectF oval, float startAngle, float sweepAngle)
     * // arcTo
     * public void arcTo (RectF oval, float startAngle, float sweepAngle)
     * public void arcTo (RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo)
     * <p/>
     * 参数	摘要
     * oval	圆弧的外切矩形。
     * startAngle	开始角度
     * sweepAngle	扫过角度(-360 <= sweepAngle <360)
     * sweepAngle取值范围是 [-360, 360)，不包括360，当 >= 360 或者 < -360 时将不会绘制任何内容， 对于360，你可以用一个接近的值替代，例如: 359.99
     * forceMoveTo	是否强制使用MoveTo
     * <p/>
     * 名称	作用	区别
     * addArc	添加一个圆弧到path	直接添加一个圆弧到path中
     * arcTo	添加一个圆弧到path	添加一个圆弧到path，如果圆弧的起点和上次最后一个坐标点不相同，就连接两个点
     * <p/>
     * forceMoveTo	含义	等价方法
     * true	将最后一个点移动到圆弧起点，即不连接最后一个点与圆弧起点	public void addArc (RectF oval, float startAngle, float sweepAngle)
     * false	不移动，而是连接最后一个点与圆弧起点	public void arcTo (RectF oval, float startAngle, float sweepAngle)
     *
     * @param canvas
     */
    private void drawBasicGraphic3(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        canvas.scale(1, -1);                         // <-- 注意 翻转y坐标轴

        Path path = new Path();
        path.lineTo(100, 100);

        RectF oval = new RectF(0, 0, 300, 300);

        path.addArc(oval, 0, 270);
        path.arcTo(oval, 0, 270);
//         path.arcTo(oval,0,270,true);             // <-- 和上面一句作用等价

        canvas.drawPath(path, mPaint);
    }

    /**
     * isEmpty、 isRect、isConvex、 set 和 offset
     *
     * @param canvas
     */
    private void drawPath(Canvas canvas) {
        Path path = new Path();
        /*判断path中是否包含内容。*/
        Log.e("1", path.isEmpty() + "");

        path.lineTo(100, 100);
        Log.e("2", path.isEmpty() + "");

        /*判断path是否是一个矩形，如果是一个矩形的话，会将矩形的信息存放进参数rect中。*/
        path.lineTo(0, 400);
        path.lineTo(400, 400);
        path.lineTo(400, 0);
        path.lineTo(0, 0);
        RectF rect = new RectF();
        boolean b = path.isRect(rect);
        Log.e("Rect", "isRect:" + b + "| left:" + rect.left + "| top:" + rect.top + "| right:" + rect.right + "| bottom:" + rect.bottom);

        /*将新的path赋值到现有path。*/
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        canvas.scale(1, -1);                         // <-- 注意 翻转y坐标轴

        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
        Path src = new Path();                      // src添加一个圆
        src.addCircle(0, 0, 100, Path.Direction.CW);
        path.set(src);                              // 大致相当于 path = src;
        canvas.drawPath(path, mPaint);
    }

    /**
     * offset
     * 方法预览：
     * <p/>
     * public void offset (float dx, float dy)
     * public void offset (float dx, float dy, Path dst)
     * <p/>
     * 这个的作用也很简单，就是对path进行一段平移，它和Canvas中的translate作用很像，但Canvas作用于整个画布，而path的offset只作用于当前path。
     * <p/>
     * 其实第二个方法中最后的参数das是存储平移后的path的。
     * <p/>
     * dst状态	效果
     * dst不为空	将当前path平移后的状态存入dst中，不会影响当前path
     * dat为空(null)	平移将作用于当前path，相当于第一种方法
     *
     * @param canvas
     */
    private void drawPathOffset(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        canvas.scale(1, -1);                         // <-- 注意 翻转y坐标轴

        Path path = new Path();                     // path中添加一个圆形(圆心在坐标原点)
        path.addCircle(0, 0, 100, Path.Direction.CW);

        Path dst = new Path();                      // dst中添加一个矩形
        dst.addRect(-200, -200, 200, 200, Path.Direction.CW);

        /*虽然我们在dst中添加了一个矩形，但是并没有表现出来，所以，当dst中存在内容时，dst中原有的内容会被清空，而存放平移后的path。*/
        path.offset(300, 0, dst);                     // 平移

        canvas.drawPath(path, mPaint);               // 绘制path

        mPaint.setColor(Color.BLUE);                // 更改画笔颜色

        canvas.drawPath(dst, mPaint);                // 绘制dst
    }

}
