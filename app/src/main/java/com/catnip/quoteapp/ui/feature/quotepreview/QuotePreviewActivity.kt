package com.catnip.quoteapp.ui.feature.quotepreview

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.catnip.quoteapp.R
import com.catnip.quoteapp.base.arch.BaseActivity
import com.catnip.quoteapp.base.wrapper.ViewResource
import com.catnip.quoteapp.databinding.ActivityQuotePreviewBinding
import com.catnip.quoteapp.ui.feature.favoritedquote.FavoriteQuotesActivity
import com.catnip.quoteapp.ui.viewparam.Quote
import com.catnip.quoteapp.utils.IntentUtils
import com.catnip.quoteapp.utils.bitmapToCacheUri
import com.catnip.quoteapp.utils.createRoundedBackground
import com.catnip.quoteapp.utils.toImageBitmap
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import org.koin.androidx.viewmodel.ext.android.viewModel


class QuotePreviewActivity : BaseActivity<ActivityQuotePreviewBinding, QuotePreviewViewModel>(
    ActivityQuotePreviewBinding::inflate
) {

    override val viewModel: QuotePreviewViewModel by viewModel()

    private var favoriteActivityResult =
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent: Intent? = result.data
                val quote =
                    intent?.extras?.getParcelable<Quote>(FavoriteQuotesActivity.EXTRAS_QUOTE)
                showData(quote)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableHomeAsBack()
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        enableHomeAsBack()
        setClickListeners()
        viewModel.getRandomQuote()
    }

    private fun setClickListeners() {
        binding.ivFavoriteList.setOnClickListener {
            favoriteActivityResult.launch(Intent(this, FavoriteQuotesActivity::class.java))
        }
        binding.btnShareQuotes.setOnClickListener {
            val bitmap = binding.clQuoteContainer.toImageBitmap()
            val bitmapUri = bitmap.bitmapToCacheUri(this)
            bitmapUri?.let { uri -> IntentUtils.shareImage(this, uri) }
        }
        binding.btnRefreshQuotes.setOnClickListener { viewModel.getRandomQuote() }
        binding.btnFavoriteQuotes.setOnClickListener {
            viewModel.currentQuote?.let { quote ->
                if (quote.isFavorite) {
                    viewModel.deleteFavoriteQuote(quote)
                } else {
                    viewModel.addFavoriteQuote(quote)
                }
            }
        }
        binding.clQuoteContainer.setOnClickListener {
            showColorPickerDialog()
        }
    }

    override fun observeData() {
        viewModel.quoteResult.observe(this) {
            handleData(it)
        }
        viewModel.addFavoriteResult.observe(this) {
            when (it) {
                is ViewResource.Success -> {
                    showData(it.data)
                }
                is ViewResource.Error -> {
                    Toast.makeText(this, getString(R.string.text_error_add_favorite), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    return@observe
                }
            }
        }
        viewModel.removeFavoriteResult.observe(this) {
            when (it) {
                is ViewResource.Success -> {
                    showData(it.data?.first)
                }
                is ViewResource.Error -> {
                    Toast.makeText(this, getString(R.string.text_error_remove_favorite), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    return@observe
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

    private fun showColorPickerDialog() {
        MaterialColorPickerDialog
            .Builder(this)
            .setTitle(getString(R.string.text_color_picker_title))
            .setColorShape(ColorShape.SQAURE)
            .setColorSwatch(ColorSwatch._300)
            .setDefaultColor(viewModel.cardColor)
            .setColorListener { _, colorHex ->
                viewModel.cardColor = colorHex
                setBackgroundCardColor(viewModel.cardColor)
            }
            .show()
    }


    override fun <T> showData(data: T) {
        if (data is Quote) {
            viewModel.currentQuote = data
            binding.btnFavoriteQuotes.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    if (data.isFavorite) R.drawable.ic_favorite_true else R.drawable.ic_favorite_false
                )
            )
            binding.tvQuotesText.text = data.content
            binding.tvAuthor.text = data.author
            setBackgroundCardColor(viewModel.cardColor)
        }
    }

    private fun setBackgroundCardColor(hexColor: String) {
        binding.clQuoteContainer.background =
            GradientDrawable().createRoundedBackground(this, hexColor, 16)
    }
}