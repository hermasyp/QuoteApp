package com.catnip.favoritelist.ui

import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.catnip.core.base.arch.BaseActivity
import com.catnip.core.base.wrapper.ViewResource
import com.catnip.core.common.viewparam.Quote
import com.catnip.core.utils.recyclerview.RemoveItemTouchHelper
import com.catnip.favoritelist.R
import com.catnip.favoritelist.databinding.ActivityFavoriteQuotesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteQuotesActivity : BaseActivity<ActivityFavoriteQuotesBinding, FavoriteQuotesViewModel>(
    ActivityFavoriteQuotesBinding::inflate
) {

    override val viewModel: FavoriteQuotesViewModel by viewModel()

    private lateinit var adapter: FavoriteQuotesAdapter

    companion object {
        const val EXTRAS_QUOTE = "EXTRAS_QUOTE"
    }


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
            setResult(RESULT_OK, intent.putExtra(EXTRAS_QUOTE, it))
            finish()
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