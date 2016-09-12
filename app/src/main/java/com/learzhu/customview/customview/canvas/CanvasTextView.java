package com.learzhu.customview.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/12 11:05
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  11:05
 * @updateDes ${TODO}
 * <p/>
 * 绘制文字
 * <p/>
 * 绘制文字部分大致可以分为三类：
 * 第一类只能指定文本基线位置位置(基线x默认在字符串左侧，基线y默认在字符串下方)。
 * 第二类可以分别指定每个文字的位置。
 * 第三类是指定一个路径，根据路径绘制文字。
 * <p/>
 * // 第一类
 * public void drawText (String text, float x, float y, Paint paint)
 * public void drawText (String text, int start, int end, float x, float y, Paint paint)
 * public void drawText (CharSequence text, int start, int end, float x, float y, Paint paint)
 * public void drawText (char[] text, int index, int count, float x, float y, Paint paint)
 * <p/>
 * // 第二类
 * public void drawPosText (String text, float[] pos, Paint paint)
 * public void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)
 * <p/>
 * // 第三类
 * public void drawTextOnPath (String text, Path path, float hOffset, float vOffset, Paint paint)
 * public void drawTextOnPath (char[] text, int index, int count, Path path, float hOffset, float vOffset, Paint paint)
 */
public class CanvasTextView extends View {
    private Paint textPaint;

    public CanvasTextView(Context context) {
        this(context, null);
    }

    public CanvasTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CanvasTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CanvasTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Paint文本相关常用方法表
     * <p/>
     * 标题	相关方法	备注
     * 色彩	setColor setARGB setAlpha	设置颜色，透明度
     * 大小	setTextSize	设置文本字体大小
     * 字体	setTypeface	设置或清除字体样式
     * 样式	setStyle	填充(FILL),描边(STROKE),填充加描边(FILL_AND_STROKE)
     * 对齐	setTextAlign	左对齐(LEFT),居中对齐(CENTER),右对齐(RIGHT)
     * 测量	measureText	测量文本大小(注意，请在设置完文本各项参数后调用)
     */
    public void initPaint() {
        textPaint = new Paint();          // 创建画笔
        textPaint.setColor(Color.BLACK);        // 设置颜色
        textPaint.setStyle(Paint.Style.FILL);   // 设置样式
        textPaint.setTextSize(50);              // 设置字体大小
    }

    /**
     * 第一类(drawText)
     * <p/>
     * 第一类可以指定文本开始的位置，可以截取文本中部分内容进行绘制。
     * 其中x，y两个参数是指定文本绘制两个基线
     *
     * @param canvas
     */
    public void drawTextPoint(Canvas canvas) {
        // 文本(要绘制的内容)
        String str = "ABCDEFG";
        // 参数分别为 (文本 基线x 基线y 画笔)
        canvas.drawText(str, 200, 500, textPaint);
        // 参数分别为 (字符串 开始截取位置 结束截取位置 基线x 基线y 画笔) [1,3)
        canvas.drawText(str, 1, 3, 200, 500, textPaint);

        // 字符数组(要绘制的内容)
        char[] chars = "ABCDEFG".toCharArray();
        str.toCharArray();
        // 参数为 (字符数组 起始坐标 截取长度 基线x 基线y 画笔)  BCD
        canvas.drawText(chars, 1, 3, 200, 500, textPaint);
    }

    /**
     * 第二类(drawPosText)
     * 通过和第一类比较，我们可以发现，第二类中没有指定x，y坐标的参数，而是出现了这样一个参数float[] pos。
     * <p/>
     * 好吧，这个名为pos的浮点型数组就是指定坐标的，至于为啥要用数组嘛，因为这家伙野心比较大，想给每个字符都指定一个位置。
     * <p/>
     * 虽然虽然这个方法也比较容易理解，但是关于这个方法我个人是不推荐使用的，因为坑比较多，主要有一下几点：
     * <p/>
     * 序号	反对理由
     * 1	必须指定所有字符位置，否则直接crash掉，反人类设计
     * 2	性能不佳，在大量使用的时候可能导致卡顿
     * 3	不支持emoji等特殊字符，不支持字形组合与分解
     *
     * @param canvas
     */
    public void drawTextPos(Canvas canvas) {
        String str = "SLOOP";
        canvas.drawPosText(str, new float[]{100, 100,    // 第一个字符位置
                200, 200,    // 第二个字符位置
                300, 300,    // ...
                400, 400,
                500, 500}, textPaint);
    }

    /**
     * 第三类
     *
     * @param canvas
     */
    public void drawTextOnPath(Canvas canvas) {

    }
}
