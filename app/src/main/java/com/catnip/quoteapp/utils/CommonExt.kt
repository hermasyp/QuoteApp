package com.catnip.quoteapp.utils

import android.content.Context

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/


fun Int.dpToPixels(context: Context?): Int {
    if (context != null) {
        val scale = context.resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
    return 0
}