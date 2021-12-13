package com.fyp.sahayogapp.custom

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import android.graphics.Canvas
import android.content.res.TypedArray
import com.fyp.sahayogapp.R
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat

class CustomTextView : AppCompatTextView {
    var customFont: String? = null
    var cf = 0

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        style(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        style(context, attrs)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    private fun style(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextView, 0, 0
        )
        cf = try {
            a.getInt(R.styleable.CustomTextView_fontName, 0)
        } finally {
            a.recycle()
        }
        var typeface: Typeface? = null
        val fontName = 0
        typeface = when (cf) {
            1 -> ResourcesCompat.getFont(context, R.font.lato_bold)
            2 -> ResourcesCompat.getFont(context, R.font.lato_light)
            4 -> ResourcesCompat.getFont(context, R.font.lato_thin)
            0 -> ResourcesCompat.getFont(context, R.font.lato_regular)
            else -> ResourcesCompat.getFont(context, R.font.lato_regular)
        }
        setTypeface(typeface)
    }
}