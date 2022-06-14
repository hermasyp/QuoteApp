package com.catnip.quoteapp.domain

import com.catnip.quoteapp.base.arch.BaseUseCase
import com.catnip.quoteapp.base.wrapper.DataResource
import com.catnip.quoteapp.base.wrapper.ViewResource
import com.catnip.quoteapp.data.repository.QuoteRepository
import com.catnip.quoteapp.ui.viewparam.Quote
import com.catnip.quoteapp.ui.viewparam.mapToViewParam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class GetRandomQuoteUseCase(
    val repository: QuoteRepository,
    val dispatcher: CoroutineDispatcher
) : BaseUseCase<Any, Quote>(dispatcher) {

    override suspend fun execute(param: Any?): Flow<ViewResource<Quote>> {
        return repository.getRandomQuote(dispatcher).map {
            when (it) {
                is DataResource.Success -> {
                    ViewResource.Success(it.data.mapToViewParam())
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