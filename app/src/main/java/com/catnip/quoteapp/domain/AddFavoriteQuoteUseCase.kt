package com.catnip.quoteapp.domain

import com.catnip.quoteapp.base.arch.BaseUseCase
import com.catnip.quoteapp.base.wrapper.DataResource
import com.catnip.quoteapp.base.wrapper.ViewResource
import com.catnip.quoteapp.data.repository.QuoteRepository
import com.catnip.quoteapp.ui.viewparam.Quote
import com.catnip.quoteapp.ui.viewparam.mapToViewParams
import com.catnip.quoteapp.ui.viewparam.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class AddFavoriteQuoteUseCase(
    val repository: QuoteRepository,
    val dispatcher: CoroutineDispatcher
) : BaseUseCase<Quote, Number?>(dispatcher) {

    override suspend fun execute(param: Quote?): Flow<ViewResource<Number?>> {
        return repository.addFavoriteQuote(dispatcher, param.toEntity()).map {
            when (it) {
                is DataResource.Success -> {
                    ViewResource.Success(it.data)
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