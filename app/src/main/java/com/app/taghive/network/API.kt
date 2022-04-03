package com.app.taghive.network


import com.app.taghive.model.CryptoResponse
import com.app.taghive.model.GetCryptoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("tickers/24hr")
    suspend fun getCryptos(): Response<CryptoResponse>

    @GET("ticker/24hr")
    suspend fun getCryptoDetails(@Query("symbol") symbol: String): Response<GetCryptoResponse>

}