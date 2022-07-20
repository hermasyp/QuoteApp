package com.catnip.core.di

import com.catnip.core.data.local.QuoteDatabase
import com.catnip.core.data.local.datasource.QuoteLocalDataSource
import com.catnip.core.data.local.datasource.QuoteLocalDataSourceImpl
import com.catnip.core.data.network.QuoteApiService
import com.catnip.core.data.network.datasource.QuoteNetworkDataSource
import com.catnip.core.data.network.datasource.QuoteNetworkDataSourceImpl
import com.chuckerteam.chucker.api.ChuckerInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

object CoreModule {
    fun getModules() = listOf(network, dataSource, database)

    private val network = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { QuoteApiService.invoke(get()) }
    }
    private val dataSource = module {
        single<QuoteLocalDataSource> { QuoteLocalDataSourceImpl(get()) }
        single<QuoteNetworkDataSource> { QuoteNetworkDataSourceImpl(get()) }
    }
    private val database = module {
        single { get<QuoteDatabase>().quotesDao() }
        single { QuoteDatabase.create(androidContext()) }
    }
}