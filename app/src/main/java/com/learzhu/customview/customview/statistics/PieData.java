package com.learzhu.customview.customview.statistics;

import android.support.annotation.NonNull;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/10 17:37
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  17:37
 * @updateDes ${TODO}
 * <p/>
 * 先分析饼状图的构成，非常明显，饼状图就是一个又一个的扇形构成的，每个扇形都有不同的颜色，对应的有名字，数据和百分比。
 */
public class PieData {
    // 用户关心数据
    private String name;        // 名字
    private float value;        // 数值
    private float percentage;   // 百分比

    // 非用户关心数据
    private int color = 0;      // 颜色
    private float angle = 0;    // 角度

    public PieData(@NonNull String name, @NonNull float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
