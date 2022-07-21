package com.catnip.quotepreview.repository

import com.catnip.core.base.exception.DatabaseExecutionFailedException
import com.catnip.core.base.wrapper.DataResource
import com.catnip.core.common.repository.CommonQuoteRepository
import com.catnip.core.common.repository.CommonQuoteRepositoryImpl
import com.catnip.core.data.local.datasource.QuoteLocalDataSource
import com.catnip.core.data.local.entity.QuoteEntity
import com.catnip.core.data.network.datasource.QuoteNetworkDataSource
import com.catnip.core.data.network.model.response.QuoteResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class PreviewQuoteRepositoryImpl(
    localDataSource: QuoteLocalDataSource,
    private val networkDataSource: QuoteNetworkDataSource
) : CommonQuoteRepositoryImpl(localDataSource), PreviewQuoteRepository {
    override suspend fun getRandomQuote(): Flow<DataResource<QuoteResponse>> =
        flow {
            emit(safeNetworkCall { networkDataSource.getRandomQuote() })
        }

    override suspend fun addFavoriteQuote(
        entity: QuoteEntity
    ): Flow<DataResource<Long>> =
        flow {
            emit(
                try {
                    val totalRowsAffected = localDataSource.addFavorite(entity)
                    if (totalRowsAffected > 0) {
                        DataResource.Success(totalRowsAffected)
                    } else {
                        DataResource.Error(DatabaseExecutionFailedException())
                    }
                } catch (exception: Exception) {
                    DataResource.Error(exception)
                }
            )
        }

    override suspend fun getFavoriteQuotesById(
        id: String?
    ): Flow<DataResource<QuoteEntity?>> =
        flow {
            emit(proceed { localDataSource.getFavoriteQuotesById(id) })
        }
}

interface PreviewQuoteRepository : CommonQuoteRepository {
    suspend fun getRandomQuote():
            Flow<DataResource<QuoteResponse>>

    suspend fun addFavoriteQuote(
        entity: QuoteEntity
    ): Flow<DataResource<Long>>

    suspend fun getFavoriteQuotesById(id: String?): Flow<DataResource<QuoteEntity?>>
}