package com.catnip.quoteapp.ui.feature.favoritedquote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.quoteapp.base.wrapper.ViewResource
import com.catnip.quoteapp.domain.AddFavoriteQuoteUseCase
import com.catnip.quoteapp.domain.DeleteFavoriteQuoteUseCase
import com.catnip.quoteapp.domain.GetFavoriteQuotesUseCase
import com.catnip.quoteapp.ui.viewparam.Quote
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class FavoriteQuotesViewModel(
    val getFavoriteQuotesUseCase: GetFavoriteQuotesUseCase,
    val addFavoriteQuoteUseCase: AddFavoriteQuoteUseCase,
    val deleteFavoriteQuoteUseCase: DeleteFavoriteQuoteUseCase
) : ViewModel() {

    val quoteResult = MutableLiveData<ViewResource<List<Quote>>>()

    val addFavoriteResult = MutableLiveData<ViewResource<Number?>>()

    val removeFavoriteResult = MutableLiveData<ViewResource<Number?>>()

    fun getRandomQuote() {
        viewModelScope.launch {
            getFavoriteQuotesUseCase().collect {
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
            deleteFavoriteQuoteUseCase(quote).collect {
                removeFavoriteResult.value = it
            }
        }
    }
}