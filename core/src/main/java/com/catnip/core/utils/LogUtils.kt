package com.catnip.core.utils

import android.util.Log
import com.catnip.core.BuildConfig

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object LogUtils {
    private val LOG_PREFIX = "${BuildConfig.APPLICATION_ID}:"
    private val LOG_PREFIX_LENGTH = LOG_PREFIX.length
    private const val MAX_LOG_TAG_LENGTH = 23

    private fun tagLogger(str: String): String {
        return if (str.length > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            // Returns end of class name
            LOG_PREFIX + str.substring(MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH)
        } else LOG_PREFIX + str
    }

    fun LogD(message: String?) {
        val className = Throwable().stackTrace[1].className
        val tag = tagLogger(className)
        if (message != null) {
            Log.d(tag, message)
        }
    }

    fun LogE(message: String?) {
        val className = Throwable().stackTrace[1].className
        val tag = tagLogger(className)
        if (message != null) {
            Log.d(tag, message)
        }
    }
}
