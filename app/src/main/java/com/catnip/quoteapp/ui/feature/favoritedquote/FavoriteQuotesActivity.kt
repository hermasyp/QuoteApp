package com.catnip.quoteapp.ui.feature.favoritedquote

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.catnip.quoteapp.base.arch.BaseActivity
import com.catnip.quoteapp.R
import com.catnip.quoteapp.base.wrapper.ViewResource
import com.catnip.quoteapp.databinding.ActivityFavoriteQuotesBinding
import com.catnip.quoteapp.ui.viewparam.Quote
import com.catnip.quoteapp.utils.recyclerview.RemoveItemTouchHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception


class FavoriteQuotesActivity : BaseActivity<ActivityFavoriteQuotesBinding, FavoriteQuotesViewModel>(
    ActivityFavoriteQuotesBinding::inflate
) {

    override val viewModel: FavoriteQuotesViewModel by viewModel()

    private lateinit var adapter: FavoriteQuotesAdapter


    override fun observeData() {
        super.observeData()
        viewModel.quoteResult.observe(this) {
            handleData(it)
        }
        viewModel.removeFavoriteResult.observe(this) {
            when (it) {
                is ViewResource.Success -> {
                    adapter.removeItem(it.data?.second ?: 0)
                    viewModel.getFavoriteQuotes()
                }
                is ViewResource.Error -> {
                    adapter.notifyItemChanged(it.data?.second ?: 0)
                }
                else -> {
                    //do nothing
                }
            }
        }
    }

    override fun showEmptyData(isEmpty: Boolean) {
        binding.tvErrorLayout.text = getString(R.string.text_empty_list_favorite)
        binding.tvErrorLayout.isVisible = isEmpty
    }

    override fun showLoading(isShowLoading: Boolean) {
        binding.pbLoading.isVisible = isShowLoading
    }

    override fun showContent(isContentVisible: Boolean) {
        binding.rvFavoriteQuotes.isVisible = isContentVisible
    }

    override fun showError(isErrorEnabled: Boolean, exception: Exception?) {
        binding.tvErrorLayout.isVisible = isErrorEnabled
        if (isErrorEnabled) {
            binding.tvErrorLayout.text = getErrorMessageByException(exception)
        }
    }

    override fun <T> showData(data: T) {
        super.showData(data)
        if (data is MutableList<*>) {
            adapter.setItems(data as List<Quote>)
        }
    }

    override fun initView() {
        enableHomeAsBack()
        initList()
        viewModel.getFavoriteQuotes()
    }


    private fun initList() {
        adapter = FavoriteQuotesAdapter {
            //on item clicked
        }
        binding.rvFavoriteQuotes.apply {
            this.adapter = this@FavoriteQuotesActivity.adapter
            this.addItemDecoration(
                DividerItemDecoration(
                    this@FavoriteQuotesActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        ItemTouchHelper(RemoveItemTouchHelper(this) { position ->
            //on Item Removed
            val item = adapter.getItems()[position]
            viewModel.deleteFavoriteQuote(item, position)
        }).apply {
            attachToRecyclerView(binding.rvFavoriteQuotes)
        }

    }
}