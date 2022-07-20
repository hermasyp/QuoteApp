package com.catnip.quoteapp.domain

import com.catnip.core.base.arch.BaseUseCase
import com.catnip.core.base.wrapper.DataResource
import com.catnip.core.base.wrapper.ViewResource
import com.catnip.core.common.viewparam.Quote
import com.catnip.core.common.viewparam.mapToViewParam
import com.catnip.quoteapp.data.repository.QuoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class GetRandomQuoteUseCase(
    val repository: QuoteRepository,
    val dispatcher: CoroutineDispatcher
) : BaseUseCase<Any, Quote>(dispatcher) {

    override suspend fun execute(param: Any?): Flow<ViewResource<Quote>> {
        return repository.getRandomQuote().map { resultNetwork ->
            when (resultNetwork) {
                is DataResource.Success -> {
                    repository.getFavoriteQuotesById(resultNetwork.data?.id)
                        .map { favResult ->
                            when (favResult) {
                                is DataResource.Success -> {
                                    if (favResult.data != null) {
                                        ViewResource.Success(
                                            resultNetwork.data.mapToViewParam()
                                                .apply { isFavorite = true })
                                    } else {
                                        ViewResource.Success(resultNetwork.data.mapToViewParam())
                                    }
                                }
                                else -> {
                                    ViewResource.Error(favResult.exception)
                                }
                            }
                        }.last()
                }
                is DataResource.Error -> {
                    ViewResource.Error(resultNetwork.exception)
                }
            }
        }.onStart { emit(ViewResource.Loading()) }
    }
}