package com.catnip.quoteapp.data.local.dao

import androidx.room.*
import com.catnip.quoteapp.data.local.entity.QuoteEntity

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Dao
interface QuotesDao {
    @Query("SELECT * FROM quotes")
    suspend fun getFavoriteQuotes(): List<QuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteQuote(note: QuoteEntity): Long

    @Delete
    suspend fun removeFavoriteQuote(note: QuoteEntity): Int
}