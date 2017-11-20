package com.example.arsenko.resty;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.TextView;


public class MyCustomView extends View {

    Paint paint = new Paint();


    public MyCustomView(Context context) {
        super(context);
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyCustomView);
        int pageIndecatorCount = typedArray.getInt(R.styleable.MyCustomView_pageIndecator, 0);

        typedArray.recycle();

    }


    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredHeigth = 50;
        int desiredWidth = 50;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else if(widthMode == MeasureSpec.AT_MOST){
            width = Math.min(desiredWidth, widthSize);
        }else{
            width = desiredWidth;
        }

        if(heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }else if(heightMode == MeasureSpec.AT_MOST){
            height = Math.min(desiredHeigth, heightMode);
        }else{
            height = desiredHeigth;
        }

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        int x = getWidth() /5 ;
        int y = getHeight() /5 ;
        int radius = y;


        paint.setColor(Color.parseColor("red"));
        canvas.drawCircle(x, y, radius, paint);



//        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
//        animator.setDuration(1000);
//        animator.setInterpolator(new DecelerateInterpolator());
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int newRadius = (int) animation.getAnimatedValue();
//            }
//        });
//        animator.start();
//
//        invalidate();


    }
}
