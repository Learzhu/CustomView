package com.learzhu.customview.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
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

    private boolean aBoolean;
    private int integer;

    private final int SIZE = 15;

    public WritingBoardView(Context context) {
        super(context);
    }

    public WritingBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WritingBoardView, 0, 0);
        try {
            aBoolean = array.getBoolean(R.styleable.WritingBoardView_showText, false);
            integer = array.getInteger(R.styleable.WritingBoardView_labelPositon, 0);
        } finally {
            array.recycle();
        }
    }

    public boolean isShowText() {
        return aBoolean;
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

    /**
     * 我们自定义控件的属性发生改变之后，控件的样子也可能发生改变，在这种情况下就需要调用invalidate()方法让系统去调用view的onDraw()重新绘制。
     * 同样的，控件属性的改变可能导致控件所占的大小和形状发生改变，所以我们需要调用requestLayout()来请求测量获取一个新的布局位置。
     *
     * @param showText
     */
    public void setShowText(boolean showText) {
        aBoolean = showText;
        invalidate();
        requestLayout();
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public WritingBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WritingBoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
