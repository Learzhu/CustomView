package com.learzhu.customview.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.learzhu.customview.customview.R;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/7/14 19:31
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  19:31
 * @updateDes ${TODO}
 */
public class CustomTextView extends TextView {
    private Paint paint1, paint2;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint1 = new Paint();
        paint1.setColor(getResources().getColor(R.color.color1));
        paint1.setStyle(Paint.Style.FILL);

        paint2 = new Paint();
        paint2.setColor(getResources().getColor(R.color.colorAccent));
        paint2.setStyle(Paint.Style.FILL);
        //绘制外层矩形
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint1);
        //绘制内层矩形
        canvas.drawRect(10, 10, getMeasuredWidth(), getMeasuredHeight() - 10, paint2);
        canvas.save();
        //父类完成的方法，即绘制完成
        super.onDraw(canvas);
    }
}
