package com.catnip.quoteapp.navigator

import com.catnip.core.navigator.ActivityNavigatorContract
import com.catnip.core.navigator.activity.FavoriteQuotesNavigator
import com.catnip.core.navigator.activity.QuotePreviewNavigator
import com.catnip.favoritelist.ui.FavoriteQuotesActivity
import com.catnip.quotepreview.ui.QuotePreviewActivity

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class ActivityNavigator : ActivityNavigatorContract {
    override fun favoriteQuotesNavigator(): FavoriteQuotesNavigator =
        FavoriteQuotesNavigator(FavoriteQuotesActivity::class.java)

    override fun quotePreviewNavigator(): QuotePreviewNavigator =
        QuotePreviewNavigator(QuotePreviewActivity::class.java)
}