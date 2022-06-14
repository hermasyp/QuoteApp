package com.catnip.quoteapp.domain

import com.catnip.quoteapp.base.arch.BaseUseCase
import com.catnip.quoteapp.base.wrapper.DataResource
import com.catnip.quoteapp.base.wrapper.ViewResource
import com.catnip.quoteapp.data.repository.QuoteRepository
import com.catnip.quoteapp.ui.viewparam.Quote
import com.catnip.quoteapp.ui.viewparam.mapToViewParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class GetFavoriteQuotesUseCase(
    val repository: QuoteRepository,
    val dispatcher: CoroutineDispatcher
) : BaseUseCase<Any, List<Quote>>(dispatcher) {

    override suspend fun execute(param: Any?): Flow<ViewResource<List<Quote>>> {
        return repository.getFavoriteQuotes(dispatcher).map {
            when (it) {
                is DataResource.Success -> {
                    ViewResource.Success(it.data.mapToViewParams())
                }
                is DataResource.Loading -> {
                    ViewResource.Loading()
                }
                is DataResource.Error -> {
                    ViewResource.Error(it.exception)
                }
            }
        }
    }
}