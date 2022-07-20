package com.catnip.quoteapp.domain

import com.catnip.core.base.arch.BaseUseCase
import com.catnip.core.common.viewparam.Quote
import com.catnip.core.common.viewparam.mapToViewParams
import com.catnip.core.base.wrapper.DataResource
import com.catnip.core.base.wrapper.ViewResource
import com.catnip.quoteapp.data.repository.QuoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class GetFavoriteQuotesUseCase(
    val repository: QuoteRepository,
    val dispatcher: CoroutineDispatcher
) : BaseUseCase<Any, MutableList<Quote>>(dispatcher) {

    override suspend fun execute(param: Any?): Flow<ViewResource<MutableList<Quote>>> {
        return repository.getFavoriteQuotes().map {
            when (it) {
                is DataResource.Success -> {
                    if (it.data?.isEmpty() == true) {
                        ViewResource.Empty()
                    } else {
                        ViewResource.Success(it.data.mapToViewParams())
                    }
                }
                is DataResource.Error -> {
                    ViewResource.Error(it.exception)
                }
            }
        }.onStart { emit(ViewResource.Loading()) }
    }
}