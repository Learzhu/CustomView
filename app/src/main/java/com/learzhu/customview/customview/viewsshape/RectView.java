package com.learzhu.customview.customview.viewsshape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
 * 矩形
 */
public class RectView extends View {

    public RectView(Context context) {
        super(context);
    }

    public RectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        //绘制
        canvas.drawRect(50, 100, 300, 300, paint);
    }
}
