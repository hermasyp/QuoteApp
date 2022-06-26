package com.catnip.quoteapp.ui.feature.favoritedquote;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.catnip.quoteapp.databinding.ItemQuoteBinding
import com.catnip.quoteapp.ui.viewparam.Quote

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class FavoriteQuotesAdapter(
    private val itemClick: (Quote) -> Unit
) :
    RecyclerView.Adapter<FavoriteQuotesAdapter.QuoteViewHolder>() {

    private var items: MutableList<Quote> = mutableListOf()

    fun getItems() = items

    fun setItems(items: List<Quote>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Quote>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem (position: Int){
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }
    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class QuoteViewHolder(
        private val binding: ItemQuoteBinding,
        val itemClick: (Quote) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Quote) {
            with(item) {
                binding.tvQuotesAuthor.text = this.author
                binding.tvQuotesText.text = this.content
                itemView.setOnClickListener { itemClick(this) }
            }

        }
    }

}