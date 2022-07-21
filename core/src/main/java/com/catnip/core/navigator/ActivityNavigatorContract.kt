package com.catnip.core.navigator

import com.catnip.core.navigator.activity.FavoriteQuotesNavigator
import com.catnip.core.navigator.activity.QuotePreviewNavigator

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface ActivityNavigatorContract {
    fun favoriteQuotesNavigator(): FavoriteQuotesNavigator
    fun quotePreviewNavigator(): QuotePreviewNavigator
}