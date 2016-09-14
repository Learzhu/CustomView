package com.learzhu.customview.customview.vector;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.learzhu.customview.customview.R;

public class VectorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        //        AnimatedStateListDrawable
        findViewById(R.id.jiv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv = (ImageView) findViewById(R.id.jiv1);
                Drawable drawable = iv.getDrawable();
                if (drawable instanceof Animatable) {
                    Animatable able = (Animatable) drawable;
                    able.start();
                }
            }
        });
    }
}
