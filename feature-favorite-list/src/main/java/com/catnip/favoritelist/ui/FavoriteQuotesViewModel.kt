package com.catnip.favoritelist.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.core.base.wrapper.ViewResource
import com.catnip.core.common.domain.DeleteFavoriteQuoteUseCase
import com.catnip.core.common.viewparam.Quote
import com.catnip.favoritelist.domain.GetFavoriteQuotesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class FavoriteQuotesViewModel(
    val getFavoriteQuotesUseCase: GetFavoriteQuotesUseCase,
    val deleteFavoriteQuoteUseCase: DeleteFavoriteQuoteUseCase
) : ViewModel() {

    val quoteResult = MutableLiveData<ViewResource<MutableList<Quote>>>()

    val removeFavoriteResult = MutableLiveData<ViewResource<Pair<Quote?, Int>>>()

    fun getFavoriteQuotes() {
        viewModelScope.launch {
            getFavoriteQuotesUseCase().collect {
                delay(300)
                quoteResult.value = it
            }
        }
    }

    fun deleteFavoriteQuote(quote: Quote, position: Int) {
        viewModelScope.launch {
            deleteFavoriteQuoteUseCase(DeleteFavoriteQuoteUseCase.Param(quote,position)).collect {
                removeFavoriteResult.value = it
            }
        }
    }
}