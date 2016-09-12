package com.learzhu.customview.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/11 11:30
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  11:30
 * @updateDes ${TODO}
 * <p/>
 * Rect(l,t,r,b)
 * 矩形左上角坐标  右下角坐标
 */
public class CanvasView extends View {
    /*1.创建一个画笔*/
    private Paint mPaint = new Paint();

    private int mWidth, mHeight;

    /*2.初始化画笔*/
    private void initPaint() {
        /*设置画笔颜色*/
        mPaint.setColor(Color.BLACK);
        /*设置画笔模式为填充*/
        mPaint.setStyle(Paint.Style.FILL);
        /*设置画笔宽度为10px*/
        mPaint.setStrokeWidth(10f);
    }

    public CanvasView(Context context) {
        super(context);
    }

    /*3.在构造函数中初始化*/
    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
        initPaint();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 获取屏幕宽高
     */
    public void initData() {
        //获取屏幕宽高
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mWidth = windowManager.getDefaultDisplay().getWidth();
        mHeight = windowManager.getDefaultDisplay().getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*位移前先保存状态*/
        canvas.save();
        //位移
        canvasTranslate(canvas);
        canvas.restore();

        /*缩放以原点为中心*/
        canvasScaleOrigin(canvas);
        /*缩放以固定点为中心*/
//        canvasScaleSpecialPoint(canvas);
        /*翻转*/
//        canvasScaleNegative(canvas);
//        canvasScaleSpecialPoint(canvas);
//        canvasScaleSpecialView(canvas);
//        canvasRotate(canvas);
//        canvasRotateSpecialPoint(canvas);
//        canvasRotateSpecialView(canvas);
//        canvasSkew(canvas);
//        canvasSkewSpecial(canvas);
    }


    /**
     * ⑴位移(translate)
     * translate是坐标系的移动，可以为图形绘制选择一个合适的坐标系。 请注意，位移是基于当前位置移动，而不是每次基于屏幕左上角的(0,0)点移动，
     * ** canvas.translate注意canvas平移每个View只有一个cnavas对象所以是连续变化的
     */
    public void canvasTranslate(Canvas canvas) {
        // 在坐标原点绘制一个黑色圆形
        mPaint.setColor(Color.BLACK);
        canvas.translate(20, 20);
        canvas.drawCircle(0, 0, 30, mPaint);

        // 在坐标原点绘制一个蓝色圆形
        mPaint.setColor(Color.BLUE);
        canvas.translate(20, 20);
        canvas.drawCircle(0, 0, 30, mPaint);
    }

