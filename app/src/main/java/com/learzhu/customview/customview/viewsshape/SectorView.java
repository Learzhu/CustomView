package com.learzhu.customview.customview.viewsshape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/7/14 17:50
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  17:50
 * @updateDes ${TODO}
 * 扇形
 */
public class SectorView extends View {

    public SectorView(Context context) {
        super(context);
    }

    public SectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SectorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 创建画笔
        Paint paint = new Paint();
        //设置实心
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        RectF rectF = new RectF(60, 100, 200, 240);
        //绘制
//        canvas.drawRect(50, 100, 300, 300, paint);
        canvas.drawArc(rectF, 200, 130, true, paint);
    }

}
