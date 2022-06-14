package com.catnip.quoteapp.ui.feature.favoritedquote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.catnip.coingeckoapp.base.arch.BaseActivity
import com.catnip.quoteapp.R
import com.catnip.quoteapp.databinding.ActivityFavoriteQuotesBinding

class FavoriteQuotesActivity : BaseActivity<ActivityFavoriteQuotesBinding, FavoriteQuotesViewModel>(
    ActivityFavoriteQuotesBinding::inflate
) {

    override val viewModel: FavoriteQuotesViewModel by viewModels()

    override fun initView() {

    }

}