package com.learzhu.customview.customview.color;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learzhu.customview.customview.R;

public class ColorActivity extends AppCompatActivity {

    int gray = Color.GRAY;
    /*半透明红色*/
    int argb = Color.argb(127, 255, 0, 0);
    int red = 0xaaff0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
    }
}
