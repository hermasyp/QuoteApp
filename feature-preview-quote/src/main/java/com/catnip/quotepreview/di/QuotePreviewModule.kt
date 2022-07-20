package com.catnip.quotepreview.di

import com.catnip.quotepreview.domain.AddFavoriteQuoteUseCase
import com.catnip.quotepreview.domain.GetRandomQuoteUseCase
import com.catnip.quotepreview.repository.PreviewQuoteRepository
import com.catnip.quotepreview.repository.PreviewQuoteRepositoryImpl
import com.catnip.quotepreview.ui.QuotePreviewViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object QuotePreviewModule {
    fun getModules() = listOf(repository, useCases, viewModels)

    private val repository = module {
        single<PreviewQuoteRepository> { PreviewQuoteRepositoryImpl(get(), get()) }
    }
    private val useCases = module {
        single { GetRandomQuoteUseCase(get(), dispatcher = Dispatchers.IO) }
        single { AddFavoriteQuoteUseCase(get(), dispatcher = Dispatchers.IO) }
    }
    private val viewModels = module {
        viewModelOf(::QuotePreviewViewModel)
    }
}