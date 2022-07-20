package com.catnip.quoteapp.di

import com.catnip.quoteapp.data.repository.QuoteRepository
import com.catnip.quoteapp.data.repository.QuoteRepositoryImpl
import com.catnip.quoteapp.domain.AddFavoriteQuoteUseCase
import com.catnip.quoteapp.domain.DeleteFavoriteQuoteUseCase
import com.catnip.quoteapp.domain.GetFavoriteQuotesUseCase
import com.catnip.quoteapp.domain.GetRandomQuoteUseCase
import com.catnip.quoteapp.ui.feature.favoritedquote.FavoriteQuotesViewModel
import com.catnip.quoteapp.ui.feature.quotepreview.QuotePreviewViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object AppModules {
    fun getModules() = listOf(repository, useCases, viewModels)
    private val repository = module {
        single<QuoteRepository> { QuoteRepositoryImpl(get(), get()) }
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