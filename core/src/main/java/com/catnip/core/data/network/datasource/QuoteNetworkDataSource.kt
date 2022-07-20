package com.catnip.core.data.network.datasource

import com.catnip.core.data.network.QuoteApiService
import com.catnip.core.data.network.model.response.QuoteResponse

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class QuoteNetworkDataSourceImpl(val apiService: QuoteApiService) : QuoteNetworkDataSource {
    override suspend fun getRandomQuote(): QuoteResponse = apiService.getRandomQuotes()
}

interface QuoteNetworkDataSource {
    suspend fun getRandomQuote(): QuoteResponse
}
