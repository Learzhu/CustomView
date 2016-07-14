package com.learzhu.customview.customview.viewsshape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
 * 圆
 */
public class CircleView extends View {

    int width;
    int height;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        Paint paint = new Paint();
        //设置实心
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        //设置画笔锯齿效果
        paint.setAntiAlias(true);
        //绘制
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
    }
}
