package com.learzhu.customview.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SketchView sketchView = (SketchView) findViewById(R.id.sketch_view);
//        sketchView.startAnimation();
        Intent intent = new Intent(this, DragLayoutActivity.class);
        startActivity(intent);
    }
}
