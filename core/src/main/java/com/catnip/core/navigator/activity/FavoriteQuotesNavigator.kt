package com.catnip.core.navigator.activity

import android.app.Activity
import android.os.Parcelable
import com.catnip.core.common.viewparam.Quote
import com.catnip.core.navigator.BaseNavigator
import com.catnip.core.navigator.NoArgs
import kotlinx.parcelize.Parcelize

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class FavoriteQuotesNavigator(activityClass: Class<out Activity>) :
    BaseNavigator<NoArgs, FavoriteQuotesNavigator.Result>(activityClass) {
    @Parcelize
    data class Result(val quote: Quote) : Parcelable
}