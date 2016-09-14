package com.learzhu.customview.customview.pathmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/13 19:32
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  19:32
 * @updateDes ${TODO}
 * <p/>
 * Path & PathMeasure
 * 顾名思义，PathMeasure是一个用来测量Path的类，主要有以下方法:
 * <p/>
 * 构造方法
 * <p/>
 * 方法名	释义
 * PathMeasure()	创建一个空的PathMeasure
 * PathMeasure(Path path, boolean forceClosed)	创建 PathMeasure 并关联一个指定的Path(Path需要已经创建完成)。
 * 公共方法
 * <p/>
 * 返回值	方法名	释义
 * void	setPath(Path path, boolean forceClosed)	关联一个Path
 * boolean	isClosed()	是否闭合
 * float	getLength()	获取Path的长度
 * boolean	nextContour()	跳转到下一个轮廓
 * boolean	getSegment(float startD, float stopD, Path dst, boolean startWithMoveTo)	截取片段
 * boolean	getPosTan(float distance, float[] pos, float[] tan)	获取指定长度的位置坐标及该点切线值
 * boolean	getMatrix(float distance, Matrix matrix, int flags)	获取指定长度的位置坐标及该点Matrix
 */
public class PathMeasureView extends View {

    private int mWidth, mHeight;
    private Paint mPaint;

    public PathMeasureView(Context context) {
        super(context);
    }

