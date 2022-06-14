package com.catnip.quoteapp.di

import com.catnip.quoteapp.data.local.QuoteDatabase
import com.catnip.quoteapp.data.local.datasource.QuoteLocalDataSource
import com.catnip.quoteapp.data.local.datasource.QuoteLocalDataSourceImpl
import com.catnip.quoteapp.data.network.QuoteApiService
import com.catnip.quoteapp.data.network.datasource.QuoteNetworkDataSource
import com.catnip.quoteapp.data.network.datasource.QuoteNetworkDataSourceImpl
import com.catnip.quoteapp.data.repository.QuoteRepository
import com.catnip.quoteapp.data.repository.QuoteRepositoryImpl
import com.catnip.quoteapp.domain.AddFavoriteQuoteUseCase
import com.catnip.quoteapp.domain.DeleteFavoriteQuoteUseCase
import com.catnip.quoteapp.domain.GetFavoriteQuotesUseCase
import com.catnip.quoteapp.domain.GetRandomQuoteUseCase
import com.catnip.quoteapp.ui.feature.quotepreview.QuotePreviewViewModel
import com.catnip.quoteapp.ui.feature.favoritedquote.FavoriteQuotesViewModel
import com.chuckerteam.chucker.api.ChuckerInterceptor
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object InjectionModules {
    fun getModules() = listOf(network, dataSource, database, repository, useCases, viewModels)

    private val network = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { QuoteApiService.invoke(get()) }
    }
    private val dataSource = module {
        single<QuoteLocalDataSource> { QuoteLocalDataSourceImpl(get()) }
        single<QuoteNetworkDataSource> { QuoteNetworkDataSourceImpl(get()) }
    }
    private val repository = module {
        single<QuoteRepository> { QuoteRepositoryImpl(get(), get()) }
    }
    private val database = module {
        single { get<QuoteDatabase>().quotesDao() }
        single { QuoteDatabase.create(androidContext()) }
    }
    private val useCases = module {
        single { GetFavoriteQuotesUseCase(get(), dispatcher = Dispatchers.IO) }
        single { GetRandomQuoteUseCase(get(), dispatcher = Dispatchers.IO) }
        single { DeleteFavoriteQuoteUseCase(get(), dispatcher = Dispatchers.IO) }
        single { AddFavoriteQuoteUseCase(get(), dispatcher = Dispatchers.IO) }
    }
    private val viewModels = module {
        viewModelOf(::QuotePreviewViewModel)
        viewModelOf(::FavoriteQuotesViewModel)
    }
}