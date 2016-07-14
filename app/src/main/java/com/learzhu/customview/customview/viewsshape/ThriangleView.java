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
 * 三角形
 */
public class ThriangleView extends View {

    public ThriangleView(Context context) {
        super(context);
    }

    public ThriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ThriangleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 创建画笔
        Paint paint = new Paint();
        //设置实心
        paint.setStyle(Paint.Style.FILL);
        //设置画笔锯齿效果
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        Path path = new Path();
        /*起点*/
        path.moveTo(80, 200);
        path.lineTo(120, 250);
        path.lineTo(80, 250);
        /*封闭多边形*/
        path.close();
        canvas.drawPath(path, paint);
    }

}