    public PathMeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PathMeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PathMeasureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPathMeasureGetSegment(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 创建画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 用这个构造函数是创建一个 PathMeasure 并关联一个 Path， 其实和创建一个空的 PathMeasure
     * 后调用 setPath 进行关联效果是一样的，同样，被关联的 Path 也必须是已经创建好的，
     * 如果关联之后 Path 内容进行了更改，则需要使用 setPath 方法重新关联。
     * <p/>
     * 该方法有两个参数，第一个参数自然就是被关联的 Path 了，第二个参数是用来确保 Path 闭合，如果设置为 true，
     * 则不论之前Path是否闭合，都会自动闭合该 Path(如果Path可以闭合的话)。
     * <p/>
     * 在这里有两点需要明确:
     * <p/>
     * 不论 forceClosed 设置为何种状态(true 或者 false)， 都不会影响原有Path的状态，即 Path 与 PathMeasure 关联之后，
     * 之前的的 Path 不会有任何改变。
     * forceClosed 的设置状态可能会影响测量结果，如果 Path 未闭合但在与 PathMeasure 关联的时候设置 forceClosed 为 true 时，
     * 测量结果可能会比 Path 实际长度稍长一点，获取到到是该 Path 闭合时的状态。
     * <p/>
     * 1.Path 与 PathMeasure进行关联并不会影响 Path 状态
     * 2.设置 forceClosed 为 true 的方法比设置为 false 的方法测量出来的长度要长一点，这是由于 Path 没有闭合的缘故，
     * 多出来的距离正是 Path 最后一个点与最开始一个点之间点距离。forceClosed 为 false 测量的是当前 Path 状态的长度，
     * forceClosed 为 true，则不论Path是否闭合测量的都是 Path 的闭合长度。
     *
     * @param canvas
     */
    private void drawPathMeasure(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);
        Path mPath = new Path();
        mPath.lineTo(0, 200);
        mPath.lineTo(200, 200);
        mPath.lineTo(200, 0);

        PathMeasure mPathMeasure1 = new PathMeasure(mPath, false);
        PathMeasure mPathMeasure2 = new PathMeasure(mPath, true);

        Log.e("TAG", "forceClosed=false---->" + mPathMeasure1.getLength());
        Log.e("TAG", "forceClosed=true----->" + mPathMeasure2.getLength());
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * getSegment 用于获取Path的一个片段，方法如下：
     * <p/>
     * boolean getSegment (float startD, float stopD, Path dst, boolean startWithMoveTo)
     * 参数	作用	备注
     * 返回值(boolean)	判断截取是否成功	true 表示截取成功，结果存入dst中，false 截取失败，不会改变dst中内容
     * startD	开始截取位置距离 Path 起点的长度	取值范围: 0 <= startD < stopD <= Path总长度
     * stopD	结束截取位置距离 Path 起点的长度	取值范围: 0 <= startD < stopD <= Path总长度
     * dst	截取的 Path 将会添加到 dst 中	注意: 是添加，而不是替换
     * startWithMoveTo	起始点是否使用 moveTo	用于保证截取的 Path 第一个点位置不变
     * true	保证截取得到的 Path 片段不会发生形变
     * false	保证存储截取片段的 Path(dst) 的连续性
     * <p/>
     * <p/>
     * 如果 startD、stopD 的数值不在取值范围 [0, getLength] 内，或者 startD == stopD 则返回值为 false，不会改变 dst 内容。
     * 如果在安卓4.4或者之前的版本，在默认开启硬件加速的情况下，更改 dst 的内容后可能绘制会出现问题，请关闭硬件加速或者
     * 给 dst 添加一个单个操作，例如: dst.rLineTo(0, 0)
     *
     * @param canvas
     */
    private void drawPathMeasureGetSegment(Canvas canvas) {
        // 平移坐标系
        canvas.translate(mWidth / 2, mHeight / 2);
        /*创建Path并添加了一个矩形*/
        Path mPath = new Path();
        mPath.addRect(-200, -200, 200, 200, Path.Direction.CW);
        /*创建用于存储截取后内容的 Path  空的*/
        Path dst = new Path();
        /*在 dst 中添加一条线段*/
        /*被截取的 Path 片段会添加到 dst 中，而不是替换 dst 中到内容。*/
       /* 如果 startWithMoveTo 为 true, 则被截取出来到Path片段保持原状，如果 startWithMoveTo 为 false，
        则会将截取出来的 Path 片段的起始点移动到 dst 的最后一个点，以保证 dst 的连续性。*/
        dst.lineTo(-300, -300);

        /*将 Path 与 PathMeasure 关联*/
        PathMeasure mPathMeasure = new PathMeasure(mPath, false);
        /*截取一部分存入dst中，并使用 moveTo 保持截取得到的 Path 第一个点的位置不变*/
        mPathMeasure.getSegment(200, 600, dst, true);
        /*绘制截取的dst*/
        canvas.drawPath(dst, mPaint);
    }

    /**
     * nextContour 就是用于跳转到下一条曲线到方法，如果跳转成功，则返回 true， 如果跳转失败，则返回 false
     * 1.曲线的顺序与 Path 中添加的顺序有关。
     * 2.getLength 获取到到是当前一条曲线分长度，而不是整个 Path 的长度。
     * 3.getLength 等方法是针对当前的曲线(其它方法请自行验证)。
     *
     * @param canvas
     */
    private void drawPathMeasureNextContour(Canvas canvas) {
        // 平移坐标系
        canvas.translate(mWidth / 2, mHeight / 2);
        Path mPath = new Path();
        /*添加小矩形*/
        mPath.addRect(-100, -100, 100, 100, Path.Direction.CW);
        /*添加大矩形*/
        mPath.addRect(-200, -200, 200, 200, Path.Direction.CW);
        /*绘制 Path*/
        canvas.drawPath(mPath, mPaint);
        /*将Path与PathMeasure关联*/
        PathMeasure mPathMeasure = new PathMeasure(mPath, false);
        /*获得第一条路径的长度*/
        float len1 = mPathMeasure.getLength();
        /*跳转到下一条路径*/
        mPathMeasure.nextContour();
        /*获得第二条路径的长度*/
        float len2 = mPathMeasure.getLength();
        Log.i("LEN", "len1=" + len1);                              // 输出两条路径的长度
        Log.i("LEN", "len2=" + len2);
    }

}
