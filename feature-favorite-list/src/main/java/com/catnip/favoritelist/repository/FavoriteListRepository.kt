package com.catnip.favoritelist.repository

import com.catnip.core.base.wrapper.DataResource
import com.catnip.core.common.repository.CommonQuoteRepository
import com.catnip.core.common.repository.CommonQuoteRepositoryImpl
import com.catnip.core.data.local.datasource.QuoteLocalDataSource
import com.catnip.core.data.local.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class FavoriteListRepositoryImpl(
    localDataSource: QuoteLocalDataSource,
) : CommonQuoteRepositoryImpl(localDataSource), FavoriteListRepository {

    override suspend fun getFavoriteQuotes(): Flow<DataResource<List<QuoteEntity>>> =
        flow {
            emit(proceed { localDataSource.getFavoriteQuotes() })
        }
}

interface FavoriteListRepository : CommonQuoteRepository {
    suspend fun getFavoriteQuotes(): Flow<DataResource<List<QuoteEntity>>>
}