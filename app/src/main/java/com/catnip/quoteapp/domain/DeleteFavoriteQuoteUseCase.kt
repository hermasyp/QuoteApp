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
class DeleteFavoriteQuoteUseCase(
    val repository: QuoteRepository,
    val dispatcher: CoroutineDispatcher
) : BaseUseCase<DeleteFavoriteQuoteUseCase.Param, Pair<Quote?, Int>>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<Pair<Quote?, Int>>> {
        return repository.deleteFavoriteQuote(param?.quote.toEntity()).map {
            when (it) {
                is DataResource.Success -> {
                    ViewResource.Success(
                        Pair(
                            param?.quote?.apply { isFavorite = false },
                            param?.position ?: -1
                        )
                    )
                }
                is DataResource.Error -> {
                    ViewResource.Error(it.exception)
                }
            }
        }.onStart { emit(ViewResource.Loading()) }
    }

    data class Param(val quote: Quote?, val position: Int? = -1)
}