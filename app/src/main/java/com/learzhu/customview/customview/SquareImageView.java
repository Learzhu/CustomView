package com.learzhu.customview.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/7/12 11:25
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  11:25
 * @updateDes ${TODO}
 */
public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
