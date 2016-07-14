package com.learzhu.customview.customview.viewsshape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
 */
public class CurveView extends View {

    public CurveView(Context context) {
        super(context);
    }

    public CurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CurveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 创建画笔
        Paint paint = new Paint();
        //设置实心
        //设置画笔锯齿效果
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        /*设置空心*/
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        /*起点*/
        path.moveTo(100, 320);
        //设置路径点和终点
        path.quadTo(150, 310, 170, 400);
        path.close();
        canvas.drawPath(path, paint);
    }

}
