package com.catnip.core.navigator.activity

import android.app.Activity
import com.catnip.core.navigator.BaseNavigator
import com.catnip.core.navigator.NoArgs
import com.catnip.core.navigator.NoResult

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class QuotePreviewNavigator(activityClass: Class<out Activity>) :
    BaseNavigator<NoArgs, NoResult>(activityClass)