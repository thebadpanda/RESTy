package com.example.arsenko.resty;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

public class MyCustomView extends View {
    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyCustomView);
        boolean showText = typedArray.getBoolean(R.styleable.MyCustomView_showText, false);


        typedArray.recycle();


    }
}
