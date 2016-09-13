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
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
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
}
