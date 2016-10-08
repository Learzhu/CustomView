package com.learzhu.customview.customview.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/29 12:05
 * @des 封装的一个RecyclerView的间隔的类
 * @updateAuthor $Author$
 * @updateDate $Date$  12:05
 * @updateDes ${TODO}\
 * 当我们调用mRecyclerView.addItemDecoration()方法添加decoration的时候，RecyclerView在绘制的时候，去会绘制decorator，即调用该类的onDraw和onDrawOver方法，
 * onDraw方法先于drawChildren
 * onDrawOver在drawChildren之后，一般我们选择复写其中一个即可。
 * onDraw   drawChildren   onDrawOver
 * getItemOffsets 可以通过outRect.set()为每个Item设置一定的偏移量，主要用于绘制Decorator。
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {
    /*调用系统的线*/
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private Drawable mDivider;
    /*获取方向*/
    private int mOrientation;
    /**
     * 间距
     */
    private int spaceLeft, spaceRight;

    public RecyclerViewItemDecoration(Context context, int roientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        /*获取系统的线*/
        mDivider = a.getDrawable(0);
        a.recycle();
        mOrientation = roientation;
    }

    /**
     * 设置线条的方向
     *
     * @param orientation
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("Invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
//        super.onDraw(c, parent);
        if (mOrientation == VERTICAL_LIST) {

        }
    }

    /**
     * 画垂直的线条
     *
     * @param canvas
     * @param parent
     */
    public void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
//            RecyclerView mRecyclerView = new RecyclerView(parent.getContext());
            RecyclerView.LayoutParams mLayoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + mLayoutParams.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    /**
     * 画水平线
     *
     * @param canvas
     * @param parent
     */
    public void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
//        final int bottom=parent.getHeight()-parent.
    }
}
