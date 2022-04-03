package com.app.taghive.repository

import com.app.taghive.network.RetrofitInstance

class AppRepository() {

    suspend fun getCryptos() = RetrofitInstance.cryptoApi.getCryptos()

    suspend fun getCryptoDetails(symbol:String) = RetrofitInstance.cryptoApi.getCryptoDetails(symbol)

}