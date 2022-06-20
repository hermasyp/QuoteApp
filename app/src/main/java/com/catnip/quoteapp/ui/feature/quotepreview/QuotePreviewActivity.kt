package com.catnip.quoteapp.ui.feature.quotepreview

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.catnip.coingeckoapp.base.arch.BaseActivity
import com.catnip.quoteapp.base.wrapper.ViewResource
import com.catnip.quoteapp.databinding.ActivityQuotePreviewBinding
import com.catnip.quoteapp.ui.viewparam.Quote
import com.catnip.quoteapp.utils.LogUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuotePreviewActivity : BaseActivity<ActivityQuotePreviewBinding, QuotePreviewViewModel>(
    ActivityQuotePreviewBinding::inflate
) {

    override val viewModel: QuotePreviewViewModel by viewModel()
    lateinit var quote: Quote

    override fun initView() {
        setClickListeners()
        viewModel.getRandomQuote()
    }

    private fun setClickListeners() {
        binding.btnRefreshQuotes.setOnClickListener { viewModel.getRandomQuote() }
        binding.btnFavoriteQuotes.setOnClickListener { viewModel.addFavoriteQuote(quote) }
    }

    override fun observeData() {
        viewModel.quoteResult.observe(this) {
            handleData(it)
        }
        viewModel.addFavoriteResult.observe(this) {
            when (it) {
                is ViewResource.Success -> {
                    Toast.makeText(this, "Add Favorite Success", Toast.LENGTH_SHORT).show()
                }
                is ViewResource.Error -> {
                    LogUtils.LogE(it.exception?.message)
                    Toast.makeText(this, "Add Favorite Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.removeFavoriteResult.observe(this) {
            when (it) {
                is ViewResource.Success -> {
                    Toast.makeText(this, "Remove Favorite Success", Toast.LENGTH_SHORT).show()
                }
                is ViewResource.Error -> {
                    LogUtils.LogE(it.exception?.message)
                    Toast.makeText(this, "Remove Favorite Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun showLoading(isShowLoading: Boolean) {
        binding.btnFavoriteQuotes.isEnabled = !isShowLoading
        binding.pbLoading.isVisible = isShowLoading
    }

    override fun showContent(isContentVisible: Boolean) {
        binding.btnFavoriteQuotes.isEnabled = isContentVisible
        binding.groupQuotes.isVisible = isContentVisible
    }

    override fun <T> showData(data: T) {
        if (data is Quote) {
            this.quote = data
            if(data.isFavorite) Toast.makeText(this, "FAVORITED", Toast.LENGTH_SHORT).show()
            binding.tvQuotesText.text = data.content
            binding.tvAuthor.text = data.author
        }
    }
}