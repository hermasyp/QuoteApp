package com.catnip.favoritelist.di

import com.catnip.favoritelist.domain.GetFavoriteQuotesUseCase
import com.catnip.favoritelist.repository.FavoriteListRepository
import com.catnip.favoritelist.repository.FavoriteListRepositoryImpl
import com.catnip.favoritelist.ui.FavoriteQuotesViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object FavoriteQuotesModule {
    fun getModules() = listOf(repository, useCases, viewModels)

    private val repository = module {
        single<FavoriteListRepository> { FavoriteListRepositoryImpl(get()) }
    }
    private val useCases = module {
        single { GetFavoriteQuotesUseCase(get(), dispatcher = Dispatchers.IO) }
    }
    private val viewModels = module {
        viewModelOf(::FavoriteQuotesViewModel)
    }
}