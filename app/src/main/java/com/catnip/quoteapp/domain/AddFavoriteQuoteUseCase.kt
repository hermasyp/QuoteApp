package com.catnip.quoteapp.domain

import com.catnip.quoteapp.base.arch.BaseUseCase
import com.catnip.quoteapp.base.wrapper.DataResource
import com.catnip.quoteapp.base.wrapper.ViewResource
import com.catnip.quoteapp.data.repository.QuoteRepository
import com.catnip.quoteapp.ui.viewparam.Quote
import com.catnip.quoteapp.ui.viewparam.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class AddFavoriteQuoteUseCase(
    val repository: QuoteRepository,
    val dispatcher: CoroutineDispatcher
) : BaseUseCase<Quote, Quote?>(dispatcher) {

    override suspend fun execute(param: Quote?): Flow<ViewResource<Quote?>> {
        return repository.addFavoriteQuote(param.toEntity()).map {
            when (it) {
                is DataResource.Success -> {
                    ViewResource.Success(param?.apply { isFavorite = true })
                }

                is DataResource.Error -> {
                    ViewResource.Error(it.exception)
                }
            }
        }.onStart { emit(ViewResource.Loading()) }

    }
}