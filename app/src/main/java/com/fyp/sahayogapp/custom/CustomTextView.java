package com.fyp.sahayogapp.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.fyp.sahayogapp.R;

import java.lang.reflect.Type;


public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {
    String customFont;
    int cf;

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        style(context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        style(context, attrs);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

    private void style(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomTextView,0,0);
        try {
            cf = a.getInt(R.styleable.CustomTextView_fontName, 0);
        } finally {
            a.recycle();
        }
        Typeface typeface = null;
        int fontName = 0;
        switch (cf) {
            case 1:
               typeface = ResourcesCompat.getFont(context, R.font.lato_bold);

                break;
            case 2:
                typeface = ResourcesCompat.getFont(context, R.font.lato_light);
                break;

            case 4:
                typeface = ResourcesCompat.getFont(context, R.font.lato_thin);
                break;
            case 0 :
            default:
                typeface = ResourcesCompat.getFont(context, R.font.lato_regular);
                break;
        }


        setTypeface(typeface);

    }
}
