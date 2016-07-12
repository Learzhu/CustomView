package com.learzhu.customview.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Size;
import android.view.View;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/7/12 10:02
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  10:02
 * @updateDes ${TODO}
 */
public class SketchView extends View {
    private int custom_size;
    private int custom_background;

    private Paint mPaint;
    private int mHeight;
    private int mWidth;
    private float scale = 1f;

    private final int SIZE = 15;
    private final int DEFAULT_COLOR = Color.BLUE;

    public SketchView(Context context) {
//        super(context);
        this(context, null);
    }

    public SketchView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    /**
     * 第三个构造函数比第二个构造函数多了一个int型的值，名字叫defStyleAttr，
     * 从名称上判断，这是一个关于自定义属性的参数，实际上我们的猜测也是正确的，
     * 第三个构造函数不会被系统默认调用，而是需要我们自己去显式调用，比如在第二个构造函数里调用调用第三个函数，并将第三个参数设为0
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SketchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchView, defStyleAttr, R.style.AppTheme);
        custom_size = array.getDimensionPixelSize(R.styleable.SketchView_size, SIZE);
        custom_background = array.getColor(R.styleable.SketchView_background_color, DEFAULT_COLOR);
        array.recycle();
        init();
    }

    /**
     * 初始化画笔
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(custom_background);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * defStyleAttr指定的是在Theme style定义的一个attr，它的类型是reference,主要生效在obtainStyledAttributes方法里，
     * obtainStyledAttributes方法有四个参数，第三个参数是defStyleAttr，第四个参数是自己指定的一个style，
     * 当且仅当defStyleAttr为0或者在Theme中找不到defStyleAttr指定的属性时，第四个参数才会生效，这些指的都是默认属性，
     * 当在xml里面定义的，就以在xml文件里指定的为准，所以优先级大概是：
     * xml>style>defStyleAttr>defStyleRes>Theme指定，
     * 在布局xml中直接定义 > 在布局xml中通过style定义 > 自定义View所在的Activity的Theme中指定style引用 > 构造函数中defStyleRes指定的默认值
     * 在布局xml中直接定义 > 在布局xml中通过style定义 > 自定义View所在的Activity的Theme中指定style引用 > 构造函数中defStyleRes指定的默认值
     * 当defStyleAttr为0时，就跳过defStyleAttr指定的reference，所以一般用0就能满足一些基本开发。
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */

    public SketchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Measure->Layout->Draw
     * <p/>
     * Measure，Layout和Draw，通过onMeasure知道一个view要占界面的大小，然后通过onLayout知道这个控件应该放在哪个位置，
     * 最后通过onDraw方法将这个控件绘制出来，然后才能展现在用户面前
     * <p/>
     * MeasureSpce的mode有三种：EXACTLY, AT_MOST，UNSPECIFIED，除却UNSPECIFIED不谈，其他两种mode：
     * 当父布局是EXACTLY时，子控件确定大小或者match_parent，mode都是EXACTLY，子控件是wrap_content时，mode为AT_MOST；
     * 当父布局是AT_MOST时，子控件确定大小，mode为EXACTLY，子控件wrap_content或者match_parent时，
     * mode为AT_MOST。所以在确定控件大小时，需要判断MeasureSpec的mode，不能直接用MeasureSpec的size。在进行一些逻辑处理以后，
     * 调用setMeasureDimension()方法，将测量得到的宽高传进去供layout使用。
     */
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
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*正方形*/
//        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    /**
     * onLayout方法里主要是具体摆放子view的位置，水平摆放或者垂直摆放，所以在单纯的自定义view是不需要重写onLayout方法，
     * 不过需要注意的一点是，子view的margin属性是否生效就要看parent是否在自身的onLayout方法进行处理，而view得padding属性是在onDraw方法中生效的。
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mHeight = getHeight();
        mWidth = getWidth();
    }

    /**
     * 用Paint在Canvas上画出你想要的图案，这样一个自定义view才算结束。
     * 如果是直接继承的View，那么在重写onDraw的方法是时候完全可以把super.ondraw(canvas)删掉，因为它的默认实现是空
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, custom_size * scale, mPaint);
    }

    private ValueAnimator mAnimator;

    public void startAnimation() {
        mAnimator = ValueAnimator.ofFloat(1, 2);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        /*重复次数 -1表示无限循环*/
        mAnimator.setRepeatCount(-1);
        // 重复模式, RESTART: 重新开始 REVERSE:恢复初始状态再开始
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        /*关闭动画*/
        mAnimator.end();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    /**
     * 在自定义view时，时常用到刷新view的方法，这时候就会有三个方法供我们选择：requestLayout()、invalidate()、postInvalidate()，
     * 其实invalidate和postInvalidate这两个方法作用是一样的，唯一不同的是invalidate用在主线程，而postInvalidate用在异步线程
     * <p/>
     * requestLayout会调用measure和layout 等一系列操作，然后根据布局是否发生改变，surface是否被销毁，来决定是否调用draw，
     * 也就是说requestlayout肯定会调用measure和layout，但不一定调用draw，读者可以试着改下我上面写的那个小程序，将postInvalidate改成requestlayout，
     * 动画效果就消失了，因为布局没有发生改变。
     * invalidate 只会调用draw，而且肯定会调，即使什么都没有发生改变，它也会重新绘制。
     */
    @Override
    public void requestLayout() {
        super.requestLayout();
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    public void postInvalidate() {
        super.postInvalidate();
    }
}
