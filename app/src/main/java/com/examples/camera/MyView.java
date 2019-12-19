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
    private Pos position;

    public Pos getPos(){
        return position;
    }


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, int width, int height) {
        this(context);
        this.position = new Pos();
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
        this.position.startX = (width- centerWidth)/2;
        this.position.startY = (height - centerHeight)/2;
        this.position.width = centerWidth;
        this.position.height = centerHeight;
        this.position.endX = this.position.startX + centerWidth;
        this.position.endY = this.position.startY + centerHeight;

        /*canvas.drawRect(0, 0, width, sideHeight, mPaint);
        canvas.drawRect(0, sideHeight, sideWidth, sideHeight+centerHeight, mPaint);
        canvas.drawRect(sideWidth + centerWidth, sideHeight, width, sideHeight+centerHeight, mPaint);
        canvas.drawRect(0, sideHeight+centerHeight, width, height, mPaint);*/

        mPaint.setColor(getContext().getResources().getColor(android.R.color.holo_red_dark));
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        // left top 确定绘图起点  right bottom确定绘图终点
        canvas.drawRect(this.position.startX, this.position.startY, this.position.endX, this.position.endY, mPaint);
        //mPaint.setColor(0x34456678);
    }

    class Pos{
        int startX;
        int startY;
        int endX;
        int endY;
        int width;
        int height;

        public int getStartX() {
            return startX;
        }

        public int getStartY() {
            return startY;
        }

        public int getEndX() {
            return endX;
        }

        public int getEndY() {
            return endY;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

    }
}
