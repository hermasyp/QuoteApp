package com.catnip.quoteapp.domain

import com.catnip.quoteapp.base.arch.BaseUseCase
import com.catnip.quoteapp.base.wrapper.DataResource
import com.catnip.quoteapp.base.wrapper.ViewResource
import com.catnip.quoteapp.data.repository.QuoteRepository
import com.catnip.quoteapp.ui.viewparam.Quote
import com.catnip.quoteapp.ui.viewparam.mapToViewParam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class GetRandomQuoteUseCase(
    val repository: QuoteRepository,
    val dispatcher: CoroutineDispatcher
) : BaseUseCase<Any, Quote>(dispatcher) {

    override suspend fun execute(param: Any?): Flow<ViewResource<Quote>> {
        return repository.getRandomQuote(dispatcher).map { resultNetwork ->
            when (resultNetwork) {
                is DataResource.Success -> {
                    repository.getFavoriteQuotesById(dispatcher, resultNetwork.data?.id)
                        .map { favResult ->
                            when (favResult) {
                                is DataResource.Success -> {
                                    if (favResult.data != null) {
                                        ViewResource.Success(resultNetwork.data.mapToViewParam().apply { isFavorite = true })
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