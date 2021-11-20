package com.example.mvvm.view

import android.content.Context
import android.os.Build
import android.text.Html
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import com.example.mvvm.R

class MyTextView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttributes: Int = 0
): AppCompatTextView(context, attributes, defStyleAttributes) {

    private var htmlText: String? = null
    @RequiresApi(Build.VERSION_CODES.N)
    set(value){
        field = value
        text = value?.let { Html.fromHtml(value, Html.FROM_HTML_MODE_COMPACT) }
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