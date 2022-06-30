package com.catnip.quoteapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri


/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object IntentUtils {
    fun shareImage(context: Context?, uri: Uri) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpg"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        context?.startActivity(Intent.createChooser(intent, "Share Quotes"))
    }

}