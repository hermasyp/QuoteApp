package com.catnip.core.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/


fun GradientDrawable.createRoundedBackground(context : Context ,colorHex : String, cornerRadius : Int = 0) : GradientDrawable {
    return apply {
        shape = GradientDrawable.RECTANGLE
        setCornerRadius(cornerRadius.dpToPixels(context).toFloat())
        setColor(Color.parseColor(colorHex))
        mutate()
    }
}
fun GradientDrawable.createCircleBackground(context : Context ,colorHex : String) : GradientDrawable {
    return apply {
        shape = GradientDrawable.OVAL
        setColor(Color.parseColor(colorHex))
        mutate()
    }
}