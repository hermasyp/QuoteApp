package com.catnip.quoteapp.data.repository

import com.catnip.quoteapp.base.arch.BaseContract
import com.catnip.quoteapp.base.arch.BaseRepositoryImpl
import com.catnip.quoteapp.base.exception.DatabaseExecutionFailedException
import com.catnip.quoteapp.base.wrapper.DataResource
import com.catnip.quoteapp.data.local.datasource.QuoteLocalDataSource
import com.catnip.quoteapp.data.local.entity.QuoteEntity
import com.catnip.quoteapp.data.network.datasource.QuoteNetworkDataSource
import com.catnip.quoteapp.data.network.model.response.QuoteResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class QuoteRepositoryImpl(
    val localDataSource: QuoteLocalDataSource,
    val networkDataSource: QuoteNetworkDataSource
) : BaseRepositoryImpl(), QuoteRepository {

    override suspend fun getRandomQuote(): Flow<DataResource<QuoteResponse>> =
        flow {
            emit(safeNetworkCall { networkDataSource.getRandomQuote() })
        }

    override suspend fun getFavoriteQuotes(): Flow<DataResource<List<QuoteEntity>>> =
        flow {
            emit(proceed { localDataSource.getFavoriteQuotes() })
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

    override suspend fun deleteFavoriteQuote(
        entity: QuoteEntity
    ): Flow<DataResource<Int>> =
        flow {
            emit(
                try {
                    val totalRowsAffected = localDataSource.deleteFavorite(entity)
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

}

interface QuoteRepository : BaseContract.BaseRepository {
    suspend fun getRandomQuote():
            Flow<DataResource<QuoteResponse>>

    suspend fun getFavoriteQuotes():
            Flow<DataResource<List<QuoteEntity>>>

    suspend fun addFavoriteQuote(
        entity: QuoteEntity
    ): Flow<DataResource<Long>>

    suspend fun getFavoriteQuotesById(
        id: String?
    ): Flow<DataResource<QuoteEntity?>>

    suspend fun deleteFavoriteQuote(
        entity: QuoteEntity
    ): Flow<DataResource<Int>>
}