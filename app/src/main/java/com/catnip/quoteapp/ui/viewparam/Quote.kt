package com.catnip.quoteapp.ui.viewparam

import android.os.Parcelable
import com.catnip.quoteapp.data.local.entity.QuoteEntity
import com.catnip.quoteapp.data.network.model.response.QuoteResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Parcelize
data class Quote(
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val id: String,
    val length: Int,
    var isFavorite: Boolean = false
) : Parcelable

fun Quote?.toEntity() = QuoteEntity(
    id = this?.id.orEmpty(),
    author = this?.author,
    authorSlug = this?.authorSlug,
    content = this?.content,
    dateAdded = this?.dateAdded,
    dateModified = this?.dateModified,
    length = this?.length,
    isFavorite = this?.isFavorite,
)

fun QuoteEntity?.mapToViewParam() = Quote(
    id = this?.id.orEmpty(),
    author = this?.author.orEmpty(),
    authorSlug = this?.authorSlug.orEmpty(),
    content = this?.content.orEmpty(),
    dateAdded = this?.dateAdded.orEmpty(),
    dateModified = this?.dateModified.orEmpty(),
    length = this?.length ?: 0,
    isFavorite = this?.isFavorite ?: false
)

fun List<QuoteEntity>?.mapToViewParams() = mutableListOf<Quote>().apply {
    addAll(this@mapToViewParams?.map {
        it.mapToViewParam()
    } ?: listOf())
}


fun QuoteResponse?.mapToViewParam() = Quote(
    id = this?.id.orEmpty(),
    author = this?.author.orEmpty(),
    authorSlug = this?.authorSlug.orEmpty(),
    content = this?.content.orEmpty(),
    dateAdded = this?.dateAdded.orEmpty(),
    dateModified = this?.dateModified.orEmpty(),
    length = this?.length ?: 0,
)