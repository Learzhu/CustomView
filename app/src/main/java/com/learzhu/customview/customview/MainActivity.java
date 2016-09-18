package com.learzhu.customview.customview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.learzhu.customview.customview.canvas.CanvasActivity;
import com.learzhu.customview.customview.matrix.ZoomImageViewActivity;
import com.learzhu.customview.customview.statistics.StatisticsActivity;
import com.learzhu.customview.customview.svg.SVGActivity;
import com.learzhu.customview.customview.view.SketchView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button toDrawListViewBtn, toStatisticsBtn, toCanvasBtn, toBezierBtn, toSvgBtn, toZoomImageViewBtn;
    private TextView toDragLayoutActivityTv;
    private Intent intent;
    private SketchView sketchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    public void initViews() {
        /*饼状图的View界面*/
        toStatisticsBtn = (Button) findViewById(R.id.statistics);
        toStatisticsBtn.setOnClickListener(this);
        /*侧滑栏的界面*/
        toDragLayoutActivityTv = (TextView) findViewById(R.id.draglayout);
        toDragLayoutActivityTv.setOnClickListener(this);
        /*自定义View列表的界面*/
        toDrawListViewBtn = (Button) findViewById(R.id.to_drawview_list);
        toDrawListViewBtn.setOnClickListener(this);
        /*CanVas详解*/
        toCanvasBtn = (Button) findViewById(R.id.to_canvas);
        toCanvasBtn.setOnClickListener(this);
        /*贝塞尔曲线*/
        toBezierBtn = (Button) findViewById(R.id.to_bezier);
        toBezierBtn.setOnClickListener(this);
        /*到SVG界面*/
        toSvgBtn = (Button) findViewById(R.id.to_svg);
        toSvgBtn.setOnClickListener(this);
        /*到ZoomImageView界面*/
        toZoomImageViewBtn = (Button) findViewById(R.id.to_zoomimageview);
        toZoomImageViewBtn.setOnClickListener(this);
        /*心跳闪动的View*/
        sketchView = (SketchView) findViewById(R.id.sketch_view);
        sketchView.startAnimation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_drawview_list:
                intent = new Intent(this, DrawViewListActivity.class);
                break;
            case R.id.draglayout:
                intent = new Intent(this, DragLayoutActivity.class);
                break;
            case R.id.statistics:
                intent = new Intent(this, StatisticsActivity.class);
                break;
            case R.id.to_canvas:
                intent = new Intent(this, CanvasActivity.class);
                break;
            case R.id.to_bezier:
                intent = new Intent(this, BezierActivity.class);
                break;
            case R.id.to_svg:
                intent = new Intent(this, SVGActivity.class);
                break;
            case R.id.to_zoomimageview:
                intent = new Intent(this, ZoomImageViewActivity.class);
                break;
        }
        startActivity(intent);
    }

//    private void testSVG() {
//        // Read an SVG from the assets folder
//        SVG mSVG = null;
//        try {
//            mSVG = SVG.getFromAsset(getAssets(), "AAAA");
//            // Create a canvas to draw onto
//            if (mSVG.getDocumentWidth() != -1) {
//                Bitmap newBM = Bitmap.createBitmap(Math.ceil(mSVG.getDocumentWidth()),
//                        Math.ceil(mSVG.getDocumentHeight()),
//                        Bitmap.Config.ARGB_8888)
//                Canvas bmcanvas = new Canvas(newBM);
//
//                // Clear background to white
//                bmcanvas.drawRGB(255, 255, 255);
//
//                // Render our document onto our canvas
//                mSVG.renderToCanvas(bmcanvas);
//            }
//        } catch (SVGParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
