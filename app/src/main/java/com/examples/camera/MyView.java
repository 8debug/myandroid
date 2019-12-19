package com.examples.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by mengyao on 17/7/21.
 */
public class MyView extends View {

    private Paint mPaint;

    // 在宽度为width的view上绘制矩形
    private int width;
    // 在高度为height的view上绘制矩形
    private int height;
    private int centerWidth;
    private int centerHeight;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, int width, int height) {
        this(context);
        this.width = width;
        this.height = height;
        // 横屏
        if(width > height){
            this.centerWidth = (int) (width*0.5);
            this.centerHeight = (int) (height*0.5);
        }
        // 竖屏 或 正方屏
        else{
            this.centerWidth = (int) (width*0.5);
            this.centerHeight = (int) (height*0.5);
        }


        mPaint = new Paint();
        //消除锯齿
        mPaint.setAntiAlias(true);
        //防止抖动
        mPaint.setDither(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mPaint.setColor(0x8c000000);
//        mPaint.setStyle(Paint.Style.FILL);

        int sideHeight = (height - centerHeight)/2 ;
        int sideWidth = (width- centerWidth)/2;

        /*canvas.drawRect(0, 0, width, sideHeight, mPaint);
        canvas.drawRect(0, sideHeight, sideWidth, sideHeight+centerHeight, mPaint);
        canvas.drawRect(sideWidth + centerWidth, sideHeight, width, sideHeight+centerHeight, mPaint);
        canvas.drawRect(0, sideHeight+centerHeight, width, height, mPaint);*/

        mPaint.setColor(getContext().getResources().getColor(android.R.color.holo_red_dark));
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        // left top 确定绘图起点  right bottom确定绘图终点
        canvas.drawRect(0, 0, width, height, mPaint);
        //mPaint.setColor(0x34456678);


    }
}
