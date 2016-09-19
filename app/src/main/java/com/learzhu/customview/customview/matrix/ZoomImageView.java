package com.learzhu.customview.customview.matrix;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/18 9:58
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  9:58
 * @updateDes ${TODO}
 * 自定义的ImageView
 * <p/>
 * 1.自由放大缩小
 * 2.双击放大缩小
 * 3.放大以后可以进行自由的移动
 * 4.处理与ViewPager之间的事件冲突
 * <p/>
 * 知识点：
 * 1.Matrix
 * 2.ScaleGestureDetector
 * 3.GestureDetector
 * 4.事件分发机制 冲突解决
 * <p/>
 * 实现方式：
 * 复写ImageView
 * <p/>
 * 需要监听图片加载完成 OnGlobalLayoutListener 然后对图片处理至合适大小显示
 */
public class ZoomImageView extends ImageView implements OnGlobalLayoutListener, OnScaleGestureListener, OnTouchListener {
    /*用于记录是否是第一次加载完成 做初始化处理使用*/
    private boolean mOnce;
    /**
     * 初始化时缩放的值
     */
    private float mInitScale;
    /**
     * 双击放大的值
     */
    private float mMidScale;
    /**
     * 放大的最大值
     */
    private float mMaxScale;
    /**
     * 一个矩阵用于变换
     */
    private Matrix mMatrix;
    /**
     * 捕获用户多点触控时缩放比例
     */
    private ScaleGestureDetector mScaleGestureDetector;

    //***以下部分为自由移动
    /*记录上一次多点触控的数量*/
    private int mLastPointerCount;
    /*记录中心点的位置*/
    private float mLastX;
    private float mLastY;
    /*标准值，用于比较*/
    private int mTouchSlop;
    private boolean isCanDrag;

    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    //****双击放大缩小
    private GestureDetector mGestureDetector;
    /*用于记录用户双击事件 防止频繁双击*/
    private boolean isAutoScale;

