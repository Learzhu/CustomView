package com.learzhu.customview.customview.matrix;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/14 17:40
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  17:40
 * @updateDes ${TODO}
 * <p/>
 * Matrix方法表
 * 这个方法表，暂时放到这里让大家看看，方法的使用讲解放在下一篇文章中。
 * <p/>
 * 方法类别	相关API	摘要
 * 基本方法	equals hashCode toString toShortString	比较、 获取哈希值、 转换为字符串
 * 数值操作	set reset setValues getValues	设置、 重置、 设置数值、 获取数值
 * 数值计算	mapPoints mapRadius mapRect mapVectors	计算变换后的数值
 * 设置(set)	setConcat setRotate setScale setSkew setTranslate	设置变换
 * 前乘(pre)	preConcat preRotate preScale preSkew preTranslate	前乘变换
 * 后乘(post)	postConcat postRotate postScale postSkew postTranslate	后乘变换
 * 特殊方法	setPolyToPoly setRectToRect rectStaysRect setSinCos	一些特殊操作
 * 矩阵相关	invert isAffine isIdentity	求逆矩阵、 是否为仿射矩阵、 是否为单位矩阵 …
 * <p/>
 * 无参构造
 * Matrix matrix = new Matrix();
 * 创建一个全新的Matrix 这种方式创建出来的并不是一个数值全部为空的矩阵，而是一个单位矩阵
 * <p/>
 * 有参构造
 * Matrix matrix = new Matrix(src);
 * 创建一个Matrix，并对src深拷贝(理解为新的matrix和src是两个对象，但内部数值相同即可)。
 */
public class MatrixView extends View {
    private Paint mPaint;
    private int mWidth, mHeight;

    public MatrixView(Context context) {
        this(context, null);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 创建画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