    /**
     * ⑵缩放(scale)
     * 缩放提供了两个方法，如下：
     * <p/>
     * public void scale (float sx, float sy)
     * <p/>
     * public final void scale (float sx, float sy, float px, float py)
     * <p/>
     * 缩放比例(sx,sy)取值范围详解：
     * <p/>
     * 取值范围(n)	说明
     * [-∞, -1)	先根据缩放中心放大n倍，再根据中心轴进行翻转
     * -1	根据缩放中心轴进行翻转
     * (-1, 0)	先根据缩放中心缩小到n，再根据中心轴进行翻转
     * 0	不会显示，若sx为0，则宽度为0，不会显示，sy同理
     * (0, 1)	根据缩放中心缩小到n
     * 1	没有变化
     * (1, +∞)	根据缩放中心放大n倍
     * <p/>
     * 缩放的中心默认为坐标原点,而缩放中心轴就是坐标轴
     * <p/>
     * 以原点为中心缩放
     *
     * @param canvas
     */
    public void canvasScaleOrigin(Canvas canvas) {
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        // 矩形区域
        RectF rectF = new RectF(0, -400, 400, 0);
        //绘制黑色矩形
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(rectF, mPaint);

        /*缩放画布*/
        canvas.scale(0.5f, 0.5f);
        /*绘制蓝色矩形*/
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 以某个点为中心缩放
     *
     * @param canvas
     */
    public void canvasScaleSpecialPoint(Canvas canvas) {
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        // 矩形区域
        RectF rectF = new RectF(0, -400, 400, 0);
        //绘制黑色矩形
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(rectF, mPaint);

        /*缩放画布  缩放中心为200,0  <-- 缩放中心向右偏移了200个单位*/
        canvas.scale(0.5f, 0.5f, 200, 0);
        /*绘制蓝色矩形*/
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 负数值
     * 当缩放比例为负数的时候会根据缩放中心轴进行翻转
     *
     * @param canvas
     */
    public void canvasScaleNegative(Canvas canvas) {
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        // 矩形区域
        RectF rectF = new RectF(0, -400, 400, 0);
        //绘制黑色矩形
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(rectF, mPaint);

        /*缩放画布  缩放中心为0,0  */
        canvas.scale(-0.5f, -0.5f);
        /*绘制蓝色矩形*/
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 负数值
     * 当缩放比例为负数的时候会根据缩放中心轴进行翻转
     *
     * @param canvas
     */
    public void canvasScaleNegativeSpecialPoint(Canvas canvas) {
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        // 矩形区域
        RectF rectF = new RectF(0, -400, 400, 0);
        //绘制黑色矩形
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(rectF, mPaint);

        /*缩放画布  缩放中心为 200,0 对称变化  */
        canvas.scale(-0.5f, -0.5f, 200, 0);
        /*绘制蓝色矩形*/
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 类似回形的图
     * <p/>
     * 和位移(translate)一样，缩放也是可以叠加的。
     * <p/>
     * canvas.scale(0.5f,0.5f);
     * canvas.scale(0.5f,0.1f);
     * 调用两次缩放则 x轴实际缩放为0.5x0.5=0.25 y轴实际缩放为0.5x0.1=0.05
     *
     * @param canvas
     */
    public void canvasScaleSpecialView(Canvas canvas) {
        /*修改画笔*/
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        //矩形区域
        RectF rectF = new RectF(-400, -400, 400, 400);
        for (int i = 0; i < 100; i++) {
            canvas.scale(0.99f, 0.99f);
            canvas.drawRect(rectF, mPaint);
        }
    }

    /**
     * ⑶旋转(rotate)
     * 旋转提供了两种方法：
     * <p/>
     * public void rotate (float degrees)
     * <p/>
     * public final void rotate (float degrees, float px, float py)
     * 和缩放一样，第二种方法多出来的两个参数依旧是控制旋转中心点的。
     *
     * @param canvas
     */
    public void canvasRotate(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        /*矩形区域*/
        RectF rectF = new RectF(0, -400, 400, 0);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectF, mPaint);
        /*旋转180度 <-- 默认旋转中心为原点*/
        canvas.rotate(180);

        // 绘制蓝色矩形
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 以某个点旋转
     *
     * @param canvas
     */
    public void canvasRotateSpecialPoint(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        /*矩形区域*/
        RectF rectF = new RectF(0, -400, 400, 0);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectF, mPaint);
        /*旋转180度 <-- 默认旋转中心为原点*/
        canvas.rotate(180, 200, 0);

        // 绘制蓝色矩形
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF, mPaint);
    }

    /**
     * 旋转也是可叠加的
     * <p/>
     * canvas.rotate(180);
     * canvas.rotate(20);
     * 调用两次旋转，则实际的旋转角度为180+20=200度。
     *
     * @param canvas
     */
    public void canvasRotateSpecialView(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        // 绘制两个圆形
        canvas.drawCircle(0, 0, 400, mPaint);
        canvas.drawCircle(0, 0, 380, mPaint);

        /*绘制圆形之间的连接线*/
        for (int i = 0; i <= 360; i += 10) {
            canvas.drawLine(0, 380, 0, 400, mPaint);
            canvas.rotate(10);
        }
    }

    /**
     * ⑷错切(skew)
     * skew这里翻译为错切，错切是特殊类型的线性变换。
     * <p/>
     * 错切只提供了一种方法：
     * <p/>
     * public void skew (float sx, float sy)
     * 参数含义：
     * float sx:将画布在x方向上倾斜相应的角度，sx倾斜角度的tan值，
     * float sy:将画布在y轴方向上倾斜相应的角度，sy为倾斜角度的tan值.
     * <p/>
     * 变换后:
     * <p/>
     * X = x + sx * y
     * Y = sy * x + y
     *
     * @param canvas
     */
    public void canvasSkew(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);

        RectF rect = new RectF(0, 0, 200, 200);   // 矩形区域

        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect, mPaint);

        canvas.skew(1, 0);                       // 水平错切 <- 45度

        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect, mPaint);
    }

    /**
     * 错切也是可叠加的，不过请注意，调用次序不同绘制结果也会不同
     *
     * @param canvas
     */
    public void canvasSkewSpecial(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);

        RectF rect = new RectF(0, 0, 200, 200);   // 矩形区域

        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect, mPaint);

        canvas.skew(1, 0);                       // 水平错切
        canvas.skew(0, 1);                       // 垂直错切

        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect, mPaint);
    }

    /**
     * ⑸快照(save)和回滚(restore)
     * 画布的操作是不可逆的，而且很多画布操作会影响后续的步骤，例如第一个例子，两个圆形都是在坐标原点绘制的，
     * 而因为坐标系的移动绘制出来的实际位置不同。所以会对画布的一些状态进行保存和回滚。
     * <p/>
     * <p/>
     * 相关API	简介
     * save	把当前的画布的状态进行保存，然后放入特定的栈中
     * saveLayerXxx	新建一个图层，并放入特定的栈中
     * restore	把栈中最顶层的画布状态取出来，并按照这个状态恢复当前的画布
     * restoreToCount	弹出指定位置及其以上所有的状态，并按照指定位置的状态进行恢复
     * getSaveCount	获取栈中内容的数量(即保存次数)
     * <p/>
     * <p/>
     * 这个栈可以存储画布状态和图层状态。
     * 你可以把这些图层看做是一层一层的玻璃板，你在每层的玻璃板上绘制内容，然后把这些玻璃板叠在一起看就是最终效果。
     * <p/>
     * <p/>
     * SaveFlags
     * <p/>
     * 名称	简介
     * ALL_SAVE_FLAG	默认，保存全部状态
     * CLIP_SAVE_FLAG	保存剪辑区
     * CLIP_TO_LAYER_SAVE_FLAG	剪裁区作为图层保存
     * FULL_COLOR_LAYER_SAVE_FLAG	保存图层的全部色彩通道
     * HAS_ALPHA_LAYER_SAVE_FLAG	保存图层的alpha(不透明度)通道
     * MATRIX_SAVE_FLAG	保存Matrix信息( translate, rotate, scale, skew)
     * <p/>
     * <p/>
     * save 有两种方法：
     * <p/>
     * // 保存全部状态
     * public int save ()
     * <p/>
     * // 根据saveFlags参数保存一部分状态
     * public int save (int saveFlags)
     * 每调用一次save方法，都会在栈顶添加一条状态信息
     * <p/>
     * <p/>
     * ***saveLayerXxx方法会让你花费更多的时间去渲染图像(图层多了相互之间叠加会导致计算量成倍增长)，使用前请谨慎，如果可能，尽量避免使用。
     *
     * @param canvas
     */
    public void canvasSave(Canvas canvas) {
        canvas.save();
//        saveLayerXxx
//        saveLayerXxx有比较多的方法：
//
//        // 无图层alpha(不透明度)通道
//        public int saveLayer (RectF bounds, Paint paint)
//        public int saveLayer (RectF bounds, Paint paint, int saveFlags)
//        public int saveLayer (float left, float top, float right, float bottom, Paint paint)
//        public int saveLayer (float left, float top, float right, float bottom, Paint paint, int saveFlags)
//
//        // 有图层alpha(不透明度)通道
//        public int saveLayerAlpha (RectF bounds, int alpha)
//        public int saveLayerAlpha (RectF bounds, int alpha, int saveFlags)
//        public int saveLayerAlpha (float left, float top, float right, float bottom, int alpha)
//        public int saveLayerAlpha (float left, float top, float right, float bottom, int alpha, int saveFlags)
    }

    /**
     * restore
     * <p/>
     * 状态回滚，就是从栈顶取出一个状态然后根据内容进行恢复。
     * <p/>
     * 同样以上面状态栈图片为例，调用一次restore方法则将状态栈中第5次取出，根据里面保存的状态进行状态恢复。
     * <p/>
     * restoreToCount
     * <p/>
     * 弹出指定位置以及以上所有状态，并根据指定位置状态进行恢复。
     * <p/>
     * 以上面状态栈图片为例，如果调用restoreToCount(2) 则会弹出 2 3 4 5 的状态，并根据第2次保存的状态进行恢复。
     * <p/>
     * getSaveCount
     * <p/>
     * 获取保存的次数，即状态栈中保存状态的数量，以上面状态栈图片为例，使用该函数的返回值为5。
     * <p/>
     * 不过请注意，该函数的最小返回值为1，即使弹出了所有的状态，返回值依旧为1，代表默认状态。
     * <p/>
     * <p/>
     * save();      //保存状态
     * ...          //具体操作
     * restore();   //回滚到之前的状态
     *
     * @param canvas
     */
    public void canvasRestore(Canvas canvas) {

    }

}
