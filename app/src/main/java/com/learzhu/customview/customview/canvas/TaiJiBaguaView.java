package com.learzhu.customview.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/11 19:22
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  19:22
 * @updateDes ${TODO}
 */
public class TaiJiBaguaView extends View {

    private Paint paint;
    private int baGuaStrokeWidth = 10;//八卦线条宽度
    private float width;

    public TaiJiBaguaView(Context context) {
        this(context, null);
    }

    public TaiJiBaguaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaiJiBaguaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TaiJiBaguaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        width = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);//让其宽高一样了
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTaiJi(canvas);
        drawBaGua(canvas);
    }

    private void drawTaiJi(Canvas canvas) {
        RectF rect = new RectF();
        Path path = new Path();
        //阴鱼
        path.moveTo(width * 1 / 2, width * 1 / 4);
        rect.set(width / 4, width / 4, width * 3 / 4, width * 3 / 4);
        path.arcTo(rect, -90, 180);
        rect.set(width * 3 / 8, width * 1 / 2, width * 5 / 8, width * 3 / 4);
        path.arcTo(rect, 90, 180);
        rect.set(width * 3 / 8, width * 1 / 4, width * 5 / 8, width / 2);
        path.arcTo(rect, 90, -180);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
        //阳鱼
        rect.set(width / 4, width / 4, width * 3 / 4, width * 3 / 4);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rect, 90, 180, false, paint);
        paint.setStyle(Paint.Style.FILL);
        //两个黑白圆
        canvas.drawCircle(width * 15 / 32, width * 3 / 8, width / 20, paint);
        paint.setColor(0xffffffff);
        canvas.drawCircle(width * 17 / 32, width * 5 / 8, width / 20, paint);
    }

    private void drawBaGua(Canvas canvas) {
        paint.setStrokeWidth(baGuaStrokeWidth);
        paint.setColor(0xff000000);
        //八卦 可能不对 不要纠结
        drawGua(canvas, 0, true, false, true);
        drawGua(canvas, 45, false, true, true);
        drawGua(canvas, 90, false, false, false);
        drawGua(canvas, 135, false, false, true);
        drawGua(canvas, 180, false, true, false);
        drawGua(canvas, 225, true, false, false);
        drawGua(canvas, 270, true, true, true);
        drawGua(canvas, 315, true, true, false);
    }

    private void drawGua(Canvas canvas, float degree, boolean line1, boolean line2, boolean line3) {
        float w = width / 6;//线条长度
        canvas.save();
        canvas.translate(width / 2, width / 2);
        canvas.rotate(degree);
        //线条y坐标
        float l1 = -width * 10 / 32;
        float l2 = -width * 11 / 32;
        float l3 = -width * 12 / 32;
        //画三条线
        drawLine(canvas, w, l1, line1);
        drawLine(canvas, w, l2, line2);
        drawLine(canvas, w, l3, line3);
        canvas.restore();
    }

    private void drawLine(Canvas canvas, float w, float y, boolean b) {
        if (b) {
            canvas.drawLine(-w / 2, y, w / 2, y, paint);
        } else {
            canvas.drawLine(-w / 2, y, -w / 8, y, paint);
            canvas.drawLine(w / 8, y, w / 2, y, paint);
        }
    }
}
