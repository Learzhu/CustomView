package com.learzhu.customview.customview.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.PictureDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.learzhu.customview.customview.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Learzhu
 * @version $Rev$
 * @time 2016/9/11 16:02
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$  16:02
 * @updateDes ${TODO}
 * 图文相关的View
 * <p>
 * 1.绘制图片
 * 绘制有两种方法，drawPicture(矢量图) 和 drawBitmap(位图)
 */
public class CanvasPictureView extends View {

    private Context mContext;
    /*1.创建Picture*/
    private Picture mPicture = new Picture();

    private int mWidth, mHeight;

    public CanvasPictureView(Context context) {
        super(context);
        System.out.println("11111111111CanvasPictureView(Context context, AttributeSet attrs)");
    }

    /**
     * 默认使用的是该构造方法
     *
     * @param context
     * @param attrs
     */
    public CanvasPictureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        System.out.println("2222222222222CanvasPictureView(Context context, AttributeSet attrs)");
        this.mContext = context;
        initData();
        /*调用录制*/
        recording();
    }

    public CanvasPictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        System.out.println("33333333CanvasPictureView(Context context, AttributeSet attrs, int defStyleAttr)");
    }

    public CanvasPictureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        System.out.println("44444444CanvasPictureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvasPicture(canvas, 2);
        canvasBitmap(canvas, 1, getBitmapByBitmapFactory(1));
    }

    /**
     * 获取屏幕宽高
     */
    public void initData() {
        //获取屏幕宽高
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mWidth = windowManager.getDefaultDisplay().getWidth();
        mHeight = windowManager.getDefaultDisplay().getHeight();
    }

    /**
     * 2.录制内容方法
     * beginRecording 和 endRecording 是成对使用的，一个开始录制，一个是结束录制，两者之间的操作将会存储在Picture中
     * 录制内容，即将一些Canvas操作用Picture存储起来，录制的内容是不会直接显示在屏幕上的，只是存储起来了而已
     */
    private void recording() {
        // 开始录制 (接收返回值Canvas)
        Canvas canvas = mPicture.beginRecording(500, 500);
        // 创建一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        // 在Canvas中具体操作
        // 位移
        canvas.translate(250, 250);
        // 绘制一个圆
        canvas.drawCircle(0, 0, 100, paint);
        mPicture.endRecording();

//        /*绘画失败  因为这里的绘画对象不对*/
//        mPicture.draw(canvas);
    }

    /**
     * (1)drawPicture
     * <p>
     * 使用Picture前请关闭硬件加速，以免引起不必要的问题！
     * 关闭View级别的硬件加速，但是你不能在View级别开启硬件加速，因为它还依赖其他的设置
     * 在AndroidMenifest文件中application节点下添上 android:hardwareAccelerated=”false”以关闭整个应用的硬件加速。
     * <p>
     * <p>
     * Picture和上文中的录像功能是类似的，只不过我们Picture录的是Canvas中绘制的内容
     * <p>
     * Picture 一张照片记录绘制调用（通过Canvas返回beginrecording），
     * 就可以玩到画布（通过画（油画）或drawpicture（图片））。大部分的内容（如文本、线条、矩形），
     * 从画序列可以比等效的API调用更快，因为图片执行播放不用任何方法调用的开销。
     * <p>
     * <p>
     * 将Picture中的内容绘制出来可以有以下几种方法：
     * <p>
     * 序号	简介
     * 1	使用Picture提供的draw方法绘制。
     * 2	使用Canvas提供的drawPicture方法绘制。
     * 3	将Picture包装成为PictureDrawable，使用PictureDrawable的draw方法绘制。
     * <p>
     * 主要区别	分类	简介
     * 是否对Canvas有影响	1有影响
     * 2,3不影响	此处指绘制完成后是否会影响Canvas的状态(Matrix clip等)
     * 可操作性强弱	1可操作性较弱
     * 2,3可操作性较强	此处的可操作性可以简单理解为对绘制结果可控程度。
     *
     * @param canvas
     */
    public void canvasPicture(Canvas canvas, int type) {
        switch (type) {
            case 1:
                // 1.将Picture中的内容绘制在Canvas上 这种方法在比较低版本的系统上绘制后可能会影响Canvas状态，所以这种方法一般不会使用。
                mPicture.draw(canvas);
                break;
            case 2:
                /*2.使用Canvas提供的drawPicture方法绘制*/
                /**
                 * drawPicture有三种方法：

                 public void drawPicture (Picture picture)

                 public void drawPicture (Picture picture, Rect dst)

                 public void drawPicture (Picture picture, RectF dst)
                 和使用Picture的draw方法不同，Canvas的drawPicture不会影响Canvas状态。
                 */
//                canvas.drawPicture(mPicture);
                /*缩放了*/
                canvas.drawPicture(mPicture, new RectF(0, 0, mPicture.getWidth(), 200));
                break;
            case 3:
                /*3.将Picture包装成为PictureDrawable，使用PictureDrawable的draw方法绘制。
                * 此处setBounds是设置在画布上的绘制区域，并非根据该区域进行缩放，也不是剪裁Picture，每次都从Picture的左上角开始绘制。*/
                /*包装成为Drawable*/
                PictureDrawable drawable = new PictureDrawable(mPicture);
                /*设置绘制区域 -- 注意此处所绘制的实际内容不会缩放*/
                drawable.setBounds(0, 0, 250, mPicture.getHeight());
                drawable.draw(canvas);
                break;
        }
    }

    /**
     * Bitmap就是很多问题的根源啊有木有，Bitmap可能导致内存不足，内存泄露，ListView中的复用混乱等诸多问题。
     * <p>
     * 获取Bitmap方式:
     * <p>
     * 序号	获取方式	备注
     * 1	通过Bitmap创建	复制一个已有的Bitmap(新Bitmap状态和原有的一致) 或者 创建一个空白的Bitmap(内容可改变)
     * 2	通过BitmapDrawable获取	从资源文件 内存卡 网络等地方获取一张图片并转换为内容不可变的Bitmap   [不推荐使用这种方式]
     * 3	通过BitmapFactory获取	从资源文件 内存卡 网络等地方获取一张图片并转换为内容不可变的Bitmap
     * <p>
     * <p>
     * *****通常来说，我们绘制Bitmap都是读取已有的图片转换为Bitmap绘制到Canvas上。
     * <p>
     * // 第一种
     * public void drawBitmap (Bitmap bitmap, Matrix matrix, Paint paint)
     * <p>
     * // 第二种
     * public void drawBitmap (Bitmap bitmap, float left, float top, Paint paint)
     * <p>
     * // 第三种
     * public void drawBitmap (Bitmap bitmap, Rect src, Rect dst, Paint paint)
     * public void drawBitmap (Bitmap bitmap, Rect src, RectF dst, Paint paint)
     *
     * @param canvas
     */
    public void canvasBitmap(Canvas canvas, int type, Bitmap bitmap) {
        switch (type) {
            case 1:
                 /*(matrix, paint)是在绘制的时候对图片进行一些改变*/
                canvas.drawBitmap(bitmap, new Matrix(), new Paint());
                break;
            case 2:
                /*此处指定的是与坐标原点的距离，并非是与屏幕顶部和左侧的距离, 虽然默认状态下两者是重合的，但是也请注意分别两者的不同*/
                canvas.drawBitmap(getBitmapByBitmapFactory(1), 200, 500, new Paint());
                break;
            case 3:
                /**
                 * Rect src	指定绘制图片的区域
                 *Rect dst 或RectF dst	指定图片在屏幕上显示(绘制)的区域  图片宽高会根据指定的区域自动进行缩放
                 *
                 *
                 * 用一张图片包含了大量的素材，在绘制的时候每次只截取一部分进行绘制，这样可以大大的减少素材数量，而且素材管理起来也很方便
                 * 把同一个动画效果的所有资源图片整理到一张图片上，会大大的减少资源文件数量，方便管理，
                 * 妈妈再也不怕我找不到资源文件了，同时也节省了图片文件头、文件结束块以及调色板等占用的空间。
                 */
                // 将画布坐标系移动到画布中央
                canvas.translate(mWidth / 2, mHeight / 2);
                // 指定图片绘制区域(左上角的四分之一)
                Rect src = new Rect(0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                // 指定图片在屏幕上显示的区域
                Rect dst = new Rect(0, 0, 200, 400);
                // 绘制图片
                canvas.drawBitmap(bitmap, src, dst, null);
                break;
        }
    }

    /**
     * @param type
     * @return
     */
    public Bitmap getBitmapByBitmapFactory(int type) {
        Bitmap bitmap = null;
        switch (type) {
            case 1:
                //资源文件(drawable/mipmap/raw)
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
                break;
            case 2:
                /*资源文件(assets):*/
                try {
                    InputStream inputStream = mContext.getAssets().open("ic_launcher.png");
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                /*内存卡文件:*/
                bitmap = BitmapFactory.decodeFile("/sdcard/bitmap.png");
                break;
            case 4:
                /*网络文件:*/
                // 此处省略了获取网络输入流的代码
//                Bitmap bitmap = BitmapFactory.decodeStream(is);
//                is.close();
                break;
        }
        return bitmap;
    }


}
