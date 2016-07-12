package com.learzhu.customview.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/7/12 16:05
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  16:05
 * @updateDes ${TODO}
 */
public class DragLayout extends FrameLayout {
    /*左侧布局*/
    private View mLeftContent;
    /*主界面布局*/
    private View mMainContent;
    /*拖拽距离*/
    private int mDragRange;
    /*视图拖拽帮助类*/
    private ViewDragHelper mDragHelper;
    /*页面状态 默认为关闭*/
    private Status mStatus = Status.Close;
    /*手势处理类*/
    private GestureDetectorCompat mDetectorCompat;
    /*是否能拖动*/
    private boolean isDrag = true;
    /*main视图距离在ViewGroup距离左边的距离*/
    private int mMainLeft;
    private int mWith;
    private int mHeight;

    public DragLayout(Context context) {
//        super(context);
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化ViewDragHelper
        //ViewDragHelper.create(forParent, cb);
        //对应参数：父布局、敏感度、回调
        mDragHelper = ViewDragHelper.create(this, mCallBack);
        mDetectorCompat = new GestureDetectorCompat(getContext(), mGestureListener);
    }

    GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            float firstX = e1.getX();
            if (firstX > 100) {
                return false;
            } else if ((Math.abs(distanceX) > Math.abs(distanceY)) && distanceX < 0 && isDrag && mStatus == Status.Close) {
                return true;
            } else
                return (Math.abs(distanceX) > Math.abs(distanceY)) && distanceX > 0 && isDrag && mStatus == Status.Open;
        }
    };

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /*视图的状态的类*/
    public enum Status {
        Open, Close, Draging
    }

    /**
     * 处理回调CallBack
     */
    ViewDragHelper.Callback mCallBack = new ViewDragHelper.Callback() {
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }

        //当拖拽状态改变的时，IDLE/DRAGGING/SETTLING
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        // 决定了当View位置改变时，希望发生的其他事情。（此时移动已经发生）
        // 高频实时的调用，在这里设置左右面板的联动
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mMainContent) {
                mMainLeft = left;
            } else {
                mMainLeft += dx;
            }
            // 进行值的修正
            if (mMainLeft < 0) {
                mMainLeft = 0;
            } else if (mMainLeft > mDragRange) {
                mMainLeft = mDragRange;
            }
            // 如果拖拽的是左面板，强制在指定位置绘制Content
            if (changedView == mLeftContent) {
                layoutContent();
            }
            dispatchDragEvent(mMainLeft);
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        // View被释放时,侧滑打开或恢复
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (xvel > 0) {
                open();
            } else if (xvel == 0 && mMainLeft > mDragRange * 0.5f) {
                open();
            } else {
                close();
            }
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            mDragHelper.captureChildView(mMainContent, pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        // 横向拖拽的范围，大于0时可拖拽，等于0无法拖拽
        // 此方法只用于计算如view释放速度，敏感度等
        // 实际拖拽范围由clampViewPositionHorizontal方法设置
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mDragRange;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }

        // 此处设置view的拖拽范围。（实际移动还未发生）
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            // 拖动前oldLeft + 变化量dx == left
            if (mMainLeft + dx < 0) {
                return 0;
            } else if (mMainLeft + dx > mDragRange) {
                return mDragRange;
            }
            return left;
        }

        // 决定child是否可被拖拽,返回true则进行拖拽。
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mMainContent || child == mLeftContent;
        }
    };

    public void close() {
        close(true);
    }

    public void open() {
        open(true);
    }

    public void open(boolean isSmooth) {
        mMainLeft = mDragRange;
        if (isSmooth) {
            if (mDragHelper.smoothSlideViewTo(mMainContent, mMainLeft, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutContent();
        }
    }

    public void close(boolean isSmooth) {
        mMainLeft = 0;
        if (isSmooth) {
            // 执行动画，返回true代表有未完成的动画, 需要继续执行
            if (mDragHelper.smoothSlideViewTo(mMainContent, mMainLeft, 0)) {
                // 注意：参数传递根ViewGroup
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutContent();
        }
    }

    /*展示界面*/
    private void layoutContent() {
        mMainContent.layout(mMainLeft, 0, mMainLeft + mWith, mHeight);
        mLeftContent.layout(0, 0, mWith, mHeight);
    }

    /**
     * 每次更新都会调用 根据当前执行的位置计算百分比percent
     */
    protected void dispatchDragEvent(int mainLeft) {
        float percent = mainLeft / (float) mDragRange;
        animViews(percent);
        if (mListener != null) {
            mListener.onDraging(percent);
        }

        Status lastStatus = mStatus;
        if (updateStatus(mainLeft) != lastStatus) {
            if (mListener == null) {
                return;
            }
            if (lastStatus == Status.Draging) {
                if (mStatus == Status.Close) {
                    mListener.onClose();
                } else if (mStatus == Status.Open) {
                    mListener.onOpen();
                }
            }
        }
    }

    /**
     * 伴随动画：
     *
     * @param percent
     */
    private void animViews(float percent) {
        // 主面板：缩放
        float inverse = 1 - percent * 0.28f;
        //mMainContent水平方向 根据百分比缩放
        ViewHelper.setScaleX(mMainContent, inverse);
        //mMainContent垂直方向，根据百分比缩放
        ViewHelper.setScaleY(mMainContent, inverse);
        // 左面板：缩放、平移
        ViewHelper.setScaleX(mLeftContent, 0.5f + 0.5f * percent);
        ViewHelper.setScaleY(mLeftContent, 0.5f + 0.5f * percent);
        //沿着水平X轴平移
        ViewHelper.setTranslationX(mLeftContent, -mWith / 2.3f + mWith / 2.3f * percent);
        //mLeftContent根据百分比进行设置透明度
        ViewHelper.setAlpha(mLeftContent, percent);
        getBackground().setColorFilter(evaluate(percent, Color.BLACK, Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
    }

    private int evaluate(float fraction, int startValue, int endValue) {
        int startInt = startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24
                | (startR + (int) (fraction * (endR - startR))) << 16
                | (startG + (int) (fraction * (endG - startG))) << 8
                | (startB + (int) (fraction * (endB - startB)));
    }

    private OnLayoutDragingListener mListener;

    public interface OnLayoutDragingListener {
        void onOpen();

        void onClose();

        void onDraging(float percent);
    }

    public void setOnLayoutDragingListener(OnLayoutDragingListener mListener) {
        this.mListener = mListener;
    }

    private Status updateStatus(int mainLeft) {
        if (mainLeft == 0) {
            mStatus = Status.Close;
        } else if (mMainLeft == mDragRange) {
            mStatus = Status.Open;
        } else {
            mStatus = Status.Draging;
        }
        return mStatus;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //拿到宽高
        mWith = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //设置拖动范围
        mDragRange = (int) (mWith * 0.64f);
    }

    /**
     * 填充结束时获得两个子布局的引用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        // 必要的检验
        if (childCount < 2) {
            throw new IllegalStateException(
                    "You need two childrens in your content");
        }
        if (!(getChildAt(0) instanceof ViewGroup)
                || !(getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalArgumentException(
                    "Your childrens must be an instance of ViewGroup");
        }
        mLeftContent = getChildAt(0);
        mMainContent = getChildAt(1);
    }
}
