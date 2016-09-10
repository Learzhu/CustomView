package com.learzhu.customview.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/10 16:03
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  16:03
 * @updateDes ${TODO}
 * <p/>
 * 一.自定义View分类
 * 我将自定义View分为了两类(sloop个人分类法，非官方)：
 * <p/>
 * 1.自定义ViewGroup
 * <p/>
 * 自定义ViewGroup一般是利用现有的组件根据特定的布局方式来组成新的组件，大多继承自ViewGroup或各种Layout，包含有子View。
 * <p/>
 * 例如：应用底部导航条中的条目，一般都是上面图标(ImageView)，下面文字(TextView)，那么这两个就可以用自定义ViewGroup组合成为一个Veiw，提供两个属性分别用来设置文字和图片，使用起来会更加方便。
 * 2.自定义View
 * <p/>
 * 在没有现成的View，需要自己实现的时候，就使用自定义View，一般继承自View，SurfaceView或其他的View，不包含子View。
 * <p/>
 * 例如：制作一个支持自动加载网络图片的ImageView，制作图表等。
 * PS： 自定义View在大多数情况下都有替代方案，利用图片或者组合动画来实现，但是使用后者可能会面临内存耗费过大，制作麻烦更诸多问题。
 * <p/>
 * <p/>
 * <p/>
 * ******完整步骤
 * 1.构造函数
 * 2.测量View大小(onMeasure)
 * 3.确定View大小(onSizeChanged)
 * 4.确定子View布局位置(onLayout)
 * 5.绘制内容(onDraw)
 * 6.对外提供操作方法和监听回调
 * <p/>
 * 自定义View总结
 * 自定义View分类
 * <p/>
 * PS ：实际上ViewGroup是View的一个子类。
 * 类别	继承自	特点
 * View	View SurfaceView 等	不含子View
 * ViewGroup	ViewGroup xxLayout等	包含子View
 * 自定义View流程：
 * <p/>
 * 步骤	关键字	作用
 * 1	构造函数	View初始化
 * 2	onMeasure	测量View大小
 * 3	onSizeChanged	确定View大小
 * 4	onLayout	确定子View布局(自定义View包含子View时有用)
 * 5	onDraw	实际绘制内容
 * 6	提供接口	控制View或监听View某些状态。
 */
public class CustomView extends View {
    /**
     * 一般在直接New一个View的时候调用。
     *
     * @param context
     */
    public CustomView(Context context) {
        super(context);
    }

    /**
     * //一般在layout文件中使用的时候会调用，关于它的所有属性(包括自定义属性)都会包含在attrs中传递进来。
     * <p/>
     * //在layout文件中 - 格式为： 包名.View名
     * <com.sloop.study.SloopView
     * android:layout_width"wrap_content"
     * android:layout_height"wrap_content"/>
     *
     * @param context
     * @param attrs
     */
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 有三个参数的构造函数中第三个参数是默认的Style，这里的默认的Style是指它在当前Application或Activity所用的Theme中的默认Style，且只有在明确调用的时候才会生效
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 有四个参数的构造函数在API21的时候才添加上，暂不考虑。
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * View的大小不仅由自身所决定，同时也会受到父控件的影响，为了我们的控件能更好的适应各种情况，一般会自己进行测量
     * 用 MeasureSpec 的 getSize是获取数值， getMode是获取模式
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec 从上面可以看出 onMeasure 函数中有 widthMeasureSpec 和 heightMeasureSpec 这两个 int 类型的参数， 毫无疑问他们是和宽高相关的， 但它们其实不是宽和高， 而是由宽、高和各自方向上对应的测量模式来合成的一个值：
     *                          <p/>
     *                          测量模式一共有三种， 被定义在 Android 中的 View 类的一个内部类View.MeasureSpec中：
     *                          <p/>
     *                          模式	二进制数值	描述
     *                          UNSPECIFIED	00	默认值，父控件没有给子view任何限制，子View可以设置为任意大小。
     *                          EXACTLY	01	表示父控件已经确切的指定了子View的大小。
     *                          AT_MOST	10	表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
     *                          <p/>
     *                          ****如果对View的宽高进行修改了，不要调用 super.onMeasure( widthMeasureSpec, heightMeasureSpec); 要调用 setMeasuredDimension( widthsize, heightsize); 这个函数。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);      //取出宽度的确切数值
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);      //取出宽度的测量模式

        int heightsize = MeasureSpec.getSize(heightMeasureSpec);    //取出高度的确切数值
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);    //取出高度的测量模式
    }

    /**
     * 这是因为View的大小不仅由View本身控制，而且受父控件的影响，所以我们在确定View大小的时候最好使用系统提供的onSizeChanged回调函数。
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh 我们只需关注 宽度(w), 高度(h) 即可，这两个参数就是View最终的大小。
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 确定布局的函数是onLayout，它用于确定子View的位置，在自定义ViewGroup中会用到，他调用的是子View的layout函数。
     * <p/>
     * 在自定义ViewGroup中，onLayout一般是循环取出子View，然后经过计算得出各个子View位置的坐标值，然后用以下函数设置子View位置。
     * <p/>
     * child.layout(l, t, r, b);
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * onDraw是实际绘制的部分，也就是我们真正关心的部分，使用的是Canvas绘图。
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
