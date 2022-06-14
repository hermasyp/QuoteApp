package com.catnip.quoteapp.base.arch

import com.catnip.quoteapp.base.wrapper.ViewResource
import java.lang.Exception

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface BaseContract {
    interface BaseView {
        fun observeData()
        fun showContent(isContentVisible: Boolean)
        fun showEmptyData(isEmpty: Boolean)
        fun showLoading(isShowLoading: Boolean)
        fun showError(isErrorEnabled: Boolean, exception: Exception? = null)
        fun handleData(viewResource: ViewResource<*>?)
        fun <T> showData(data: T)
    }

    interface BaseRepository {
        fun logResponse(msg: String?)
    }
}