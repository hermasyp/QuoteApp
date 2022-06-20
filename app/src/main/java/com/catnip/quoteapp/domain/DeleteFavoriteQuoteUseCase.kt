package com.catnip.quoteapp.domain

import android.view.View
import com.catnip.quoteapp.base.arch.BaseUseCase
import com.catnip.quoteapp.base.exception.DatabaseExecutionFailedException
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
class DeleteFavoriteQuoteUseCase(
    val repository: QuoteRepository,
    val dispatcher: CoroutineDispatcher
) : BaseUseCase<Quote, Quote?>(dispatcher) {

    override suspend fun execute(param: Quote?): Flow<ViewResource<Quote?>> {
        return repository.deleteFavoriteQuote(dispatcher, param.toEntity()).map {
            when (it) {
                is DataResource.Success -> {
                    ViewResource.Success(param?.apply { isFavorite = false })
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