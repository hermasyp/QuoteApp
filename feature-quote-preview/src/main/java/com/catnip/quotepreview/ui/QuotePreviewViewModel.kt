package com.catnip.quotepreview.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.core.base.wrapper.ViewResource
import com.catnip.core.common.domain.DeleteFavoriteQuoteUseCase
import com.catnip.core.common.viewparam.Quote
import com.catnip.quotepreview.domain.AddFavoriteQuoteUseCase
import com.catnip.quotepreview.domain.GetRandomQuoteUseCase
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class QuotePreviewViewModel(
    val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    val addFavoriteQuoteUseCase: AddFavoriteQuoteUseCase,
    val deleteFavoriteQuoteUseCase: DeleteFavoriteQuoteUseCase
) : ViewModel() {

    val quoteResult = MutableLiveData<ViewResource<Quote>>()

    val addFavoriteResult = MutableLiveData<ViewResource<Quote?>>()

    val removeFavoriteResult = MutableLiveData<ViewResource<Pair<Quote?, Int>>>()

    var cardColor = "#546E7A"

    var currentQuote : Quote? = null

    fun getRandomQuote() {
        viewModelScope.launch {
            getRandomQuoteUseCase().collect {
                quoteResult.value = it
            }
        }
    }

    fun addFavoriteQuote(quote: Quote) {
        viewModelScope.launch {
            addFavoriteQuoteUseCase(quote).collect {
                addFavoriteResult.value = it
            }
        }
    }

    fun deleteFavoriteQuote(quote: Quote) {
        viewModelScope.launch {
            deleteFavoriteQuoteUseCase(DeleteFavoriteQuoteUseCase.Param(quote,0)).collect {
                removeFavoriteResult.value = it
            }
        }
    }
}