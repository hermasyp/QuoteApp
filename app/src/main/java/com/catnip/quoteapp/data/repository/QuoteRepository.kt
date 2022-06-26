package com.catnip.quoteapp.data.repository

import com.catnip.quoteapp.base.arch.BaseContract
import com.catnip.quoteapp.base.arch.BaseRepositoryImpl
import com.catnip.quoteapp.base.exception.DatabaseExecutionFailedException
import com.catnip.quoteapp.base.wrapper.DataResource
import com.catnip.quoteapp.data.local.datasource.QuoteLocalDataSource
import com.catnip.quoteapp.data.local.entity.QuoteEntity
import com.catnip.quoteapp.data.network.datasource.QuoteNetworkDataSource
import com.catnip.quoteapp.data.network.model.response.QuoteResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class QuoteRepositoryImpl(
    val localDataSource: QuoteLocalDataSource,
    val networkDataSource: QuoteNetworkDataSource
) : BaseRepositoryImpl(), QuoteRepository {

    override fun getRandomQuote(dispatcher: CoroutineDispatcher): Flow<DataResource<QuoteResponse>> =
        flow {
            emit(safeNetworkCall { networkDataSource.getRandomQuote() })
        }.flowOn(dispatcher)

    override fun getFavoriteQuotes(dispatcher: CoroutineDispatcher): Flow<DataResource<List<QuoteEntity>>> =
        flow {
            emit(proceed { localDataSource.getFavoriteQuotes() })
        }.flowOn(dispatcher)

    override fun addFavoriteQuote(
        dispatcher: CoroutineDispatcher,
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
        }.flowOn(dispatcher)

    override suspend fun getFavoriteQuotesById(
        dispatcher: CoroutineDispatcher,
        id: String?
    ): Flow<DataResource<QuoteEntity?>> =
        flow {
            emit(proceed { localDataSource.getFavoriteQuotesById(id) })
        }.flowOn(dispatcher)

    override fun deleteFavoriteQuote(
        dispatcher: CoroutineDispatcher,
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
            .flowOn(dispatcher)

}

interface QuoteRepository : BaseContract.BaseRepository {
    fun getRandomQuote(dispatcher: CoroutineDispatcher): Flow<DataResource<QuoteResponse>>
    fun getFavoriteQuotes(dispatcher: CoroutineDispatcher): Flow<DataResource<List<QuoteEntity>>>
    fun addFavoriteQuote(
        dispatcher: CoroutineDispatcher,
        entity: QuoteEntity
    ): Flow<DataResource<Long>>

    suspend fun getFavoriteQuotesById(
        dispatcher: CoroutineDispatcher,
        id: String?
    ): Flow<DataResource<QuoteEntity?>>

    fun deleteFavoriteQuote(
        dispatcher: CoroutineDispatcher,
        entity: QuoteEntity
    ): Flow<DataResource<Int>>
}