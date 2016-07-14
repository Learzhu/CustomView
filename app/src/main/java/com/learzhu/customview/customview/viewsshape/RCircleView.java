package com.learzhu.customview.customview.viewsshape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/7/14 17:50
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  17:50
 * @updateDes ${TODO}
 * 半圆弧
 */
public class RCircleView extends View {
    //弧的笔画
    private Paint rpaint;
    /*圆的笔画*/
    private Paint cpaint;
    /*文字的笔画*/
    private Paint tvpaint;
    /*屏幕的 高宽*/
    private int width, height;
    //文字
    private String text = "绘制半弧圆";

    @Override
    protected int getSuggestedMinimumHeight() {
        return super.getSuggestedMinimumHeight();
    }

    @Override
    protected int getHorizontalScrollbarHeight() {
        return super.getHorizontalScrollbarHeight();
    }

    public RCircleView(Context context) {
        super(context);
        init();
    }

    public RCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RCircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        //获取屏幕的宽高
        //Android绘图机制（一）——自定义View的基础属性和方法里面有讲
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 创建画笔
        rpaint = new Paint();
        //设置实心
        rpaint.setStyle(Paint.Style.STROKE);

        cpaint = new Paint();
        tvpaint = new Paint();
        tvpaint.setColor(Color.WHITE);
        tvpaint.setTextSize(20);
        //绘制弧线的区域矩形
        RectF rectF = new RectF((float) (width * 0.1), (float) (width * 0.1), (float) (width * 0.9), (float) (width * 0.9));

        //绘制圆
        canvas.drawCircle(width / 2, height / 2, (float) (width * 0.5 / 3), cpaint);
        canvas.drawArc(rectF, 270, 240, false, rpaint);
        //这样绘制的话，会出现偏差的，文字以圆形的中心开始绘制
        canvas.drawText(text, width / 2 - text.length() / 2, height / 2, tvpaint);
    }
}
