package com.catnip.quoteapp.ui.feature.quotepreview

import androidx.activity.viewModels
import com.catnip.coingeckoapp.base.arch.BaseActivity
import com.catnip.quoteapp.databinding.ActivityQuotePreviewBinding

class QuotePreviewActivity : BaseActivity<ActivityQuotePreviewBinding, QuotePreviewViewModel>(
    ActivityQuotePreviewBinding::inflate
) {

    override val viewModel: QuotePreviewViewModel by viewModels()

    override fun initView() {

    }

}