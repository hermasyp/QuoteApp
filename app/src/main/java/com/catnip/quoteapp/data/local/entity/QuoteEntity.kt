package com.catnip.quoteapp.data.local.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val author: String?,
    @ColumnInfo
    val authorSlug: String?,
    @ColumnInfo
    val content: String?,
    @ColumnInfo
    val dateAdded: String?,
    @ColumnInfo
    val dateModified: String?,
    @ColumnInfo
    val length: Int?
)