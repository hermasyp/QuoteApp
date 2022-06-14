package com.catnip.quoteapp.data.network.model.response


import com.google.gson.annotations.SerializedName

data class QuoteResponse(
    @SerializedName("author")
    val author: String?,
    @SerializedName("authorSlug")
    val authorSlug: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("dateAdded")
    val dateAdded: String?,
    @SerializedName("dateModified")
    val dateModified: String?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("length")
    val length: Int?,
    @SerializedName("tags")
    val tags: List<String?>?
)