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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }
}
