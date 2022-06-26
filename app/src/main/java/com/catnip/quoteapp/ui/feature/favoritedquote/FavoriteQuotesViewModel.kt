package com.catnip.quoteapp.ui.feature.favoritedquote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.quoteapp.base.wrapper.ViewResource
import com.catnip.quoteapp.domain.AddFavoriteQuoteUseCase
import com.catnip.quoteapp.domain.DeleteFavoriteQuoteUseCase
import com.catnip.quoteapp.domain.GetFavoriteQuotesUseCase
import com.catnip.quoteapp.ui.viewparam.Quote
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