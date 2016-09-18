package com.learzhu.customview.customview.matrix;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.learzhu.customview.customview.R;

public class ZoomImageViewActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private int[] mImages = new int[]{R.drawable.ic_launcher, R.drawable.arrow, R.drawable.back};
    private ImageView[] mImageViews = new ImageView[mImages.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image_view);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ZoomImageView mZoomImageView = new ZoomImageView(getApplicationContext());
                mZoomImageView.setImageResource(mImages[position]);
                container.addView(mZoomImageView);
                mImageViews[position] = mZoomImageView;
                return mZoomImageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViews[position]);
            }

            @Override
            public int getCount() {
                return mImageViews.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
    }
}
