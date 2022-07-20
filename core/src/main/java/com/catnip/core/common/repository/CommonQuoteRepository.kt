package com.catnip.core.common.repository

import com.catnip.core.base.arch.BaseContract
import com.catnip.core.base.arch.BaseRepositoryImpl
import com.catnip.core.base.exception.DatabaseExecutionFailedException
import com.catnip.core.base.wrapper.DataResource
import com.catnip.core.data.local.datasource.QuoteLocalDataSource
import com.catnip.core.data.local.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

open class CommonQuoteRepositoryImpl(
    val localDataSource: QuoteLocalDataSource,
) : BaseRepositoryImpl(), CommonQuoteRepository {
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

interface CommonQuoteRepository : BaseContract.BaseRepository {
    suspend fun deleteFavoriteQuote(
        entity: QuoteEntity
    ): Flow<DataResource<Int>>
}