package com.catnip.quoteapp.data.repository

import com.catnip.quoteapp.base.arch.BaseContract
import com.catnip.quoteapp.base.arch.BaseRepositoryImpl
import com.catnip.quoteapp.base.wrapper.DataResource
import com.catnip.quoteapp.data.local.datasource.QuoteLocalDataSource
import com.catnip.quoteapp.data.local.entity.QuoteEntity
import com.catnip.quoteapp.data.network.datasource.QuoteNetworkDataSource
import com.catnip.quoteapp.data.network.model.response.QuoteResponse
import kotlinx.coroutines.CoroutineDispatcher
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

    override fun getRandomQuote(dispatcher: CoroutineDispatcher): Flow<DataResource<QuoteResponse>> =
        flow {
            emit(DataResource.Loading())
            emit(safeNetworkCall { networkDataSource.getRandomQuote() })
        }.flowOn(dispatcher)

    override fun getFavoriteQuotes(dispatcher: CoroutineDispatcher): Flow<DataResource<List<QuoteEntity>>> =
        flow {
            emit(DataResource.Loading())
            emit(proceed { localDataSource.getFavoriteQuotes() })
        }.flowOn(dispatcher)

    override fun addFavoriteQuote(
        dispatcher: CoroutineDispatcher,
        entity: QuoteEntity
    ): Flow<DataResource<Long>> =
        flow {
            emit(DataResource.Loading())
            emit(proceed { localDataSource.addFavorite(entity) })
        }.flowOn(dispatcher)

    override fun deleteFavoriteQuote(
        dispatcher: CoroutineDispatcher,
        entity: QuoteEntity
    ): Flow<DataResource<Int>> =
        flow {
            emit(DataResource.Loading())
            emit(proceed { localDataSource.deleteFavorite(entity) })
        }.flowOn(dispatcher)


}

interface QuoteRepository : BaseContract.BaseRepository {
    fun getRandomQuote(dispatcher: CoroutineDispatcher): Flow<DataResource<QuoteResponse>>
    fun getFavoriteQuotes(dispatcher: CoroutineDispatcher): Flow<DataResource<List<QuoteEntity>>>
    fun addFavoriteQuote(
        dispatcher: CoroutineDispatcher,
        entity: QuoteEntity
    ): Flow<DataResource<Long>>

    fun deleteFavoriteQuote(
        dispatcher: CoroutineDispatcher,
        entity: QuoteEntity
    ): Flow<DataResource<Int>>
}