    /*构造方法养成良好的习惯，一个参数的调用两个的 ，两个调用三个的  默认是调用两个的*/
    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMatrix = new Matrix();
        /*设置缩放模式*/
        setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        /*该方法已经有所有的空实现 现在是复写*/
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAutoScale) return true;
                float x = e.getX();
                float y = e.getY();
                if (getScale() < mMidScale) {
                    /*瞬间放大*/
//                    mMatrix.postScale(mMidScale / getScale(), mMidScale / getScale(), x, y);
//                    setImageMatrix(mMatrix);
                    // 缓慢放大
                    postDelayed(new AutoScaleRunnable(mMidScale, x, y), 16);
                    isAutoScale = true;
                } else {
//                    mMatrix.postScale(mInitScale / getScale(), mInitScale / getScale(), x, y);
//                    setImageMatrix(mMatrix);
                    postDelayed(new AutoScaleRunnable(mInitScale, x, y), 16);
                    isAutoScale = true;
                }
                return true;
            }
        });
    }

    /**
     * 自动放大与缩小
     * 梯度缩放
     */
    private class AutoScaleRunnable implements Runnable {
        /**
         * 缩放的目标值
         */
        private float mTargetScale;
        /*缩放的中心点*/
        private float x;
        private float y;

        private final float BIGGER = 1.07f;
        private final float SMALL = 0.93f;

        private float tmpScale;

        public AutoScaleRunnable(float targetScale, float x, float y) {
            mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            }
            if (getScale() > mTargetScale) {
                tmpScale = SMALL;
            }
        }

        @Override
        public void run() {
            /*进行缩放*/
            mMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mMatrix);
            float currentScale = getScale();
            if ((tmpScale > 1.0f && currentScale < mTargetScale) || tmpScale < 1.0f && currentScale > mTargetScale) {
                /*每16毫秒执行一次run方法*/
                postDelayed(this, 16);
            } else {
                /*设置为目标值*/
                float scale = mTargetScale / currentScale;
                mMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mMatrix);
                isAutoScale = false;
            }
        }
    }


    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        getViewTreeObserver().removeGlobalOnLayoutListener(this);
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     * 获取加载ImageView加载完成的图片
     */
    @Override
    public void onGlobalLayout() {
        if (!mOnce) {
            /*得到控件宽高*/
            int mWidth = getWidth();
            int mHeight = getHeight();
            /*获取图片 以及宽高*/
            Drawable mDrawable = getDrawable();
            if (mDrawable == null)
                return;
            int mDrawableWidth = mDrawable.getIntrinsicWidth();
            int mDrawableHeight = mDrawable.getIntrinsicHeight();

            float scale = 1.0f;
            /*图片的宽度度大于控件的宽度，高度小于控件的高度*/
            if (mWidth > mDrawableWidth && mHeight < mDrawableHeight) {
                /*根据宽度横向缩小*/
                scale = mWidth * 1.0f / mDrawableWidth;
            }
            /*图片的高度大于控件的高度，宽度小于控件的宽度*/
            if (mDrawableHeight > mHeight && mDrawableWidth < mWidth) {
                /*根据纵向缩小*/
                scale = mHeight * 1.0f / mDrawableHeight;
            }
            /*图片的宽度度大于控件的宽度，高度大于控件的高度*/
            if (mDrawableWidth > mWidth && mDrawableHeight > mHeight) {
                /*取最小比例缩小*/
                scale = Math.min(mWidth * 1.0f / mDrawableWidth, mHeight * 1.0f / mDrawableHeight);
            }

            /*图片的宽度度小于控件的宽度，高度小于控件的高度*/
            if (mDrawableWidth < mWidth && mDrawableHeight < mHeight) {
                /*取最小比例放大*/
                scale = Math.min(mWidth * 1.0f / mDrawableWidth, mHeight * 1.0f / mDrawableHeight);
            }

            /**
             * 得到初始化时的缩放比例
             */
            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMidScale = mInitScale * 2;
            /*将图片移动至当前控件的中心*/
            int dx = mWidth / 2 - mDrawableWidth / 2;
            int dy = mHeight / 2 - mDrawableHeight / 2;
            /*将缩放后的图片平移至中心*/
            mMatrix.postTranslate(dx, dy);
            /*将图片缩放*/
            mMatrix.postScale(mInitScale, mInitScale, mWidth / 2, mHeight / 2);
            /*执行修改图片状态*/
            setImageMatrix(mMatrix);
            mOnce = true;
        }
    }

    /**
     * 获取当前图片缩放值
     * <p/>
     * xScale   xSkew   xTrans
     * ySkew    yScale  yTrans
     * 0        0       0
     *
     * @return
     */
    public float getScale() {
        float[] values = new float[9];
        mMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 以下为缩放的情况
     * 缩放区间： initScale maxScale
     * 实现多指触碰缩放操作
     *
     * @param detector
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        /*当前缩放值*/
        float mScale = getScale();
        /*当前捕获的缩放值*/
        /*返回从前一个伸缩事件至当前伸缩事件的伸缩比率。*/
        float mScaleFactor = detector.getScaleFactor();
        if (getDrawable() == null)
            return true;
        // 缩放范围的控制,如果当前缩放值小于最大所放置，允许放大，如果当前缩放值大于最小缩放值，允许缩小
        if ((mScale < mMaxScale && mScaleFactor > 1.0f) || (mScale > mInitScale && mScaleFactor < 1.0f)) {
            /*小于最小值的时候设置为最小值*/
            if (mScale * mScaleFactor < mInitScale) {
                mScaleFactor = mInitScale / mScale;
            }

            /*大于最大的时候设置为最大值*/
            if (mScale * mScaleFactor > mMaxScale) {
//                mScaleFactor = mMaxScale / mScale;
                mScale = mMaxScale / mScale;
            }
            /*以屏幕中心点为缩放中心执行缩放*/
            mMatrix.postScale(mScaleFactor, mScaleFactor, getWidth() / 2, getHeight() / 2);

            /*以触点的坐标为缩放中心缩放*/
            /*以多指触碰中心位置缩放 返回当前手势焦点的X坐标。*/
            /*detector.getFocusX()如果手势正在进行中，焦点位于组成手势的两个触点之间。*/
            /*如果手势正在结束，焦点为仍留在屏幕上的触点的位置。*/
//            mMatrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mMatrix);
        }
        return true;
//        return false;
    }

    /**
     * 获得图片缩放后的宽高，以及 l,r,t,b
     *
     * @return
     */
    public RectF getMatrixRectF() {
        Matrix mMatrix = this.mMatrix;
        RectF rectF = new RectF();
        Drawable mDrawable = getDrawable();
        if (mDrawable != null) {
            rectF.set(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
            mMatrix.mapRect(rectF);
        }
        return rectF;
    }

    /**
     * 在缩放的时候进行边界控制以及我们的位置控制
     * 去除缩放操作后图片偏移的问题 空白留余
     * 防止出现白边
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        /*记录差值 边界超出的一部分*/
        float deltaX = 0;
        float deltaY = 0;
        int mWidth = getWidth();
        int mHeight = getHeight();
        // 缩放时进行边界检测，防止边沿出现空隙
        if (rectF.width() >= mWidth) {
            // 图片的宽大于等于控件的宽才需要移动
            /*左侧有空白*/
            if (rectF.left > 0) {
                /*负数向左平移*/
                deltaX = -rectF.left;
            }
            /*右侧不足  rectF.right为矩形右侧的横坐标*/
            if (rectF.right < mWidth) {
                /*正数向右平移*/
                deltaX = mWidth - rectF.right;
            }
        }
        // 图片的高大于等于控件的高才需要移动
        if (rectF.height() >= mHeight) {
            if (rectF.top > 0) {
                /*顶部有距离*/
                deltaY = -rectF.top;
            }
            if (rectF.bottom < mHeight) {
                /*底部有距离*/
                deltaY = mHeight - rectF.bottom;
            }
        }
        /**
         * 宽度或高度小于控件
         * 居中显示
         */
        if (rectF.width() < mWidth) {
            deltaX = mWidth / 2f - rectF.right + rectF.width() / 2f;
        }
        if (rectF.height() < mHeight) {
//            deltaY = mHeight / 2f - rectF.top + rectF.height() / 2f;
            deltaY = mHeight / 2f - rectF.bottom + rectF.height() / 2f;
        }
        /*平移*/
        mMatrix.postTranslate(deltaX, deltaY);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        // 一定要返回true才会进入onScale()这个函数
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /*双击事件优先*/
        if (mGestureDetector.onTouchEvent(event))
            return true;
        mScaleGestureDetector.onTouchEvent(event);
        // <------------------处理放大后移动查看隐藏的部分-------------------------------->
        /*几个触控点的中心点的位置*/
        float x = 0;
        float y = 0;
        int mPointerCount = event.getPointerCount();
        for (int i = 0; i < mPointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= mPointerCount;
        y /= mPointerCount;
        if (mLastPointerCount != mPointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        mLastPointerCount = mPointerCount;
        RectF rectF = getMatrixRectF();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /*防止出错 浮点数操作 加上*/
                if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01) {
                    /*防止ViewPager的拦截事件*/
                    if (getParent() instanceof ViewPager)
                    /*当图片放大 宽高大于屏幕宽度或高度 请求不被屏蔽*/
                        getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                /*防止出错 加上*/
                if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01) {
                    /*防止ViewPager的拦截事件*/
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true);
                }
                /*变化的值 偏移量*/
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }
                /*移动显示隐藏的部分*/
                if (isCanDrag) {
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        /*宽度小于控件宽度，不允许横向移动*/
                        if (rectF.width() < getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        /*高度小于控件高度，不允许纵向移动*/
                        if (rectF.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }
                        mMatrix.postTranslate(dx, dy);
                        /*平移的时候检查边沿*/
                        checkBorderWhenTranslate();
                        setImageMatrix(mMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 当移动时进行边界检查
     */
    private void checkBorderWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }
        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom;
        }
        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }
        mMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 判断是否是move
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }

}
