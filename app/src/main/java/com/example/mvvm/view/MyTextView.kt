package com.example.mvvm.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.example.mvvm.R

class MyTextView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttributes: Int = 0
): AppCompatTextView(context, attributes, defStyleAttributes) {

    var htmlText: String? = null
    set(value){
        field = value
        text = value?.let { Html.fromHtml(value) }
    }

    init {
        context.theme.obtainStyledAttributes(
            attributes,
            R.styleable.MyTextView,
            defStyleAttributes,
            0
        ).also {
            htmlText = it.getString(R.styleable.MyTextView_htmlText)
        }.recycle()
        isClickable = true
    }
}