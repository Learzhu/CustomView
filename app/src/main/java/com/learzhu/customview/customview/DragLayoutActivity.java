package com.learzhu.customview.customview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DragLayoutActivity extends Activity {
    private DragLayout dragLayout;

    private Button btnLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_layout);
        dragLayout = (DragLayout) findViewById(R.id.drag_ll);
        dragLayout.setOnLayoutDragingListener(new DragLayout.OnLayoutDragingListener() {
            @Override
            public void onOpen() {
                btnLeft.setText("SUCCESS OPEN");
            }

            @Override
            public void onClose() {

            }

            @Override
            public void onDraging(float percent) {

            }
        });
        btnLeft = (Button) findViewById(R.id.left);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragLayout.open();
            }
        });
    }
}
