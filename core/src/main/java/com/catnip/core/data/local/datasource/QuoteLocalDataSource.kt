package com.catnip.core.data.local.datasource

import com.catnip.core.data.local.dao.QuotesDao
import com.catnip.core.data.local.entity.QuoteEntity

class QuoteLocalDataSourceImpl(val quotesDao: QuotesDao) : QuoteLocalDataSource {
    override suspend fun getFavoriteQuotes(): List<QuoteEntity> = quotesDao.getFavoriteQuotes()

    override suspend fun getFavoriteQuotesById(id: String?): QuoteEntity? = quotesDao.getFavoriteQuotesByID(id)

    override suspend fun addFavorite(quoteEntity: QuoteEntity): Long =
        quotesDao.addFavoriteQuote(quoteEntity)

    override suspend fun deleteFavorite(quoteEntity: QuoteEntity): Int =
        quotesDao.removeFavoriteQuote(quoteEntity)
}

interface QuoteLocalDataSource {
    suspend fun getFavoriteQuotes(): List<QuoteEntity>
    suspend fun getFavoriteQuotesById(id : String?): QuoteEntity?
    suspend fun addFavorite(quoteEntity: QuoteEntity): Long
    suspend fun deleteFavorite(quoteEntity: QuoteEntity): Int
}
