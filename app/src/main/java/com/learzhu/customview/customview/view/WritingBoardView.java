package com.learzhu.customview.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.learzhu.customview.customview.R;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/7/14 10:32
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  10:32
 * @updateDes ${TODO}
 * <p/>
 * 1、创建View
 * <p/>
 * 2、处理View的布局
 * <p/>
 * 3、绘制View
 * <p/>
 * 4、与用户进行交互
 * <p/>
 * 5、优化已定义的View
 */
public class WritingBoardView extends View {

    private int mBoardBackground;//画板颜色
    private int mPaintColor;//画笔颜色
    private int mPaintWidth;//画笔宽度
    private Path mPath;
    private Paint mPaint;//画笔

    private final int SIZE = 15;

    public WritingBoardView(Context context) {
        super(context);
    }

    public WritingBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WritingBoardView, 0, 0);
//        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WritingBoardView, 0, 0);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WritingBoardView);
        mBoardBackground = a.getColor(R.styleable.WritingBoardView_boardBackground, Color.WHITE);
        mPaintColor = a.getColor(R.styleable.WritingBoardView_paintColor, Color.BLUE);
        mPaintWidth = a.getDimensionPixelSize(R.styleable.WritingBoardView_paintWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        a.recycle();
        mPaint = new Paint();
        mPath = new Path();
        setBackgroundColor(mBoardBackground);
        mPaint.setColor(mPaintColor);
        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    /**
     * 确定子View布局位置(onLayout)
     * <p/>
     * 确定布局的函数是onLayout，它用于确定子View的位置，在自定义ViewGroup中会用到，他调用的是子View的layout函数。
     * <p/>
     * 在自定义ViewGroup中，onLayout一般是循环取出子View，然后经过计算得出各个子View位置的坐标值，然后用以下函数设置子View位置。
     * <p/>
     * child.layout(l, t, r, b);
     * 名称	说明	对应的函数
     * l	View左侧距父View左侧的距离	getLeft();
     * t	View顶部距父View顶部的距离	getTop();
     * r	View右侧距父View左侧的距离	getRight();
     * b	View底部距父View顶部的距离	getBottom();
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * View的大小不仅由View本身控制，而且受父控件的影响，所以我们在确定View大小的时候最好使用系统提供的onSizeChanged回调函数。
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * View的大小不仅由自身所决定，同时也会受到父控件的影响，为了我们的控件能更好的适应各种情况，一般会自己进行测量
     * 用 MeasureSpec 的 getSize是获取数值， getMode是获取模式即可
     * <p/>
     * 测量模式一共有三种， 被定义在 Android 中的 View 类的一个内部类View.MeasureSpec中：
     * <p/>
     * 模式	二进制数值	描述
     * UNSPECIFIED	00	默认值，父控件没有给子view任何限制，子View可以设置为任意大小。
     * EXACTLY	01	表示父控件已经确切的指定了子View的大小。
     * AT_MOST	10	表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*取出宽度的确切数值*/
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //取出宽度的测量模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int measuredHeight, measuredWidth;
        if (widthMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthSize;
        } else {
            measuredWidth = SIZE;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            measuredHeight = heightSize;
        } else {
            measuredHeight = SIZE;
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }


    public WritingBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WritingBoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //    在onTouch中return true表示要处理当前事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //重新设置即将出现的线的起点
                mPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                //连线
                mPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();//通知系统重绘
        return true;//要处理当前事件
    }

    /**
     * 绘制内容(onDraw)
     * <p/>
     * onDraw是实际绘制的部分，也就是我们真正关心的部分，使用的是Canvas绘图。
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }
}
