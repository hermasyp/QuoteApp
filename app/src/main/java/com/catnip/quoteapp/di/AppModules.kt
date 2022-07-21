package com.catnip.quoteapp.di

import com.catnip.core.navigator.ActivityNavigatorContract
import com.catnip.quoteapp.navigator.ActivityNavigator
import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object AppModules {
    fun getModules() = listOf(navigator)

    private val navigator = module {
        single<ActivityNavigatorContract> { ActivityNavigator() }
    }

}