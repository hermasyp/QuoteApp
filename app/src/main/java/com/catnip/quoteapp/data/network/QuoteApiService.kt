package com.catnip.quoteapp.data.network

import com.catnip.quoteapp.BuildConfig
import com.catnip.quoteapp.data.network.model.response.QuoteResponse
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface QuoteApiService {

    @GET("random")
    suspend fun getRandomQuotes(): QuoteResponse

    companion object {
        @JvmStatic
        operator fun invoke(chuckerInterceptor: ChuckerInterceptor): QuoteApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chuckerInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(QuoteApiService::class.java)
        }
    }
}