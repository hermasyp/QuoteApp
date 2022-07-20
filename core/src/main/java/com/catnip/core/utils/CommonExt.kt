package com.catnip.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import androidx.core.content.FileProvider
import com.catnip.core.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
fun <T> T.isNotNull(): Boolean {
    return this != null
}

fun <T> T.isNull(): Boolean {
    return this == null
}

fun Int.dpToPixels(context: Context?): Int {
    if (context != null) {
        val scale = context.resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
    return 0
}

fun View.toImageBitmap(): Bitmap? {
    val returnedBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(returnedBitmap)
    val bgDrawable: Drawable? = this.background
    if (bgDrawable.isNotNull()) {
        bgDrawable?.draw(canvas)
    } else {
        canvas.drawColor(Color.WHITE)
    }
    this.draw(canvas)
    return returnedBitmap
}

fun Bitmap?.bitmapToCacheUri(context: Context?): Uri? {
    var bmpUri: Uri? = null
    try {
        val file = File(context?.externalCacheDir, System.currentTimeMillis().toString() + ".jpg")
        val out = FileOutputStream(file)
        this?.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.close()
        bmpUri = context?.let { file.toUriPath(it) }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bmpUri
}

fun File?.toUriPath(context: Context): Uri? {
    return this?.let {
        FileProvider.getUriForFile(
            context, BuildConfig.APPLICATION_ID, it
        )
    }
}