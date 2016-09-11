package com.learzhu.customview.customview.statistics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/10 17:38
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  17:38
 * @updateDes ${TODO}
 * 饼状图的View
 * 步骤	关键字	作用
 * 1	构造函数	初始化(初始化画笔Paint)
 * 2	onMeasure	测量View的大小(暂时不用关心)
 * 3	onSizeChanged	确定View大小(记录当前View的宽高)
 * 4	onLayout	确定子View布局(无子View，不关心)
 * 5	onDraw	实际绘制内容(绘制饼状图)
 * 6	提供接口	提供接口(提供设置数据的接口)
 */
public class PieView extends View {
    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    // 数据
    private ArrayList<PieData> mData;
    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint();

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /*填充*/
        mPaint.setStyle(Paint.Style.FILL);
        //设置画笔锯齿效果
        mPaint.setAntiAlias(true);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        if (null == mData) {
            return;
        }
        /*当前起始角度*/
        float currentStartAngle = mStartAngle;
        /*将画布坐标原点移动到中心位置*/
        canvas.translate(mWidth / 2, mHeight / 2);
        /*饼状图的半径*/
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        /*饼状图的绘制区*/
        RectF rectF = new RectF(-r, -r, r, r);
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            mPaint.setColor(pie.getColor());
            canvas.drawArc(rectF, currentStartAngle, pie.getAngle(), true, mPaint);
            currentStartAngle += pie.getAngle();
        }
    }

    /**
     * 设置起始的角度
     *
     * @param mStartAngle
     */
    public void setStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        /*刷新*/
        invalidate();
    }

    // 设置数据
    public void setData(ArrayList<PieData> mData) {
        this.mData = mData;
        initData(mData);
        invalidate();   // 刷新
    }

    // 初始化数据
    private void initData(ArrayList<PieData> mData) {
        if (null == mData || mData.size() == 0) {
            // 数据有问题 直接返回
            return;
        }
        float sumValue = 0;
        float sumAngle = 0;
        /*设置颜色*/
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            /*计算数值和*/
            sumValue += pie.getValue();
            /*设置颜色*/
            int j = i % mColors.length;
            pie.setColor(mColors[j]);
        }
        /*计算角度*/
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            float percentage = pie.getValue() / sumValue;   // 百分比
            float angle = percentage * 360;                 // 对应的角度
            pie.setPercentage(percentage);                  // 记录百分比
            pie.setAngle(angle);                            // 记录角度大小
            sumAngle += angle;

            Log.i("angle", "" + pie.getAngle());
        }

    }

}
