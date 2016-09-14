package com.learzhu.customview.customview.pathmeasure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import com.learzhu.customview.customview.R;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/14 10:59
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  10:59
 * @updateDes ${TODO}
 * <p/>
 * 一个箭头旋转
 * 这个方法是用于得到路径上某一长度的位置以及该位置的正切值：
 * <p/>
 * boolean getPosTan (float distance, float[] pos, float[] tan)
 * 方法各个参数释义：
 * <p/>
 * 参数	作用	备注
 * 返回值(boolean)	判断获取是否成功	true表示成功，数据会存入 pos 和 tan 中，
 * false 表示失败，pos 和 tan 不会改变
 * distance	距离 Path 起点的长度	取值范围: 0 <= distance <= getLength
 * pos	该点的坐标值	当前点在画布上的位置，有两个数值，分别为x，y坐标。
 * tan	该点的正切值	当前点在曲线上的方向，使用 Math.atan2(tan[1], tan[0]) 获取到正切角的弧度值。
 * tan 是用来判断 Path 的趋势的，即在这个位置上曲线的走向
 * <p/>
 * 1.通过 tan 得值计算出图片旋转的角度，tan 是 tangent 的缩写，即中学中常见的正切， 其中tan[0]是邻边边长，tan[1]是对边边长，
 * 而Math中 atan2 方法是根据正切是数值计算出该角度的大小,得到的单位是弧度(取值范围是 -pi 到 pi)，所以上面又将弧度转为了角度。
 * 2.通过 Matrix 来设置图片对旋转角度和位移，这里使用的方法与前面讲解过对 canvas操作 有些类似，对于 Matrix 会在后面专一进行讲解，敬请期待。
 * 3.页面刷新，页面刷新此处是在 onDraw 里面调用了 invalidate 方法来保持界面不断刷新，但并不提倡这么做，
 * 正确对做法应该是使用 线程 或者 ValueAnimator 来控制界面的刷新，关于控制页面刷新这一部分会在后续的 动画部分 详细讲解，同样敬请期待。
 */
public class ArrowRotateView extends View {
    private int mWidth, mHeight;
    private Paint mPaint;
    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度

    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作

    public ArrowRotateView(Context context) {
        this(context, null);
    }

    public ArrowRotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initData(context);
    }

    public ArrowRotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ArrowRotateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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

    private void initData(Context context) {
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
        /*缩放图片*/
//        mOptions.inSampleSize = 2;
        /*1/N*/
        mOptions.inSampleSize = 4;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow, mOptions);
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawPathMeasureArrow(canvas);
        drawPathMeasureGetMatrix(canvas);
    }

    /**
     * @param canvas tan[0] 和 tan[1] 数值均取自单位圆上对应角度的坐标
     */
    private void drawPathMeasureArrow(Canvas canvas) {
        /*平移坐标系*/
        canvas.translate(mWidth / 2, mHeight / 2);
        /*创建 Path*/
        Path mPath = new Path();
        /*添加一个圆形*/
        mPath.addCircle(0, 0, 200, Path.Direction.CW);
        /*创建 PathMeasure*/
        PathMeasure mPathMeasure = new PathMeasure(mPath, false);
        /*计算当前的位置在总长度上的比例[0,1]*/
        currentValue += 0.005;
        if (currentValue >= 1) {
            currentValue = 0;
        }
        /*获取当前位置的坐标以及趋势*/
        mPathMeasure.getPosTan(mPathMeasure.getLength() * currentValue, pos, tan);
        /*重置Matrix*/
        mMatrix.reset();
        /*计算图片旋转角度*/
        /*Math中 atan2 方法是根据正切是数值计算出该角度的大小,得到的单位是弧度(取值范围是 -pi 到 pi)，所以上面又将弧度转为了角度*/
        float degree = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        /*旋转图片*/
        mMatrix.postRotate(degree, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        /*将图片绘制中心调整到与当前点重合*/
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);
        /*绘制Path*/
        canvas.drawPath(mPath, mPaint);
        /*绘制箭头*/
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
        /*重绘页面*/
        /*页面刷新此处是在 onDraw 里面调用了 invalidate 方法来保持界面不断刷新，但并不提倡这么做，
        正确对做法应该是使用 线程 或者 ValueAnimator 来控制界面的刷新，关于控制页面刷新这一部分会在后续的 动画部分*/
        invalidate();
    }

    /**
     * 这个方法就相当于我们在前一个例子中封装 matrix 的过程由 getMatrix 替我们做了，我们可以直接得到一个封装好到 matrix
     * 这个方法是用于得到路径上某一长度的位置以及该位置的正切值的矩阵：
     * <p/>
     * boolean getMatrix (float distance, Matrix matrix, int flags)
     * <p/>
     * 参数	作用	备注
     * 返回值(boolean)	判断获取是否成功	true表示成功，数据会存入matrix中，false 失败，matrix内容不会改变
     * distance	距离 Path 起点的长度	取值范围: 0 <= distance <= getLength
     * matrix	根据 falgs 封装好的matrix	会根据 flags 的设置而存入不同的内容
     * flags	规定哪些内容会存入到matrix中	可选择
     * POSITION_MATRIX_FLAG(位置)
     * ANGENT_MATRIX_FLAG(正切)
     * <p/>
     * measure.getMatrix(distance, matrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
     * 总结：
     * 1.对 matrix 的操作必须要在 getMatrix 之后进行，否则会被 getMatrix 重置而导致无效。
     * 2.矩阵对旋转角度默认为图片的左上角，我们此处需要使用 preTranslate 调整为图片中心。
     * 3.pre(矩阵前乘) 与 post(矩阵后乘) 的区别，此处请等待后续的文章或者自行搜索。
     *
     * @param canvas
     */
    private void drawPathMeasureGetMatrix(Canvas canvas) {
        Path path = new Path();                                 // 创建 Path

        path.addCircle(0, 0, 200, Path.Direction.CW);           // 添加一个圆形

        PathMeasure measure = new PathMeasure(path, false);     // 创建 PathMeasure

        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1) {
            currentValue = 0;
        }
        // 获取当前位置的坐标以及趋势的矩阵
        measure.getMatrix(measure.getLength() * currentValue, mMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);   // <-- 将图片绘制中心调整到与当前点重合(注意:此处是前乘pre)
        canvas.drawPath(path, mPaint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);                     // 绘制箭头

        invalidate();                                                           // 重绘页面
    }

}
