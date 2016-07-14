package com.learzhu.customview.customview.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/7/14 16:39
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  16:39
 * @updateDes ${TODO}
 * /**
 * dp，sp转换成px的工具类
 * Created by lgl on 16/3/23.
 */

public class DisplayUtils {
    /**
     * 将px值转换成dpi或者dp值，保持尺寸不变
     *
     * @param content
     * @param pxValus
     * @return
     */
    public static int px2dip(Context content, float pxValus) {
        final float scale = content.getResources().getDisplayMetrics().density;
        return (int) (pxValus / scale + 0.5f);
    }

    /**
     * 将dip和dp转化成px,保证尺寸大小不变。
     *
     * @param content
     * @param pxValus
     * @return
     */
    public static int dip2px(Context content, float pxValus) {
        final float scale = content.getResources().getDisplayMetrics().density;
        return (int) (pxValus / scale + 0.5f);
    }

    /**
     * 将px转化成sp,保证文字大小不变。
     *
     * @param content
     * @param pxValus
     * @return
     */
    public static int px2sp(Context content, float pxValus) {
        final float fontScale = content.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValus / fontScale + 0.5f);
    }

    /**
     * 将sp转化成px,保证文字大小不变。
     *
     * @param content
     * @param pxValus
     * @return
     */
    public static int sp2px(Context content, float pxValus) {
        final float fontScale = content.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValus / fontScale + 0.5f);
    }

    /**
     * 其实的density就是前面所说的换算比例，这里使用的是公式换算方法进行转换，同时系统也提供了TypedValue帮助我们转换
     */
    protected int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * sp2px
     *
     * @param sp
     * @return
     */
    protected int sp2px(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}
